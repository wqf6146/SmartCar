package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */

public class NewMessageBean {

    /**
     * code : 0
     * data : false
     * msg : 1
     */

    private int code;
    private boolean data;
    private String msg;

    public static NewMessageBean objectFromData(String str) {

        return new Gson().fromJson(str, NewMessageBean.class);
    }

    public static NewMessageBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), NewMessageBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NewMessageBean> arrayNewMessageBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewMessageBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<NewMessageBean> arrayNewMessageBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<NewMessageBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
