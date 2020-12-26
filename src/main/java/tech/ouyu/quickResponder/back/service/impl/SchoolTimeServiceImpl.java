package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.ouyu.quickResponder.back.entity.SchoolTime;
import tech.ouyu.quickResponder.back.entity.SubjectSchoolTime;
import tech.ouyu.quickResponder.back.mapper.SchoolTimeMapper;
import tech.ouyu.quickResponder.back.service.SchoolTimeService;
import tech.ouyu.quickResponder.back.vo.SchoolTimeVo;

import javax.swing.*;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-18 14:46
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Service
public class SchoolTimeServiceImpl extends ServiceImpl<SchoolTimeMapper, SchoolTime> implements SchoolTimeService {
    @Override
    public List<SchoolTime> findSchoolTime(String[] schoolTime) {
        Time date = new Time(System.currentTimeMillis());
        return getBaseMapper().selectList(Wrappers.<SchoolTime>lambdaQuery()
                .in(SchoolTime::getRealSeveralLesson, schoolTime).le(SchoolTime::getSchoolTimeStart, date.toString()).ge(SchoolTime::getSchoolTimeEnd, date.toString()));
    }

    @Override
    public Object findByFreeTime(Long userId, Long classGradeId) {
        List<SubjectSchoolTime> freeTime = baseMapper.findByFreeTime(classGradeId);
        Map<Long, List<SubjectSchoolTime>> collect = freeTime.stream().collect(Collectors.groupingBy(SubjectSchoolTime::getWeek, Collectors.toList()));
        List<SchoolTime> schoolTimes = getBaseMapper().selectList(null);
        List<SchoolTimeVo> schoolTimeVos = new ArrayList<>();
        collect.forEach((key, value) -> {
            SchoolTimeVo schoolTimeVo = new SchoolTimeVo();
            schoolTimeVo.setWeek(key.toString());
            schoolTimeVo.setDesc(SchoolTimeVo.pullWeek(key.toString()));
            //把同一天的时间拼接好
            String joinTime = value.stream()
                    .map(SubjectSchoolTime::getSchoolTime)
                    .collect(Collectors.joining(","));
            //找寻已被占用的课程节数
            List<SchoolTime> holdUpSchoolTime = getBaseMapper().selectList(Wrappers.<SchoolTime>lambdaQuery()
                    .in(SchoolTime::getRealSeveralLesson, (Object[]) joinTime.split(",")));

            //给被占用的节数打标
            for (SchoolTime schoolTime : schoolTimes) {
                boolean flag = false;
                for (SchoolTime time : holdUpSchoolTime) {
                    if (schoolTime.getId() == time.getId()) {
                        schoolTimeVo.addTime(time.getWhens(), time.getSeveralLesson().toString(), time.getRealSeveralLesson().toString(), true);
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    schoolTimeVo.addTime(schoolTime.getWhens(), schoolTime.getSeveralLesson().toString(), schoolTime.getRealSeveralLesson().toString(), false);
                }
            }
            schoolTimeVos.add(schoolTimeVo);
        });
        //说明选课的老师没有把一周选全 需要把剩下的时候补齐为空显示给老师看（可能新加的一班都没老师开课）
        if (schoolTimeVos.size() < 5) {
            back:
            for (int i = 1; i <= 5; i++) {
                for (int ii = 0; ii < schoolTimeVos.size(); ii++) {
                    //当有被选的了星期时间就跳出大循环
                    if (schoolTimeVos.get(ii).getWeek().equals(i + "")) {
                        continue back;
                    }
                }
                SchoolTimeVo schoolTimeVo = new SchoolTimeVo();
                schoolTimeVo.setWeek(i + "");
                schoolTimeVo.setDesc(SchoolTimeVo.pullWeek((i + "").toString()));
                for (SchoolTime schoolTime : schoolTimes) {
                    schoolTimeVo.addTime(schoolTime.getWhens(), schoolTime.getSeveralLesson().toString(), schoolTime.getRealSeveralLesson().toString(), false);
                }
                schoolTimeVos.add(schoolTimeVo);
            }
        }
        schoolTimeVos.sort(SchoolTimeVo::compareTo);
        return schoolTimeVos;
    }
}
