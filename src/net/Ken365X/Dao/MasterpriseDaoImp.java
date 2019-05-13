package net.Ken365X.Dao;

import net.Ken365X.Entity.Masterprise;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by mac on 2018/7/12.
 */
public class MasterpriseDaoImp extends HibernateDaoSupport implements MasterpriseDao {
    @Override
    public Masterprise getMasterpriseById(int id) {
        Masterprise masterprise = null;
        try{
            masterprise = (Masterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.masterprise_id = ?1")
                    .setParameter("1",id)
                    .getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
        }
        return masterprise;
    }

    @Override
    public Masterprise getMasterpriseByUsernameAndTaskid(String username, int task_id) {
        Masterprise masterprise = null;
        try{
            masterprise = (Masterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.designer.username = ?1 and m.task.task_id = ?2")
                    .setParameter("1",username).setParameter("2",task_id)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
        }
        return masterprise;
    }

    @Override
    public Masterprise getMasterpriseByUsernameAndTaskName(String username, String taskname) {
        Masterprise masterprise = null;
        try{
            masterprise = (Masterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.designer.username = ?1 and m.task.name = ?2")
                    .setParameter("1",username).setParameter("2",taskname)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
        }
        return masterprise;
    }

    @Override
    public boolean deleteMasterpriseById(int id) {
        Masterprise masterprise = null;
        try{
            masterprise = (Masterprise) getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.masterprise_id = ?1")
                    .setParameter("1",id)
                    .getResultList();
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (masterprise == null){
            return false;
        }else{
            getSessionFactory().getCurrentSession().delete(masterprise);
            return true;
        }
    }

    @Override
    public List<Masterprise> getMasterpieceSuccess() {
        List<Masterprise> masterprises = null;
        try{
            masterprises = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.stage = 2")
                    .getResultList();
            return masterprises;
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addNewMasterprise(Masterprise masterprise) {
        Masterprise mas = getMasterpriseByUsernameAndTaskid(
                masterprise.getDesigner().getUsername(),
                masterprise.getTask().getTask_id());
        if (mas == null){
            getSessionFactory().getCurrentSession().save(masterprise);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMasterprise(Masterprise masterprise) {
        try{
            getSessionFactory().getCurrentSession().saveOrUpdate(masterprise);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean setMasterpriseStage(int id, int stage) {
        try{
            Masterprise masterprise = getMasterpriseById(id);
            masterprise.setStage(stage);
            getSessionFactory().getCurrentSession().update(masterprise);
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Masterprise> getMasterpriceByTask(int Task_id) {
        List<Masterprise> masterprises = null;
        try{
            masterprises = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.task.task_id = ?1")
                    .setParameter("1",Task_id)
                    .getResultList();
            return masterprises;
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Masterprise> getAllMasterpiece() {
        List<Masterprise> masterprises = null;
        try{
            masterprises = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m")
                    .getResultList();
            return masterprises;
        }catch(Exception e){
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Masterprise> getMasteroriceByDesignerName(String username) {
        List<Masterprise> masterprises = null;
        try{
            masterprises = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m where m.designer.username = ?1 order by m.create_date")
                    .setParameter("1",username)
                    .getResultList();
        }catch(Exception e){
            // e.printStackTrace();
        }
        return masterprises;
    }

    @Override
    public List<Masterprise> getMasterpriceByLike(int start, int end) {
        List<Masterprise> result = null;
        try{
            result = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m order by m.like_designer_username.size desc ")
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        if (start <= result.size()){
            if (end <= result.size()){
                return result.subList(start - 1,end);
            }else {
                return result.subList(start - 1,result.size());
            }
        }else {
            return null;
        }
    }

    @Override
    public List<Masterprise> getMasterpriceByShare(int start, int end) {
        List<Masterprise> result = null;
        try{
            result = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m order by m.share_designer_username.size desc ")
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        if (start <= result.size()){
            if (end <= result.size()){
                return result.subList(start - 1,end);
            }else {
                return result.subList(start - 1,result.size());
            }
        }else {
            return null;
        }
    }

    @Override
    public List<Masterprise> getMasterpiceByView(int start, int end) {
        List<Masterprise> result = null;
        try{
            result = getSessionFactory().getCurrentSession()
                    .createQuery("select m from Masterprise as m order by m.view desc ")
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        if (start <= result.size()){
            if (end <= result.size()){
                return result.subList(start - 1,end);
            }else {
                return result.subList(start - 1,result.size());
            }
        }else {
            return null;
        }
    }
}
