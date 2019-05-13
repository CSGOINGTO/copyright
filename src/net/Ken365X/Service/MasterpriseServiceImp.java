package net.Ken365X.Service;

import net.Ken365X.Dao.DesignerDao;
import net.Ken365X.Dao.MasterpriseDao;
import net.Ken365X.Dao.TaskDao;
import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Entity.Task;
import sun.security.krb5.internal.crypto.Des;

import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/12.
 */
public class MasterpriseServiceImp implements MasterpriseService {
    private MasterpriseDao masterpriseDao;
    private DesignerDao designerDao;
    private TaskDao taskDao;
    @Override
    public boolean addNewMasterprise(String designer_username, int task_id,
                                     String info,int num_video, String videoInfo,
                                     int num_picture, List<String> picture_info) {
        Masterprise masterprise = masterpriseDao.getMasterpriseByUsernameAndTaskid(designer_username,task_id);
        if (masterprise == null){
            masterprise = new Masterprise();
            masterprise.setCreate_date(new Date());
            masterprise.setVideo_info(videoInfo);
            masterprise.setNumber_of_video(num_video);
            Designer designer = designerDao.getDesignerByName(designer_username);
            if (designer == null){
                return false;
            }
            masterprise.setDesigner(designer);
            masterprise.setNumber_of_picture(num_picture);
            masterprise.setPicture_annotation(picture_info);
            masterprise.setInfo(info);
            Task task = taskDao.getTaskById(task_id);
            if (task == null){
                return false;
            }
            masterprise.setTask(task);
            return  masterpriseDao.addNewMasterprise(masterprise);
        }else {
            return false;
        }
    }

    @Override
    public boolean updateMasterprise(String designer_username, int task_id, String info,
                                      int num_video,String videoInfo, int num_picture,
                                     List<String> picture_info) {
        Masterprise masterprise = masterpriseDao.getMasterpriseByUsernameAndTaskid(designer_username,task_id);
        if (masterprise == null){
            return false;
        }
        if (info != null){
            masterprise.setInfo(info);
        }
        if (videoInfo != null){
            masterprise.setVideo_info(videoInfo);
        }
        if (num_picture != -1){
            masterprise.setNumber_of_picture(num_picture);
        }
        if (num_video != -1)
        if (picture_info != null){
            masterprise.setPicture_annotation(picture_info);
        }
        return masterpriseDao.updateMasterprise(masterprise);
    }

    @Override
    public boolean updateMasterpriseById(int id, String info, int num_video, String videoInfo, int num_picture, List<String> picture_info,String video_prefix) {
        Masterprise masterprise = masterpriseDao.getMasterpriseById(id);
        if (masterprise == null){
            return false;
        }
        if (info != null){
            masterprise.setInfo(info);
        }
        if (video_prefix != null){
            masterprise.setVideo_prefix(video_prefix);
        }
        if (videoInfo != null){
            masterprise.setVideo_info(videoInfo);
        }
        if (num_picture != -1){
            masterprise.setNumber_of_picture(num_picture);
        }
        if (num_video != -1){
            masterprise.setNumber_of_video(num_video);
        }
            if (picture_info != null){
                masterprise.setPicture_annotation(picture_info);
            }
        return masterpriseDao.updateMasterprise(masterprise);
    }

    @Override
    public Masterprise getMasterpriseById(int id) {
        return masterpriseDao.getMasterpriseById(id);
    }

    @Override
    public Masterprise getMasterpriseByUsernameAndTaskName(String username, String taskname) {
        return masterpriseDao.getMasterpriseByUsernameAndTaskName(username,taskname);
    }


    @Override
    public boolean deleteMasterpriseById(int id) {
        return masterpriseDao.deleteMasterpriseById(id);
    }

    @Override
    public List<Masterprise> getMasterpriseByDesigner(String username) {
        return masterpriseDao.getMasteroriceByDesignerName(username);
    }

    @Override
    public List<Masterprise> getMasterpriseByTask(int task_id) {
        return masterpriseDao.getMasterpriceByTask(task_id);
    }

    @Override
    public List<Masterprise> getMasterpriseByView(int start, int end) {
        return masterpriseDao.getMasterpiceByView(start, end);
    }

    @Override
    public List<Masterprise> getMasterpriseByLike(int start, int end) {
        return masterpriseDao.getMasterpriceByLike(start,end);
    }

    @Override
    public List<Masterprise> getMasterpriseByShare(int start, int end) {
        return masterpriseDao.getMasterpriceByShare(start, end);
    }

    @Override
    public boolean addShareToMasterprice(String username, int masterprice_id) {
        Designer  designer = designerDao.getDesignerByName(username);
        if (designer == null){
            return false;
        }
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }
        if (masterprise.getShare_designer_username().contains(designer.getUsername())){
            return false;
        } else {
            masterprise.getShare_designer_username().add(username);
            designer.setCredit(designer.getCredit() + 1);

            // TODO
            // designer add money logical

            return masterpriseDao.updateMasterprise(masterprise);
        }
    }

    @Override
    public boolean addLikeToMasterprice(String username, int masterprice_id) {
        Designer  designer = designerDao.getDesignerByName(username);
        if (designer == null){
            return false;
        }
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }
        if (masterprise.getLike_designer_username().contains(designer.getUsername())){
            return false;
        } else {
            masterprise.getLike_designer_username().add(username);
            return masterpriseDao.updateMasterprise(masterprise);
        }
    }

    @Override
    public boolean addViewToMasterprice(int id) {
        Masterprise masterprise = masterpriseDao.getMasterpriseById(id);
        if (masterprise == null){
            return false;
        }
        masterprise.setView(masterprise.getView() + 1);
        return masterpriseDao.updateMasterprise(masterprise);
    }

    @Override
    public boolean addCommentToMasterprice(String username, int masterprice_id, String comment) {
        Designer designer = designerDao.getDesignerByName(username);
        if (designer == null){
            return false;
        }
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }
        masterprise.getComments().add(comment);
        masterprise.getComments_designer_username().add(username);
        return masterpriseDao.updateMasterprise(masterprise);
    }

    @Override
    public Masterprise getMasterpriseByUsernameAndTaskID(String username, int task_di) {
        return masterpriseDao.getMasterpriseByUsernameAndTaskid(username,task_di);
    }

    @Override
    public List<Masterprise> getAllSueccess() {
        return masterpriseDao.getMasterpieceSuccess();
    }

    @Override
    public List<Masterprise> getAllMasterpiece() {
        return masterpriseDao.getAllMasterpiece();
    }

    public MasterpriseDao getMasterpriseDao() {
        return masterpriseDao;
    }

    public void setMasterpriseDao(MasterpriseDao masterpriseDao) {
        this.masterpriseDao = masterpriseDao;
    }

    public DesignerDao getDesignerDao() {
        return designerDao;
    }

    public void setDesignerDao(DesignerDao designerDao) {
        this.designerDao = designerDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
