package net.Ken365X.Service;

import net.Ken365X.Entity.Masterprise;

import java.util.List;

/**
 * Created by mac on 2018/7/12.
 */
public interface MasterpriseService {
    boolean addNewMasterprise(String designer_username, int task_id, String info,int num_video,
                              String videoInfo, int num_picture, List<String> picture_info);
    boolean updateMasterprise(String designer_username, int task_id,String info,int num_video,
                              String videoInfo,int num_picture,List<String> picture_info);
    boolean updateMasterpriseById(int id,String info,int num_video, String videoInfo,
                                  int num_picture,List<String> picture_info,String video_prefix);
    Masterprise getMasterpriseById(int id);
    Masterprise getMasterpriseByUsernameAndTaskName(String username,String taskname);
    Masterprise getMasterpriseByUsernameAndTaskID(String username,int task_di);
    boolean deleteMasterpriseById(int id);
    List<Masterprise> getMasterpriseByDesigner(String username);
    List<Masterprise> getMasterpriseByTask(int task_id);
    List<Masterprise> getMasterpriseByView(int start,int end);
    List<Masterprise> getMasterpriseByLike(int start,int end);
    List<Masterprise> getMasterpriseByShare(int start,int end);
    boolean addLikeToMasterprice(String username,int masterprice_id);
    boolean addShareToMasterprice(String username,int masterprice_id);
    boolean addViewToMasterprice(int materpiece_id);
    boolean addCommentToMasterprice(String username, int masterprice_id,String comment);
    List<Masterprise> getAllSueccess();
    List<Masterprise> getAllMasterpiece();
 }
