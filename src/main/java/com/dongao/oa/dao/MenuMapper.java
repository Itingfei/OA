package com.dongao.oa.dao;

import com.dongao.oa.pojo.Menu;
import com.dongao.oa.utils.MapperUtils;

import java.util.List;
import java.util.Map;

public interface MenuMapper extends MapperUtils<Menu> {
    List<Menu> selectMenusByUserId(Map map);
}