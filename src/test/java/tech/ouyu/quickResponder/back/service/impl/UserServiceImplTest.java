package tech.ouyu.quickResponder.back.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.service.AuthService;
import tech.ouyu.quickResponder.back.service.UserService;
import tech.ouyu.quickResponder.back.vo.UserInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-09 09:36
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Test
    void userInfo() {
        UserInfo userInfo = userService.userInfo(1L);
        System.out.println(userInfo);
    }

    @Test
    void userSourse(){
        UserSourse byCourses = userService.findByCourses(1L);
        System.out.println(byCourses);

        UserSourse courseSchoolTime = userService.findByCourseSchoolTime(1L);
        System.out.println(courseSchoolTime);

    }

    @Test
    void subjects(){
        authService.course(1L,"pages/course/course",1L);
    }

    @Test
    void studentClassGradeCOurse(){
        userService.findClassGradeAllCourse(1L);
    }
}