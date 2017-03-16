package com.dongao.oa.dao;

import com.dongao.oa.pojo.Role;
import com.dongao.oa.utils.MapperUtils;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends MapperUtils<Role> {
    List<Map<String,Object>> menuTree();

    List<Map<String,Object>> selectMenusByRoleId(Long id);
    List<Map<String,Object>> selectRolesByUserId(Long userId);
}