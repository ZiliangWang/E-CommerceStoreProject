package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.service.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid, username, address);

        return new JsonResult<Void>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> resultList = addressService.getByUid(uid);
        return new JsonResult<>(OK, resultList);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefaultAddress(@PathVariable("aid") Integer aid,
                                              HttpSession session) {
        addressService.setDefaultAddress(aid,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult<Void>(OK);
    }



}
