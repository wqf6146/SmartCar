package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HomeMsgBean {

    /**
     * code : 0
     * msg : 1
     * data : {"temp":"22","humidity":"70%","platenum":"冀F88888","devicekey":"1004"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static HomeMsgBean objectFromData(String str) {

        return new Gson().fromJson(str, HomeMsgBean.class);
    }

    public static HomeMsgBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), HomeMsgBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<HomeMsgBean> arrayHomeMsgBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<HomeMsgBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<HomeMsgBean> arrayHomeMsgBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<HomeMsgBean>>() {
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
         * temp : 22
         * humidity : 70%
         * platenum : 冀F88888
         * devicekey : 1004
         */

        private String temp;
        private String humidity;
        private String platenum;
        private String devicekey;

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

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPlatenum() {
            return platenum;
        }

        public void setPlatenum(String platenum) {
            this.platenum = platenum;
        }

        public String getDevicekey() {
            return devicekey;
        }

        public void setDevicekey(String devicekey) {
            this.devicekey = devicekey;
        }
    }
}
