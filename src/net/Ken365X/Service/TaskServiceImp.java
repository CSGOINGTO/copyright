package net.Ken365X.Service;

import net.Ken365X.Dao.EnterpriseDao;
import net.Ken365X.Dao.TaskDao;
import net.Ken365X.Entity.Enterprise;
import net.Ken365X.Entity.Task;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/12.
 */
public class TaskServiceImp implements TaskService {
    private TaskDao taskDao;
    private EnterpriseDao enterpriseDao;
    @Override
    public boolean addNewTask(String name, String info_title, String info,
                              String info_class, double money, int num_picture,
                              Date start, Date end, List<String> sub_title,
                              List<String> sub_info, List<String> image_info,
                              String enterprise_username,double property,Date start_day2, Date end_day2) {
        Enterprise enterprise = enterpriseDao.getEnterpriseByName(enterprise_username);
        if (enterprise == null){
            return false;
        }
        Task task = new Task();
        task.setName(name);
        task.setTask_info_title(info_title);
        task.setTask_info(info);
        task.setInfo_class(info_class);
        task.setGive_money(money);
        task.setStart_day(start);
        task.setEnd_day(end);
        task.setSub_task_info_title(sub_title);
        task.setSub_task_info(sub_info);
        task.setNum_of_picture(num_picture);
        task.setSub_task_img_info(image_info);
        task.setEnterprise(enterprise);
        task.setProperty(property);
        task.setStart_day2(start_day2);
        task.setEnd_day2(end_day2);
        // System.out.println(task.toString());
        return taskDao.addNewTask(task);
    }

    @Override
    public boolean changeTaskBasicInfoById(int id, String info_title, String info,String classify,
                                           double money, int num_picture, Date start, Date End,double property) {
        Task task = null;
        try{
            task = taskDao.getTaskById(id);
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (task == null){
            return false;
        }
        if (info_title != null){
            task.setTask_info_title(info_title);
        }
        if (info != null){
            task.setTask_info(info);
        }
        if (money != 0){
            task.setGive_money(money);
        }
        if (num_picture != -1){
            task.setNum_of_picture(num_picture);
        }
        if (classify != null){
            task.setInfo_class(classify);
        }
        if (start != null){
            task.setStart_day(start);
        }
        if (End != null){
            task.setEnd_day(End);
        }
        if (property > 0){
            task.setProperty(property);
        }
        taskDao.updateTask(task);
        return true;
    }

    @Override
    public boolean changeTaskBasicInfoByName(String name, String info_title, String info,
                                             String classify, double money, int num_picture,
                                             Date start, Date End,double property) {
        Task task = null;
        try{
            task = taskDao.getTaskByName(name);
        }catch(Exception e){
            // e.printStackTrace();
        }
        if (task == null){
            return false;
        }
        if (info_title != null){
            task.setTask_info_title(info_title);
        }
        if (info != null){
            task.setTask_info(info);
        }
        if (money != 0){
            task.setGive_money(money);
        }
        if (classify != null){
            task.setInfo_class(classify);
        }
        if (num_picture != -1){
            task.setNum_of_picture(num_picture);
        }
        if (start != null){
            task.setStart_day(start);
        }
        if (End != null){
            task.setEnd_day(End);
        }
        if (property > 0){
            task.setProperty(property);
        }
        taskDao.updateTask(task);
        return true;
    }

    @Override
    public boolean changeTaskTextById(List<String> subtitle, List<String> subinfo,
                                      List<String> picture_info, int id) {
        Task task = null;
        try{
            task = taskDao.getTaskById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (task == null){
            return false;
        }
        if (subtitle != null && subtitle.size() != 0){
            task.setSub_task_info_title(subtitle);
        }
        if (subinfo != null && subinfo.size() != 0){
            task.setSub_task_info(subinfo);
        }
        if (picture_info != null && picture_info.size() != 0){
            task.setSub_task_img_info(picture_info);
        }
        taskDao.updateTask(task);
        return true;
    }

    @Override
    public boolean changeTaskTextByName(List<String> subtitle, List<String> subinfo,
                                        List<String> picture_info, String name) {
        Task task = null;
        try{
            task = taskDao.getTaskByName(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (task == null){
            return false;
        }
        if (subtitle != null && subtitle.size() != 0){
            task.setSub_task_info_title(subtitle);
        }
        if (subinfo != null && subinfo.size() != 0){
            task.setSub_task_info(subinfo);
        }
        if (picture_info != null && picture_info.size() != 0){
            task.setSub_task_img_info(picture_info);
        }
        taskDao.updateTask(task);
        return true;
    }

    @Override
    public Task getTaskProperty(int id) {
        return taskDao.getTaskById(id);
    }

    @Override
    public Task getTaskPropertyByName(String name) {
        return taskDao.getTaskByName(name);
    }

    @Override
    public List<Task> getTaskByEnterprise(String enterprise_username) {
        return taskDao.getTaskByEnterprise(enterprise_username);
    }

    @Override
    public List<Task> getTaskByClass(List<String> targe_class, int num) {
        return null;
    }

    @Override
    public List<Task> getTaskEnded(int start, int end) {
        return taskDao.gettaskStop(start, end);
    }

    @Override
    public List<Task> getTaskNotEnded(int start, int end) {
        return taskDao.getTaskNotStop(start, end);
    }

    @Override
    public List<Task> getTaskByClassify(String target) {
        return taskDao.getTaskByClassify(target);
    }

    @Override
    public List<Task> getTaskOrderByHot(int start, int end) {
        return taskDao.getTaskOrderByView(start, end);
    }

    @Override
    public List<Task> getTaskOrderByMoney(int start, int end) {
        return taskDao.getTaskOrderByMoney(start, end);
    }

    @Override
    public boolean readTask(int[] ids) {
        return taskDao.readTask(ids);
    }

    @Override
    public List<Task> getAllTask() {
        return taskDao.getAllTask();
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public EnterpriseDao getEnterpriseDao() {
        return enterpriseDao;
    }

    public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }
}
