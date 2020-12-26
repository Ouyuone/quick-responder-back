package tech.ouyu.quickResponder.back.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ouyu.quickResponder.back.service.SchoolTimeService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-21 15:03
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@SpringBootTest
class SchoolTimeServiceImplTest {

    @Autowired
    SchoolTimeService schoolTimeService;
    @Test
    void findByFreeTime() {
        schoolTimeService.findByFreeTime(1L,1L);
    }
}