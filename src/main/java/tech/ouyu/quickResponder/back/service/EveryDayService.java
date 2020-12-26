package tech.ouyu.quickResponder.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.ouyu.quickResponder.back.entity.Everyday;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-11 11:22
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public interface EveryDayService extends IService<Everyday> {
    Everyday readOneword();
}
