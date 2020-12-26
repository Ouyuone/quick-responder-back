package tech.ouyu.quickResponder.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.entity.User;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-07 16:02
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(User user);

    User findUserInfo(@Param("userId") Long userId);

    /**
     * 获取自己的课程
     *
     * @author ouyu
     */
    UserSourse findByCourses(Long userId);

    /**
     * 获取自己进入在上课的课程
     *
     * @author ouyu
     */
    UserSourse findByCourseSchoolTime(@Param("userId") Long userId, @Param("week") Integer weeks);


    /**
     * 老师本班级创建的所有课程
     * @author ouyu
     */
    UserSourse findByTeacherCourses(@Param("userId") Long userId, @Param("classGradeId") Long classGradeId);

    UserSourse findByTeacherCourseSchoolTime(@Param("userId") Long userId, @Param("week") Integer weeks, @Param("classGradeId") Long classGradeId);

    /**
     * 获取本班级所有的课程
     *
     * @author ouyu
     */
    UserSourse findClassGradeAllCourse(@Param("classGradeId") Long classGradeId);

    /**
     * 获取本班级今日所有在上课的课程
     *
     * @author ouyu
     */
    UserSourse findClassGradeAllCourseSchoolTime(@Param("week") Integer weeks, @Param("classGradeId") Long classGradeId);
}
