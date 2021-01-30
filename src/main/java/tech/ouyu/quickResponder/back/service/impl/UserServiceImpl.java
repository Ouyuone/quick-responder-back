package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.entity.Storage;
import tech.ouyu.quickResponder.back.entity.User;
import tech.ouyu.quickResponder.back.exception.MessageException;
import tech.ouyu.quickResponder.back.mapper.UserMapper;
import tech.ouyu.quickResponder.back.service.StorageService;
import tech.ouyu.quickResponder.back.service.UserService;
import tech.ouyu.quickResponder.back.util.*;
import tech.ouyu.quickResponder.back.vo.MpUserInfo;
import tech.ouyu.quickResponder.back.vo.UserBean;
import tech.ouyu.quickResponder.back.vo.UserClaim;
import tech.ouyu.quickResponder.back.vo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.util.Calendar;
import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 09:19
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    final JwtUtil jwtUtil;

    final RedisManager redisManager;

    final StorageService storageService;

    @Override
    public String doLogin(UserBean userBean) {
        //从session中获取验证码
        String captcha =  redisManager.getKey(Constants.KAPTCHA_SESSION_KEY);
        if(StringUtils.isBlank(userBean.getCaptcha())){
            log.error("请输入验证码！");
            throw new MessageException("请输入验证码！");
        }else if(!userBean.getCaptcha().equals(captcha)){
            log.error("验证码不正确！");
            throw new MessageException("验证码不正确！");
        }
        User user;
        //此字段可同时接受用户名、手机号和学号登录
        if (StringUtils.isNotBlank(userBean.getCommonNumber())) {
            user = getOne(new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, userBean.getCommonNumber())
                    .or()
                    .eq(User::getPhone, userBean.getCommonNumber())
                    .or((s) -> s.eq(User::getStudentNumber, userBean.getCommonNumber())));
        } else if (StringUtils.isNotBlank(userBean.getPhone())) {
            user = phoneLogin(userBean);
        } else if (StringUtils.isNotBlank(userBean.getUsername())) {
            user = usernameLogin(userBean);
        } else {
            log.error("用户名、手机号、学号不能为空");
            throw new MessageException("用户名、手机号、学号不能为空");
        }
        if (user == null) {
            log.error("登录名或密码错误！");
            throw new MessageException("登录名或密码错误！");
        }
        String salt = user.getSalt();
        String passwordNew = userBean.getPassword();
        String encryptionPwd = EncryptionUtil.encryptionMd5(passwordNew, salt);
        if (!encryptionPwd.equals(user.getPassword())) {
            log.error("密码不正确，请重新输入");
            throw new MessageException("密码不正确，请重新输入");
        }
        UserClaim userClaim = new UserClaim(user);
        return jwtUtil.generateToken(userClaim);
    }

    private User usernameLogin(UserBean userBean) {
        User user;
        String username = userBean.getUsername();
        user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return user;
    }

    private User phoneLogin(UserBean userBean) {
        User user;
        String phone = userBean.getPhone();
        user = getOne(new QueryWrapper<User>().eq("phone", phone));
        return user;
    }

    /**
     * 注册
     *
     * @author ouyu
     */
    @Override
    public String doRegister(UserBean userBean) {
        String phone = userBean.getPhone();
        String username = userBean.getUsername();
        String password = userBean.getPassword();
        if (StringUtils.isAnyBlank(phone, username, password)) {
            log.error("请填写完整的注册信息");
            throw new MessageException("请填写完整的注册信息");
        }
        List<User> users =getBaseMapper().selectList(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
                .or()
                .eq(User::getPhone, phone));
        //判断注册信息是否已经存在
        long count = users.stream()
                .filter((user) -> !user.getUsername().equals(username))
                .filter(user -> !user.getPhone().equals(phone))
                .count();
        if (users.size() != count) {
            log.error("手机号或用户名已被注册");
            throw new MessageException("手机号或用户名已被别人注册,请重新注册");
        }
        String salt = AES.generateRandomKey();
        String passwordMd5 = EncryptionUtil.encryptionMd5(password.replaceAll(" ",""), salt);
        User user =User.builder()
                .username(username)
                .phone(phone)
                .salt(salt)
                .studentNumber(GenerateUtil.generateStudentNum(redisManager.getRedisTemplate()))
                .password(passwordMd5)
                .roleCode(userBean.getRoleCode())
                .build();
        save(user);
        log.info("学员{}，注册成功",user.getUsername());
        return jwtUtil.generateToken(new UserClaim(user));
    }

    /**
     * 获取用户信息
     *
     * @author ouyu
     */
    @Override
    public UserInfo userInfo(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            log.error("获取用户信息时用户ID为空");
            throw new MessageException("获取用户信息时用户ID为空");
        }
        User user = getBaseMapper().findUserInfo(userId);
        if (ObjectUtils.isEmpty(user)) {
            log.error("获取用户信息失败");
            throw new MessageException("获取用户信息失败");
        }

        UserInfo info = new UserInfo();
        info.setUserId(user.getId());
        info.setUsername(user.getUsername());
        info.setClassGradeName(user.getClassGrade().getClassGradeName());
        info.setClassGradeId(user.getClassGrade().getId());
        info.setHeadPicture(user.getStorage().getAccessUrl());
        info.setIntegral(user.getIntegral());
        info.setOpenId(user.getOpenId());
        info.setStudentNumber(user.getStudentNumber());
        info.setRoleCode(user.getRole().getRoleCode());
        info.setRoleName(user.getRole().getRoleName());
        return info;
    }

    @Override
    @Transactional
    public MpUserInfo UserInfoByOpenIdOrRegister(UserInfo userInfo) {
        if(userInfo.getOpenId() == null){
            log.error("小程序授权登录失败，为获取到openId");
            throw new MessageException("小程序授权登录失败，为获取到openId");
        }
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, userInfo.getOpenId()));
        boolean isNew=false;
        if(one == null){
            //注册
           one = doMpweixinRegister(userInfo);
            isNew=true;
        }
        //找出对应的用户信息返给页面
        userInfo = userInfo(one.getId());
        userInfo.setIsNew(isNew);
        //生成token
        String token = jwtUtil.generateToken(new UserClaim(new User()
                .setId(userInfo.getUserId())
                .setUsername(userInfo.getUsername())));
       return  new MpUserInfo(userInfo,token);
    }

    /**
     * 微信小程序 注册用户
     * @author ouyu
     * @date 2020-12-15
     * @param userInfo
     * @return void
     */
    public User doMpweixinRegister(UserInfo userInfo) {
        String salt = AES.generateRandomKey();
        String passwordMd5 = EncryptionUtil.encryptionMd5("123456".replaceAll(" ",""), salt);
        User user = new User()
                .setUsername(userInfo.getUsername())
                .setSalt(salt)
                .setPhone("13333333333")
                .setOpenId(userInfo.getOpenId())
                .setRoleCode(userInfo.getRoleCode())
                .setStudentNumber(GenerateUtil.generateStudentNum(redisManager.getRedisTemplate()))
                .setPassword(passwordMd5);
        if(StringUtils.isNotBlank(userInfo.getHeadPicture())){
            Storage storage = new Storage(userInfo.getHeadPicture(), "jpg");
            storageService.save(storage);
            //保存头像
            user.setHeadPicture(storage.getId().intValue());
        }

        save(user);
        return user;
    }

    @Override
    public UserSourse findByCourses(Long userId) {
        UserSourse userSourse = getBaseMapper().findByCourses(userId);
        log.info("获取到同学的班级学科信息",userSourse);
        return userSourse;
    }

    @Override
    public UserSourse findByCourseSchoolTime(Long userId) {
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        UserSourse userSourse = getBaseMapper().findByCourseSchoolTime(userId,week-1);
        log.info("获取到同学的班级学科本星期上课信息",userSourse);
        return userSourse;
    }

    @Override
    public String doLoginUserId(Long userId) {
        if(userId == 0){
            log.error("学生ID不能为空");
            throw new MessageException("学生ID不能为空");
        }
        User user = getById(userId);
        return jwtUtil.generateToken(new UserClaim(user));
    }

    @Override
    public UserSourse findByTeacherCourse(Long userId,Long classGradeId) {
        UserSourse userSourse = getBaseMapper().findByTeacherCourses(userId,classGradeId);
        log.info("获取到的老师班级学科信息",userSourse);
        return userSourse;
    }

    @Override
    public UserSourse findByTeacherCourseSchoolTime(Long userId,Long classGradeId) {
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        UserSourse userSourse = getBaseMapper().findByTeacherCourseSchoolTime(userId,week-1,classGradeId);
        log.info("获取到同学的班级学科本星期上课信息",userSourse);
        return userSourse;
    }

    @Override
    public UserSourse findClassGradeAllCourse(Long classGradeId) {
        UserSourse classGradeAllCourse = getBaseMapper().findClassGradeAllCourse(classGradeId);
        log.info("本班级的所有课程:{}",classGradeAllCourse);
        return classGradeAllCourse;
    }

    @Override
    public UserSourse findClassGradeAllCourseSchoolTime(Long classGradeId) {
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        UserSourse schoolTime = getBaseMapper().findClassGradeAllCourseSchoolTime(week - 1, classGradeId);
        log.info("本班级所有课程j今天的上课信息:{}",schoolTime);
        return schoolTime;
    }

    @Override
    public Boolean addUserRole(Long userId, String roleCode) {
        User one = getOne(Wrappers.lambdaQuery(User.class).eq(User::getId, userId));
        one.setRoleCode(roleCode);
        updateById(one);
        return true;
    }
}
