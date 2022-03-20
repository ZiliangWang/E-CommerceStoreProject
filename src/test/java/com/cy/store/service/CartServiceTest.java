package com.cy.store.service;

import com.cy.store.entity.Cart;
import com.cy.store.mapper.CartMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class CartServiceTest {
    @Autowired
    ICartService cartService;
    @Test
    public void insert() {
        cartService.addToCart(22, 100000401, 12, "admin");
    }

//    @Test
//    public void updateNumByCid() {
//        cartService.updateCartNum(1,22, 22, "Admin");
//    }
////
//    @Test
//    public void findByUidAndPid() {
//        System.out.println(cartMapper.findByUidAndPid(22,10000011));
//    }
}
