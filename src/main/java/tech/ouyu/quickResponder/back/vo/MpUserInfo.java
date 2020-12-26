package tech.ouyu.quickResponder.back.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-15 16:20
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
@AllArgsConstructor
public class MpUserInfo {
    private UserInfo userInfo;
    private String token;
}
