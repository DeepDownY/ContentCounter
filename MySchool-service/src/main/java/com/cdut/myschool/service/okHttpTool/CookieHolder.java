package com.cdut.myschool.service.okHttpTool;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XuSt on 2017/3/18.
 */
public class CookieHolder implements CookieJar {
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        cookieStore.put(httpUrl.host(), list);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> cookies = cookieStore.get(httpUrl.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
