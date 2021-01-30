package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.entity.*;
import tech.ouyu.quickResponder.back.exception.MessageException;
import tech.ouyu.quickResponder.back.mapper.AuthMapper;
import tech.ouyu.quickResponder.back.service.AuthService;
import tech.ouyu.quickResponder.back.service.ClassGradeService;
import tech.ouyu.quickResponder.back.service.SchoolTimeService;
import tech.ouyu.quickResponder.back.service.UserService;
import tech.ouyu.quickResponder.back.vo.AuthBean;
import tech.ouyu.quickResponder.back.vo.AuthTopic;
import tech.ouyu.quickResponder.back.vo.UserBean;

import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 17:17
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthAccess> implements AuthService {
    final UserService userService;

    final SchoolTimeService schoolTimeService;

    final ClassGradeService classGradeService;

    @Override
    @Transactional
    public AuthBean course(Long userId, String accessUrl, Long classGradeId) {
        User user = userService.getById(userId);
        //角色和访问路径取授权访问数据
        AuthAccess authAccess = getOne(Wrappers.<AuthAccess>lambdaQuery()
                .eq(AuthAccess::getRoleCode, user.getRoleCode())
                .eq(AuthAccess::getAccessUrl, accessUrl));
        AuthBean authBean = AuthBean.builder()
                .title(authAccess.getDescribes())
                .roleCode(user.getRoleCode()).build();
        UserSourse courses = null;
        UserSourse schoolTime = null;

        //是老师的话需要走老师获取老师自己创建的课程的接口
        if (user.getRoleCode().equals(DescribeConstant.Role.QR_TEACHER)) {
            Map<String, Object> userSourseMap = teacherCourse(authBean, user, classGradeId);
            courses = (UserSourse) userSourseMap.get("userSourse");
            //添加本老师的所有班级
            authBean.setClassGrades((List<ClassGrade>) userSourseMap.get("classGrades"));
            if (courses == null) {
                //说明还没有加入学科
                return authBean;
            }
            schoolTime = (UserSourse) userSourseMap.get("schoolTime");

        } else if (user.getRoleCode().equals(DescribeConstant.Role.QR_STUDENT)) {
            //是同学但是带了班级id说明是来添加课程的需要把本班级所有课程取出来
            if (classGradeId != null) {
                courses = userService.findClassGradeAllCourse(classGradeId);
                if (courses == null) {
                    return authBean;
                }
                schoolTime = userService.findClassGradeAllCourseSchoolTime(classGradeId);
                UserSourse studentCourses = userService.findByCourses(userId);
                //同学添加过课程需要把已添加的课程过滤掉
                if (studentCourses != null) {
                    List<Subject> subjectList = getDifferentSubjects(courses, studentCourses);
                    courses.setSubject(subjectList);
                    //同学所在的班级
                    findAllClassByUserId(userId, authBean);
                    //这里上课时间过滤很重要
                    //有可能刚好今天上课的课程是之前添加过的，那么又会被这里的上课时间给添加回去
                    //所有需要过滤一次以添加的课程
                    if (schoolTime != null && !schoolTime.getSubject().isEmpty()) {
                        List<Subject> subjectSchoolTimeList = getDifferentSubjects(schoolTime, studentCourses);
                        schoolTime.setSubject(subjectList);
                    }
                }

            } else {
                //同学的所有学科
                courses = userService.findByCourses(userId);
                //同学所在的班级
                findAllClassByUserId(userId, authBean);
                if (courses == null) {
                    //说明还没有加入学科
                    return authBean;
                }
                //今天需要上课的学科及课数时间段
                schoolTime = userService.findByCourseSchoolTime(userId);
            }

        } else {
            return authBean;
        }

        //得到本星期几没有课的学科
        List<Subject> subjectList = getDifferentSubjects(courses, schoolTime);

        if (schoolTime != null && !schoolTime.getSubject().isEmpty()) {
            //加入本星期几有课的学科
            subjectList.addAll(schoolTime.getSubject());
        }
        //当添加课程已经没有课程可以添加subjectList中没有课程了就直接返回
        if(CollectionUtils.isEmpty(subjectList)){
            return authBean;
        }
        //把有课的学科排在前面根据上课时间升序排序
        subjectList.sort(Subject::compareTo);
        courses.setSubject(subjectList);

        //获取班级
        ClassGrade classGrade = courses.getClassGrade().get(0);
        //组装返回数据
        courses.getSubject().forEach(subject -> {
            boolean schoolTimeMark = false;
            String schoolTimeDesc = "还未上课,请稍事休息!";
            //当天有课 查询上课时间
            back:
            if (StringUtils.isNotBlank(subject.getSchoolTime())) {
                List<SchoolTime> time = schoolTimeService.findSchoolTime(subject.getSchoolTime().split(","));
                if (!time.isEmpty()) {
                    schoolTimeMark = true;
                    schoolTimeDesc = "正在上课！";
                    break back;
                }
                log.error("本科目上课时间:{},未找到", subject.getSchoolTime());
            }
           /* //班级id
            authBean.setClassGradeId(classGrade.getId());
            //班级名
            authBean.setClassGradeName(classGrade.getClassGradeName());*/
            authBean.addCourses(subject.getId(), subject.getSubject()
                    , classGrade.getClassGradeName()
                    , schoolTimeDesc
                    , schoolTimeMark, subject.getWeek(), subject.getSchoolTime());
        });
        return authBean;
    }

    private void findAllClassByUserId(Long userId, AuthBean authBean) {
        List<ClassGrade> classGrades = classGradeService.findClassGradeByUserId(userId);
        if (classGrades == null || classGrades.isEmpty()) {
            log.error("请先添加班级");
            throw new MessageException("请先添加班级");
        }
        authBean.setClassGrades(classGrades);
    }

    private List<Subject> getDifferentSubjects(UserSourse courses, UserSourse schoolTime) {
        UserSourse finalSchoolTime = schoolTime;
        if (courses == null || courses.getSubject().isEmpty()) {
            return null;
        }
        return courses.getSubject().stream().filter((subject) -> {
            AtomicBoolean flag = new AtomicBoolean(true);
            //找到今天上课科目并过滤掉
            try {
                if (finalSchoolTime != null && !finalSchoolTime.getSubject().isEmpty()) {
                    finalSchoolTime.getSubject().forEach((subjectTime) -> {
                        if (subjectTime.getId() == subject.getId()) {
                            flag.set(false);
                            throw new IllegalArgumentException("找到相同科目,开始过滤！");
                        }
                        flag.set(true);
                    });
                }

            } catch (IllegalArgumentException e) {
                log.info(e.getMessage());
            }
            return flag.get();
        }).collect(Collectors.toList());
    }

    private Map<String, Object> teacherCourse(AuthBean authBean, User user, Long classGradeId) {
        Map<String, Object> userSourseMap = new HashMap<>();
        //没有班级id从老师教授的班级中寻找第一个班显示课程

        List<ClassGrade> classGradeList = classGradeService.findClassGradeByUserId(user.getId());
        if (classGradeList == null || classGradeList.isEmpty()) {
            log.error("请先添加班级");
            throw new MessageException("请先添加班级");
        }
        userSourseMap.put("classGrades", classGradeList);
        if (classGradeId == null) {
            classGradeId = classGradeList.get(0).getId();
        }

        UserSourse userSourse = userService.findByTeacherCourse(user.getId(), classGradeId);
        if (userSourse == null) {
            userSourseMap.put("userSourse", userSourse);
            return userSourseMap;
        }
        UserSourse schoolTime = userService.findByTeacherCourseSchoolTime(user.getId(), classGradeId);

        userSourseMap.put("userSourse", userSourse);
        userSourseMap.put("schoolTime", schoolTime);
        return userSourseMap;
    }

    @Override
    public AuthTopic liveTopic(Long userId, String accseeUrl) {

        return null;
    }
}
