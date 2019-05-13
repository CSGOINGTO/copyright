package net.Ken365X.Service;

import net.Ken365X.Dao.MasterpriseDao;
import net.Ken365X.Dao.TaskDao;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Entity.Task;
import net.Ken365X.Utils.FileUtil;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/7/8.
 */
public class FileServiceImp implements FileService {
    private MasterpriseDao masterpriseDao;
    private TaskDao taskDao;

    @Override
    public boolean saveFile(String filename, String base64) {
        String s = ServletActionContext.getServletContext().getRealPath("./UserFile1");
        return FileUtil.base642File(base64,filename,s);
    }

    @Override
    public String getFile(String filename) {
        String s = ServletActionContext.getServletContext().getRealPath("./Masterpiece");
        return FileUtil.file2Base64(s + "/"+ filename);
    }

    @Override
    public boolean deleteFile(String filename) {
        return FileUtil.deleteFileByName("UserFile1" + filename);
    }

    @Override
    public boolean saveTaskImages(int task_id, List<String> filePrefix, List<String> base64s) {
        if (filePrefix.size() != base64s.size()){
            return false;
        }
        Task task = taskDao.getTaskById(task_id);
        if (task == null){
            return false;
        }
        if (task.getNum_of_picture() != filePrefix.size()){
            return false;
        }
        // clear the prefix of file
        task.getPicture_prefix().clear();

        // clear the old picture
        String s = ServletActionContext.getServletContext().getRealPath("./Task");
        File file = new File(s);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        File [] files = file.listFiles();
        for (File f :
                files) {
            String name = f.getName();
            String id_s = name.substring(0,name.indexOf('_'));
            int id_i = Integer.valueOf(id_s);
            if (id_i == task_id){
                f.delete();
            }
        }
        for (int i = 0; i < filePrefix.size(); i++) {
            task.getPicture_prefix().add(filePrefix.get(i));
            String filename = task_id + "" + "_" + i + filePrefix.get(i);
            FileUtil.base642File(base64s.get(i), filename, s);
        }
        return taskDao.updateTask(task);
    }

    @Override
    public boolean saveMasterpriceImage(int masterprise_id, List<String> filePrefix, List<String> base64s) {
        if (filePrefix.size() != base64s.size()){
            return false;
        }
        Masterprise masterprise =masterpriseDao.getMasterpriseById(masterprise_id);
        if (masterprise == null){
            return false;
        }
        if (masterprise.getNumber_of_picture() != filePrefix.size()){
            return false;
        }
        masterprise.getPicture_prefix().clear();

        String s = ServletActionContext.getServletContext().getRealPath("./Masterpiece");
        File file = new File(s);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        File [] files = file.listFiles();
        for (File f :
                files) {
            String name = f.getName();
            String id_s = name.substring(0,name.indexOf('_'));
            int id_i = Integer.valueOf(id_s);
            if (id_i == masterprise_id){
                f.delete();
            }
        }

        for (int i = 0; i < filePrefix.size(); i++) {
            masterprise.getPicture_prefix().add(filePrefix.get(i));
            String filename = masterprise_id + "" + "_" + i + filePrefix.get(i);
            FileUtil.base642File(base64s.get(i), filename, s);
        }
        return masterpriseDao.updateMasterprise(masterprise);
    }

    @Override
    public boolean uploadMasterpieceVideo(int masterprice_id, String filePrefix, String base64) {
        Masterprise masterprise = masterpriseDao.getMasterpriseById(masterprice_id);
        if (masterprise == null){
            return false;
        }

        // clear the prefix of file
        masterprise.setVideo_prefix(filePrefix);

        // clear the old picture
        String s = ServletActionContext.getServletContext().getRealPath("./Video");
        File file = new File(s);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        File [] files = file.listFiles();
        for (File f :
                files) {
            String name = f.getName();
            String id_s = name.substring(0,name.indexOf('.'));
            int id_i = Integer.valueOf(id_s);
            if (id_i == masterprice_id){
                f.delete();
            }
        }

        String filename = masterprice_id + filePrefix;
        FileUtil.base642File(base64, filename, s);
        masterprise.setNumber_of_video(1);
        return masterpriseDao.updateMasterprise(masterprise);
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
