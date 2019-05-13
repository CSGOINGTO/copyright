package net.Ken365X.Dao;

import net.Ken365X.Entity.Task;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.Date;
import java.util.List;


/**
 * Created by mac on 2018/7/11.
 */
public class TaskDaoImp extends HibernateDaoSupport implements TaskDao {
    @Override
    public Task getTaskByName(String name) {
        Task task = null;
        try{
            task = (Task) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.name = ?1")
                    .setParameter("1",name)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            task = null;
        }
        return task;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = null;
        try{
            task = (Task) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.task_id = ?1")
                    .setParameter("1",id)
                    .getSingleResult();
        }catch(Exception e){
            // e.printStackTrace();
            task = null;
        }
        return task;
    }

    @Override
    public boolean addNewTask(Task task) {
        Task result = null;
        try{
            result = (Task) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.name = ?1 and t.enterprise.username = ?2")
                    .setParameter("1",task.getName())
                    .setParameter("2",task.getEnterprise().getUsername())
                    .getSingleResult();

        }catch(Exception e){
            // e.printStackTrace();
        }
        if (result == null){
            try{
                getSessionFactory().getCurrentSession().save(task);
                return true;
            }catch(Exception e){
                // e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteTaskById(int id) {
        Task task = getTaskById(id);
        if (task != null){
            try{
                getSessionFactory().getCurrentSession().delete(task);
                return true;
            }catch(Exception e){
                // e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateTask(Task task) {
        try{
            getSessionFactory().getCurrentSession().update(task);
        }catch(Exception e){
            // e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Task> getTaskOrderByMoney(int start, int end) {
        List<Task> result = null;
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t order by t.give_money desc")
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
    public List<Task> getTaskOrderByView(int start, int end) {
        List<Task> result = null;
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t order by t.num_view desc")
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
    public List<Task> getTaskByEnterprise(String username) {
        List<Task> result = null;
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.enterprise.username = ?1")
                    .setParameter("1",username)
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Task> getTaskNotStop(int start, int end) {
        List<Task> result = null;
        Date date = new Date();
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.end_day > ?1")
                    .setParameter("1",date)
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(result.toString());
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
    public List<Task> gettaskStop(int start, int end) {
        List<Task> result = null;
        Date date = new Date();
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.end_day < ?1")
                    .setParameter("1",date)
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
    public List<Task> getTaskByClassify(String target) {
        List<Task> result = null;
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t where t.info_class like ?1")
                    .setParameter("1","%" + target + "%")
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean readTask(int[] number) {
        for (int i :
                number) {
            Task task = getTaskById(i);
            if (task != null){
                task.setNum_view(task.getNum_view() + 1);
                getSessionFactory().getCurrentSession().update(task);
            }
        }
        return true;
    }

    @Override
    public List<Task> getAllTask() {
        List<Task> result = null;
        try{
            result = (List<Task>) getSessionFactory().getCurrentSession()
                    .createQuery("select t from Task as t")
                    .getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
