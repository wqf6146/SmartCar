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

public class BannerBean {

    /**
     * code : 0
     * msg : 1
     * data : {"pic":"http://auto2.com/images/201706081501153294.png","list":[{"id":1,"title":"轮播图1","link":"www.baidu.com","picture":"1","sn":1},{"id":2,"title":"轮播图2","link":"www.sian.com","picture":"2","sn":2}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static BannerBean objectFromData(String str) {

        return new Gson().fromJson(str, BannerBean.class);
    }

    public static BannerBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BannerBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<BannerBean> arrayBannerBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<BannerBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<BannerBean> arrayBannerBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<BannerBean>>() {
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
         * pic : http://auto2.com/images/201706081501153294.png
         * list : [{"id":1,"title":"轮播图1","link":"www.baidu.com","picture":"1","sn":1},{"id":2,"title":"轮播图2","link":"www.sian.com","picture":"2","sn":2}]
         */

        private String pic;
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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * title : 轮播图1
             * link : www.baidu.com
             * picture : 1
             * sn : 1
             */

            private int id;
            private String title;
            private String link;
            private String picture;
            private int sn;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getSn() {
                return sn;
            }

            public void setSn(int sn) {
                this.sn = sn;
            }
        }
    }
}
