package net.Ken365X.Dao;

import net.Ken365X.Entity.Masterprise;

import java.util.List;

/**
 * Created by mac on 2018/7/12.
 */
public interface MasterpriseDao {
    Masterprise getMasterpriseById(int id);
    Masterprise getMasterpriseByUsernameAndTaskid(String username,int task_id);
    Masterprise getMasterpriseByUsernameAndTaskName(String username,String taskname);
    boolean deleteMasterpriseById(int id);
    boolean addNewMasterprise(Masterprise masterprise);
    boolean updateMasterprise(Masterprise masterprise);
    List<Masterprise> getMasterpriceByTask(int Task_id);
    List<Masterprise> getMasteroriceByDesignerName(String username);
    List<Masterprise> getMasterpriceByLike(int start,int end);
    List<Masterprise> getMasterpriceByShare(int start,int end);
    List<Masterprise> getMasterpiceByView(int start,int end);
    boolean setMasterpriseStage(int id,int stage);
    List<Masterprise> getMasterpieceSuccess();
    List<Masterprise> getAllMasterpiece();
}
