package com.cdut.myschool.service.okHttpTool;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by XuSt on 2017/3/18.
 */
public class OkHttpJar {
    private OkHttpClient client;
    private int resultCode;
    private JSONObject jsonObject;

    private Headers headers;
    private List<String> cookies;
    private String sessions;
    private String sessionId;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpJar(){}

    public OkHttpJar(OkHttpClient client, int returnCode){
        this.client = client;
        this.resultCode = resultCode;
    }

    /*
    function :
    parameters :
    return :
    */
    public String getResponseBody(String url, FormBody form) throws IOException {
        Request request = new Request.Builder().url(url).post(form).build();
        Response response = client.newCall(request).execute();
        return response.body().string().replace("&nbsp;", " ");
    }
    public String getResponseBody(String url) throws IOException {
        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        return response.body().string().replace("&nbsp;", " ");
    }
}

