package com.dongao.oa.dao;

import com.dongao.oa.pojo.Message;
import com.dongao.oa.utils.MapperUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

public interface MessageMapper extends MapperUtils<Message> {
    List<Map<String,Object>> selectByCondition(Map<String,Object> map);
}