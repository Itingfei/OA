package com.dongao.oa.dao;

import com.dongao.oa.pojo.CategoryItem;
import com.dongao.oa.utils.MapperUtils;

public interface CategoryItemExtMapper extends MapperUtils<CategoryItem> {
    public Double findApplicationPrice(Long id);
    public Double findRealPrice(Long id);
}