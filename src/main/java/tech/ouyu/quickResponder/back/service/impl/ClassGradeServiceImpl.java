package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.LambdaFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tech.ouyu.quickResponder.back.entity.ClassGrade;
import tech.ouyu.quickResponder.back.entity.User;
import tech.ouyu.quickResponder.back.entity.UserClassgrade;
import tech.ouyu.quickResponder.back.exception.MessageException;
import tech.ouyu.quickResponder.back.mapper.ClassGradeMapper;
import tech.ouyu.quickResponder.back.mapper.UserClassGradeMapper;
import tech.ouyu.quickResponder.back.mapper.UserMapper;
import tech.ouyu.quickResponder.back.service.ClassGradeService;
import tech.ouyu.quickResponder.back.util.UserIdThreadLocal;
import tech.ouyu.quickResponder.back.vo.ClassGradeVo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-19 14:53
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
public class ClassGradeServiceImpl extends ServiceImpl<ClassGradeMapper, ClassGrade> implements ClassGradeService {
   final UserClassGradeMapper userClassGradeMapper;
   final UserMapper userMapper;
    @Override
    public List<ClassGrade> findClassGradeByUserId(Long userId) {
       return  getBaseMapper().findClassGradeByUserId(userId);
    }

    @Override
    public List<ClassGradeVo> getMyGrade(Long userId) {
        if (userId == null || userId == 0) {
            log.error("用户id不能够为空");
            throw new MessageException("用户id不能够为空");
        }
        //查询自己所在的班级
        List<ClassGradeVo> myGrade = getBaseMapper().getMyGrade(userId);
        //判断是否添加了班级
        if(CollectionUtils.isEmpty(myGrade)){
            log.error("未添加班级");
            return Collections.emptyList();
        }
        //查询本班的最新消息
        //TODO 等到开发聊天的时候来加上
        return myGrade;
    }

    @Override
    public List<ClassGrade> getAllGrade(Long userId) {
        return getBaseMapper().findNotHaveClassGrades(userId);
                //selectList(null);
    }

    @Override
    public List<ClassGradeVo> doAddClassGrade(Long userId, Long classGradeId) {
        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getId,userId));
        if(Objects.isNull(user)){
            throw new MessageException("不要拿假的学生、老师来糊弄我");
        }
        List<UserClassgrade> userClassGrades = userClassGradeMapper.selectList(Wrappers.lambdaQuery(UserClassgrade.class).eq(UserClassgrade::getUserId, userId));
        //如果是学生的话只允许加一个班级
        if(user.getRoleCode().equals("QR_STUDENT") && userClassGrades != null && !userClassGrades.isEmpty()){
            throw new MessageException("同学您好;知道你爱学习！但是一个学生只能加一个班级哦,不可贪多!");
        }
        UserClassgrade userClassgrade = new UserClassgrade();
        userClassgrade.setClassGradeId(classGradeId);
        userClassgrade.setUserId(userId);
        userClassGradeMapper.insert(userClassgrade);
        //是同学直接返回空数组
        if(user.getRoleCode().equals("QR_STUDENT")){
            return Collections.emptyList();
        }
        return getMyGrade(userId);
    }
/*  public static void main(String[] args) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 E HH:mm:ss.s", Locale.CHINA)));
        Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }*/

    @Override
    @Transactional
    public Boolean addNewClassGrade(String classGradeName) {
        if(StringUtils.isBlank(classGradeName)){
            log.error("请输入班级名称");
            throw new MessageException("请输入班级名称");
        }
        ClassGrade classGrade = ClassGrade.builder().classGradeName(classGradeName).build();
        save(classGrade);
        return true;
    }
}
