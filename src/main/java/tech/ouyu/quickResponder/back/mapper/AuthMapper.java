package tech.ouyu.quickResponder.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.ouyu.quickResponder.back.entity.AuthAccess;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 17:16
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Mapper
public interface AuthMapper extends BaseMapper<AuthAccess> {
}
