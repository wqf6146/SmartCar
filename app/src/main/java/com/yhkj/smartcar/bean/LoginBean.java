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

public class LoginBean {


    /**
     * code : 0
     * msg : 1
     * data : {"token":"70d46e4a10a984cd280b1978bf5c3d11","hasbind":true,"sys_msg":true}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static LoginBean objectFromData(String str) {

        return new Gson().fromJson(str, LoginBean.class);
    }

    public static LoginBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), LoginBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<LoginBean> arrayLoginBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<LoginBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<LoginBean> arrayLoginBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<LoginBean>>() {
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : 70d46e4a10a984cd280b1978bf5c3d11
         * hasbind : true
         * sys_msg : true
         */

        private String token;
        private boolean hasbind;
        private boolean sys_msg;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isHasbind() {
            return hasbind;
        }

        public void setHasbind(boolean hasbind) {
            this.hasbind = hasbind;
        }

        public boolean isSys_msg() {
            return sys_msg;
        }

        public void setSys_msg(boolean sys_msg) {
            this.sys_msg = sys_msg;
        }
    }
}
