
package com.cy.store.service;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class UserServiceTest {
    //idea interface cannot directly create bean
    @Autowired
    private IUserService userService;
    //Must be void; param not defined; must be public; must be @Test
    @Test
    public void insert() {
        try{
            User user = new User();
            user.setUsername("17Niry");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());

            System.out.println(e.getMessage());
        }


    }
    @Test
    public void login() {
        User user = userService.login("17Niry","321");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(22, "admin", "321", "123");
    }

    @Test
    public void getByUid() {
        userService.getByUid(22);
    }

    @Test
    public void changeUserInfo() {
        User user = new User();
        user.setPhone("2709575");
        user.setEmail("2709575@gmail.com");
        user.setGender(2);
        userService.changeUserInfo(22, "admin", user);

    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(22, "avatar", "admin");
    }
}
