package net.Ken365X.Dao;

import net.Ken365X.Entity.Root;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * Created by mac on 2018/7/6.
 */
public class RootDaoImp extends HibernateDaoSupport implements RootDao{
    public String getPasswordByName(String name) {
        return (String) getSessionFactory().getCurrentSession()
                .createQuery("select r.Root_Password from Root as r where r.Root_Username = ?1 ")
                .setParameter("1",name).getSingleResult();
    }

    public boolean deleteRootByName(String name) {
        Root root = (Root) getSessionFactory().getCurrentSession()
                .createQuery("select r from Root as r where r.Root_Username = ?1")
                .setParameter("1",name).getSingleResult();
        if(root == null){
            return false;
        }else {
            getSessionFactory().getCurrentSession().delete(root);
            return true;
        }
    }

    public boolean addRoot(String username, String password) {
        Root root = (Root) getSessionFactory().getCurrentSession()
                .createQuery("select r from Root as r where r.Root_Username = ?1")
                .setParameter("1",username).getSingleResult();
        if (root == null){
            Root root1 = new Root(username,password);
            getSessionFactory().getCurrentSession().save(root);
            return true;
        }else {
            return false;
        }
    }

    public boolean rePasswordRoot(String username, String newPassword) {
        Root root = (Root) getSessionFactory().getCurrentSession()
                .createQuery("select r from Root as r where r.Root_Username = ?1")
                .setParameter("1",username).getSingleResult();
        if(root == null){
            return false;
        }else {
            root.setRoot_Password(newPassword);
            getSessionFactory().getCurrentSession().update(root);
            return true;
        }
    }
}
