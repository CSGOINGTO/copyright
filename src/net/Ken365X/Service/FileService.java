package net.Ken365X.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/7/8.
 */
public interface FileService {
    boolean saveFile(String filename,String base64);
    String getFile(String filename);
    boolean deleteFile(String filename);
    boolean saveTaskImages(int task_id,List<String> filePrefix,List<String> base64s);
    boolean saveMasterpriceImage(int masterprise_id,List<String> filePrefix,List<String> base64s);
    boolean uploadMasterpieceVideo(int masterprise_id,String filePrefix,String base64);
}
