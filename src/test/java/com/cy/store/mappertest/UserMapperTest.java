package com.cy.store.mappertest;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class UserMapperTest {
    //idea interface cannot directly create bean
    @Autowired
    private UserMapper userMapper;
    //Must be void; param not defined; must be public; must be @Test
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("Tom");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername() {
        String username = "Tom";
        User result = userMapper.findByUsername(username);
        System.out.println(result);
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(20,"321",
                "admin", new Date());
    }

    @Test
    public void findByUid() {
        User user = userMapper.findByUid(20);
        System.out.println(user.getUsername());
    }

    @Test
    public void updateUserInfo() {
        User user = new User();
        user.setUid(22);
        user.setPhone("123123");
        user.setEmail("212@gmail.com");
        user.setGender(1);
        userMapper.updateUserInfo(user);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(
                22,
                "/upload/avatar.png",
                "admin",
                new Date());
    }
}
