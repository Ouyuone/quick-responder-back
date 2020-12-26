package tech.ouyu.quickResponder.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.ouyu.quickResponder.back.dao.UserSourse;
import tech.ouyu.quickResponder.back.entity.UserSubject;
import tech.ouyu.quickResponder.back.vo.AuthBean;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-20 16:38
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public interface UserSubjectService extends IService<UserSubject> {
    AuthBean addCourse(Long userId, Long courseId);
}
