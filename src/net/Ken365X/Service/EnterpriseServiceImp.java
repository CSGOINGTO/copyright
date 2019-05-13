package net.Ken365X.Service;

import net.Ken365X.Dao.EnterpriseDao;
import net.Ken365X.Dao.MasterpriseDao;
import net.Ken365X.Dao.TaskDao;
import net.Ken365X.Entity.Enterprise;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Entity.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public class EnterpriseServiceImp implements  EnterpriseService{
    private EnterpriseDao enterpriseDao;
    private MasterpriseDao masterpriseDao;
    private TaskDao taskDao;

    public static final int SAME_USERNAME_USED = -1;
    public static final int ADD_ENTERPRISE_SUCCESS = 0;

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAIL_NOUSER = -1;
    public static final int LOGIN_FAIL_PASSWORDWRONG = -2;

    public static final boolean CHANGE_PSD_SUCCESS = true;
    public static final boolean CHANGE_PSD_FAIL = false;

    public static final boolean UPDATE_ENTERPRISE_INFO_FALI = false;
    public static final boolean UPDATE_ENTERPRISE_INFO_SUCCESS = true;

    @Override
    public int addNewEnterprise(String username, String password,
                                String phonenum, String name, String realm,
                                String email, String info) {
        boolean res = enterpriseDao.addNewEnterprise(username, password, phonenum, name, realm, email, info);
        if (res == false){
            return SAME_USERNAME_USED;
        }else {
            return ADD_ENTERPRISE_SUCCESS;
        }
    }

    @Override
    public int loginEnterprise(String usernmae, String password) {
        Enterprise enterprise = null;
        try{
            enterprise = enterpriseDao.getEnterpriseByName(usernmae);
        }catch(Exception e){
            // e.printStackTrace();
            return -1;
        }
        if(enterprise == null){
            return LOGIN_FAIL_NOUSER;
        }
        if (enterprise.getPassword().equals(password)){
            return LOGIN_SUCCESS;
        }else {
            return LOGIN_FAIL_PASSWORDWRONG;
        }
    }

    @Override
    public int loginEnterpriseByEmail(String email, String password) {
        Enterprise enterprise = null;
        try{
            enterprise = enterpriseDao.getEnterpriseByEmail(email);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        if(enterprise == null){
            return LOGIN_FAIL_NOUSER;
        }
        if (enterprise.getPassword().equals(password)){
            return LOGIN_SUCCESS;
        }else {
            return LOGIN_FAIL_PASSWORDWRONG;
        }
    }

    @Override
    public boolean updateEnterpriseInfoByUsername(String username,
                                                  String name, String realm,
                                                  String email, String info) {
        Enterprise enterprise = enterpriseDao.getEnterpriseByName(username);
        if (enterprise == null) {
            // No record
            return false;
        }
        if(name != null){
            enterprise.setName(name);
        }
        if(realm != null){
            enterprise.setRealm(realm);
        }
        if(email != null){
            enterprise.setEmail(email);
        }
        if(info != null){
            enterprise.setInfo(info);
        }
        enterpriseDao.updateEnterprise(enterprise);
        return true;
    }

    @Override
    public boolean updateEnterprisePasswordByUsername(String username, String password,
                                                      String newPassword) {
        System.out.println(username);
        Enterprise enterprise = enterpriseDao.getEnterpriseByName(username);
        if (enterprise == null){
            return CHANGE_PSD_FAIL;
        }
        if (!enterprise.getPassword().equals(password)){
            return CHANGE_PSD_FAIL;
        }
        // System.out.println(newPassword + "newpsd");
        enterprise.setPassword(newPassword);
        enterpriseDao.updateEnterprise(enterprise);
        return CHANGE_PSD_SUCCESS;
    }

    @Override
    public Enterprise getInfoOfEnterpriseByUsername(String username) {
        Enterprise enterprise = enterpriseDao.getEnterpriseByName(username);
        return enterprise;
    }

    @Override
    public boolean chooseMasterpriceOne(int task_id, List<Integer> masterprice_id) {
        Task task = taskDao.getTaskById(task_id);
        List<Masterprise> list = new ArrayList<>();
        if (task == null) {
            return false;
        }
        for (Integer i:
             masterprice_id) {
            Masterprise masterprise = masterpriseDao.getMasterpriseById(i);
            // System.out.println(masterprise.getMasterprise_id());

            // change the stage of masterprice
            masterprise.setStage(1);
            // write down the logical here
            masterprise.setMoney_A(100);

            masterpriseDao.updateMasterprise(masterprise);
        }

        // change the stage of task
        // task.setStage(1);
        // taskDao.updateTask(task);
        return true;
    }

    @Override
    public boolean setScoreMasterprice(int task_id, int masterprice_id, int score) {
        Task task = taskDao.getTaskById(task_id);
        if (task == null){
            return false;
        }
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }
        if (masterprise.getTask().getTask_id() != task_id){
            return false;
        }
        masterprise.setScore_enterprise(score);

        // the logical of score shared

        // get the total of the share
        int total = 0;
        List<Masterprise> masterprises = masterpriseDao.getMasterpriceByTask(task_id);
        List<Masterprise> masterprises1 = new ArrayList<>();
        for (Masterprise m :
                masterprises) {
            if (m.getStage() == 1 || m.getStage() == 2){
                total += m.getShare_designer_username().size();
            }
            if (m.getStage() == 1 && m.getScore_enterprise() == 0){
                masterprises1.add(m);
            }
        }
        if (total == 0 ){
            total = 1;
        }
        // Equation of score computed
        int final_score = (int) (task.getProperty() * score + (1 - task.getProperty()) *
                (masterprise.getShare_designer_username().size() * 100 / total));

        masterprise.setFinal_score(final_score);
        masterprise.setStage(2);

        // if the last score given
        if (masterprises1.size() == 1 && masterprises1.get(0).getMasterprise_id() == masterprice_id){
            // the last of the materprice in take was scored
            // shift the task to 2
            task.setStage(2);
        }
        masterpriseDao.updateMasterprise(masterprise);
        // taskDao.updateTask(task);
        return true;

    }

    @Override
    public boolean change_stage(int task_id, int stage) {
        Task task = taskDao.getTaskById(task_id);
        task.setStage(stage);
        return taskDao.updateTask(task);
    }

    public EnterpriseDao getEnterpriseDao() {
        return enterpriseDao;
    }

    public void setEnterpriseDao(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }

    public MasterpriseDao getMasterpriseDao() {
        return masterpriseDao;
    }

    public void setMasterpriseDao(MasterpriseDao masterpriseDao) {
        this.masterpriseDao = masterpriseDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
