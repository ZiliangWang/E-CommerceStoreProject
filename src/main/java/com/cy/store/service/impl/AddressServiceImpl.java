package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
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
        Address result = addressMapper.findByAid(aid);
        if(result == null) {
            throw new AddressNotFoundException("Address data is not exist!");
        }

        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("Illegal data access!");
        }
        Integer rows = addressMapper.updateNonDefaultByUid(uid);

        if (rows < 1) {
            throw new UpdateException("Reset default failed!");
        }

        rows = addressMapper.updateDefaultByAid(aid, username, new Date());

        if(rows != 1) {
            throw new UpdateException("Set default failed!");
        }

    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result == null) {
            throw new AddressNotFoundException("Address data is not exist!");
        }

        if(!result.getUid().equals(uid)) {
            throw new AccessDeniedException("Illegal data access!");
        }

        Integer rows = addressMapper.deleteByAid(aid);

        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }

        if(rows != 1) {
            throw new DeleteException("Deletion failed!");
        }

        if(result.getIsDefault() == 1) {
            Address newDefault = addressMapper.findLastModifiedAddress(uid);
            rows = addressMapper.updateDefaultByAid(newDefault.getAid(), username, new Date());
            if(rows != 1) {
                throw new UpdateException("Reset new default address failed!");
            }
        }
    }

    @Override
    public Address getByAid(Integer uid, Integer aid) {
        Address address = addressMapper.findByAid(aid);

        if (address == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedTime(null);
        address.setCreatedUser(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);


        return address;
    }

}
