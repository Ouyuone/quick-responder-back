package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.entity.UserClassgrade;
import tech.ouyu.quickResponder.back.entity.UserSubject;
import tech.ouyu.quickResponder.back.exception.MessageException;
import tech.ouyu.quickResponder.back.mapper.UserClassGradeMapper;
import tech.ouyu.quickResponder.back.mapper.UserSubjectMapper;
import tech.ouyu.quickResponder.back.service.AuthService;
import tech.ouyu.quickResponder.back.service.UserService;
import tech.ouyu.quickResponder.back.service.UserSubjectService;
import tech.ouyu.quickResponder.back.vo.AuthBean;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-20 16:39
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
public class UserSubjectServiceImpl extends ServiceImpl<UserSubjectMapper, UserSubject> implements UserSubjectService {
    final AuthService authService;
    final UserClassGradeMapper userClassGradeMapper;

    @Override
    @Transactional
    public AuthBean addCourse(Long userId, Long courseId) {
        UserClassgrade classgrade = userClassGradeMapper.selectOne(Wrappers.<UserClassgrade>lambdaQuery().eq(UserClassgrade::getUserId, userId));
        if(classgrade == null){
            log.error("同学id:{}你还未添加班级，不能添加课程",userId);
            throw new MessageException(String.format("同学id:%s你还未添加班级，不能添加课程",userId.toString()));
        }
        UserSubject userSubject = UserSubject.builder()
                .subjectId(courseId)
                .userId(userId).build();
        save(userSubject);
       return authService.course(userId,"pages/course/course",classgrade.getClassGradeId());
    }
}
