package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.ouyu.quickResponder.back.entity.ClassGrade;
import tech.ouyu.quickResponder.back.mapper.ClassGradeMapper;
import tech.ouyu.quickResponder.back.service.ClassGradeService;

import java.util.List;

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
public class ClassGradeServiceImpl extends ServiceImpl<ClassGradeMapper, ClassGrade> implements ClassGradeService {
    @Override
    public List<ClassGrade> findClassGradeByUserId(Long userId) {
       return  getBaseMapper().findClassGradeByUserId(userId);
    }
}
