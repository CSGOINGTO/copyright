package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Service.FileService;
import net.Ken365X.Service.MasterpriseService;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.Ken365X.Action.MessageAction.LoseOfImportantParam;

/**
 * Created by mac on 2018/7/12.
 */
public class MasterpriseAction extends ActionSupport {
    private MasterpriseService masterpriseService;
    private FileService fileService;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    {
        // get the instance of request and response
        httpServletRequest = ServletActionContext.getRequest();
        httpServletResponse = ServletActionContext.getResponse();
    }

    public static final int NoErrorOccured = 0;
    public static final int JSONSemanticError = -101;
    public static final int ImportantParamIsNull = -1;
    public static final int DataFormatError = -2;
    public static final int AddMasterpriseFail = -3;
    public static final int Update_Error = -4;

    public static final int ChangeFail = -1;
    public static final int ChangeSuccess = 0;

    public static final int GetFail = -1;
    public static final int GetSuccess = 0;

    public String addNewMasterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;


        String designer_username = null;
        int task_id = 0;
        String info = null;
        int num_picture = -1;
        int num_video = -1;
        String video_info = null;
        List<String> picture_info = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("designer_username") && jsonObject.has("task_id") &&
                    jsonObject.has("info") && jsonObject.has("num_video") &&
                    jsonObject.has("num_picture") ){

                designer_username = jsonObject.getString("designer_username");
                if(designer_username.length() == 0 || designer_username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if (jsonObject.has("video_info")){
                    video_info = jsonObject.getString("video_info");
                }
                num_picture = jsonObject.getInt("num_picture");
                num_video = jsonObject.getInt("num_video");
                task_id = jsonObject.getInt("task_id");
                info = jsonObject.getString("info");

                if (jsonObject.has("video_info")) {
                    video_info = jsonObject.getString("video_info");
                }

                if (jsonObject.has("picture_info")){
                    JSONArray jsonArray = jsonObject.getJSONArray("picture_info");
                    picture_info = (List<String>) (List) jsonArray.toList();
                }


                if(res_code == NoErrorOccured){
                    boolean result = masterpriseService.addNewMasterprise(designer_username,
                            task_id,info,num_video,video_info,num_picture,picture_info);
                    if (result == false){
                        res_code = AddMasterpriseFail;
                        res_info = "Add masterprise fail.";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Add Success";
                    }
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

    public String changeMasterprisePropertyById() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;


        int masterprise_id;
        String info = null;
        int num_picture = -1;
        int num_video = -1;
        String video_info = null;
        String video_prefix = null;
        List<String> picture_info = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                masterprise_id = jsonObject.getInt("id");

                if (jsonObject.has("num_picture")){
                    num_picture = jsonObject.getInt("num_picture");
                }
                if (jsonObject.has("num_video")){
                    num_video = jsonObject.getInt("num_video");
                }
                if (jsonObject.has("video_info")) {
                    video_info = jsonObject.getString("video_info");
                }
                if (jsonObject.has("picture_info")){
                    JSONArray jsonArray = jsonObject.getJSONArray("picture_info");
                    picture_info = (List<String>) (List) jsonArray.toList();
                }
                if (jsonObject.has("video_prefix")){
                    video_prefix = jsonObject.getString("video_prefix");
                }
                if (jsonObject.has("info")){
                    info = jsonObject.getString("info");
                }


                if(res_code == NoErrorOccured){
                    boolean result = masterpriseService.updateMasterpriseById(masterprise_id,
                            info,num_video,video_info,num_picture,picture_info,video_prefix);
                    if (result == false){
                        res_code = ChangeFail;
                        res_info = "Change masterprise fail.";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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

    public String changeMasterprisePropertyByName() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;


        String designer_username = null;
        int task_id = 0;
        String info = null;
        int num_picture = -1;
        int num_video = -1;
        String video_info = null;
        List<String> picture_info = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("designer_username") && jsonObject.has("task_id")){

                designer_username = jsonObject.getString("designer_username");
                task_id = jsonObject.getInt("task_id");

                if (jsonObject.has("num_picture")){
                    num_picture = jsonObject.getInt("num_picture");
                }
                if (jsonObject.has("num_video")){
                    num_video = jsonObject.getInt("num_video");
                }
                if (jsonObject.has("video_info")) {
                    video_info = jsonObject.getString("video_info");
                }
                if (jsonObject.has("picture_info")){
                    JSONArray jsonArray = jsonObject.getJSONArray("picture_info");
                    picture_info = (List<String>) (List) jsonArray.toList();
                }

                if(res_code == NoErrorOccured){
                    boolean result = masterpriseService.updateMasterprise(designer_username,
                            task_id,info,num_video,video_info,num_picture,picture_info);
                    if (result == false){
                        res_code = ChangeFail;
                        res_info = "Change masterprise fail.";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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

    public String getMasterPropertyById() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        Masterprise masterprise = null;

        int masterprise_id;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                masterprise_id = jsonObject.getInt("id");

                if(res_code == NoErrorOccured){
                    masterprise = masterpriseService.getMasterpriseById(masterprise_id);
                    if (masterprise == null){
                        res_code = GetFail;
                        res_info = "Get masterprise property fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise property Success";
                    }
                }
            }else{
                res_code = LoseOfImportantParam;
                res_info = "Lose Of Param";
            }
        }catch (JSONException e){
            //System.out.println(content);
            res_code = JSONSemanticError;
            res_info = "Json Semantic worng";
        }catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        if (masterprise != null){
            jsonObject1.put("id",masterprise.getMasterprise_id());
            jsonObject1.put("designer_username",masterprise.getDesigner().getUsername());
            jsonObject1.put("designer_name",masterprise.getDesigner().getNick_name());
            jsonObject1.put("task_id",masterprise.getTask().getTask_id());
            jsonObject1.put("info",masterprise.getInfo());
            jsonObject1.put("stage",masterprise.getStage());
            jsonObject1.put("date",masterprise.getCreate_date().toString());
            jsonObject1.put("final_score",masterprise.getFinal_score());
            jsonObject1.put("money_A",masterprise.getMoney_A());
            jsonObject1.put("money_B",masterprise.getMoney_B());
            jsonObject1.put("money_C",masterprise.getMoney_C());
            jsonObject1.put("likes",masterprise.getLike_designer_username().size());
            jsonObject1.put("share",masterprise.getShare_designer_username().size());
            jsonObject1.put("view",masterprise.getView());
            jsonObject1.put("num_picture",masterprise.getNumber_of_picture());
            jsonObject1.put("num_video",masterprise.getNumber_of_video());
            jsonObject1.put("video_prefix",masterprise.getVideo_prefix());
            jsonObject1.put("video_info",masterprise.getVideo_info());

            List<String> comment = masterprise.getComments();
            if (comment != null){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        comment) {
                    jsonArray.put(s);
                }
                jsonObject1.put("comments",jsonArray);
            }

            List<String> comment_id = masterprise.getComments_designer_username();
            if (comment_id != null){
                JSONArray jsonArray = new JSONArray();
                for (String i :
                        comment_id) {
                    jsonArray.put(i);
                }
                jsonObject1.put("comment_username",jsonArray);
            }

            List<String> image_prefix = masterprise.getPicture_prefix();
            if (image_prefix != null && image_prefix.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        image_prefix){
                    jsonArray.put(s);
                }
                jsonObject1.put("image_prefix",jsonArray);
            }

            List<String> picture_anno = masterprise.getPicture_annotation();
            if (picture_anno != null){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        picture_anno) {
                    jsonArray.put(s);
                }
                jsonObject1.put("picture_anno",jsonArray);
            }
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

    public String getMasterPropertyByName() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        Masterprise masterprise = null;

        String username = null;
        String taskname = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("taskname")){

                username = jsonObject.getString("username");
                taskname = jsonObject.getString("taskname");

                if(res_code == NoErrorOccured){
                    masterprise = masterpriseService
                            .getMasterpriseByUsernameAndTaskName(username,taskname);
                    if (masterprise == null){
                        res_code = GetFail;
                        res_info = "Get masterprise property fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise property Success";
                    }
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
        if (masterprise != null){
            jsonObject1.put("id",masterprise.getMasterprise_id());
            jsonObject1.put("designer_username",masterprise.getDesigner().getUsername());
            jsonObject1.put("task_id",masterprise.getTask().getTask_id());
            jsonObject1.put("info",masterprise.getInfo());
            jsonObject1.put("stage",masterprise.getStage());
            jsonObject1.put("date",masterprise.getCreate_date().toString());
            jsonObject1.put("final_score",masterprise.getFinal_score());
            jsonObject1.put("money_A",masterprise.getMoney_A());
            jsonObject1.put("money_B",masterprise.getMoney_B());
            jsonObject1.put("money_C",masterprise.getMoney_C());
            jsonObject1.put("likes",masterprise.getLike_designer_username().size());
            jsonObject1.put("share",masterprise.getShare_designer_username().size());
            jsonObject1.put("view",masterprise.getView());
            jsonObject1.put("num_picture",masterprise.getNumber_of_picture());
            jsonObject1.put("video_prefix",masterprise.getVideo_prefix());
            jsonObject1.put("num_video",masterprise.getNumber_of_video());
            jsonObject1.put("video_info",masterprise.getVideo_info());

            List<String> comment = masterprise.getComments();
            if (comment != null){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        comment) {
                    jsonArray.put(s);
                }
                jsonObject1.put("comments",jsonArray);
            }

            List<String> comment_id = masterprise.getComments_designer_username();
            if (comment_id != null){
                JSONArray jsonArray = new JSONArray();
                for (String i :
                        comment_id) {
                    jsonArray.put(i);
                }
                jsonObject1.put("comment_username",jsonArray);
            }

            List<String> image_prefix = masterprise.getPicture_prefix();
            if (image_prefix != null && image_prefix.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        image_prefix){
                    jsonArray.put(s);
                }
                jsonObject1.put("image_prefix",jsonArray);
            }

            List<String> picture_anno = masterprise.getPicture_annotation();
            if (picture_anno != null){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        picture_anno) {
                    jsonArray.put(s);
                }
                jsonObject1.put("picture_anno",jsonArray);
            }
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

    public String getMasterpriseByTask() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        int task_id = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("task_id")){

                task_id = jsonObject.getInt("task_id");


                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getMasterpriseByTask(task_id);
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("designer_name",m.getDesigner().getNick_name());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());
                jsonObject2.put("score",m.getFinal_score());
                jsonObject2.put("money",m.getMoney_A() + m.getMoney_B());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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

    public String getMasterpriceByDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        String username = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){

                username = jsonObject.getString("username");


                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getMasterpriseByDesigner(username);
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());
                jsonObject2.put("title",m.getTask().getTask_info_title());
                jsonObject2.put("money",m.getMoney_A() + m.getMoney_B());
                Date d = new Date();
                jsonObject2.put("time1",m.getTask().getEnd_day().getTime() - d.getTime());
                jsonObject2.put("time2",m.getTask().getStart_day2().getTime() - d.getTime());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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

    public String getMasterpriceByShare() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        int start = 0;
        int end = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start") && jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");


                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getMasterpriseByShare(start,end);
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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

    public String getMasterpriceByLike() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        int start = 0;
        int end = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start") && jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");


                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getMasterpriseByLike(start,end);
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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

    public String getMasterpriceByView() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        int start = 0;
        int end = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start") && jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");


                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getMasterpriseByView(start,end);
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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

    public String addLikeToMasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        String username = null;
        int id = 0;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id") && jsonObject.has("username")){

                id = jsonObject.getInt("id");
                username = jsonObject.getString("username");


                if(res_code == NoErrorOccured){
                    res = masterpriseService.addLikeToMasterprice(username,id);
                    if (res  == false){
                        res_code = GetFail;
                        res_info = "Add like fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Add like Success";
                    }
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

    public String addShareTomasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        String username = null;
        int id = 0;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id") && jsonObject.has("username")){

                id = jsonObject.getInt("id");
                username = jsonObject.getString("username");


                if(res_code == NoErrorOccured){
                    res = masterpriseService.addShareToMasterprice(username,id);
                    if (res == false){
                        res_code = GetFail;
                        res_info = "Add share fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Add share Success";
                    }
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

    public String addViewRomasterpiece() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        int id = 0;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                id = jsonObject.getInt("id");

                if(res_code == NoErrorOccured){
                    res = masterpriseService.addViewToMasterprice(id);
                    if (res == false){
                        res_code = GetFail;
                        res_info = "Add view fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Add view Success";
                    }
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

    public String addCommentToMasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        int id = 0;
        String username = null;
        String comment = null;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id") && jsonObject.has("username")
                    && jsonObject.has("comment")){

                id = jsonObject.getInt("id");
                username = jsonObject.getString("username");
                comment = jsonObject.getString("comment");

                if(res_code == NoErrorOccured){
                    res = masterpriseService.addCommentToMasterprice(username,id,comment);
                    if (res == false){
                        res_code = GetFail;
                        res_info = "Add comment fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Add comment Success";
                    }
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

    public String designer_get_file() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<String> base64s = new ArrayList<>();
        int id = 0;
        try{
            jsonObject = new JSONObject(content);

            if(jsonObject.has("m_id")){

                id = jsonObject.getInt("m_id");
                Masterprise masterprise = masterpriseService.getMasterpriseById(id);
                int j = 0;
                while (true){
                    try{
                        String s = new String();
                        s += id;
                        s = s + "_" + j + masterprise.getPicture_prefix().get(j);
                        String tar = fileService.getFile(s);
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
            }else{
                res_code = LoseOfImportantParam;
                res_info = "Lose Of Param";
            }

        }catch(JSONException e){
            res_code = JSONSemanticError;
            res_info = "Json Semantic Error";
        }catch (Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);

        JSONArray jsonArray = new JSONArray();
        for (String s :
                base64s) {
            jsonArray.put(s);
        }
        jsonObject1.put("base64",jsonArray);

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
            System.out.println("-----" + content);

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

    public String getAllSuccess() throws Exception {
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        try {
            jsonObject = new JSONObject(content);

            if(true){

                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getAllSueccess();
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("designer_name",m.getDesigner().getNick_name());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());
                if (m.getPicture_prefix().size() != 0){
                    jsonObject2.put("prefix",m.getPicture_prefix().get(0));
                }
                jsonObject2.put("title",m.getTask().getTask_info_title());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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

    public String getAllMasterpiece() throws Exception {
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Masterprise> masterprises = null;

        try {
            jsonObject = new JSONObject(content);

            if(true){

                if(res_code == NoErrorOccured){
                    masterprises = masterpriseService
                            .getAllMasterpiece();
                    if (masterprises == null){
                        res_code = GetFail;
                        res_info = "Get masterprise fail";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get masterprise Success";
                    }
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

        JSONArray jsonArray = new JSONArray();

        if (masterprises != null){
            for (Masterprise m :
                    masterprises) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",m.getMasterprise_id());
                jsonObject2.put("designer_username",m.getDesigner().getUsername());
                jsonObject2.put("designer_name",m.getDesigner().getNick_name());
                jsonObject2.put("task_id",m.getTask().getTask_id());
                jsonObject2.put("stage",m.getStage());
                jsonObject2.put("date",m.getCreate_date().toString());
                jsonObject2.put("final_score",m.getFinal_score());
                jsonObject2.put("likes",m.getLike_designer_username().size());
                jsonObject2.put("share",m.getShare_designer_username().size());
                jsonObject2.put("view",m.getView());
                jsonObject2.put("describe",m.getInfo());
                if (m.getPicture_prefix().size() != 0){
                    jsonObject2.put("prefix",m.getPicture_prefix().get(0));
                }
                jsonObject2.put("title",m.getTask().getTask_info_title());

                jsonArray.put(jsonObject2);
            }

            jsonObject1.put("result",jsonArray);

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


    public MasterpriseService getMasterpriseService() {
        return masterpriseService;
    }

    public void setMasterpriseService(MasterpriseService masterpriseService) {
        this.masterpriseService = masterpriseService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
