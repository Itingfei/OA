package com.dongao.oa.service;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.CategoryItem;
import com.dongao.oa.pojo.DeploymentCategory;
import com.dongao.oa.pojo.Role;
import com.dongao.oa.utils.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by fengjifei on 2016/8/4.
 */
public interface CategoryService {
    public int delete(Category category);
    public PageInfo<Map<String,Object>> selectAll(Category category);
    long saveCategory(Category category, List<CategoryItem> items);
    public Category selectCategoryById(Long id);

    PageInfo<CategoryItem> selectCategoryItemByCategoryId(CategoryItem categoryItem);

    List<CategoryItem> selectCategoryItemByCategoryIdNoPage(Long categoryId);

    long updateCategory(Category category, List<CategoryItem> items,String deleteId);
    Category selectOne(Long id);

    List<Category> selectAllNoPage();
    public Double selectRealPrice(Long id);

}
