package com.dongao.oa.service.impl;

import com.dongao.oa.dao.MessageMapper;
import com.dongao.oa.dao.RoleAndMenuMapper;
import com.dongao.oa.dao.RoleMapper;
import com.dongao.oa.dao.UserMapper;
import com.dongao.oa.pojo.Message;
import com.dongao.oa.pojo.Role;
import com.dongao.oa.pojo.RoleAndMenu;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.MenuService;
import com.dongao.oa.service.MessageService;
import com.dongao.oa.service.RoleService;
import com.dongao.oa.utils.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by yangjie on 2016/9/9.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Message selectOne(Long messageId) {
        return messageMapper.selectByPrimaryKey(messageId);
    }

    @Override
    public PageInfo<Map<String,Object>> selectAll(Message message) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("title",message.getTitle());
        map.put("startCreateDate",message.getStartCreateDate());
        map.put("endCreateDate",message.getEndCreateDate());
        map.put("recipient",message.getRecipient());
        map.put("status",message.getStatus());
        map.put("type",message.getType());
        List<Map<String,Object>> messages = messageMapper.selectByCondition(map);
        PageInfo pageInfo = new PageInfo(messages);
        return pageInfo;
    }

    @Override
    public int createMessage(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public int updateMessage(Message message) {
        return messageMapper.updateByPrimaryKey(message);
    }

    @Override
    public Message selectMessageByTitle(String title) {
        Message message = new Message();
        message.setDelFlag(0);
        message.setTitle(title);
        return messageMapper.selectOne(message);
    }

    @Override
    public List<Message> selectByRecipient(Long recipientId) {
        Example o = new Example(Message.class);
        o.createCriteria().andEqualTo("delFlag",0).andEqualTo("status",0).andEqualTo("recipient",recipientId);
        return messageMapper.selectByExample(o);
    }
}

