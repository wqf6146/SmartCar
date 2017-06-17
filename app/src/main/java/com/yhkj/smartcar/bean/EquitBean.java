package com.yhkj.smartcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class EquitBean {

    /**
     * code : 0
     * msg : 1
     * data : {"list":[{"id":24,"serverid":"","devicekey":"1001","platenum":"9999","vin":"12345678912345678","status":1,"net_status":0}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static EquitBean objectFromData(String str) {

        return new Gson().fromJson(str, EquitBean.class);
    }

    public static EquitBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), EquitBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<EquitBean> arrayEquitBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<EquitBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<EquitBean> arrayEquitBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<EquitBean>>() {
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
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 24
             * serverid :
             * devicekey : 1001
             * platenum : 9999
             * vin : 12345678912345678
             * status : 1
             * net_status : 0
             */

            private int id;
            private String serverid;
            private String devicekey;
            private String platenum;
            private String vin;
            private int status;
            private int net_status;

            public static ListBean objectFromData(String str) {

                return new Gson().fromJson(str, ListBean.class);
            }

            public static ListBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), ListBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<ListBean> arrayListBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ListBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<ListBean> arrayListBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<ListBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getServerid() {
                return serverid;
            }

            public void setServerid(String serverid) {
                this.serverid = serverid;
            }

            public String getDevicekey() {
                return devicekey;
            }

            public void setDevicekey(String devicekey) {
                this.devicekey = devicekey;
            }

            public String getPlatenum() {
                return platenum;
            }

            public void setPlatenum(String platenum) {
                this.platenum = platenum;
            }

            public String getVin() {
                return vin;
            }

            public void setVin(String vin) {
                this.vin = vin;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getNet_status() {
                return net_status;
            }

            public void setNet_status(int net_status) {
                this.net_status = net_status;
            }
        }
    }
}
