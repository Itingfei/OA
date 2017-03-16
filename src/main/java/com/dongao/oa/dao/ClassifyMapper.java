package com.dongao.oa.dao;

import com.dongao.oa.pojo.Classify;
import com.dongao.oa.utils.MapperUtils;

import java.util.List;
import java.util.Map;

public interface ClassifyMapper extends MapperUtils<Classify> {
    public List<Map<String,Object>> selectIdAndName();
}