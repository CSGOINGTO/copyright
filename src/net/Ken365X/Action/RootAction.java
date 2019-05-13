package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Service.RootService;
import net.Ken365X.Service.RootServiceImp;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by mac on 2018/7/6.
 */
public class RootAction extends ActionSupport {

    private RootService rootService;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    public static final int JSONSemanticError = 3;

    {
        // get the instance of request and response
        httpServletRequest = ServletActionContext.getRequest();
        httpServletResponse = ServletActionContext.getResponse();
    }

    @Override
    public String execute() throws Exception {
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        // System.out.println(content);
        JSONObject jsonObject = new JSONObject(content);
        int i = RootServiceImp.LOGIN_FAIL_NOROOT;
        try {
            i = rootService.Login(jsonObject.getString("username"),
                    jsonObject.getString("password"));
        }catch (JSONException e){
            i = 3;
        }catch(Exception e){
            e.printStackTrace();
        }

        int res_code;
        String res_str;
        if(i == RootServiceImp.LOGIN_SUCCESS){
            res_code = RootServiceImp.LOGIN_SUCCESS;
            res_str = "LoginSUCCESS";
        }else if(i== RootServiceImp.LOGIN_FAIL_NOROOT){
            res_code = RootServiceImp.LOGIN_FAIL_NOROOT;
            res_str = "NotARoot";
        }else if(i == RootServiceImp.LOGIN_FAIL_PASSWORDWRONG){
            res_code = RootServiceImp.LOGIN_FAIL_PASSWORDWRONG;
            res_str = "WrongPassword";
        }else {
            res_code = 3;
            res_str = "JSONSemanticError";
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

    public String Logoutfunction() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        JSONObject jsonObject = new JSONObject(content);
        int i = 0;
        try{
            rootService.Logout(jsonObject.getString("username"));
        }catch (JSONException e){
            i = JSONSemanticError;
        } catch(Exception e){
            e.printStackTrace();
        }

        JSONObject jsonObject1 = new JSONObject();
        if (i == 0){
            jsonObject1.put("res_code","0");
            jsonObject1.put("res_str","LogoutSuccess");
        }else {
            jsonObject1.put("res_code","3");
            jsonObject1.put("res_str","JSONSemanticError");
        }


        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public String changePasswordFunction() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());
        JSONObject jsonObject = new JSONObject(content);
        int i = 1;
        try{
            i =rootService.ChangePassword(jsonObject.getString("username")
                    ,jsonObject.getString("password"),jsonObject.getString("newpassword"));
        }catch(Exception e){
            // e.printStackTrace();
            // JSON Error
        }


        int res_code;
        String res_str;
        if (i == RootServiceImp.CHANGE_SUCCESS){
            res_code = 0;
            res_str = "SUCCESS";
        }else {
            res_code = 1;
            res_str = "FAIL";
        }

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("res_code","0");
        jsonObject1.put("res_str","LogoutSuccess");

        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();

        return SUCCESS;
    }

    public RootService getRootService() {
        return rootService;
    }

    public void setRootService(RootService rootService) {
        this.rootService = rootService;
    }
}
