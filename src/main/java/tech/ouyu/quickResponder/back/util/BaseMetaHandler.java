package tech.ouyu.quickResponder.back.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 10:54
 * @Description: mybatisPlus fill 自动注入数据处理接口
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Component
@Slf4j
public class BaseMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject,"createTime",()->new Date(),Date.class);
        this.strictInsertFill(metaObject,"createBy",Long.class,UserIdThreadLocal.getUserId());
        this.strictInsertFill(metaObject,"createTime",Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateBy", Long.class, UserIdThreadLocal.getUserId());
        this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }

}
