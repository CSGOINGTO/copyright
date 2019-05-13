package net.Ken365X.Dao;

import net.Ken365X.Entity.Enterprise;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * Created by mac on 2018/7/10.
 */
public class EnterpriseDaoImp extends HibernateDaoSupport implements EnterpriseDao {
    @Override
    public boolean addNewEnterprise(String username, String password,String phonenum,
                                    String name, String realm, String email, String info) {
        Enterprise enterprise = null;
        try{
            enterprise = (Enterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select e from Enterprise as e where e.username = ?1")
                    .setParameter("1",username)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (enterprise != null){
            return false;
        }
        enterprise = new Enterprise();
        enterprise.setUsername(username);
        enterprise.setPassword(password);
        enterprise.setEmail(email);
        enterprise.setInfo(info);
        enterprise.setName(name);
        enterprise.setRealm(realm);
        enterprise.setPhonenum(phonenum);
        getHibernateTemplate().save(enterprise);
        return true;
    }

    @Override
    public Enterprise getEnterpriseByName(String username) {
        Enterprise enterprise = null;
        try{
            enterprise = (Enterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select e from Enterprise as e where e.username = ?1")
                    .setParameter("1",username)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            enterprise = null;
        }
        return enterprise;
    }

    @Override
    public Enterprise getEnterpriseById(int id) {
        Enterprise enterprise = null;
        try{
            enterprise = (Enterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select e from Enterprise as e where e.id = ?1")
                    .setParameter("1",id)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            enterprise = null;
        }
        return enterprise;
    }

    @Override
    public boolean updateEnterprise(Enterprise enterprise) {
        try{
            getSessionFactory().getCurrentSession().update(enterprise);
        }catch(Exception e){
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteEnterpriseByName(String username) {
        Enterprise enterprise = getEnterpriseByName(username);
        if(enterprise == null){
            return false;
        }
        try{
            getSessionFactory().getCurrentSession().delete(enterprise);
        }catch(Exception e){
            // e.printStackTrace();
        }
        return true;
    }

    @Override
    public Enterprise getEnterpriseByEmail(String email) {
        Enterprise enterprise = null;
        try{
            enterprise = (Enterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select e from Enterprise as e where e.email = ?1")
                    .setParameter("1",email)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            enterprise = null;
        }
        return enterprise;
    }
}
