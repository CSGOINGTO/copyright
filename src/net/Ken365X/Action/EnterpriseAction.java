package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Enterprise;
import net.Ken365X.Service.CitiService;
import net.Ken365X.Service.DesignerServiceImp;
import net.Ken365X.Service.EnterpriseService;
import net.Ken365X.Service.EnterpriseServiceImp;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mac on 2018/7/11.
 */
public class EnterpriseAction extends ActionSupport {
    private EnterpriseService enterpriseService;
    private CitiService citiService;


    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    {
        // get the instance of request and response
        httpServletRequest = ServletActionContext.getRequest();
        httpServletResponse = ServletActionContext.getResponse();
    }

    public static final int NoErrorOccured = 0;
    public static final int JSONSemanticError = -101;

    public static final int SameUserNameHasAdded = -3;
    public static final int LoseOfImportantParam = -2;
    public static final int ImportantParamIsNull = -1;

    public static final int LoginFailWithNoUser = -1;
    public static final int LoginFailWithWrongPassword =-2;

    public static final int ChangePasswordFail = -1;
    public static final int GetNoUserFouned = -1;

    public static final int Update_Error = -1;

    public String addNewEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;
        String phonenum = null;
        String info = null;
        String email = null;
        String name = null;
        String realm = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("email") &&
                    jsonObject.has("password") && jsonObject.has("phonenum") &&
                        jsonObject.has("name") && jsonObject.has("realm")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                password = jsonObject.getString("password");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                email = jsonObject.getString("email");
                if(email.length() == 0 || email.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                name = jsonObject.getString("name");
                if(name.length() == 0 || name.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                realm = jsonObject.getString("realm");
                if(realm.length() == 0 || realm.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                phonenum = jsonObject.getString("phonenum");
                if(phonenum.length() == 0 || phonenum.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                info = jsonObject.getString("info");
                if(info.length() == 0 || info.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    int result = enterpriseService.addNewEnterprise(username,password
                            ,phonenum,name,realm,email,info);
                    if (result == DesignerServiceImp.SAME_USERNAME_USED){
                        res_code = SameUserNameHasAdded;
                        res_info = "Same UserName Used";
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

    public String loginEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("password")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                password = jsonObject.getString("password");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    int result = enterpriseService.loginEnterprise(username,password);
                    if (result == EnterpriseServiceImp.LOGIN_FAIL_NOUSER){
                        res_code = LoginFailWithNoUser;
                        res_info = "No UserName Signed up";
                    }else if(result == EnterpriseServiceImp.LOGIN_FAIL_PASSWORDWRONG){
                        res_code = LoginFailWithWrongPassword;
                        res_info = "The password is wrong";
                    } else {
                        res_code = NoErrorOccured;
                        res_info = "Login Success";
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

    public String loginEnterpriseEmail() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String password = null;
        String email = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("email") && jsonObject.has("password")){

                email = jsonObject.getString("email");
                if(email.length() == 0 || email.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                password = jsonObject.getString("password");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    int result = enterpriseService.loginEnterpriseByEmail(email,password);
                    if (result == EnterpriseServiceImp.LOGIN_FAIL_NOUSER){
                        res_code = LoginFailWithNoUser;
                        res_info = "No email Signed up";
                    } else if(result == EnterpriseServiceImp.LOGIN_FAIL_PASSWORDWRONG){
                        res_code = LoginFailWithWrongPassword;
                        res_info = "The password is wrong";
                    } else {
                        res_code = NoErrorOccured;
                        res_info = "Login Success";
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String changeEnterprisePassword() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String newpassword = null;
        String password = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("password")
                    && jsonObject.has("newpassword")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                password = jsonObject.getString("password");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                newpassword = jsonObject.getString("newpassword");
                if(newpassword.length() == 0 || newpassword.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    boolean result = enterpriseService
                            .updateEnterprisePasswordByUsername(username,password,newpassword);
                    if (result == EnterpriseServiceImp.CHANGE_PSD_FAIL){
                        res_code = ChangePasswordFail;
                        res_info = "Change Password Fail";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Password Success";
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

    public String changeEnterpriseInfo() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        String username = null;
        String phonenum = null;
        String name = null;
        String email = null;
        String info = null;
        String realm = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");

                if(jsonObject.has("name") && res_code != Update_Error){
                    name = jsonObject.getString("name");
                    if (name.equals("")){
                        name = null;
                    }
                }
                if(jsonObject.has("info") && res_code != Update_Error){
                    info = jsonObject.getString("info");
                    if (info.equals("")){
                        info = null;
                    }
                }
                if(jsonObject.has("email") && res_code != Update_Error){
                    email = jsonObject.getString("email");
                    if (email.equals("")){
                        email = null;
                    }
                }
                if(jsonObject.has("realm") && res_code != Update_Error){
                    realm = jsonObject.getString("realm");
                    if (realm.equals("")){
                        realm = null;
                    }
                }

                if(res_code == NoErrorOccured){
                    boolean result = enterpriseService
                            .updateEnterpriseInfoByUsername(username,name,realm,
                                    email,info);
                    if (result == EnterpriseServiceImp.UPDATE_ENTERPRISE_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No enterprise record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Update Success";
                    }
                }
            }else{
                // username should not be null
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

    public String getPropertyOfEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

            JSONObject jsonObject = null;

            int res_code = NoErrorOccured;
            String res_info = null;
            String username = null;
            Enterprise enterprise = null;

            try {
                jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");
                enterprise = enterpriseService.getInfoOfEnterpriseByUsername(username);
                if (enterprise == null){
                    res_code = GetNoUserFouned;
                    res_info = "No User Found";
                }
            }else{
                // username should not be null
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
        if (res_code == NoErrorOccured){
            jsonObject1.put("username",enterprise.getUsername());
            jsonObject1.put("name",enterprise.getName());
            jsonObject1.put("phonenumber",enterprise.getPhonenum());
            jsonObject1.put("email",enterprise.getEmail());
            jsonObject1.put("info",enterprise.getInfo());
            jsonObject1.put("realm",enterprise.getRealm());

            if (enterprise.getCiti_account() != null){
                jsonObject1.put("citi",enterprise.getCiti_account());
            }

            if (enterprise.getCiti_num() != null){
                jsonObject1.put("citi_num",enterprise.getCiti_display());
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

    public String shiftTaskToStage2() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        int task_id = 0;
        List<Integer> masterprice_id;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("task_id") && jsonObject.has("id")){
                task_id = jsonObject.getInt("task_id");
                JSONArray jsonArray = jsonObject.getJSONArray("id");
                masterprice_id = (List<Integer>) (List) jsonArray.toList();
                res = enterpriseService.chooseMasterpriceOne(task_id,masterprice_id);
                if (res == true){
                    enterpriseService.change_stage(task_id,1);
                }

                if (res == false){
                    res_code = GetNoUserFouned;
                    res_info = "Shift fail";
                }else {
                    res_code = NoErrorOccured;
                    res_info = "Shift success";
                }
            }else{
                // username should not be null
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

    public String giveScoreToMasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        int task_id = 0;
        int id = 0;
        int score = 0;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("task_id") && jsonObject.has("id")
                    && jsonObject.has("score")){
                task_id = jsonObject.getInt("task_id");
                id = jsonObject.getInt("id");
                score = jsonObject.getInt("score");

                res = enterpriseService.setScoreMasterprice(task_id,id,score);
                enterpriseService.change_stage(task_id,2);
                if (res == false){
                    res_code = GetNoUserFouned;
                    res_info = "Score fail";
                }else {
                    res_code = NoErrorOccured;
                    res_info = "Score success";
                }
            }else{
                // username should not be null
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

    public String enterpriseGetCode() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String phonenum = null;


        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("phonenum")){
                phonenum = jsonObject.getString("phonenum");

                JSONObject req = new JSONObject();
                req.put("sid","a1e3fc48cf0145a3c60902ed7b5f4037");
                req.put("token","bb5ddf802a361915e053b5238031fd57");
                req.put("appid","db17e1cb5e8c41b2985e8e0090a2d977");
                req.put("templateid","363693");
                req.put("param",(int)((Math.random()*9+1)*100000));
                req.put("mobile",phonenum);
                UUID uuid = UUID.randomUUID();
                req.put("uid","2d92c6132139467b989d087c84a365d8");
                final String requestdata = req.toString();
                new Thread(() -> {
                    try {
                        String path = "https://open.ucpaas.com/ol/sms/sendsms";
                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setConnectTimeout(5000);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("ser-Agent",
                                "Fiddler");
                        connection.setRequestProperty("Content-Type","application/json");
                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        OutputStream os = connection.getOutputStream();
                        os.write(requestdata.getBytes());
                        os.close();
                        int code = connection.getResponseCode();
                        String responsedata;
                        if(code == 200){
                            InputStream is = connection.getInputStream();
                            responsedata = StreamUtil.inputStream2String(is);
                            // System.out.println(responsedata);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }).start();
            }else{
                // username should not be null
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

    public String enterpriseBindeCiti() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;
        String username_citi = null;

        try{
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("username_citi")
                    && jsonObject.has("password")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                password = jsonObject.getString("password");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                username_citi = jsonObject.getString("username_citi");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    String result = citiService.bindCitiBankAccountE(username,username_citi,password);
                    if (result != null){
                        res_code = 0;
                        res_info = result;
                    } else {
                        res_code = -1;
                        res_info = "Fail bind";
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

    public String enterpriseBindeCitiAcc() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String citi_num = null;
        String citi_display = null;

        try{
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("citi_num")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                citi_num = jsonObject.getString("citi_num");
                if(citi_num.length() == 0 || citi_num.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                citi_display = jsonObject.getString("citi_display");
                if(citi_display.length() == 0 || citi_display.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    boolean result = citiService.bindCitiBankAccountE2(username,citi_num,citi_display);
                    if (result == true){
                        res_code = 0;
                        res_info = "Success";
                    } else {
                        res_code = -1;
                        res_info = "Fail bind";
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

    public String enterpriseCheckCiti() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;
        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("password")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                password = jsonObject.getString("password");
                if(password.length() == 0 || password.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    int result = citiService.checkCitiBankE(username,password);
                    if (result == EnterpriseServiceImp.LOGIN_FAIL_NOUSER){
                        res_code = LoginFailWithNoUser;
                        res_info = "No UserName Signed up";
                    }else if(result == EnterpriseServiceImp.LOGIN_FAIL_PASSWORDWRONG){
                        res_code = LoginFailWithWrongPassword;
                        res_info = "The password is wrong";
                    } else {
                        res_code = NoErrorOccured;
                        res_info = "Login Success";
                    }
                }
            }else{
                res_code = LoseOfImportantParam;
                res_info = "Lose Of Param";
            }
        }catch (JSONException e){
            res_code = JSONSemanticError;
            res_info = "Json Semantic worng";
            e.printStackTrace();
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

    public EnterpriseService getEnterpriseService() {
        return enterpriseService;
    }

    public void setEnterpriseService(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    public CitiService getCitiService() {
        return citiService;
    }

    public void setCitiService(CitiService citiService) {
        this.citiService = citiService;
    }
}
