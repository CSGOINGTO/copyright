package net.Ken365X.Service;

import net.Ken365X.Entity.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public interface MessageService {
    boolean addMessageToDesigner(String username, String from_username, String title,
                                 String content, Date date);
    boolean addMessageToEnterprise(String username, String from_username, String title,
                                 String content, Date date);
    List<Message> getMessageList(String username);
    List<Message> getMessageListEnterprise(String username);
    boolean readMessage(int id);
    boolean removeMessage(int id);
    boolean readMessageE(int id);
    boolean removeMessageE(int id);
}
