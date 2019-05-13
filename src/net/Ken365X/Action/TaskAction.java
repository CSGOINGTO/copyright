package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Entity.Task;
import net.Ken365X.Service.TaskService;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static net.Ken365X.Action.MessageAction.LoseOfImportantParam;

/**
 * Created by mac on 2018/7/12.
 */
public class TaskAction extends ActionSupport{
    private TaskService taskService;

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
    public static final int AddTaskFail = -3;
    public static final int Update_Error = -4;

    public static final int ChangeFail = -1;
    public static final int ChangeSuccess = 0;

    public static final int GetFail = -1;
    public static final int GetSuccess = 0;

    public String addNewtask() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String name = null;
        String title = null;
        String info = null;
        String classify = null;
        Date startDay = null;
        Date endDay = null;
        Date starrDay2 = null;
        Date endDay2 = null;
        List<String> sub_task_title = null;
        List<String> sub_task_info = null;
        List<String> picture_info = null;
        double money = 0;
        int num_picture = 0;
        double property = -1;
        int id = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("name") &&
                    jsonObject.has("title") && jsonObject.has("info") &&
                    jsonObject.has("classify") && jsonObject.has("start_day")&&
                    jsonObject.has("end_day") && jsonObject.has("sub_task_title")&&
                    jsonObject.has("sub_task_info") && jsonObject.has("picture_info")&&
                    jsonObject.has("num_picture") && jsonObject.has("money") && jsonObject.has("property")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                classify = jsonObject.getString("classify");
                if(classify.length() == 0 || classify.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                title = jsonObject.getString("title");
                if(title.length() == 0 || title.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                name = jsonObject.getString("name");
                if(name.length() == 0 || name.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                info = jsonObject.getString("info");
                if(info.length() == 0 || info.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                num_picture = jsonObject.getInt("num_picture");
                money = jsonObject.getDouble("money");
                property = jsonObject.getDouble("property");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    startDay = simpleDateFormat.parse(jsonObject.getString("start_day"));
                    endDay = simpleDateFormat.parse(jsonObject.getString("end_day"));
                    starrDay2 = simpleDateFormat.parse(jsonObject.getString("start_day2"));
                    endDay2 = simpleDateFormat.parse(jsonObject.getString("end_day2"));
                }catch(Exception e){
                    // e.printStackTrace();
                    res_code = DataFormatError;
                    res_info = "Data Format error";
                }

                JSONArray jsonArray = jsonObject.getJSONArray("sub_task_title");
                sub_task_title =  (List<String>) (List) jsonArray.toList();
                jsonArray = jsonObject.getJSONArray("sub_task_info");
                sub_task_info =  (List<String>) (List) jsonArray.toList();
                jsonArray = jsonObject.getJSONArray("picture_info");
                picture_info = (List<String>) (List) jsonArray.toList();

                if(res_code == NoErrorOccured){
                    boolean result = taskService.addNewTask(name,title,info,classify,
                            money,num_picture,startDay,endDay,sub_task_title,sub_task_info,
                            picture_info,username,property,starrDay2,endDay2);
                    if (result == false){
                        res_code = AddTaskFail;
                        res_info = "Add task fail. Maybe No that enterprise";
                    }else {
                        Task task = taskService.getTaskPropertyByName(name);
                        id = task.getTask_id();
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
        jsonObject1.put("id",id);

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

    public String changeTaskInfoById() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int id = 0;
        String title = null;
        String info = null;
        String classify = null;
        Date startDay = null;
        Date endDay = null;
        double money = 0;
        int num_picture = -1;
        double property = -1;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                if(jsonObject.has("id") && res_code != Update_Error){
                    id = jsonObject.getInt("id");
                }
                if(jsonObject.has("info") && res_code != Update_Error){
                    info = jsonObject.getString("info");
                    if (info.equals("")){
                        info = null;
                    }
                }
                if(jsonObject.has("title") && res_code != Update_Error){
                    title = jsonObject.getString("title");
                    if (title.equals("")){
                        title = null;
                    }
                }
                if(jsonObject.has("classify") && res_code != Update_Error){
                    classify = jsonObject.getString("classify");
                    if (classify.equals("")){
                        classify = null;
                    }
                }

                if (jsonObject.has("num_picture")&& res_code != Update_Error){
                    num_picture = jsonObject.getInt("num_picture");
                }
                if (jsonObject.has("money")&& res_code != Update_Error){
                    money = jsonObject.getDouble("money");
                }

                if (jsonObject.has("property")){
                    property = jsonObject.getDouble("property");
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    if (jsonObject.has("start_day")){
                        startDay = simpleDateFormat.parse(jsonObject.getString("start_day"));
                    }
                    if (jsonObject.has("end_day")){
                        endDay = simpleDateFormat.parse(jsonObject.getString("end_day"));
                    }
                }catch(Exception e){
                    // e.printStackTrace();
                    res_code = DataFormatError;
                    res_info = "Data Format error";
                }

                if(res_code == NoErrorOccured){
                    boolean result = taskService.changeTaskBasicInfoById(id,title,info
                            ,classify, money,num_picture,startDay,endDay,property);
                    if (result == false){
                        res_code = ChangeFail;
                        res_info = "Change task fail. Maybe No that enterprise";
                    }else {
                        res_code = ChangeSuccess;
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

    public String changeTaskInfoByName() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String title = null;
        String info = null;
        String classify = null;
        Date startDay = null;
        Date endDay = null;
        double money = 0;
        int num_picture = -1;
        double property = -1;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("name")){

                if(jsonObject.has("name") && res_code != Update_Error){
                    username = jsonObject.getString("name");
                }
                if(jsonObject.has("info") && res_code != Update_Error){
                    info = jsonObject.getString("info");
                    if (info.equals("")){
                        info = null;
                    }
                }
                if(jsonObject.has("title") && res_code != Update_Error){
                    title = jsonObject.getString("title");
                    if (title.equals("")){
                        title = null;
                    }
                }
                if(jsonObject.has("classify") && res_code != Update_Error){
                    classify = jsonObject.getString("classify");
                    if (classify.equals("")){
                        classify = null;
                    }
                }

                if (jsonObject.has("num_picture")&& res_code != Update_Error){
                    num_picture = jsonObject.getInt("num_picture");
                }
                if (jsonObject.has("money")&& res_code != Update_Error){
                    money = jsonObject.getDouble("money");
                }
                if (jsonObject.has("property")){
                    property = jsonObject.getDouble("property");
                }


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try{
                    if (jsonObject.has("start_day")){
                        startDay = simpleDateFormat.parse(jsonObject.getString("start_day"));
                    }
                    if (jsonObject.has("end_day")){
                        endDay = simpleDateFormat.parse(jsonObject.getString("end_day"));
                    }
                }catch(Exception e){
                    // e.printStackTrace();
                    res_code = DataFormatError;
                    res_info = "Data Format error";
                }

                if(res_code == NoErrorOccured){
                    boolean result = taskService.changeTaskBasicInfoByName(username,title,info,
                            classify ,money,num_picture,startDay,endDay,property);
                    if (result == false){
                        res_code = ChangeFail;
                        res_info = "Change task fail. Maybe No that enterprise";
                    }else {
                        res_code = ChangeSuccess;
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

    public String changeTaskSubInfoById() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int id =0;
        List<String> sub_task_title = null;
        List<String> sub_task_info = null;
        List<String> picture_info = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){
                id = jsonObject.getInt("id");
                JSONArray jsonArray = null;
                if (jsonObject.has("sub_title")){
                    jsonArray = jsonObject.getJSONArray("sub_title");
                    sub_task_title =  (List<String>) (List) jsonArray.toList();
                }
                if (jsonObject.has("sub_info")){
                    jsonArray = jsonObject.getJSONArray("sub_info");
                    sub_task_info =  (List<String>) (List) jsonArray.toList();
                }
                if (jsonObject.has("picture_info")){
                    jsonArray = jsonObject.getJSONArray("picture_info");
                    picture_info =  (List<String>) (List) jsonArray.toList();
                }


                if(res_code == NoErrorOccured){
                    boolean result = taskService.changeTaskTextById(sub_task_title,
                            sub_task_info,picture_info,id);
                    if (result == false){
                        res_code = ChangeFail;
                        res_info = "Change task fail. Maybe No that enterprise";
                    }else {
                        res_code = ChangeSuccess;
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

    public String changeTaskSubInfoByName() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String name = null;
        List<String> sub_task_title = null;
        List<String> sub_task_info = null;
        List<String> picture_info = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("name")){
                JSONArray jsonArray = null;
                name = jsonObject.getString("name");
                if (jsonObject.has("sub_title")){
                    jsonArray = jsonObject.getJSONArray("sub_title");
                    sub_task_title =  (List<String>) (List) jsonArray.toList();
                }
                if (jsonObject.has("sub_info")){
                    jsonArray = jsonObject.getJSONArray("sub_info");
                    sub_task_info =  (List<String>) (List) jsonArray.toList();
                }
                if (jsonObject.has("picture_info")){
                    jsonArray = jsonObject.getJSONArray("picture_info");
                    picture_info =  (List<String>) (List) jsonArray.toList();
                }


                if(res_code == NoErrorOccured){
                    boolean result = taskService.changeTaskTextByName(sub_task_title,
                            sub_task_info,picture_info,name);
                    if (result == false){
                        res_code = ChangeFail;
                        res_info = "Change task fail. Maybe No that enterprise";
                    }else {
                        res_code = ChangeSuccess;
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

    public String getTaskById() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int id = 0;
        Task task = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                if(jsonObject.has("id") && res_code != Update_Error){
                    id = jsonObject.getInt("id");
                }

                if(res_code == NoErrorOccured){
                    task = taskService.getTaskProperty(id);
                    if (task == null){
                        res_code = GetFail;
                        res_info = "Get task property fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get Success";
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
        if (task != null){
            jsonObject1.put("id",task.getTask_id());
            jsonObject1.put("name",task.getName());
            jsonObject1.put("describe",task.getTask_info());
            jsonObject1.put("title",task.getTask_info_title());
            jsonObject1.put("start_day",task.getStart_day().toString());
            jsonObject1.put("end_day",task.getEnd_day().toString());
            jsonObject1.put("enterprise_username",task.getEnterprise().getUsername());
            jsonObject1.put("num_picture",task.getNum_of_picture());
            jsonObject1.put("class",task.getInfo_class());
            jsonObject1.put("stage",task.getStage());
            jsonObject1.put("property",task.getProperty());
            jsonObject1.put("money",task.getGive_money());
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

    public String getTaskByName()throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String name = null;
        Task task = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("name")){

                if(jsonObject.has("name") && res_code != Update_Error){
                    name = jsonObject.getString("name");
                }

                if(res_code == NoErrorOccured){
                    task = taskService.getTaskPropertyByName(name);
                    if (task == null){
                        res_code = GetFail;
                        res_info = "Get task property fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get Success";
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
        if (task != null){
            jsonObject1.put("id",task.getTask_id());
            jsonObject1.put("name",task.getName());
            jsonObject1.put("describe",task.getTask_info());
            jsonObject1.put("title",task.getTask_info_title());
            jsonObject1.put("start_day",task.getStart_day().toString());
            jsonObject1.put("end_day",task.getEnd_day().toString());
            jsonObject1.put("enterprise_username",task.getEnterprise().getUsername());
            jsonObject1.put("num_picture",task.getNum_of_picture());
            jsonObject1.put("class",task.getInfo_class());
            jsonObject1.put("stage",task.getStage());
            jsonObject1.put("property",task.getProperty());
            jsonObject1.put("money",task.getGive_money());

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

    public String getTaskAllPropertyById() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int id = 0;
        Task task = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                if(jsonObject.has("id") && res_code != Update_Error){
                    id = jsonObject.getInt("id");
                }

                if(res_code == NoErrorOccured){
                    task = taskService.getTaskProperty(id);
                    if (task == null){
                        res_code = GetFail;
                        res_info = "Get task property fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get Success";
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
        if (task != null){
            jsonObject1.put("id",task.getTask_id());
            jsonObject1.put("name",task.getName());
            jsonObject1.put("describe",task.getTask_info());
            jsonObject1.put("title",task.getTask_info_title());
            jsonObject1.put("start_day",task.getStart_day().toString());
            jsonObject1.put("end_day",task.getEnd_day().toString());
            jsonObject1.put("start_day2",task.getStart_day2().toString());
            jsonObject1.put("end_day2",task.getEnd_day2().toString());
            jsonObject1.put("enterprise_username",task.getEnterprise().getUsername());
            jsonObject1.put("num_picture",task.getNum_of_picture());
            jsonObject1.put("class",task.getInfo_class());
            jsonObject1.put("stage",task.getStage());
            jsonObject1.put("property",task.getProperty());
            jsonObject1.put("money",task.getGive_money());
            jsonObject1.put("num_join",task.getMasterprises().size());
            jsonObject1.put("num_view", task.getNum_view());

            List<String> subtitle = task.getSub_task_info_title();
            if (subtitle != null && subtitle.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s :
                        subtitle) {
                    jsonArray.put(s);
                }
                jsonObject1.put("sub_title",jsonArray);
            }

            List<String> subinfo = task.getSub_task_info();
            if (subinfo != null && subinfo.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s :
                        subinfo) {
                    jsonArray.put(s);
                }
                jsonObject1.put("sub_info",jsonArray);
            }

            List<String> image_prefix = task.getPicture_prefix();
            if (image_prefix != null && image_prefix.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        image_prefix){
                    jsonArray.put(s);
                }
                jsonObject1.put("image_prefix",jsonArray);
            }

            List<String> picture_info = task.getSub_task_img_info();
            if (picture_info != null && picture_info.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s :
                        picture_info) {
                    jsonArray.put(s);
                }
                jsonObject1.put("picture_info",jsonArray);
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

    public String getTaskAllPropertyByName() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String name = null;
        Task task = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("name")){

                if(jsonObject.has("name") && res_code != Update_Error){
                    name = jsonObject.getString("name");
                }

                if(res_code == NoErrorOccured){
                    task = taskService.getTaskPropertyByName(name);
                    if (task == null){
                        res_code = GetFail;
                        res_info = "Get task property fail.";
                    }else {
                        res_code = GetSuccess;
                        res_info = "Get Success";
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

        if (task != null){
            jsonObject1.put("id",task.getTask_id());
            jsonObject1.put("name",task.getName());
            jsonObject1.put("describe",task.getTask_info());
            jsonObject1.put("title",task.getTask_info_title());
            jsonObject1.put("start_day",task.getStart_day().toString());
            jsonObject1.put("end_day",task.getEnd_day().toString());
            jsonObject1.put("enterprise_username",task.getEnterprise().getUsername());
            jsonObject1.put("num_picture",task.getNum_of_picture());
            jsonObject1.put("class",task.getInfo_class());
            jsonObject1.put("stage",task.getStage());
            jsonObject1.put("property",task.getProperty());
            jsonObject1.put("money",task.getGive_money());

            List<String> subtitle = task.getSub_task_info_title();
            if (subtitle != null && subtitle.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s :
                        subtitle) {
                    jsonArray.put(s);
                }
                jsonObject1.put("sub_title",jsonArray);
            }

            List<String> subinfo = task.getSub_task_info();
            if (subinfo != null && subinfo.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s :
                        subinfo) {
                    jsonArray.put(s);
                }
                jsonObject1.put("sub_info",jsonArray);
            }

            List<String> image_prefix = task.getPicture_prefix();
            if (image_prefix != null && image_prefix.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s:
                        image_prefix){
                    jsonArray.put(s);
                }
                jsonObject1.put("image_prefix",jsonArray);
            }

            List<String> picture_info = task.getSub_task_img_info();
            if (picture_info != null && picture_info.size() != 0){
                JSONArray jsonArray = new JSONArray();
                for (String s :
                        picture_info) {
                    jsonArray.put(s);
                }
                jsonObject1.put("picture_info",jsonArray);
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

    public String getTaskNotStop() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int start = 0;
        int end = 0;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start")&& jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");

                tasks = taskService.getTaskNotEnded(start,end);

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                System.out.println(task);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("money",task.getGive_money());

                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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

    public String getTaskStop() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int start = 0;
        int end = 0;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start")&& jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");

                tasks = taskService.getTaskEnded(start,end);

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("money",task.getGive_money());
                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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

    public String getTaskOrderByMoney() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int start = 0;
        int end = 0;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start")&& jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");

                tasks = taskService.getTaskOrderByMoney(start,end);

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("money",task.getGive_money());
                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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

    public String getTaskOrderByHot() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int start = 0;
        int end = 0;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("start")&& jsonObject.has("end")){

                start = jsonObject.getInt("start");
                end = jsonObject.getInt("end");

                tasks = taskService.getTaskOrderByHot(start,end);

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("money",task.getGive_money());
                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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

    public String getTaskByEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){

                username = jsonObject.getString("username");

                tasks = taskService.getTaskByEnterprise(username);

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("money",task.getGive_money());
                jsonObj.put("num_join",task.getMasterprises().size());
                Set<Masterprise> masterprises = task.getMasterprises();
                int num_share = 0;
                int num_view = 0;
                for (Masterprise m:
                     masterprises) {
                    num_share += m.getShare_designer_username().size();
                    num_view += m.getView();
                }
                jsonObj.put("num_share",num_share);
                jsonObj.put("num_view",num_view);
                Date d = new Date();
                jsonObj.put("time1",task.getEnd_day().getTime() - d.getTime());
                jsonObj.put("time2",task.getStart_day2().getTime() - d.getTime());
                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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

    public String getTaskByClassify() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String classify = null;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("classify")){

                classify = jsonObject.getString("classify");

                tasks = taskService.getTaskByClassify(classify);

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("money",task.getGive_money());
                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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

    public String designerViewTask() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int [] ids = null;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("ids")){

                JSONArray jsonArray = jsonObject.getJSONArray("ids");
                List<Integer> list = (List<Integer>) (List)jsonArray.toList();
                ids = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    ids[i] = list.get(i);
                }

                res = taskService.readTask(ids);

                if (res == true){
                    res_code = 0;
                }else {
                    res_code = -1;
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

    public String getAllTask() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Task> tasks = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("id")){

                tasks = taskService.getAllTask();

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
        JSONArray jsonArray = new JSONArray();

        if (tasks != null && tasks.size() != 0){
            for (Task task :
                    tasks) {
                System.out.println(task);
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id",task.getTask_id());
                jsonObj.put("name",task.getName());
                jsonObj.put("describe",task.getTask_info());
                jsonObj.put("title",task.getTask_info_title());
                jsonObj.put("start_day",task.getStart_day().toString());
                jsonObj.put("end_day",task.getEnd_day().toString());
                jsonObj.put("enterprise_username",task.getEnterprise().getUsername());
                jsonObj.put("num_picture",task.getNum_of_picture());
                jsonObj.put("class",task.getInfo_class());
                jsonObj.put("property",task.getProperty());
                jsonObj.put("stage",task.getStage());
                jsonObj.put("money",task.getGive_money());
                jsonObj.put("num_join",task.getMasterprises().size());
                jsonObj.put("num_view",task.getNum_view());

                if (task.getNum_of_picture() != 0 && task.getPicture_prefix().size()!=0){
                    jsonObj.put("img_prefix",task.getPicture_prefix().get(0));
                }

                jsonArray.put(jsonObj);
            }
        }
        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        jsonObject1.put("list",jsonArray);

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


    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

}
