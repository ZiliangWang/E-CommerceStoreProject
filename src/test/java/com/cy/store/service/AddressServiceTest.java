
package com.cy.store.service;
import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //Unit test
@SpringBootTest
public class AddressServiceTest {
    //idea interface cannot directly create bean
    @Autowired
    private IAddressService addressService;
    //Must be void; param not defined; must be public; must be @Test
    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(22);
        address.setPhone("2879022");
        address.setName("TestName");


        addressService.addNewAddress(22, "admin", address);
    }

    @Test
    public void setDefault() {
        addressService.setDefaultAddress(4, 22, "admin");
    }
}
