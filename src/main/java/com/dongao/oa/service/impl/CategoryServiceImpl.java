package com.dongao.oa.service.impl;

import com.dongao.oa.dao.*;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.CategoryItem;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.CategoryService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by fengjifei on 2016/8/4.
 */
@Service
@CacheConfig(cacheNames = "DeploymentCatrgory")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryItemMapper categoryItemMapper;
    @Autowired
    private CategoryItemExtMapper categoryItemExtMapper;
    @Override
    public int delete(Category category) {
        category.setDeleteFlag(1);//删除
        int i = categoryMapper.updateByPrimaryKey(category);
        if (i > 0) {
            List<CategoryItem> items = selectCategoryItemByCategoryIdNoPage(category.getId());
            for (CategoryItem categoryItem : items) {
                categoryItem.setDeleteFlag(1);
                categoryItemMapper.updateByPrimaryKey(categoryItem);
            }
        }
        return i;
    }

    @Override
    public PageInfo<Map<String, Object>> selectAll(Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", category.getTitle());
        map.put("startCreateDate", category.getStartCreateDate());
        map.put("endCreateDate", category.getEndCreateDate());
        map.put("userid", category.getUserId());
        map.put("assignee", category.getAssignee());
        map.put("status", category.getStatus());
        map.put("orgIds", category.getOrgIds());
        map.put("ids", category.getIds());
        List<Map<String, Object>> categories = categoryMapper.selectCategoryList(map);
        return new PageInfo(categories);
    }


    @Override
    public long saveCategory(Category category, List<CategoryItem> items) {
        int result = categoryMapper.insert(category);
        if (result > 0) {
            for (CategoryItem categoryItem : items) {
                categoryItem.setCategoryId(category.getId());
                categoryItemMapper.insert(categoryItem);
            }
            category.setBeforeTotalPrice(BigDecimal.valueOf(categoryItemExtMapper.findApplicationPrice(category.getId())));
            categoryMapper.updateByPrimaryKey(category);
        }
        return category.getId();
    }

    @Override
    public Category selectCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<CategoryItem> selectCategoryItemByCategoryId(CategoryItem categoryItem) {
        Example example = new Example(CategoryItem.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleteFlag", 0).andEqualTo("categoryId", categoryItem.getCategoryId());
        if (StringUtils.isNotEmpty(categoryItem.getItemName()))
            criteria.andLike("itemName", "%" + categoryItem.getItemName() + "%");
        return new PageInfo(categoryItemMapper.selectByExample(example));
    }

    @Override
    public List<CategoryItem> selectCategoryItemByCategoryIdNoPage(Long categoryId) {
        Example example = new Example(CategoryItem.class);
        example.createCriteria().andEqualTo("deleteFlag", 0).andEqualTo("categoryId", categoryId);
        return categoryItemMapper.selectByExample(example);
    }

    @Override
    public long updateCategory(Category category, List<CategoryItem> items, String deleteId) {
        Category updateCategory = categoryMapper.selectByPrimaryKey(category.getId());
        category.setCreateDate(updateCategory.getCreateDate());
        category.setCreateBy(updateCategory.getCreateBy());
        category.setUserId(updateCategory.getUserId());
        if (StringUtils.isEmpty(category.getDeploymentId()))
            category.setDeploymentId(updateCategory.getDeploymentId());
        if (StringUtils.isEmpty(category.getStatus()))
            category.setStatus(updateCategory.getStatus());
        category.setDeleteFlag(updateCategory.getDeleteFlag());
        long result = categoryMapper.updateByPrimaryKeySelective(category);
        String[] deleteIds = null;
        if (StringUtils.isNotEmpty(deleteId)) {
            deleteIds = deleteId.split(",");
        }
        if (deleteIds != null) {
            for (String delId : deleteIds) {
                if (StringUtils.isNotEmpty(delId)) {
                    CategoryItem categoryItem = categoryItemMapper.selectByPrimaryKey(Long.valueOf(delId));
                    categoryItem.setDeleteFlag(1);//删除
                    categoryItemMapper.updateByPrimaryKey(categoryItem);
                }
            }
        }
        if (result > 0) {
            if (items != null) {
                for (CategoryItem categoryItem : items) {
                    if (categoryItem != null && categoryItem.getId() != null) { //编辑
                        categoryItem.setCategoryId(category.getId());
                        categoryItemMapper.updateByPrimaryKey(categoryItem);
                    } else {
                        categoryItem.setCategoryId(category.getId());
                        categoryItemMapper.insert(categoryItem);
                    }
                }
            }
            result = category.getId();
        }
        return result;
    }

    @Override
    public Category selectOne(Long id) {
        Category seachcategory = new Category();
        seachcategory.setDeleteFlag(0);
        seachcategory.setId(id);
        Category category = categoryMapper.selectOne(seachcategory);
        category.setCategoryItems(selectCategoryItemByCategoryIdNoPage(id));
        return category;
    }

    @Override
    public List<Category> selectAllNoPage() {
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("deleteFlag", 0);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public Double selectRealPrice(Long id) {
        return categoryItemExtMapper.findRealPrice(id);
    }
}
