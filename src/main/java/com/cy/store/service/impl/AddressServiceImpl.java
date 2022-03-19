package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    AddressMapper addressMapper;

    //used to complete address
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("User stored address is over limit!");
        }

        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());


        Integer rows = addressMapper.insert(address);

        if(rows != 1) {
            throw new InsertException("Address adding failed!");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> resultList = addressMapper.findByUid(uid);
        List<Address> newList = new ArrayList<>();

        for(Address a : resultList) {
            Address newAddress = new Address();
            newAddress.setUid(a.getUid());
            newAddress.setAid(a.getAid());
            newAddress.setTag(a.getTag());
            newAddress.setPhone(a.getPhone());
            newAddress.setProvinceName(a.getProvinceName());
            newAddress.setCityName(a.getCityName());
            newAddress.setAreaName(a.getAreaName());
            newAddress.setName(a.getName());
            newAddress.setAddress(a.getAddress());

            newList.add(newAddress);
        }

        return newList;
    }

    @Override
    public void setDefaultAddress(Integer aid, Integer uid, String username) {
        
    }
}
