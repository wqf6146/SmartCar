package com.yhkj.smartcar.http;

import android.content.Context;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.http.api.ApiService;
import com.vise.xsnow.net.api.ViseApi;
import com.vise.xsnow.net.callback.ApiCallback;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/24.
 */

public class SmartCarApi {

    private static SmartCarApi smartCarApi;

    public static SmartCarApi getInstance() {
        if (smartCarApi == null){
            synchronized (SmartCarApi.class){
                if (smartCarApi == null){
                    return smartCarApi = new SmartCarApi();
                }
            }
        }
        return smartCarApi;
    }

    /**
     * 注册
     */
    public <T> void startRegister(final Context context,String phone,String password,String code, ApiCallback<T> apicallback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashmap = new HashMap();
        hashmap.put("phone",phone);
        hashmap.put("password",password);
        hashmap.put("pin",code);
        api.apiPost(ApiService.REGISTER,hashmap,apicallback);
    }

    /***
     *
     */
    public <T> void getLocaitonPos(final Context context,String latng,ApiCallback<T> apiCallback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashmap = new HashMap();
        hashmap.put("location",latng);
        api.get(ApiService.GETLOCATIONPOS,hashmap,apiCallback);
    }

    /***
     * 上传头像
     */
    public <T> void uplodeUserImg(final Context context, File file, ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/jpg;"),
                        file
                );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("pic", file.getName(), requestFile);
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);
        api.uploadFile(ApiService.UPLODEPIC,App.getInstance().getToken(),description,body,callback);
    }

    /**
     * 登录
     * @param context
     * @param phone
     * @param password
     * @param callback
     * @param <T>
     */
    public <T> void startLogin(Context context,String phone,String password,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashmap = new HashMap();
        hashmap.put("phone",phone);
        hashmap.put("password",password);
        api.apiPost(ApiService.LOGIN,hashmap,callback);
    }

    public <T> void getQuestionDetail(Context context,String tag, ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashmap = new HashMap();
        hashmap.put("flag",tag);
        api.apiPost(ApiService.GETQUESTION,hashmap,callback);
    }

    /****
     * 找回密码
     * @param context
     * @param phone
     * @param password
     * @param callback
     * @param <T>
     */
    public <T> void findPwd(Context context,String phone,String password,String code,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashmap = new HashMap();
        hashmap.put("phone",phone);
        hashmap.put("password",password);
        hashmap.put("pin",code);
        api.apiPost(ApiService.FINDPWD,hashmap, callback);
    }

    /**
     * 绑定
     */
    public <T> void startBindCar(Context context,String carNumb,String vin,String equitId,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashmap = new HashMap();
        hashmap.put("devicekey",equitId);
        hashmap.put("platenum",carNumb);
        hashmap.put("vin",vin);
        api.apiPost(ApiService.BIND_CAR, App.getInstance().getToken(),hashmap,callback);
    }

    /***
     * 获取设备列表
     */
    public <T> void getEquitList(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.GET_BIND_EQUIT, App.getInstance().getToken(),new HashMap<String, String>(),callback);
    }

    /***
     * 设置系统消息
     *
     * */
    public <T> void setSysMes(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.SETSYSMES, App.getInstance().getToken(),new HashMap<String, String>(),callback);
    }

    /***
     * 获取轨迹
     */
    public <T> void getTraclDetail(Context context,String date,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("devicekey",App.getInstance().getDevicekey());
        hashMap.put("date",date);
        api.apiPost(ApiService.GETTRACK, App.getInstance().getToken(),hashMap,callback);
    }

    /***
     * 解除设备列表
     */
    public <T> void reliveEquit(Context context,int id,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("id",String.valueOf(id));
        api.apiPost(ApiService.RELIVE_EQUIT, App.getInstance().getToken(),hashMap,callback);
    }

    /**
     * 获取月份
     */
    public <T> void getTrackMonth(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("devicekey",App.getInstance().getDevicekey());
        api.apiPost(ApiService.GETTRACK_MONTH,App.getInstance().getToken(),hashMap,callback);
    }

    /***
     * 轮播图
     */
    public <T> void getBannerImg(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.BANNER_IMG,App.getInstance().getToken(), new HashMap(),callback);
    }

    /***
     * 获取消息列表
     */
    public <T> void getMessage(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.GET_MESSAGE, App.getInstance().getToken(),new HashMap(),callback);
    }

    /***
     * 获取定位点
     */
    public <T> void getPosition(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap map = new HashMap();
        map.put("devicekey",App.getInstance().getDevicekey());
        api.apiPost(ApiService.GETPOSITION, App.getInstance().getToken(),map,callback);
    }

    /***
     * 获取轨迹天数
     */
    public <T> void getTrackDay(Context context,String month,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("devicekey",App.getInstance().getmHomeMsg().getDevicekey());
        hashMap.put("date",month);
        api.apiPost(ApiService.GETTRACK_DAY,App.getInstance().getToken(),hashMap,callback);
    }

    /***
     * 获取更多消息列表
     */
    public <T> void getMessage(Context context,String id, ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("id",id);
        api.apiPost(ApiService.GET_MESSAGE, App.getInstance().getToken(),hashMap,callback);
    }

    /***
     * 获取消息列表
     */
    public <T> void getFaqList(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.GET_FAQ, new HashMap(),callback);
    }

    /***
     * 获取消息列表
     */
    public <T> void getFaqDetail(Context context,String id,String numb,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap =  new HashMap();
        hashMap.put("id",id);
        hashMap.put("num",numb);
        api.apiPost(ApiService.GET_FAQDETAIL,hashMap,callback);
    }

    /***
     * 修改密码
     */
    public <T> void updatePassword(Context context,String pwd,String code,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap =  new HashMap();
        hashMap.put("password",pwd);
        hashMap.put("pin",code);
        api.apiPost(ApiService.UPDATEPWD,App.getInstance().getToken(),hashMap,callback);
    }

    /***
     * 获取首页相关信息
     */
    public <T> void getHomeMessage(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.GETHOMEMSG,App.getInstance().getToken(),new HashMap(),callback);
    }

    /***
     * 切换默认设备
     */
    public <T> void setDefaultEquit(Context context,String id,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("id",id);
        api.apiPost(ApiService.SWITCH_EQUIT,App.getInstance().getToken(),hashMap,callback);
    }

    /*****
     * 获取公司信息
     */
    public <T> void getCompanyInfo(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.GETCOMPANYINFO,new HashMap(),callback);
    }

    /*****
     * 服务协议
     */
    public <T> void getServiceProtocol(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.SERVICE_PROTOCOL,new HashMap(),callback);
    }

    /*****
     * 公司介绍
     */
    public <T> void getCompanyIntro(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.COMPANY_INTRO,new HashMap(),callback);
    }

    /***
     * 提交建议
     */
    public <T> void reportAdvance(Context context,String content,String phone,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("phone",phone);
        hashMap.put("content",content);
        api.apiPost(ApiService.REPORTADVANCE,App.getInstance().getToken(),hashMap,callback);
    }

    /***
     * 获取新消息
     */
    public <T> void getNewMessage(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.apiPost(ApiService.GETNEWMES,App.getInstance().getToken(),new HashMap(),callback);
    }
}
