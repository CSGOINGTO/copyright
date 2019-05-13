package net.Ken365X.Dao;

import net.Ken365X.Entity.Message;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public class MessageDaoImp extends HibernateDaoSupport implements MessageDao {
    @Override
    public boolean addNewMessage(Message message) {
        try{
            getSessionFactory().getCurrentSession().save(message);
        }catch(Exception e){
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Message getMessageById(int id) {
        Message message = null;
        try{
            message = (Message) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Message as m where m.message_id = ?1")
                    .setParameter("1",id)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            return null;
        }
        return message;
    }

    @Override
    public List<Message> getAllUserMessageByUsername(String username) {
        List<Message> result = null;
        try {
            result = (List<Message>) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Message as m where m.designer.username = ?1")
                    .setParameter("1",username)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        // System.out.println(result.toString());
        return result;
    }

    @Override
    public List<Message> getAllEnterpriseMessageByUsername(String username) {
        List<Message> result = null;
        try {
            result = (List<Message>) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Message as m where m.enterprise.username = ?1")
                    .setParameter("1",username)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        // System.out.println(result.toString());
        return result;
    }

    @Override
    public boolean deleteMessage(Message message) {
        try {
            getHibernateTemplate().delete(message);
        }catch (Exception e){
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateMessage(Message message) {
        try{
            getHibernateTemplate().update(message);
        }catch(Exception e){
            // e.printStackTrace();
            return false;
        }
        return true;
    }
}
