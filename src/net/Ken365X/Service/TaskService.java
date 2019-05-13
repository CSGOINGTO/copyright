package net.Ken365X.Service;

import net.Ken365X.Entity.Task;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/12.
 */
public interface TaskService {
    boolean addNewTask(String name, String info_title, String info,String info_class,
                        double money, int num_picture, Date start, Date End,
                        List<String> sub_title, List<String> sub_info, List<String> image_info,
                       String enterprise_username,double property, Date start_day2, Date end_day2);
    boolean changeTaskBasicInfoById(int id , String info_title, String info,String classify,
                           double money, int num_picture, Date start, Date End,double property);
    boolean changeTaskBasicInfoByName(String name , String info_title, String info, String classify,
                                    double money, int num_picture, Date start, Date End,double property);
    boolean changeTaskTextById(List<String> subtitle,List<String> subinfo,
                           List<String> picture_info,int id);
    boolean changeTaskTextByName(List<String> subtitle,List<String> subinfo,
                               List<String> picture_info,String name);
    Task getTaskProperty(int id);
    Task getTaskPropertyByName(String name);
    List<Task> getTaskByEnterprise(String enterprise_username);
    List<Task> getTaskByClass(List<String> targe_class,int num);
    List<Task> getTaskEnded(int start,int end);
    List<Task> getTaskNotEnded(int start,int end);
    List<Task> getTaskOrderByMoney(int start,int end);
    List<Task> getTaskOrderByHot(int start,int end);
    List<Task> getTaskByClassify(String target);
    List<Task> getAllTask();
    boolean readTask(int [] ids);
}
