package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class FaqDetailBean {

    /**
     * code : 0
     * msg : 1
     * data : {"sn":1,"title":"怎么看车架号","attachment":"车架号在。。。。。"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static FaqDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, FaqDetailBean.class);
    }

    public static FaqDetailBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), FaqDetailBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<FaqDetailBean> arrayFaqDetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<FaqDetailBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<FaqDetailBean> arrayFaqDetailBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<FaqDetailBean>>() {
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
         * sn : 1
         * title : 怎么看车架号
         * attachment : 车架号在。。。。。
         */

        private int sn;
        private String title;
        private String attachment;

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

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }
    }
}
