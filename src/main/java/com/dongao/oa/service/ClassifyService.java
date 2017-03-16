package com.dongao.oa.service;
import com.dongao.oa.pojo.Classify;
import com.dongao.oa.utils.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2016/9/20.
 * 采购商品分类管理
 */
public interface ClassifyService {
    /**
     * 根据ID查询采购商品分类
     * @param classifId
     * @return
     */
    Classify selectOne(Long classifId);

    /**
     * 查询全部采购商品分类
     * @return
     */
    PageInfo<Classify> selectAll(Classify classify);

    int createClassify(Classify classify);

    int updateClassify(Classify classify);

    Classify selectByName(String name);

    List<Map<String,Object>> selectAll();
}
