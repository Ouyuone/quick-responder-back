package tech.ouyu.quickResponder.back.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ouyu.quickResponder.back.entity.User;
import tech.ouyu.quickResponder.back.service.impl.UserServiceImpl;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 09:00
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@SpringBootTest
class UserServiceTest {


    @Autowired
    UserService userService;

    @Test
    public void testServiceMethod(){
        User byId = userService.getById(6);
//        userService.removeById(byId.getId());
        UpdateWrapper<User> uw = new UpdateWrapper<>(byId);
        userService.remove(uw);
    }

    /**
     * 测试插入的时候 fill自动注入创建人 创建时间
     * @author ouyu
     */
    @Test
    public void saveUserInfo(){
        User user = new User().setUsername("ddddd")
                .setPassword("222222")
                .setSalt("wwww")
                .setPhone("12312312");
        userService.save(user);
    }

    @Test
    public void updateUserInfo(){
        User byId = userService.getById(6);
        byId.setSalt("123321");
        byId.setPhone("");
        userService.updateById(byId);
    }


}
