package com.yhkj.smartcar.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/5/26.
 */

@Entity
public class LocalConfig {

    @Id
    Long id;

    boolean bAutoLogin;

    String phone;
    String password;
    String token;
    boolean sysmes;
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean getBAutoLogin() {
        return this.bAutoLogin;
    }
    public void setBAutoLogin(boolean bAutoLogin) {
        this.bAutoLogin = bAutoLogin;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean getSysmes() {
        return this.sysmes;
    }
    public void setSysmes(boolean sysmes) {
        this.sysmes = sysmes;
    }
    @Generated(hash = 219422654)
    public LocalConfig(Long id, boolean bAutoLogin, String phone, String password,
            String token, boolean sysmes) {
        this.id = id;
        this.bAutoLogin = bAutoLogin;
        this.phone = phone;
        this.password = password;
        this.token = token;
        this.sysmes = sysmes;
    }
    @Generated(hash = 1119994875)
    public LocalConfig() {
    }
   
}
