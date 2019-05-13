package net.Ken365X.Utils;

/**
 * Created by mac on 2018/7/21.
 */


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Description：用此类将图片转换为字符串，以便将图片封装为JSON进行传输
 *
 * @author 傅川溥
 * @version 1.0
 * @Date 2018-05-27
 */
public class ImgHelper {

    /**
     * TODO:将byte数组以Base64方式编码为字符串
     *
     * @param bytes 待编码的byte数组
     * @return 编码后的字符串
     */
    public static String encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * TODO:将以Base64方式编码的字符串解码为byte数组
     *
     * @param encodeStr 待解码的字符串
     * @return 解码后的byte数组
     * @throws IOException
     */
    public static byte[] decode(String encodeStr) throws IOException {
        byte[] bt = null;
        BASE64Decoder decoder = new BASE64Decoder();
        bt = decoder.decodeBuffer(encodeStr);
        return bt;
    }

    /**
     * TODO:将两个byte数组连接起来后，返回连接后的Byte数组
     *
     * @param front 拼接后在前面的数组
     * @param after 拼接后在后面的数组
     * @return 拼接后的数组
     */
    public static byte[] connectBytes(byte[] front, byte[] after) {
        byte[] result = new byte[front.length + after.length];
        System.arraycopy(front, 0, result, 0, after.length);
        System.arraycopy(after, 0, result, front.length, after.length);
        return result;
    }

    /**
     * TODO:将图片以Base64方式编码为字符串
     *
     * @param imgUrl 图片的绝对路径
     * @return 编码后的字符串
     * @throws IOException
     */
    public static String encodeImage(String imgUrl) throws IOException {
        FileInputStream fis = new FileInputStream(imgUrl);
        byte[] rs = new byte[fis.available()];
        fis.read(rs);
        fis.close();
        return encode(rs);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        test_One();
    }

    // test for file interface

    public static void test_One(){
        String str1 = null;
        String str2 = null;
        try {
            str1 = encodeImage("/Users/mac/Desktop/one.png");
            // str2 = encodeImage("/Users/mac/Desktop/one.png");
            str2 = encodeImage("/Users/mac/Desktop/two.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject req = new JSONObject();
        req.put("id",1);

        JSONArray prefixs = new JSONArray();
        prefixs.put(".png");
        prefixs.put(".jpg");
        req.put("prefix",prefixs);

        JSONArray base64s = new JSONArray();
        base64s.put(str1);
        base64s.put(str2);
        req.put("base64s",base64s);

        final String requestdata = req.toString();
        System.out.println(requestdata);

        new Thread(() -> {
            // http request
            try {
                // for Debug
                String path = "http://localhost:8080/upload_image_Masterpiece.action";
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("ser-Agent", "Fiddler");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                os.write(requestdata.getBytes());
                os.close();

                int code = connection.getResponseCode();
                String responsedata;

                // 200 is success
                if(code == 200){
                    InputStream is = connection.getInputStream();
                    responsedata = StreamUtil.inputStream2String(is);
                    System.out.println(responsedata);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }

    public static void test_Two(){
        String str1 = null;
        String str2 = null;
        try {
            str1 = encodeImage("/Users/mac/Desktop/one.png");
            // str2 = encodeImage("/Users/mac/Desktop/one.png");
            str2 = encodeImage("/Users/mac/Desktop/two.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject req = new JSONObject();
        req.put("id",1);

        JSONArray prefixs = new JSONArray();
        prefixs.put(".png");
        prefixs.put(".jpg");
        req.put("prefix",prefixs);

        JSONArray base64s = new JSONArray();
        base64s.put(str1);
        base64s.put(str2);
        req.put("base64s",base64s);

        final String requestdata = req.toString();

        new Thread(() -> {
            // http request
            try {
                // for Debug
                String path = "http://localhost:8080/upload_image_Task.action";
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("ser-Agent", "Fiddler");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                os.write(requestdata.getBytes());
                os.close();

                int code = connection.getResponseCode();
                String responsedata;

                // 200 is success
                if(code == 200){
                    InputStream is = connection.getInputStream();
                    responsedata = StreamUtil.inputStream2String(is);
                    System.out.println(responsedata);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }

    public static void test_Three(){
        String str1 = null;
        try {
            str1 = encodeImage("/Users/mac/Desktop/A005_C029_0925TO_001.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject req = new JSONObject();
        req.put("id",1);
        req.put("prefix",".mp4");
        req.put("base64",str1);
        final String requestdata = req.toString();
        new Thread(() -> {
            // http request
            try {
                // for Debug
                String path = "http://localhost:8080/upload_video_Masterpiece.action";
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("ser-Agent", "Fiddler");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                os.write(requestdata.getBytes());
                os.close();

                int code = connection.getResponseCode();
                String responsedata;

                // 200 is success
                if(code == 200){
                    InputStream is = connection.getInputStream();
                    responsedata = StreamUtil.inputStream2String(is);
                    System.out.println(responsedata);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
    }
}
