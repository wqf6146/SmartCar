package com.yhkj.smartcar.http.api;

/**
 * Created by Administrator on 2017/5/24.
 */

public interface ApiService {

    String HOST = "http://auto-api.yuanhuiit.cn";

    String LOGIN = HOST + "/member/login";
    String REGISTER = HOST + "/member/regedit";
    String BIND_CAR = HOST + "/device/bind";
    String GET_BIND_EQUIT = HOST + "/device/bind_list";
    String RELIVE_EQUIT = HOST + "/device/unbind";
    String BANNER_IMG = HOST + "/slide/slideList";
    String GET_MESSAGE = HOST + "/news/get_newslist";
    String GET_FAQ = HOST + "/problem/problemList";
    String GET_FAQDETAIL = HOST + "/problem/detail";
    String UPDATEPWD = HOST + "/member/changepwd";
    String FINDPWD = HOST + "/member/getbackpwd";
    String GETHOMEMSG = HOST +"/auto/getinfo";
    String SWITCH_EQUIT = HOST + "/device/toggle";
    String GETCOMPANYINFO = HOST + "/company/info";
    String REPORTADVANCE = HOST + "/advise/add";
    String SERVICE_PROTOCOL = HOST + "/company/service";
    String COMPANY_INTRO = HOST + "/company/introduce";
    String GETNEWMES = HOST + "/news/hasnew";
    String GETTRACK_MONTH = HOST + "/auto/getmonthlist";
    String GETTRACK_DAY = HOST + "/auto/getdaylist";
    String GETPOSITION = HOST + "/auto/getpos";
    String GETTRACK  = HOST  + "/auto/getpath";
    String GETLOCATIONPOS = "http://restapi.amap.com/v3/geocode/regeo?key=173f81826cfe23348e5974148b5ac6e6";
    String UPLODEPIC = HOST + "/member/changepic";

    String SETSYSMES = HOST + "/member/togglesysmsg";
    String GETQUESTION = HOST + "/problem/fdetail";
}
