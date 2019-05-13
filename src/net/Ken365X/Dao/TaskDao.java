package net.Ken365X.Dao;

import net.Ken365X.Entity.Task;

import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public interface TaskDao {
    Task getTaskByName(String name);
    Task getTaskById(int id);
    boolean addNewTask(Task task);
    boolean deleteTaskById(int id);
    boolean updateTask(Task task);
    boolean readTask(int[] number);
    List<Task> getTaskByClassify(String target);
    List<Task> getTaskOrderByMoney(int start,int end);
    List<Task> getTaskOrderByView(int start,int end);
    List<Task> getTaskByEnterprise(String username);
    List<Task> getTaskNotStop(int start,int end);
    List<Task> gettaskStop(int start,int end);
    List<Task> getAllTask();
}
