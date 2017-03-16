package com.dongao.oa.dao;

import com.dongao.oa.pojo.User;
import com.dongao.oa.utils.MapperUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends MapperUtils<User> {
    List<Map<String,Object>> selectAllByCondition(Map<String, Object> map);

    Map<String,Object> selectUserInfoByUserId(Long id);

    List<Map<String,Object>> selectAddressBookSortLetters(@Param("name")String name);
}