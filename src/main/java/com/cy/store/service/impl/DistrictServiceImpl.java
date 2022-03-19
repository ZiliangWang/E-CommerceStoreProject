package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;


    @Override
    public List<District> getByParent(String parent) {
        List<District> result = districtMapper.findByParent(parent);

        //Saving data flow and increase efficiency.
        for(District d : result) {
            d.setId(null);
            d.setParent(null);
        }

        return result;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
        //over 8 lines code better to do unit test
    }
}
