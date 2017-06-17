package com.yhkj.smartcar;

import android.app.Application;

import com.vise.xsnow.database.DBManager;
import com.yhkj.smartcar.bean.HomeMsgBean;
import com.yhkj.smartcar.bean.LocalConfig;
import com.yhkj.smartcar.bean.LoginBean;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.utils.CommonUtils;

/**
 * Created by Administrator on 2017/5/22.
 */

public class App extends Application {

    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }

    private LocalConfig mUser;
    private HomeMsgBean.DataBean mHomeMsg;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        DbHelper.getInstance().init(this);
    }

    public void setUserBean(LocalConfig dataBean){
        mUser = dataBean;
    }

    public LocalConfig getUser() {
        if (mUser == null)
            mUser = DbHelper.getInstance().configLongDBManager().load(0L);
        return mUser;
    }

    public HomeMsgBean.DataBean getmHomeMsg() {
        return mHomeMsg;
    }

    public void setmHomeMsg(HomeMsgBean.DataBean mHomeMsg) {
        this.mHomeMsg = mHomeMsg;
    }

    public String getToken(){
        if (mUser == null)
            return null;
        return mUser.getToken();
    }

    public String getDevicekey(){
        if (mHomeMsg == null)
            return null;
        return mHomeMsg.getDevicekey();
    }
}
