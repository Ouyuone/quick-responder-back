package tech.ouyu.quickResponder.back.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.ouyu.quickResponder.back.entity.User;
import tech.ouyu.quickResponder.back.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-07 16:06
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;


    @Test
    public void findByUserName(){
        User user = new User();
         user.setUsername("ouyu");
        User ouyu = userMapper.findByUsername(user);
        QueryWrapper<User> userEntityWrapper = new QueryWrapper<User>(user);
        User user1 = userMapper.selectOne(userEntityWrapper);
        System.out.println(ouyu);
    }

    @Test
    public void findPageable(){
        User user = new User();
//        user.setUsername("ouyu");
        QueryWrapper<User> userEntityWrapper = new QueryWrapper<User>(user);
        Page<User> userPage = userMapper.selectPage(new Page<>(3, 2), userEntityWrapper);
        System.out.println(userPage);
    }

    @Test
    public void lambdaWrapper(){
        LambdaQueryWrapper<User> ouyu = Wrappers.lambdaQuery(new User()).eq(User::getUsername, "zs");
        User user = userMapper.selectOne(ouyu);
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setUsername("ndy");
        user.setPassword("123456");
        int update = userMapper.insert(user);
        System.out.println(update);
    }
}