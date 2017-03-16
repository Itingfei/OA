package com.dongao.oa.service.impl;
import com.dongao.oa.dao.ClassifyMapper;
import com.dongao.oa.pojo.Classify;
import com.dongao.oa.service.ClassifyService;
import com.dongao.oa.utils.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2016/8/18.
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired
    private ClassifyMapper classifyMapper;
    @Override
    public Classify selectOne(Long classifId) {
        Classify classify = new Classify();
        classify.setId(classifId);
        classify.setDelFlag(0);
        return classifyMapper.selectOne(classify);
    }

    @Override
    public PageInfo<Classify> selectAll(Classify classify) {
        Example example = new Example(Classify.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag", 0);//去除删除的采购物品分类，只查询有效采购物品分类
        if (StringUtils.isNotEmpty(classify.getName()))
            criteria.andLike("name","%"+classify.getName()+"%");
        return new PageInfo(classifyMapper.selectByExample(example));
    }

    @Override
    public int createClassify(Classify classify) {
        return classifyMapper.insert(classify);
    }

    @Override
    public int updateClassify(Classify classify) {
        return classifyMapper.updateByPrimaryKeySelective(classify);
    }

    @Override
    public Classify selectByName(String name) {
        Classify classify = new Classify();
        classify.setDelFlag(0);
        classify.setName(name);
        return classifyMapper.selectOne(classify);
    }

    @Override
    public List<Map<String,Object>> selectAll() {
        return classifyMapper.selectIdAndName();
    }
}

