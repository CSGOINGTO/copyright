package net.Ken365X.Action;

import com.opensymphony.xwork2.ActionSupport;
import net.Ken365X.Entity.Message;
import net.Ken365X.Service.MessageService;
import net.Ken365X.Utils.StreamUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created by mac on 2018/7/11.
 */
public class MessageAction extends ActionSupport {
    private MessageService messageService;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    {
        // get the instance of request and response
        httpServletRequest = ServletActionContext.getRequest();
        httpServletResponse = ServletActionContext.getResponse();
    }

    public static final int NoErrorOccured = 0;
    public static final int JSONSemanticError = -101;

    public static final int LoseOfImportantParam = -2;
    public static final int ImportantParamIsNull = -1;

    public static final int AddMessageFail = -1;
    public static final int AddMessageSuccess = 0;

    public static final int GetMessageFail = -1;
    public static final int GetMessageSuccess = 0;

    public String enterpriseSendMessagetoDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String from_usernmae = null;
        String title = null;
        String passage = null;
        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("from_username") &&
                    jsonObject.has("title") && jsonObject.has("passage")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                from_usernmae = jsonObject.getString("from_username");
                if(from_usernmae.length() == 0 || from_usernmae.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                title = jsonObject.getString("title");
                if(title.length() == 0 || title.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                passage = jsonObject.getString("passage");
                if(title.length() == 0 || title.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    boolean result = messageService.addMessageToDesigner
                            (username,from_usernmae,title,passage,new Date());
                    if (result == false){
                        res_code = AddMessageFail;
                        res_info = "Add Message Fail";
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);

        out.flush();
        out.close();

        return SUCCESS;
    }

    public String designerSendMessagetoEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        String from_usernmae = null;

        String title = null;
        String passage = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username") && jsonObject.has("from_username") &&
                    jsonObject.has("title") && jsonObject.has("passage")){

                passage = jsonObject.getString("passage");
                if(passage.length() == 0 || passage.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                from_usernmae = jsonObject.getString("from_username");
                if(from_usernmae.length() == 0 || from_usernmae.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }
                title = jsonObject.getString("title");
                if(title.length() == 0 || title.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }


                if(res_code == NoErrorOccured){
                    boolean result = messageService.addMessageToEnterprise
                            (username,from_usernmae,title,passage,new Date());
                    if (result == false){
                        res_code = AddMessageFail;
                        res_info = "Add Message Fail";
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
        String jsonoutput = jsonObject1.toString();
        PrintWriter out = httpServletResponse.getWriter();
        out.print(jsonoutput);
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String getAllOfDesignerMessage() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        List<Message> result = null;
        
        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }


                if(res_code == NoErrorOccured){
                    result = messageService.getMessageList(username);
                    if (result == null || result.size() == 0){
                        res_code = GetMessageFail;
                        res_info = "get Message Fail";
                    }else {
                        res_code = GetMessageSuccess;
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

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();

        if (result != null){
            for (Message m :
                    result) {
                JSONObject jsonObj= new JSONObject();
                jsonObj.put("id",m.getMessage_id());
                jsonObj.put("orientation",m.isOrientation());
                jsonObj.put("readed",m.isReadornot());
                jsonObj.put("from",m.getEnterprise().getUsername());
                jsonObj.put("title",m.getTitle());
                jsonObj.put("passage",m.getMessage());
                jsonObj.put("date",m.getSendtime());

                jsonArray.put(jsonObj);
            }
        }

        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        if(res_code == NoErrorOccured){
            jsonObject1.put("message_array",jsonArray);
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

    public String getAllOfEnterpriseMessage() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        List<Message> result = null;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("username")){

                username = jsonObject.getString("username");
                if(username.length() == 0 || username.equals("")){
                    res_code = ImportantParamIsNull;
                    res_info = "Some Of Important Params is null";
                }

                if(res_code == NoErrorOccured){
                    result = messageService.getMessageListEnterprise(username);
                    if (result == null || result.size() == 0){
                        res_code = GetMessageFail;

                        res_info = "get Message Fail";
                    }else {
                        res_code = GetMessageSuccess;
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

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();

        if (result != null){
            for (Message m :
                    result) {
                JSONObject jsonObj= new JSONObject();
                jsonObj.put("id",m.getMessage_id());
                jsonObj.put("from",m.getEnterprise().getUsername());
                jsonObj.put("title",m.getTitle());
                jsonObj.put("passage",m.getMessage());
                jsonObj.put("readed",m.isReadornot());
                jsonObj.put("date",m.getSendtime());
                jsonObj.put("orientation",m.isOrientation());
                jsonArray.put(jsonObj);
            }
        }

        jsonObject1.put("res_code",res_code);
        jsonObject1.put("res_str",res_info);
        if(res_code == NoErrorOccured){
            jsonObject1.put("message_array",jsonArray);
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

    public String readMessageDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        String username = null;
        int messageid = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("messageid")){

                messageid = jsonObject.getInt("messageid");

                if(res_code == NoErrorOccured){
                    boolean result = messageService.readMessage(messageid);
                    if (result == false){
                        res_code = -1;
                        res_info = "designer read Message Fail";
                    }else {
                        res_code = GetMessageSuccess;
                        res_info = "designer read Success";
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

    public String readMessageEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;

        int messageid = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("messageid")){

                messageid = jsonObject.getInt("messageid");

                if(res_code == NoErrorOccured){
                    boolean result = messageService.readMessageE(messageid);
                    if (result == false){
                        res_code = -1;
                        res_info = "enterprise read Message Fail";
                    }else {
                        res_code = GetMessageSuccess;
                        res_info = "enterprise read Success";
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

    public String deleteMessageDesigner() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int messageid = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("messageid")){

                messageid = jsonObject.getInt("messageid");

                if(res_code == NoErrorOccured){
                    boolean result = messageService.removeMessage(messageid);
                    if (result == false){
                        res_code = -1;
                        res_info = "designer remove Message Fail";
                    }else {
                        res_code = GetMessageSuccess;
                        res_info = "designer remove Success";
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

    public String deleteMessageEnterprise() throws Exception{
        String content = StreamUtil.inputStream2String(httpServletRequest.getInputStream());

        JSONObject jsonObject = null;

        int res_code = NoErrorOccured;
        String res_info = null;
        int messageid = 0;

        try {
            jsonObject = new JSONObject(content);

            if(jsonObject.has("messageid")){

                messageid = jsonObject.getInt("messageid");

                if(res_code == NoErrorOccured){
                    boolean result = messageService.removeMessageE(messageid);
                    if (result == false){
                        res_code = -1;
                        res_info = "enterprises remove Message Fail";
                    }else {
                        res_code = GetMessageSuccess;
                        res_info = "enterprises remove Success";
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

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
}
