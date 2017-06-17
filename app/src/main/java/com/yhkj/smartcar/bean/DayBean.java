package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */

public class DayBean {

    /**
     * code : 0
     * msg : 1
     * data : {"days":[0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static DayBean objectFromData(String str) {

        return new Gson().fromJson(str, DayBean.class);
    }

    public static DayBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), DayBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<DayBean> arrayDayBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<DayBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<DayBean> arrayDayBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<DayBean>>() {
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
        private List<Integer> days;

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

        public List<Integer> getDays() {
            return days;
        }

        public void setDays(List<Integer> days) {
            this.days = days;
        }
    }
}
