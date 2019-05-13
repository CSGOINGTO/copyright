package net.Ken365X.Utils;

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
 * Created by mac on 2018/8/7.
 */
public class CitiApiTest2 {
    final String CLIENT_ID = "35906ac6-96b2-4e3a-8864-9010e27d5bd6";
    final String CLIENT_SCRENT = "eJ4dE3bH8nG1cU0pL2iL0xI4kX6dW6vG8wG7qE6qQ4cW7jU0yA";
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

    private String flowId;


    public static void main(String [] args){
        CitiApiTest2 citiApiTest = new CitiApiTest2();
        citiApiTest.Step1();
        citiApiTest.Step2();
        citiApiTest.Step3();
        citiApiTest.Step4();
        // citiApiTest.Step5();
        citiApiTest.Step6();
        citiApiTest.Step77();
        citiApiTest.Strp88();
        citiApiTest.Step4();
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
                .url("https://sandbox.apihub.citi.com/gcb/api/clientCredentials/oauth2/token/hk/gcb")
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
        String jsFile = "./web/RSA.js";
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
        String username = "SandboxUser1";
        // String password = this.getRSA("P@ssUser1$");
        String password = this.getRSA("P@ssUser1$");
        // System.out.println(password);
        UUID uuid = UUID.randomUUID();
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=password&scope=/api&username="+username+"&password="+password);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/password/oauth2/token/hk/gcb")
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
        OkHttpClient client = new OkHttpClient();
        UUID uuid = UUID.randomUUID();
        String authorization = "Bearer " + this.realAccessToken;
        // MediaType mediaType = MediaType.parse("application/json");
        // RequestBody body = RequestBody.create(mediaType, "{\"sourceAccountId\":\"355a515030616a53576b6a65797359506a634175764a734a3238314e4668627349486a676f7449463949453d\",\"transactionAmount\":1,\"transferCurrencyIndicator\":\"SOURCE_ACCOUNT_CURRENCY\",\"destinationAccountId\":\"3739334c4d3463614356474f6d7650667a737656664652677747796855646c5552745a43346d37423653553d\",\"chargeBearer\":\"BENEFICIARY\",\"fxDealReferenceNumber\":\"1232143\",\"remarks\":\"Fund Transfer\"}");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/payees/sourceAccounts?paymentType=ALL&nextStartIndex=0")
                //.post(body)
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("accept", "application/json")
                .addHeader("client_id", CLIENT_ID)
                .addHeader("content-type", "application/json")
                .build();

        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step6 payee:");
        System.out.println("\t"+responseBodyString);
    }

    public void Step7(){
        OkHttpClient client = new OkHttpClient();
        UUID uuid = UUID.randomUUID();
        String authorization = "Bearer " + this.realAccessToken;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"sourceAccountId\":\"355a515030616a53576b6a65797359506a634175764a734a3238314e4668627349486a676f7449463949453d\",\"transactionAmount\":1,\"transferCurrencyIndicator\":\"SOURCE_ACCOUNT_CURRENCY\",\"payeeId\":\"7977557255484c7345546c4e53424766634b6c53756841672b556857626e395253334b70416449676b42673d\",\"chargeBearer\":\"BENEFICIARY\",\"fxDealReferenceNumber\":\"1232143\",\"remarks\":\"Fund Transfer\"}");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/internalDomesticTransfers/preprocess")
                .post(body)
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("accept", "application/json")
                .addHeader("client_id", CLIENT_ID)
                .addHeader("content-type", "application/json")
                .build();

        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step7 move:");
        System.out.println("\t"+responseBodyString);
        flowId = responseBodyString;

    }

    public void Step77(){
        OkHttpClient client = new OkHttpClient();
        UUID uuid = UUID.randomUUID();
        String authorization = "Bearer " + this.realAccessToken;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"sourceAccountId\":\"674d4a4f6a443741656e5a584a6f57665a444e685772393273615777397a4c665073305a5a2b51356f76513d\",\"transactionAmount\":1,\"transferCurrencyIndicator\":\"SOURCE_ACCOUNT_CURRENCY\",\"destinationAccountId\":\"5557596e6f556132725970397479356a4e66504f4638516772434d4663784176616174663332366b4739383d\",\"chargeBearer\":\"BENEFICIARY\",\"fxDealReferenceNumber\":\"1232143\",\"remarks\":\"Fund Transfer\"}");
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess")
                .post(body)
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("accept", "application/json")
                .addHeader("client_id", CLIENT_ID)
                .addHeader("content-type", "application/json")
                .build();

        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step77 move:");
        System.out.println("\t"+responseBodyString);
        flowId = responseBodyString;
    }

    public void Strp8(){
        OkHttpClient client = new OkHttpClient();
        UUID uuid = UUID.randomUUID();
        String authorization = "Bearer " + this.realAccessToken;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, flowId);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/internalDomesticTransfers")
                .post(body)
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("accept", "application/json")
                .addHeader("client_id", CLIENT_ID)
                .addHeader("content-type", "application/json")
                .build();

        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step8 result:");
        System.out.println("\t"+responseBodyString);
    }

    public void Strp88(){
        OkHttpClient client = new OkHttpClient();
        UUID uuid = UUID.randomUUID();
        String authorization = "Bearer " + this.realAccessToken;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, flowId);
        Request request = new Request.Builder()
                .url("https://sandbox.apihub.citi.com/gcb/api/v1/moneyMovement/personalDomesticTransfers")
                .post(body)
                .addHeader("authorization", authorization)
                .addHeader("uuid", uuid.toString())
                .addHeader("accept", "application/json")
                .addHeader("client_id", CLIENT_ID)
                .addHeader("content-type", "application/json")
                .build();

        String responseBodyString = null;
        try{
            Response response = client.newCall(request).execute();
            responseBodyString = response.body().string();
        }catch(Exception e){
            e.printStackTrace();
        }
        this.accounts = responseBodyString;
        System.out.println("step8 result:");
        System.out.println("\t"+responseBodyString);
    }
}
