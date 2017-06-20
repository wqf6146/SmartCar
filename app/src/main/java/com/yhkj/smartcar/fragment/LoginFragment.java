package com.yhkj.smartcar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.log.ViseLog;
import com.vise.xsnow.database.DBManager;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.ApiResult;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.activity.LaunchActiviy;
import com.yhkj.smartcar.activity.LoginActivity;
import com.yhkj.smartcar.activity.MainActivity;
import com.yhkj.smartcar.bean.EquitBean;
import com.yhkj.smartcar.bean.LocalConfig;
import com.yhkj.smartcar.bean.LoginBean;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.AccountValidatorUtil;
import com.yhkj.smartcar.utils.Constant;

import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/22.
 */

public class LoginFragment extends BaseToolbarFragment {

    @Bind(R.id.fl_edit_phone)
    EditText mEditPhone;

    @Bind(R.id.fl_edit_password)
    EditText mEditPassword;

    @Bind(R.id.fn_btn_eye)
    ImageView mImgEye;

    @Bind(R.id.fl_cb_autologin)
    ImageView mImgAutoLogin;

    @Bind(R.id.fl_tv_findpw)
    TextView mTvFindPd;

    @Bind(R.id.fl_btn_login)
    TextView mTvLogin;

    @Bind(R.id.fl_btn_register)
    TextView mTvRegister;

    @Bind(R.id.fl_btn_phoneclear)
    ImageView mBtnPhoneClear;

    @Bind(R.id.fl_btn_pwdclear)
    ImageView mBtnPwdClear;

    @Bind(R.id.fl_tv_autologin)
    TextView mTvAutoLogin;

    @Bind(R.id.fl_rl_container)
    RelativeLayout mRlContainer;

    private boolean bEyeOpen = false;

    private boolean bAutoLogin = false;

    public static LoginFragment getInstance() {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        setBackBtnVisible(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("用户登录");

        List<LocalConfig> localConfigList = DbHelper.getInstance().configLongDBManager().loadAll();

        if (localConfigList.size() > 0){
            LocalConfig localConfig = localConfigList.get(0);
            if (localConfig.getBAutoLogin() &&
                    !TextUtils.isEmpty(localConfig.getPhone()) && !TextUtils.isEmpty(localConfig.getPassword())){
                mEditPhone.setText(localConfig.getPhone());
                mEditPhone.setSelection(mEditPhone.getText().toString().length());
                mEditPassword.setText(localConfig.getPassword());
                mEditPassword.setSelection(mEditPassword.getText().toString().length());
                bAutoLogin = localConfig.getBAutoLogin();
                mImgAutoLogin.setImageResource(bAutoLogin ? R.mipmap.ic_btn_select_2 : R.mipmap.ic_btn_select_1);
            }
        }

        enqueueAction(new Runnable() {
            @Override
            public void run() {
                getView().requestFocus();
            }
        });
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        mRlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        mTvAutoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bAutoLogin){
                    bAutoLogin = false;
                    mImgAutoLogin.setImageResource(R.mipmap.ic_btn_select_1);
                }else{
                    bAutoLogin = true;
                    mImgAutoLogin.setImageResource(R.mipmap.ic_btn_select_2);
                }
            }
        });

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mEditPhone.getText().toString();
                String password = mEditPassword.getText().toString();

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
                    showTip(R.mipmap.warning_blue, "请输入手机号和密码");
                    return;
                }
                if (!AccountValidatorUtil.isMobile(phone)){
                    showTip(R.mipmap.warning_blue, "请输入正确格式的手机号");
                    return;
                }
                final SweetAlertDialog sDialog = showDialog("正在登录，请稍后‥");
                doLogin(sDialog,phone,password);
            }
        });

        mImgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bEyeOpen){
                    //明文
                    mEditPassword.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    mEditPassword.setSelection(mEditPassword.getText().toString().length());
                    mImgEye.setImageResource( R.mipmap.ic_btn_eye_2 );
                    bEyeOpen = true;
                }else{
                    mEditPassword.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                    mEditPassword.setSelection(mEditPassword.getText().toString().length());
                    mImgEye.setImageResource( R.mipmap.ic_btn_eye_1 );
                    bEyeOpen = false;
                }
            }
        });
        mImgAutoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bAutoLogin){
                    bAutoLogin = false;
                    mImgAutoLogin.setImageResource(R.mipmap.ic_btn_select_1);
                }else{
                    bAutoLogin = true;
                    mImgAutoLogin.setImageResource(R.mipmap.ic_btn_select_2);
                }
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(RegisterFragment.getInstance());
            }
        });

        mBtnPhoneClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPhone.getText().clear();
            }
        });
        mBtnPwdClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPassword.getText().clear();
            }
        });
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    mBtnPhoneClear.setVisibility(View.VISIBLE);
                }else if (s.length() == 0){
                    mBtnPhoneClear.setVisibility(View.INVISIBLE);
                }
            }
        });
        mEditPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    mBtnPwdClear.setVisibility(View.VISIBLE);
                }else if (s.length() == 0){
                    mBtnPwdClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        mTvFindPd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePasswordFragment fragment = UpdatePasswordFragment.getInstance();
                Bundle bundle = fragment.getArguments();
                bundle.putString("title", Constant.TITLE_FINDPWD);
                start(fragment);
            }
        });
    }

    private void doLogin(final SweetAlertDialog sDialog,final String phone,final String password) {

        SmartCarApi.getInstance().startLogin(_mActivity, phone, password, new ApiCallback<LoginBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                sDialog.setTitleText("登录失败")
                        .setContentText(e.getMessage())
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE,false,true);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(LoginBean.DataBean loginbean) {

                sDialog.dismissWithDelay(500);
                LocalConfig localConfig = new LocalConfig();
                localConfig.setBAutoLogin(bAutoLogin);
                localConfig.setPhone(phone);
                localConfig.setPassword(password);
                localConfig.setToken(loginbean.getToken());
                localConfig.setSysmes(loginbean.isSys_msg());
                DbHelper.getInstance().configLongDBManager().deleteAll();
                DbHelper.getInstance().configLongDBManager().insert(localConfig);

                App.getInstance().setUserBean(localConfig);
                showToast("登录成功");
                if (loginbean.isHasbind()){
                    Intent intent = new Intent();
                    intent.setClass(_mActivity, MainActivity.class);
                    _mActivity.startActivity(intent);
                    _mActivity.finish();
                }else{
                    start(BindCarFragment.getInstance());
                }

//                if (loginBean.getCode() == 0){
//
//                }else{
//                    sDialog.setTitleText("登录失败")
//                            .setContentText(loginBean.getMsg())
//                            .showCancelButton(false)
//                            .setCancelClickListener(null)
//                            .setConfirmClickListener(null)
//                            .changeAlertType(SweetAlertDialog.ERROR_TYPE,false,true);
//                }
            }
        });

    }
}
