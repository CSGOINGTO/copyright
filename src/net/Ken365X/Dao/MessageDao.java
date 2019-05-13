package net.Ken365X.Dao;

import net.Ken365X.Entity.Message;

import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public interface MessageDao {
    boolean addNewMessage(Message message);
    Message getMessageById(int id);
    boolean deleteMessage(Message message);
    boolean updateMessage(Message message);
    List<Message> getAllUserMessageByUsername(String username);
    List<Message> getAllEnterpriseMessageByUsername(String username);
}
