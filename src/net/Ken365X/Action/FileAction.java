package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Service.FileService;
import net.Ken365X.Service.MasterpriseService;
import net.Ken365X.Service.RootServiceImp;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.Ken365X.Action.MessageAction.LoseOfImportantParam;

/**
 * Created by mac on 2018/7/8.
 * Action used for file environment test
 */
public class FileAction extends ActionSupport {
    private FileService fileService;
    private MasterpriseService masterpriseService;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    public static final int JSONSemanticError = 3;

    {
        // get the instance of request and response
        httpServletRequest = ServletActionContext.getRequest();
        httpServletResponse = ServletActionContext.getResponse();
    }

    public String Upload() throws Exception {
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        // System.out.println(content);
        JSONObject jsonObject = new JSONObject(content);
        int sym = 1;
        try {
            if (fileService.saveFile(jsonObject.getString("filename"),
                    jsonObject.getString("base64"))){
                sym = 0;
            }
        }catch (JSONException e){
            sym = 1;
        }catch(Exception e){
            e.printStackTrace();
        }

        int res_code;
        String res_str;
        if (sym == 0){
            res_code = 0;
            res_str = "success";
        }else {
            res_code = 1;
            res_str = "failed";
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_str);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String Download() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        System.out.println(content);
        JSONObject jsonObject = new JSONObject(content);

        String result = null;
        int sym = 1;
        try {
                result = fileService.getFile(jsonObject.getString("filename"));
                if (result == null){
                    sym = 1;
                }else{
                    sym = 0;
                }

        }catch (JSONException e){
            sym = JSONSemanticError;
        }catch(Exception e){
            e.printStackTrace();
        }

        int res_code;
        if (sym == 0){
            res_code = 0;
        }else {
            res_code = 1;
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("base64",result);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String addImageToMasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        // System.out.println(content);
        JSONObject jsonObject;

        int masterprise_id = 0;
        List<String> prifix = null;
        List<String> base64s = null;
        boolean res = false;

        int res_code = -1;
        String res_info = null;
        try {
            jsonObject = new JSONObject(content);

            if (jsonObject.has("id") && jsonObject.has("prefix")
                    && jsonObject.has("base64s")){

                masterprise_id = jsonObject.getInt("id");
                JSONArray jsonArray1 = jsonObject.getJSONArray("prefix");
                JSONArray jsonArray2 = jsonObject.getJSONArray("base64s");
                prifix = (List<String>) (List) jsonArray1.toList();
                base64s = (List<String>) (List) jsonArray2.toList();

                res = fileService.saveMasterpriceImage(masterprise_id,prifix,base64s);

                if (res == false){
                    res_code = -1 ;
                    res_info = "Add file fail";
                }else {
                    res_code = 0;
                    res_info = "Add file Success";
                }
            }else{
                res_code = LoseOfImportantParam;
                res_info = "Lose Of Param";
            }
        }catch (JSONException e){
            res_code = JSONSemanticError;
            res_info = "Json Semantic worng";
        }catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String addImageToTask() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        // System.out.println(content);
        JSONObject jsonObject;

        int masterprise_id = 0;
        List<String> prifix = null;
        List<String> base64s = null;
        boolean res = false;

        int res_code = -1;
        String res_info = null;
        try {
            jsonObject = new JSONObject(content);

            if (jsonObject.has("id") && jsonObject.has("prefix")
                    && jsonObject.has("base64s")){

                masterprise_id = jsonObject.getInt("id");
                JSONArray jsonArray1 = jsonObject.getJSONArray("prefix");
                JSONArray jsonArray2 = jsonObject.getJSONArray("base64s");
                prifix = (List<String>) (List) jsonArray1.toList();
                base64s = (List<String>) (List) jsonArray2.toList();

                res = fileService.saveTaskImages(masterprise_id,prifix,base64s);

                if (res == false){
                    res_code = -1 ;
                    res_info = "Add file fail";
                }else {
                    res_code = 0;
                    res_info = "Add file Success";
                }
            }else{
                res_code = LoseOfImportantParam;
                res_info = "Lose Of Param";
            }
        }catch (JSONException e){
            res_code = JSONSemanticError;
            res_info = "Json Semantic worng";
        }catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String addVidoToMasterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        // System.out.println(content);
        JSONObject jsonObject;

        int masterprise_id = 0;
        String prifix = null;
        String base64s = null;
        boolean res = false;

        int res_code = -1;
        String res_info = null;
        try {
            jsonObject = new JSONObject(content);

            if (jsonObject.has("id") && jsonObject.has("prefix")
                    && jsonObject.has("base64")){

                masterprise_id = jsonObject.getInt("id");
                prifix = jsonObject.getString("prefix");
                base64s = jsonObject.getString("base64");

                res = fileService.uploadMasterpieceVideo(masterprise_id,prifix,base64s);

                if (res == false){
                    res_code = -1 ;
                    res_info = "Add file fail";
                }else {
                    res_code = 0;
                    res_info = "Add file Success";
                }
            }else{
                res_code = LoseOfImportantParam;
                res_info = "Lose Of Param";
            }
        }catch (JSONException e){
            e.printStackTrace();
            res_code = JSONSemanticError;
            res_info = "Json Semantic worng";
        }catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String getMasterpieceBase64() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = new JSONObject(content);

        List<String> base64s = new ArrayList<>();
        int sym = 0;
        int id = 0;
        try {
            id = jsonObject.getInt("id");
            Masterprise masterprise = masterpriseService.getMasterpriseById(id);
            int j = 0;
            while (true){
                try{
                    String s = new String();
                    s += id;
                    s = s + "_" + j + masterprise.getPicture_prefix().get(j);
                    String tar = fileService.getFile(s);
                    System.out.println("A");
                    System.out.println(s);
                    System.out.println("AA");
                    if (tar == null){
                        break;
                    } else {
                        base64s.add(tar);
                        j++;
                    }
                }catch(Exception e){
                    break;
                }
            }

        }catch (JSONException e){
            sym = JSONSemanticError;
        }catch(Exception e){
            e.printStackTrace();
        }

        int res_code = 0;

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        if (sym == 0){
            JSONArray jsonArray = new JSONArray();
            for (String s :
                    base64s) {
                jsonArray.put(s);
            }
            jsonObject1.put("base64",jsonArray);
        }

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;

    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public MasterpriseService getMasterpriseService() {
        return masterpriseService;
    }

    public void setMasterpriseService(MasterpriseService masterpriseService) {
        this.masterpriseService = masterpriseService;
    }
}
