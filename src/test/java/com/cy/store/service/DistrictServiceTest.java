
package com.cy.store.service;
import com.cy.store.entity.District;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class DistrictServiceTest {
    //idea interface cannot directly create bean
    @Autowired
    private IDistrictService districtService;
    //Must be void; param not defined; must be public; must be @Test
    @Test
    public void getParent() {
        List<District> list = districtService.getByParent("86");
        for (District d : list) {
            System.out.println(d);
        }
    }
}
