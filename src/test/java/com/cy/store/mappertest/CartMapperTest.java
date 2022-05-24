package com.cy.store.mappertest;

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
public class CartMapperTest {
    @Autowired
    CartMapper cartMapper;
    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(22);
        cart.setPid(10000011);
        cart.setCreatedTime(new Date());
        cart.setCreatedUser("Admin");
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {
        cartMapper.updateNumberByCid(1,22, "Amdin", new Date());
    }

    @Test
    public void findByUidAndPid() {
        System.out.println(cartMapper.findByUidAndPid(22,10000011));
    }

    @Test
    public void findVOByUid() {
        System.out.println(cartMapper.findVOByUid(22).toString());
    }

    @Test
    public void findByCid() {
        System.out.println(cartMapper.findByCid(3));
    }

    @Test
    public void findVOByCids() {
        Integer[] cids = {1,3};
        System.out.println(cartMapper.findVOByCids(cids));
    }
}
