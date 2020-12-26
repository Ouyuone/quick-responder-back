package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.ouyu.quickResponder.back.entity.Role;
import tech.ouyu.quickResponder.back.mapper.RoleMapper;
import tech.ouyu.quickResponder.back.service.RoleService;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 14:17
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public List<Role> roles() {
        return getBaseMapper().selectList(null );
    }
}
