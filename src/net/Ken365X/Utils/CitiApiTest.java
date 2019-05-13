package net.Ken365X.Utils;

// import okhttp3.*;

import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mac on 2018/7/27.
 */
public class CitiApiTest {

    final String CLIENT_ID = "b0a812e6-1e18-4145-a5ed-53b02b808878";
    final String CLIENT_SCRENT = "S2lB4nJ3qL7sE6eR6hQ4vP8sH5lU5qT8gF5lL5yN6xM5uP4qL8";
    private String accessToken;
    private String bizToken;
    private String realAccessToken;
    private String accounts;
    private String username;
    private String password;
    private String accountId;

    private String modulus;
    private String exponent;
    private String eventId;


    public static void main(String [] args){
        CitiApiTest citiApiTest = new CitiApiTest();
        citiApiTest.Step1();
        citiApiTest.Step2();
        citiApiTest.Step3();
        citiApiTest.Step4();
        // citiApiTest.Step5();
        citiApiTest.Step6();
    }
    public void Step1(){
        OkHttpClient client = new OkHttpClient();
        String client_id = CLIENT_ID;
        String client_scrent = CLIENT_SCRENT;
        String encode_key = client_id + ":" + client_scrent;
        String authorization = "Basic " + Base64.encodeBase64String(encode_key.getBytes());
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&scope=/api");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/clientCredentials/oauth2/token/us/gcb")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("authorization", authorization)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = null;
        JSONObject jsonObject = null;
        try{
            response = client.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());
        }catch(Exception e){
            e.printStackTrace();
        }

        accessToken = (String) jsonObject.get("access_token");
        System.out.println("step1 access_token:");
        System.out.println("\t" + accessToken);
    }

    public void Step2(){
        Map<String, String> map = new HashMap<>();
        OkHttpClient client = new OkHttpClient();
        String client_id = CLIENT_ID;
        String accessToken = this.accessToken;
        String authorization = "Bearer " + accessToken;
        UUID uuid = UUID.randomUUID();
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/security/e2eKey")
                .get()
                .addHeader("authorization", authorization)
                .addHeader("client_id", client_id)
                .addHeader("uuid", uuid.toString())
                .addHeader("content-type", "application/json")
                .build();
        Response response = null;
        JSONObject jsonObject = null;
        try{
            response = client.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());
        }catch(Exception e){
            e.printStackTrace();
        }

        String modulus = null;
        String exponent = null;
        String bizToken = null;
        String eventId = null;
        if (jsonObject != null) {
            modulus = (String) jsonObject.get("modulus");
            exponent = (String) jsonObject.get("exponent");
            Headers headers = response.headers();
            bizToken = headers.get("bizToken");
            eventId = headers.get("eventId");
            map.put("modulus", modulus);
            map.put("exponent", exponent);
            map.put("bizToken", bizToken);
            map.put("eventId", eventId);
            this.eventId = eventId;
            this.bizToken = bizToken;
            this.modulus = modulus;
            this.exponent = exponent;
            this.eventId = eventId;
        }
        System.out.println("step2 map:");
        for (String s : map.keySet()) {
            System.out.println("\tkey:" + s + "\tvalues:" + map.get(s));
        }
    }

    public String getRSA(String password){
        // 得到一个ScriptEngine对象
        String result = null;
        ScriptEngineManager maneger = new ScriptEngineManager();
        ScriptEngine engine = maneger.getEngineByName("JavaScript");

        // 读js文件
        String jsFile = "/Users/mac/Desktop/TROPEDO/web/RootLogin/RSA.js";
        Reader scriptReader = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(jsFile));
            scriptReader = new InputStreamReader(fileInputStream, "utf-8");
        }catch(Exception e){
            e.printStackTrace();
        }

        try
        {
            engine.eval(scriptReader);
            if (engine instanceof Invocable)
            {
                // 调用JS方法
                Invocable invocable = (Invocable)engine;
                result = (String)invocable.invokeFunction("RSA",
                        this.modulus, this.exponent,this.eventId,password);
                //System.out.println(result);
                //System.out.println(result.length());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try{
                scriptReader.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public void Step3(){
        String client_id = CLIENT_ID;
        String client_scrent = CLIENT_SCRENT;
        String bizToken = this.bizToken;
        // System.out.println("bizToken: "+bizToken);
        String encode_key = client_id + ":" + client_scrent;
        String authorization = "Basic " + Base64.encodeBase64String(encode_key.getBytes());
        // String username = "SandboxUser1";
        String username = "SandboxUser2";
        // String password = this.getRSA("P@ssUser1$");
        String password = this.getRSA("P@ssUser2$");
        // System.out.println(password);
        UUID uuid = UUID.randomUUID();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=password&scope=/api&username="+username+"&password="+password);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/password/oauth2/token/us/gcb")
                .post(body)
                .addHeader("authorization", authorization)
                .addHeader("bizToken", bizToken)
                .addHeader("uuid", uuid.toString())
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept", "application/json")
                .build();
        JSONObject jsonObject = null;
        try{
            Response response = client.newCall(request).execute();
            jsonObject = new JSONObject(response.body().string());
        }catch(Exception e){
            e.printStackTrace();
        }
        String realAccessToken = (String) jsonObject.get("access_token");
        this.realAccessToken = realAccessToken;
        System.out.println("step3 real_access_token:");
        System.out.println("\t" + realAccessToken);
    }

    public void Step4(){
        String client_id = CLIENT_ID;
        String authorization = "Bearer " + this.realAccessToken;
        UUID uuid = UUID.randomUUID();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/accounts")
                .get()
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("client_id", client_id)
                .build();
        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step4 accounts:");
        System.out.println("\t"+responseBodyString);
    }

    public void Step5(){
        String client_id = CLIENT_ID;
        String authorization = "Bearer " + realAccessToken;
        UUID uuid = UUID.randomUUID();
        String accountId = "2016971048";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/accounts/"+accountId)
                .get()
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("client_id", client_id)
                .build();
        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step5 account details:");
        System.out.println("\t"+responseBodyString);
    }

    public void Step6(){
        String client_id = CLIENT_ID;
        String authorization = "Bearer " + this.realAccessToken;
        UUID uuid = UUID.randomUUID();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/payees/sourceAccounts?paymentType=ALL")
                .get()
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("content-type", "application/json")
                .addHeader("accept", "application/json")
                .addHeader("client_id", client_id)
                .build();
        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step6 cards:");
        System.out.println("\t"+responseBodyString);
    }
}
