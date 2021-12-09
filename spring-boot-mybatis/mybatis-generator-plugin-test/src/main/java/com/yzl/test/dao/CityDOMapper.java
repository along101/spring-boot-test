package com.yzl.test.dao;

import com.yzl.test.domain.CityDO;

public interface CityDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CityDO record);

    int insertSelective(CityDO record);

    CityDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CityDO record);

    int updateByPrimaryKey(CityDO record);
}