package com.dongao.oa.dao;

import com.dongao.oa.pojo.Category;
import com.dongao.oa.utils.MapperUtils;

import java.util.List;
import java.util.Map;

public interface CategoryMapper extends MapperUtils<Category> {
    List<Map<String,Object>> selectCategoryList(Map<String, Object> map);
}