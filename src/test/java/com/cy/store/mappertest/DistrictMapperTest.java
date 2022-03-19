package com.cy.store.mappertest;

import com.cy.store.entity.District;
import com.cy.store.entity.User;
import com.cy.store.mapper.DistrictMapper;
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
public class DistrictMapperTest {
    //idea interface cannot directly create bean
    @Autowired
    private DistrictMapper districtMapper;
    //Must be void; param not defined; must be public; must be @Test
    @Test
    public void findByParent() {
        List<District> list = districtMapper.findByParent("210100");
        for (District d : list) {
            System.out.println(d);
        }
    }
    @Test
    public void findNameByCode() {
        String name = districtMapper.findNameByCode("610000");
        System.out.println(name);
    }

}
