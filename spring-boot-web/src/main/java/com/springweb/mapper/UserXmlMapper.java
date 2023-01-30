package com.springweb.mapper;

import com.springweb.entity.UserXml;

public interface UserXmlMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserXml record);

    int insertSelective(UserXml record);

    UserXml selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserXml record);

    int updateByPrimaryKey(UserXml record);
}