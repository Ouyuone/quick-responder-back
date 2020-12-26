package tech.ouyu.quickResponder.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.ouyu.quickResponder.back.entity.Role;

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
public interface RoleService extends IService<Role> {
    List<Role> roles();
}
