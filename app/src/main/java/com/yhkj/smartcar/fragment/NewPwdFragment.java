package com.yhkj.smartcar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.ActionBar;
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

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.ApiResult;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.NorBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.AccountValidatorUtil;
import com.yhkj.smartcar.utils.Constant;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/22.
 */

public class NewPwdFragment extends BaseToolbarFragment {

    @Bind(R.id.fn_btn_cancel)
    TextView mBtnCancel;


    @Bind(R.id.fn_btn_pwdclear)
    ImageView mBtnPwdClear;

    @Bind(R.id.fn_edit_password)
    EditText mEditPassword;

    @Bind(R.id.fn_btn_eye)
    ImageView mImgEye;

    @Bind(R.id.fn_btn_done)
    TextView mBtnDone;

    @Bind(R.id.fn_rl_container)
    RelativeLayout mRlLayout;

    private String mCode;
    private String mPhone;
    private int mType;  //1-修改退回set 2-找回退回login
    public static NewPwdFragment getInstance() {
        NewPwdFragment fragment = new NewPwdFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_newpwd;
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("设置新登陆密码");
        mCode = getArguments().getString("code");
        mType = getArguments().getString("title").equals(Constant.TITLE_UPDATE) ? 1 : 2;
        mPhone = getArguments().getString("phone");
        enqueueAction(new Runnable() {
            @Override
            public void run() {
                getView().requestFocus();
            }
        });

    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
    }


    private boolean bEyeOpen;

    @Override
    protected void bindEvent() {
        super.bindEvent();
        mRlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mEditPassword.getText().toString();
                if (TextUtils.isEmpty(password)){
                    showTip(R.mipmap.warning_blue,"先输入新密码");
                    return;
                }
                if (!AccountValidatorUtil.isPassword(password)){
                    showTip(R.mipmap.warning_blue, "密码由6-12位数字与字母组成");
                    return;
                }
                final SweetAlertDialog sDialog = showDialog("正在修改，请稍后‥");
                if (mType == 2){
                    doFindPwd(sDialog,mPhone,password,mCode);
                }else{
                    doUpdatePwd(sDialog,password, mCode);

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
        mBtnPwdClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPassword.getText().clear();
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
    }

    private void doUpdatePwd(final SweetAlertDialog sweetAlertDialog,String password, String mCode) {
        SmartCarApi.getInstance().updatePassword(_mActivity,password ,mCode, new ApiCallback<NorBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                sweetAlertDialog.setTitleText("修改失败")
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
            public void onNext(NorBean norBean) {
                sweetAlertDialog.dismissWithDelay(500);
                showTip(R.mipmap.warning_blue,"修改成功");
                popTo(SetFragment.class,false);
//                if (norBean.getCode() == 0){
//
//                }else{
//                    sweetAlertDialog.setTitleText("修改失败")
//                            .setContentText(norBean.getMsg())
//                            .showCancelButton(false)
//                            .setCancelClickListener(null)
//                            .setConfirmClickListener(null)
//                            .changeAlertType(SweetAlertDialog.ERROR_TYPE,false,true);
//                }
            }
        });
    }

    private void doFindPwd(final SweetAlertDialog sweetAlertDialog,String mPhone, String password, String mCode) {
        SmartCarApi.getInstance().findPwd(_mActivity, mPhone, password, mCode, new ApiCallback<NorBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                sweetAlertDialog.setTitleText("修改失败")
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
            public void onNext(NorBean norBeanApiResult) {
                sweetAlertDialog.dismissWithDelay(500);
                showTip(R.mipmap.warning_blue,"修改成功");
                popTo(LoginFragment.class,false);
            }
        });
    }
}
