package tech.ouyu.quickResponder.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.ouyu.quickResponder.back.entity.Everyday;
import tech.ouyu.quickResponder.back.mapper.EveryDayMapper;
import tech.ouyu.quickResponder.back.service.EveryDayService;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-11 13:18
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Service
@Slf4j
public class EveryDayServiceImpl extends ServiceImpl<EveryDayMapper, Everyday> implements EveryDayService {

    @Override
    public Everyday readOneword() {
        Everyday oneword = getOne(new LambdaQueryWrapper<>());
        if(oneword == null){
            oneword = Everyday.builder()
                    .oneword("今生最好不过你胖,我也爱!")
                    .author("无名")
                    .build();
            log.error("init oneword {}",oneword.getOneword());
        }
        return oneword;
    }
}
