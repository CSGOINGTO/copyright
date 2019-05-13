package net.Ken365X.Service;

import net.Ken365X.Dao.DesignerDao;
import net.Ken365X.Dao.EnterpriseDao;
import net.Ken365X.Dao.MessageDao;
import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Enterprise;
import net.Ken365X.Entity.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public class MessageServiceImp implements MessageService {
    private DesignerDao designerDao;
    private EnterpriseDao enterpriseDao;
    private MessageDao messageDao;

    @Override
    public boolean addMessageToDesigner(String username, String from_username,
                                        String title, String content, Date date) {
        // System.out.println(date.toString());
        Designer designer = designerDao.getDesignerByName(username);
        Enterprise enterprise = enterpriseDao.getEnterpriseByName(from_username);
        if(designer == null){
            return false;
        }
        if (enterprise == null){
            return false;
        }

        Message message = new Message(content,title,date,false,
                true,designer,enterprise);
        messageDao.addNewMessage(message);
        return true;
    }

    @Override
    public boolean addMessageToEnterprise(String username, String from_username,
                                          String title, String content, Date date) {
        Designer designer = designerDao.getDesignerByName(from_username);
        Enterprise enterprise = enterpriseDao.getEnterpriseByName(username);
        if(designer == null){
            return false;
        }
        if (enterprise == null){
            return false;
        }
        Message message = new Message(content,title,date,false,
                false,designer,enterprise);
        messageDao.addNewMessage(message);
        return true;
    }

    @Override
    public List<Message> getMessageList(String username) {
        return messageDao.getAllUserMessageByUsername(username);
    }

    @Override
    public List<Message> getMessageListEnterprise(String username) {
        return messageDao.getAllEnterpriseMessageByUsername(username);
    }

    @Override
    public boolean readMessage(int id) {
        Message message = messageDao.getMessageById(id);
        if (message == null){
            return false;
        }
        if (message.isOrientation() == true && message.isReadornot() == false){
            message.setReadornot(true);
            messageDao.updateMessage(message);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean removeMessage(int id) {
        Message message = messageDao.getMessageById(id);
        if (message == null){
            return false;
        }
        if(message.isOrientation() == true){
            return messageDao.deleteMessage(message);
        }
        return false;
    }

    @Override
    public boolean readMessageE(int id) {
        Message message = messageDao.getMessageById(id);
        if (message == null){
            return false;
        }
        if (message.isOrientation() == false && message.isReadornot() == false){
            message.setReadornot(true);
            messageDao.updateMessage(message);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean removeMessageE(int id) {
        Message message = messageDao.getMessageById(id);
        if (message == null){
            return false;
        }
        if(message.isOrientation() == false){
            return messageDao.deleteMessage(message);
        }
        return false;
    }

    public DesignerDao getDesignerDao() {
        return designerDao;
    }

    public void setDesignerDao(DesignerDao designerDao) {
        this.designerDao = designerDao;
    }

    public EnterpriseDao getEnterpriseDao() {
        return enterpriseDao;
    }

    public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
