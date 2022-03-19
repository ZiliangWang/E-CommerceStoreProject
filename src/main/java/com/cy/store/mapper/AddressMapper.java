package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    Integer insert(Address address);

    Integer countByUid(Integer uid);

    //get user's address list
    List<Address> findByUid(Integer uid);

    Address findByAid(Integer aid);

    Integer updateNonDefaultByUid(Integer uid);

    Integer updateDefaultByAid( @Param("aid") Integer aid,
                                @Param("modifiedUser") String modifiedUSer,
                                @Param("modifiedTime") Date modifiedTime);

    Integer deleteByAid(Integer aid);

    Address findLastModifiedAddress(Integer uid);
}
