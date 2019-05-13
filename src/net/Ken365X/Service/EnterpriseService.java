package net.Ken365X.Service;

import net.Ken365X.Entity.Enterprise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public interface EnterpriseService {
    int addNewEnterprise(String username,String password,String phonenum,String name,
                         String realm,String email,String info);
    int loginEnterprise(String usernmae,String password);
    int loginEnterpriseByEmail(String email,String password);
    boolean updateEnterpriseInfoByUsername(String username, String name, String realm,
                                           String email,String info);
    boolean updateEnterprisePasswordByUsername(String username,String password,String newPassword);
    Enterprise getInfoOfEnterpriseByUsername(String username);
    boolean chooseMasterpriceOne(int task_id, List<Integer> masterprice_id);
    boolean setScoreMasterprice(int task_id ,int masterprice_id, int score);
    boolean change_stage(int task_id,int stage);
}
