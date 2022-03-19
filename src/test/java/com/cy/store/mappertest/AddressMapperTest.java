package com.cy.store.mappertest;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class AddressMapperTest {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(22);
        address.setPhone("2879022");
        address.setName("TestName");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(22);
        System.out.println(count);
    }

    @Test
    public void findByUid() {
        List<Address> list = addressMapper.findByUid(22);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.out.println(addressMapper.findByAid(6));
    }

    @Test
    public void updateNonDefaultByUid(){
        addressMapper.updateNonDefaultByUid(22);
    }
    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(6, "Admin", new Date());
    }
}
