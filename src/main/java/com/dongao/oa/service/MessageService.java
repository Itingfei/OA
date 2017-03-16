package com.dongao.oa.service;
import com.dongao.oa.pojo.Message;
import com.dongao.oa.utils.PageInfo;
import java.util.List;
import java.util.Map;
/**
 * Created by yangjie on 2016/8/18.
 * 消息管理
 */
public interface MessageService {
    /**
     * 根据ID查询消息
     * @param messageId
     * @return
     */
    Message selectOne(Long messageId);

    /**
     * 查询全部消息
     * @return
     */
    PageInfo<Map<String,Object>> selectAll(Message message);

    int createMessage(Message message);

    int updateMessage(Message message);

    Message selectMessageByTitle(String title);

    /**
     * 查询用户所有的消息
     * @param recipientId
     * @return
     */
    List<Message> selectByRecipient(Long recipientId);
}
