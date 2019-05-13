package net.Ken365X.Dao;

import net.Ken365X.Entity.Designer;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by mac on 2018/7/9.
 */
public class DesignerDaoImp extends HibernateDaoSupport implements DesignerDao {
    @Override
    public boolean addNewDesigner(String username, String password, String phonenum) {
        Designer designer = null;
        try{
            designer = (Designer) getSessionFactory().getCurrentSession()
                    .createQuery("select d from Designer as d where d.username = ?1")
                    .setParameter("1",username)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (designer != null){
            return false;
        }
        Designer target = new Designer();
        target.setPassword(password);
        target.setUsername(username);
        target.setPhone_number(phonenum);
        try{
            getHibernateTemplate().save(target);
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Designer getDesignerByEmail(String email) {
        Designer designer = null;
        try{
            designer = (Designer) getSessionFactory().getCurrentSession()
                    .createQuery("select D from Designer as D where D.email = ?1")
                    .setParameter("1",email)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            designer = null;
        }
        return designer;
    }

    @Override
    public boolean deleteDesignerById(int id) {
        Designer designer = getDesignerById(id);
        try{
            getSessionFactory().getCurrentSession().delete(designer);
        }catch(Exception e){
            // do not have designer
            return false;
        }
        return true;
    }

    @Override
    public Designer getDesignerById(int id) {
        Designer designer = null;
        try{
            designer = (Designer) getSessionFactory().getCurrentSession()
                    .createQuery("select D from Designer as D where D.designer_id = ?1")
                    .setParameter("1",id)
                    .getSingleResult();
        }catch(Exception e){
            //e.printStackTrace();
            designer = null;
        }
        return designer;
    }

    @Override
    public Designer getDesignerByName(String name) {
        Designer designer;
        try {
            designer = (Designer) getSessionFactory().getCurrentSession()
                    .createQuery("select D from Designer as D where D.username = ?1")
                    .setParameter("1",name)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
        return designer;
    }

    @Override
    public boolean updateDesigner(Designer designer) {
        try{
            getSessionFactory().getCurrentSession().update(designer);
        }catch(Exception e){
            // no designer before
        }
        return false;
    }

    @Override
    public List<Designer> getAllDesigner() {
        List<Designer> designers = null;
        try{
            designers = getSessionFactory().getCurrentSession()
                    .createQuery("select d from Designer as d")
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return designers;
    }

    @Override
    public int getCountOfDesigner() {
        return (int) getSessionFactory().getCurrentSession()
                .createQuery("select count(*) from Designer").getSingleResult();
    }

}
