package tech.ouyu.quickResponder.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.ouyu.quickResponder.back.entity.SchoolTime;
import tech.ouyu.quickResponder.back.entity.SubjectSchoolTime;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-18 14:45
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Mapper
public interface SchoolTimeMapper extends BaseMapper<SchoolTime> {
    List<SubjectSchoolTime> findByFreeTime(@Param("classGradeId") Long classGradeId);
}
