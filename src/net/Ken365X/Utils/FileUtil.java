package net.Ken365X.Utils;

import java.io.*;
import org.apache.commons.codec.binary.Base64;


/**
 * Created by mac on 2018/7/7.
 */
public class FileUtil {

    // convert the file to base64 code
    public static String file2Base64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            System.out.println(path);
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            byte[] base64bytes = Base64.encodeBase64(bytes);
            System.out.println(bytes.length);
            base64 = new String(base64bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    // convert the base64 code to file
    public static boolean base642File(String base64, String fileName,String filePath) {
        File file = null;
        // create the library for the user file
        File dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64);
            System.out.println(bytes.length);
            file=new File(filePath+"/"+fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @author fuchuanpu
     * @param pathAndName pathname and filename file
     *                    file type should be covered
     */
    public static boolean fileExists(String pathAndName){
        try
        {
            File f = new File(pathAndName);
            if(!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * @author fuchuanpu
     * @param pathAndName pathname and filename file
     *                    file type should be covered
     * @return success or not
     */
    public static boolean deleteFileByName(String pathAndName){
        try
        {
            File f = new File(pathAndName);
            if(!f.exists())
            {
                return false;
            }
            return f.delete();
        }
        catch (Exception e) {
            return false;
        }
    }
}
