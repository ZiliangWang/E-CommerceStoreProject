package com.cy.store.mappertest;

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Order order  = new Order();
        order.setUid(22);
        order.setRecvName("Admin");
        order.setRecvPhone("123123");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem  = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(10000003);
        orderItem.setTitle("123123");
        orderMapper.insertOrderItem(orderItem);
    }
}
