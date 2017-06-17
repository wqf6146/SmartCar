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

public class MessageBean {

    /**
     * code : 0
     * msg : 1
     * data : {"list":[{"id":166,"status":0,"content":"尊敬的用户，您的汽车【车牌：1006】于2017-05-27 09:47:35成功绑定。如非本人操作，请联系客服101-101-10111。","create_time":1495849655},{"id":165,"status":0,"content":"尊敬的用户，您的汽车【车牌：1006】于2017-05-27 09:44:31成功绑定。如非本人操作，请联系客服101-101-10111。","create_time":1495849471},{"id":164,"status":0,"content":"尊敬的用户，您的汽车【车牌：1005】于2017-05-27 09:44:12成功解绑。如非本人操作，请联系客服101-101-10111。","create_time":1495849452},{"id":163,"status":0,"content":"尊敬的用户，您的汽车【车牌：1005】于2017-05-27 09:43:53成功绑定。如非本人操作，请联系客服101-101-10111。","create_time":1495849433},{"id":162,"status":0,"content":"尊敬的用户，您的汽车【车牌：1005】于2017-05-27 09:43:20成功解绑。如非本人操作，请联系客服101-101-10111。","create_time":1495849400},{"id":161,"status":0,"content":"尊敬的用户，您的汽车【车牌：1006】于2017-05-27 09:43:14成功解绑。如非本人操作，请联系客服101-101-10111。","create_time":1495849394},{"id":160,"status":0,"content":"尊敬的用户，您的汽车【车牌：1006】于2017-05-27 09:43:06成功绑定。如非本人操作，请联系客服101-101-10111。","create_time":1495849386},{"id":159,"status":0,"content":"尊敬的用户，您的汽车【车牌：1005】于2017-05-27 09:41:59成功绑定。如非本人操作，请联系客服101-101-10111。","create_time":1495849319},{"id":158,"status":0,"content":"尊敬的用户，您的汽车【车牌：1002】于2017-05-27 09:22:27成功解绑。如非本人操作，请联系客服101-101-10111。","create_time":1495848147},{"id":157,"status":0,"content":"尊敬的用户，您的汽车【车牌：1006】于2017-05-27 09:22:19成功解绑。如非本人操作，请联系客服101-101-10111。","create_time":1495848139}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public static MessageBean objectFromData(String str) {

        return new Gson().fromJson(str, MessageBean.class);
    }

    public static MessageBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), MessageBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<MessageBean> arrayMessageBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<MessageBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<MessageBean> arrayMessageBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<MessageBean>>() {
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
             * id : 166
             * status : 0
             * content : 尊敬的用户，您的汽车【车牌：1006】于2017-05-27 09:47:35成功绑定。如非本人操作，请联系客服101-101-10111。
             * create_time : 1495849655
             */

            private int id;
            private int status;
            private String content;
            private int create_time;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }
    }
}
