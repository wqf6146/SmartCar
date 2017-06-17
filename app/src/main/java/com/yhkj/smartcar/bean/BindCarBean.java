package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class BindCarBean {

    /**
     * code : 0
     * data : []
     * msg : 1
     */

    private int code;
    private String msg;
    private List<?> data;

    public static BindCarBean objectFromData(String str) {

        return new Gson().fromJson(str, BindCarBean.class);
    }

    public static BindCarBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BindCarBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<BindCarBean> arrayBindCarBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BindCarBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<BindCarBean> arrayBindCarBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<BindCarBean>>() {
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
