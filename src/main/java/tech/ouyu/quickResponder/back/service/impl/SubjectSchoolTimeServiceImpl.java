package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ouyu.quickResponder.back.entity.Subject;
import tech.ouyu.quickResponder.back.entity.SubjectSchoolTime;
import tech.ouyu.quickResponder.back.exception.MessageException;
import tech.ouyu.quickResponder.back.mapper.SubjectSchoolTimeMapper;
import tech.ouyu.quickResponder.back.service.SubjectSchoolTimeService;
import tech.ouyu.quickResponder.back.service.SubjectService;
import tech.ouyu.quickResponder.back.vo.SchoolTimeVo;
import tech.ouyu.quickResponder.back.vo.TeacherCouser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-29 15:44
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
public class SubjectSchoolTimeServiceImpl extends ServiceImpl<SubjectSchoolTimeMapper, SubjectSchoolTime> implements SubjectSchoolTimeService {
    final SubjectService subjectService;

    @Override
    @SuppressWarnings("all")
    @Transactional
    public Boolean teacherAddCourse(TeacherCouser teacherCouser) {

        String subject = teacherCouser.getSubject();
        if (StringUtils.isBlank(subject)) {
            log.error("课程名不能为空");
            throw new MessageException("课程名不能为空");
        }
        List<SchoolTimeVo> weeks = teacherCouser.getWeeks();
        Subject couser = new Subject();
        couser.setGradeId(teacherCouser.getGradeId());
        couser.setOverdueTime(teacherCouser.getOverdueTime());
        couser.setSubject(subject);
        subjectService.save(couser);
        weeks.stream().filter(week_ -> {
            String week = week_.getWeek();
            StringBuilder schoolAmtime = new StringBuilder();
            StringBuilder schoolPmtime = new StringBuilder();
            StringBuilder schoolEvtime = new StringBuilder();
            week_.getAmTime().stream().filter(time -> {
                if (time.isChecked() && time.getUserId() == 1) {
                    schoolAmtime.append(time.getRealSeveralLesson());
                    schoolAmtime.append(",");
                }
                return true;
            }).collect(Collectors.toList());
            ;
            week_.getPmTime().stream().filter(time -> {
                if (time.isChecked() && time.getUserId() == 1) {
                    schoolPmtime.append(time.getRealSeveralLesson());
                    schoolPmtime.append(",");
                }
                return true;
            }).collect(Collectors.toList());
            ;
            week_.getEvTime().stream().filter(time -> {
                if (time.isChecked() && time.getUserId() == 1) {
                    schoolEvtime.append(time.getRealSeveralLesson());
                    schoolEvtime.append(",");
                }
                return true;
            }).collect(Collectors.toList());
            ;
            String alltime = "";
            schoolAmtime.append(schoolPmtime).append(schoolEvtime);
            //当这一天没有选择上课时间时直接跳过
            if (schoolAmtime.length() > 0) {
                alltime = schoolAmtime.substring(0, schoolAmtime.length() - 1);
            } else {
                return true;
            }

            SubjectSchoolTime subjectSchoolTime = new SubjectSchoolTime();
            subjectSchoolTime.setWeek(Long.valueOf(week));
            subjectSchoolTime.setSubjectId(couser.getId());
            subjectSchoolTime.setSchoolTime(alltime);
            save(subjectSchoolTime);
            return true;
        }).collect(Collectors.toList());
        return true;
    }

}
