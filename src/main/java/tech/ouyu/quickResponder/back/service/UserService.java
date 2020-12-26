package tech.ouyu.quickResponder.back.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.entity.User;
import tech.ouyu.quickResponder.back.mapper.UserMapper;
import tech.ouyu.quickResponder.back.vo.MpUserInfo;
import tech.ouyu.quickResponder.back.vo.UserBean;
import tech.ouyu.quickResponder.back.vo.UserInfo;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 08:51
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */

public interface UserService extends IService<User>{

    String doLogin(UserBean userBean);

    String doRegister(UserBean userBean);

    UserInfo userInfo(Long userId);

    MpUserInfo UserInfoByOpenIdOrRegister(UserInfo userInfo);

    UserSourse findByCourses(Long userId);


    UserSourse findByCourseSchoolTime(Long userId);

    String doLoginUserId(Long userId);

    UserSourse findByTeacherCourse(Long id,Long classGradeId);

    UserSourse findByTeacherCourseSchoolTime(Long id,Long classGradeId);

    UserSourse findClassGradeAllCourse(Long i);

    UserSourse findClassGradeAllCourseSchoolTime(Long classGradeId);
}
