package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Entity.Designer;
import net.Ken365X.Entity.Masterprise;
import net.Ken365X.Service.*;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.security.krb5.internal.crypto.Des;

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

import static net.Ken365X.Action.MessageAction.LoseOfImportantParam;

/**
 * Created by mac on 2018/7/9.
 */
public class DesignerAction extends ActionSupport {
    DesignerService designerService;
    MasterpriseService masterpriseService;
    CitiService citiService;

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

    public static final int Update_Error = -1;
    public static final int GetPropertyNoUserFound = -1;

    public static final int ShiftToDesignerFailHasShifted = -2;
    public static final int ShiftToDesignerFailNoUser = -1;
    public static final int ShiftToDesignerFailSomeInfoNull = -3;
    public static final int ShiftToDesignerSuccess = 0;

    public String addNewDesigner()throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;
        String phonenum = null;
        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") &&
                    jsonObject.has("password") && jsonObject.has("phonenum")){

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
                phonenum = jsonObject.getString("phonenum");
                if(phonenum.length() == 0 || phonenum.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    int result = designerService.addNewDesigner(username,password,phonenum);
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

    public String loginDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;
        try{
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
                    int result = designerService.loginDesigner(username,password);
                    if (result == DesignerServiceImp.LOGIN_FAIL_NOUSER){
                        res_code = LoginFailWithNoUser;
                        res_info = "No user";
                    }else if(result == DesignerServiceImp.LOGIN_FAIL_PASSWORDWRONG){
                        res_code = LoginFailWithWrongPassword;
                        res_info = "Password is wrong";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Login success";
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

    public String loginDesignerByEmail() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String email = null;
        String password = null;
        String result = null;
        try{
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
                    result = designerService.loginDesignerByEmail(email,password);
                    if (result == "none"){
                        res_code = LoginFailWithNoUser;
                        res_info = "No email record, Sign in or use phonenum";
                    }else if(result == "wrong"){
                        res_code = LoginFailWithWrongPassword;
                        res_info = "Password is wrong";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Login success";
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
        jsonObject1.put("username",result);

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

    public String changeUserPassword() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String password = null;
        String newpassword = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") &&
                    jsonObject.has("password") && jsonObject.has("newpassword")){

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
                    boolean result = designerService.updatePasswordOfDesignerByUsername(username,password,newpassword);
                    if (result == DesignerServiceImp.CHANGE_PSD_FAIL){
                        res_code = ChangePasswordFail;
                        res_info = "Change password Failed";
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

    public String changeUserInfo() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String nickname = null;
        int sex = 0;
        String realname = null;
        String birthday = null;
        Date birthdayDate = null;
        String qq = null;
        String weixin = null;
        String email = null;
        String realm = null;
        String phonenum = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");

                if (jsonObject.has("nickname")){
                    nickname = jsonObject.getString("nickname");
                    if (nickname.equals("")){
                        nickname = null;
                    }
                }
                if (jsonObject.has("sex") && res_code != Update_Error){
                    sex = jsonObject.getInt("sex");
                    if (sex == Designer.MAN || sex == Designer.WOMAN || sex == Designer.UNKNOWN){
                        // VOID
                    }else {
                        res_code = Update_Error;
                        res_info = "Sex is invalid";
                    }
                }
                if(jsonObject.has("birthday") && res_code != Update_Error){
                    birthday = jsonObject.getString("birthday");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try{
                        birthdayDate = simpleDateFormat.parse(birthday);
                    }catch(Exception e){
                        res_code = Update_Error;
                        res_info = "Birthday is invalid";
                    }
                }
                if(jsonObject.has("qq") && res_code != Update_Error){
                    qq = jsonObject.getString("qq");
                    if (qq.equals("")){
                        qq = null;
                    }
                }
                if(jsonObject.has("realname") && res_code != Update_Error){
                    realname = jsonObject.getString("realname");
                    if (realname.equals("")){
                        realname = null;
                    }
                }
                if(jsonObject.has("weixin") && res_code != Update_Error){
                    weixin = jsonObject.getString("weixin");
                    if (weixin.equals("")){
                        weixin = null;
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
                if(jsonObject.has("phonenum") && res_code != Update_Error){
                    phonenum = jsonObject.getString("phonenum");
                    if (phonenum.equals("")){
                        phonenum = null;
                    }
                }

                if(res_code == NoErrorOccured){
                    boolean result = designerService
                            .updateBasicInfoOfUserByUsername(username,realname,nickname,sex,
                                    birthdayDate,qq,weixin,email,realm,phonenum);
                    if (result == DesignerServiceImp.UPDATE_USER_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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

    public String changeExtendUserInfo() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String exp_work = null;
        String exp_edu = null;
        String exp_prise = null;
        String sign = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");

                if (jsonObject.has("exp_work")){
                    exp_work = jsonObject.getString("exp_work");
                    if (exp_work.equals("")){
                        exp_work = null;
                    }
                }
                if (jsonObject.has("exp_edu")){
                    exp_edu = jsonObject.getString("exp_edu");
                    if (exp_edu.equals("")){
                        exp_edu = null;
                    }
                }
                if (jsonObject.has("exp_prise")){
                    exp_prise = jsonObject.getString("exp_prise");
                    if (exp_prise.equals("")){
                        exp_prise = null;
                    }
                }
                if (jsonObject.has("sign")){
                    sign = jsonObject.getString("sign");
                    if (sign.equals("")){
                        sign = null;
                    }
                }

                if(res_code == NoErrorOccured){
                    boolean result = designerService
                            .updateExtendInfoOfUserByUsername(username,
                                    exp_edu,exp_prise,exp_work,sign);
                    if (result == DesignerServiceImp.UPDATE_USER_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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

    public String setDesignerExp() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int exp = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("exp")){
                username = jsonObject.getString("username");
                exp = jsonObject.getInt("exp");

                if (exp < 0){
                    res_code = Update_Error;
                    res_info = "Exp value Invalid";
                }

                if(res_code == NoErrorOccured){
                    boolean result = designerService
                            .setExpOfUser(username,exp);
                    if (result == DesignerServiceImp.UPDATE_USER_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String addDesignerExp() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int exp_add = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("exp_add")){
                username = jsonObject.getString("username");
                exp_add = jsonObject.getInt("exp_add");

                if (exp_add < 0){
                    res_code = Update_Error;
                    res_info = "Exp_add value Invalid";
                }

                if(res_code == NoErrorOccured){
                    boolean result = designerService
                            .addExpOfUser(username,exp_add);
                    if (result == DesignerServiceImp.UPDATE_USER_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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

    public String getDesignerExp() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int Exp = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");

                if(res_code == NoErrorOccured){
                    Exp = designerService.getExpByName(username);
                    if (Exp == -1){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Get Exp Success";
                    }
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
        jsonObject1.put("Exp",Exp);

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

    public String setDesignerMoney() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        double money = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("money")){
                username = jsonObject.getString("username");
                money = jsonObject.getDouble("money");

                if (money < 0){
                    res_code = Update_Error;
                    res_info = "Money value Invalid";
                }

                if(res_code == NoErrorOccured){
                    boolean result = designerService
                            .setMoneyOfUser(username,money);
                    if (result == DesignerServiceImp.UPDATE_USER_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String addDesignerMoney() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        double money_add = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("money_add")){
                username = jsonObject.getString("username");
                money_add = jsonObject.getDouble("money_add");

                if (money_add < 0){
                    res_code = Update_Error;
                    res_info = "Money_add value Invalid";
                }

                if(res_code == NoErrorOccured){
                    boolean result = designerService
                            .addMoneyOfUser(username,money_add);
                    if (result == DesignerServiceImp.UPDATE_USER_INFO_FALI){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Change Success";
                    }
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String getDesignerMoney() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        double Money = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");

                if(res_code == NoErrorOccured){
                    Money = designerService.getMoneyByUsername(username);
                    if (Money == -1){
                        res_code = Update_Error;
                        res_info = "No user record in database";
                    }else {
                        res_code = NoErrorOccured;
                        res_info = "Get Money Success";
                    }
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
        jsonObject1.put("Money",Money);

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String   getAllPropertyDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        Designer designer = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");
                designer = designerService.getBasicInfoOfUser(username);
                if (designer == null){
                    res_code = GetPropertyNoUserFound;
                    res_info = "No User founed";
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
            jsonObject1.put("username",designer.getUsername());
            jsonObject1.put("realname",designer.getReal_name());
            jsonObject1.put("nickname",designer.getNick_name());
            jsonObject1.put("phonenumber",designer.getPhone_number());
            jsonObject1.put("weixin",designer.getWeixin());
            jsonObject1.put("qq",designer.getQq());
            jsonObject1.put("email",designer.getEmail());
            jsonObject1.put("sign",designer.getSign());
            jsonObject1.put("sex",designer.getSex());
            jsonObject1.put("credit",designer.getCredit());
            jsonObject1.put("designer",designer.isDesigner_ornot());
            jsonObject1.put("exp_work",designer.getExp_work());
            jsonObject1.put("exp_edu",designer.getExp_edu());
            jsonObject1.put("exp_prise",designer.getExp_prise());
            jsonObject1.put("realm",designer.getRealm());

            if (designer.getCiti_account() != null){
                jsonObject1.put("citi",designer.getCiti_account());
            }

            if (designer.getCiti_num() != null){
                jsonObject1.put("citi_num",designer.getCiti_display());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateS = null;
            if (designer.getBirthday() != null){
                dateS = sdf.format(designer.getBirthday());
            }
            jsonObject1.put("birthday",dateS);
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

    public String shiftIntoDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int shift_res = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){
                username = jsonObject.getString("username");

                shift_res = designerService.shiftToDesigner(username);
                if (shift_res == DesignerServiceImp.SHIFT_DESIGNER_FAIL_HAS_SHIFTED){
                    res_code = ShiftToDesignerFailHasShifted;
                    res_info = "User han been shifted";
                }else if (shift_res == DesignerServiceImp.SHIFT_DESIGNER_FAIL_NO_USERNAME){
                    res_code = ShiftToDesignerFailSomeInfoNull;
                    res_info = "No user recoded";
                }else if (shift_res == DesignerServiceImp.SHIFT_DESIGNER_FAIL_INFO_NOT_COMPLETE){
                    res_code = ShiftToDesignerFailNoUser;
                    res_info = "Some of basic info not completed";
                }else {
                    res_code = ShiftToDesignerSuccess;
                    res_info = "Shift Success";
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

    public String designerGetCode() throws Exception{
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
                            System.out.println(responsedata);
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

    public String designerBindeCiti() throws Exception{
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
                	// TODO 验证账户
                    String result = citiService.bindCitiBankAccount(username,username_citi,password);
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

    public String designerBindeCitiAcc() throws Exception{
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
                	// TODO 验证账户
                    boolean result = citiService.bindCitiBankAccount2(username,citi_num,citi_display);
                    if (result != true){
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

    public String getRankDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        List<Designer> designers = null;

        try {
            jsonObject = new JSONObject(content);

            if(true){

                if(res_code == NoErrorOccured){
                    designers = designerService
                            .getAllDesigners();
                    if (designers == null){
                        res_code = -1;
                        res_info = "Get masterprise fail";
                    }else {
                        res_code = 0;
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

        List<Integer> share = new ArrayList<>();
        List<Integer> like = new ArrayList<>();
        if (designers != null){
            for (int i = 0; i < designers.size(); i++) {
                List<Masterprise> masterprises = masterpriseService.getMasterpriseByDesigner(designers.get(i).getUsername());
                share.add(0);
                like.add(0);
                for (Masterprise m:
                     masterprises) {
                    share.set(i,share.get(i) + m.getShare_designer_username().size());
                    like.set(i,like.get(i) + m.getLike_designer_username().size());
                }
            }
        }

        if (designers != null){
            for (int i = 0; i < 5; i++) {
                if (i == designers.size()){
                    break;
                }
                Designer d = designers.get(i);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("designer_username",d.getUsername());
                jsonObject2.put("designer_name",d.getNick_name());
                jsonObject2.put("share",share.get(i));
                jsonObject2.put("view",like.get(i));
                List<String> img = new ArrayList<>();
                List<Masterprise> masterprises = masterpriseService.getMasterpriseByDesigner(designers.get(i).getUsername());
                for (int j = 0; j < masterprises.size(); j++) {
                    if (masterprises.get(j).getNumber_of_picture() != 0){
                        for (int k = 0; k < masterprises.get(j).getNumber_of_picture(); k++) {
                            try {
                                img.add(masterprises.get(j).getMasterprise_id() + "_" + k + masterprises.get(j).getPicture_prefix().get(k));
                            } catch (Exception e){
                                break;
                            }
                        }
                        if (img.size() >= 3){
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                ArrayList alist = new ArrayList(img);
                jsonObject2.put("img",alist);

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

    public String userLikeMasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int id = 0;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("id")){
                username = jsonObject.getString("username");
                id = jsonObject.getInt("id");
                res = designerService.userLikeMasterprice(username,id);
                if (res ==  true){
                    res_code = NoErrorOccured;
                    res_info = "like success";
                } else {
                    res_code = -1;
                    res_info = "like success";
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String userSharemasterprice() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int id = 0;
        boolean res = false;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("id")){
                username = jsonObject.getString("username");
                id = jsonObject.getInt("id");
                res = designerService.userShareMasterprice(username,id);
                if (res ==  true){
                    res_code = NoErrorOccured;
                    res_info = "share success ";
                } else {
                    res_code = -1;
                    res_info = "share fail";
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public CitiService getCitiService() {
        return citiService;
    }

    public void setCitiService(CitiService citiService) {
        this.citiService = citiService;
    }

    public DesignerService getDesignerService() {
        return designerService;
    }

    public void setDesignerService(DesignerService designerService) {
        this.designerService = designerService;
    }

}
