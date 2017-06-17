package com.yhkj.smartcar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.RegisterBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.AccountValidatorUtil;
import com.yhkj.smartcar.view.CountDownButton;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/22.
 */

public class RegisterFragment extends BaseToolbarFragment {

    @Bind(R.id.fr_edit_phone)
    EditText mEditPhone;

    @Bind(R.id.fr_edit_password)
    EditText mEditPassword;

    @Bind(R.id.fr_edit_code)
    EditText mEditCode;

    @Bind(R.id.fr_btn_eye)
    ImageView mImgEye;

    @Bind(R.id.fu_tv_getcode)
    CountDownButton mTvGetCode;

    @Bind(R.id.fr_tv_protocol)
    TextView mTvProtocol;

    @Bind(R.id.fr_btn_register)
    TextView mTvRegister;

    @Bind(R.id.fr_btn_cancel)
    TextView mTvCancel;

    @Bind(R.id.fr_btn_phoneclear)
    ImageView mBtnPhoneClear;

    @Bind(R.id.fr_btn_pwdclear)
    ImageView mBtnPwdClear;

    @Bind(R.id.fr_rl_container)
    RelativeLayout mRlContainer;

    private boolean bEyeOpen;

    public static RegisterFragment getInstance() {
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
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
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                final String phone = mEditPhone.getText().toString();
                final String password = mEditPassword.getText().toString();
                final String code = mEditCode.getText().toString();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)){
                    showTip(R.mipmap.warning_blue,"请输入手机号和密码");
                    return;
                }

                if (TextUtils.isEmpty(code)){
                    showTip(R.mipmap.warning_blue,"请输入短信验证码");
                    return;
                }

                if (!AccountValidatorUtil.isMobile(phone)){
                    showTip(R.mipmap.warning_blue, "请输入正确格式的手机号");
                    return;
                }

                if (!AccountValidatorUtil.isPassword(password)){
                    showTip(R.mipmap.warning_blue, "密码由6-12位数字与字母组成");
                    return;
                }

                if (code.length() < 4){
                    showTip(R.mipmap.warning_blue, "请输入4位验证码");
                    return;
                }

                final SweetAlertDialog sDialog = showDialog("正在加载，请稍后‥");
                SmartCarApi.getInstance().startRegister(_mActivity, phone, password, code, new ApiCallback<RegisterBean>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        ViseLog.e(e);
                        sDialog.setTitleText("注册失败")
                                .setContentText(e.getMessage())
                                .setConfirmText("确定")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE,false,true);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        showTip(R.mipmap.warning_blue,"注册成功");
                        sDialog.dismiss();
                        BindCarFragment fragment = BindCarFragment.getInstance();
                        Bundle bundle = fragment.getArguments();
                        bundle.putString("phone",phone);
                        bundle.putString("password",password);
                        start(fragment);
                    }
                });
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
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

        mTvProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(ProtocolFragment.getInstance());
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        setBackBtnVisible(View.VISIBLE);
        setTitleText("用户注册");

        enqueueAction(new Runnable() {
            @Override
            public void run() {
                getView().requestFocus();
            }
        });
    }


}
