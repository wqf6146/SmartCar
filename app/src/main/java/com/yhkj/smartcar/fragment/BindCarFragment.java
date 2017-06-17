package com.yhkj.smartcar.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
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
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.activity.MainActivity;
import com.yhkj.smartcar.bean.BindCarBean;
import com.yhkj.smartcar.bean.CompanyMsgBean;
import com.yhkj.smartcar.bean.LocalConfig;
import com.yhkj.smartcar.bean.LoginBean;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.AccountValidatorUtil;
import com.yhkj.smartcar.utils.Constant;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/22.
 */

public class BindCarFragment extends BaseToolbarFragment {

    @Bind(R.id.fb_edit_carnumb)
    EditText mEditCarNumb;

    @Bind(R.id.fb_edit_vin)
    EditText mEditVin;

    @Bind(R.id.fb_edit_equitid)
    EditText mEditEquitId;

    @Bind(R.id.fb_btn_carnumbclear)
    ImageView mBtnCarNumbClear;

    @Bind(R.id.fb_btn_vinclear)
    ImageView mBtnVinClear;

    @Bind(R.id.fb_btn_equitidclear)
    ImageView mBtnEquitidClear;

    @Bind(R.id.fb_btn_scan)
    ImageView mBtnScan;

    @Bind(R.id.fb_btn_equitquestion)
    ImageView mBtnEquitQuestion;

    @Bind(R.id.fb_btn_vinquestion)
    ImageView mBtnVinQuestion;

    @Bind(R.id.fb_btn_done)
    TextView mBtnBind;

    @Bind(R.id.fn_btn_service)
    TextView mBtnService;

    @Bind(R.id.fn_btn_cancel)
    TextView mTvCancel;

    @Bind(R.id.fb_rl_container)
    RelativeLayout mRlLayout;

    private boolean bInit = false;

    private String mCompanyPhone;

    public static BindCarFragment getInstance() {
        BindCarFragment fragment = new BindCarFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_bindcar;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        bInit = getArguments().getBoolean("new",false);
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data!=null){
            String res = data.getString("res");
            if (!TextUtils.isEmpty(res)){
                mEditEquitId.setText(res);
            }
        }
    }

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
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBindEquit();
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bInit) {
                    pop();
                }else {
                    popTo(LoginFragment.class,false);
                }
            }
        });

        setBtnBackLisiten(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bInit) {
                    pop();
                }else {
                    popTo(LoginFragment.class,false);
                }
            }
        });

        mBtnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //联系客服
                if (TextUtils.isEmpty(mCompanyPhone))
                    return;
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mCompanyPhone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mBtnEquitidClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditEquitId.getText().clear();
            }
        });

        mBtnVinClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditVin.getText().clear();
            }
        });

        mBtnCarNumbClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditCarNumb.getText().clear();
            }
        });


        mEditCarNumb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    mBtnCarNumbClear.setVisibility(View.VISIBLE);
                }else if (s.length() == 0){
                    mBtnCarNumbClear.setVisibility(View.INVISIBLE);
                }
            }
        });
        mEditVin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    mBtnVinClear.setVisibility(View.VISIBLE);
                }else if (s.length() == 0){
                    mBtnVinClear.setVisibility(View.INVISIBLE);
                }
            }
        });

        mEditEquitId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    mBtnEquitidClear.setVisibility(View.VISIBLE);
                }else if (s.length() == 0){
                    mBtnEquitidClear.setVisibility(View.INVISIBLE);
                }
            }
        });


        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫描
//                Intent intent = new Intent();
//                intent.setClass(_mActivity, ScanFragment.class);
//                startActivity(intent);
                startForResult(ScanFragment.getInstance(),1);
            }
        });

        mBtnEquitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设备问题
                FaqDetailFragment faqDetailFragment = FaqDetailFragment.getInstance();
                Bundle bundle = faqDetailFragment.getArguments();
                bundle.putString("flag","devicekey");
                start(faqDetailFragment);
            }
        });

        mBtnVinQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //车架号问题
                FaqDetailFragment faqDetailFragment = FaqDetailFragment.getInstance();
                Bundle bundle = faqDetailFragment.getArguments();
                bundle.putString("flag","vin");
                start(faqDetailFragment);
            }
        });



    }

    private void doBackLogin(final String phone,final String password){
        final SweetAlertDialog sDialog = showDialog("正在加载，请稍后‥");
        SmartCarApi.getInstance().startLogin(_mActivity, phone, password, new ApiCallback<LoginBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                sDialog.setTitleText("加载失败")
                        .setContentText(e.getMessage())
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.WARNING_TYPE,false,true);
                popTo(LoginFragment.class,false);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(LoginBean.DataBean loginBean) {

                LocalConfig localConfig = new LocalConfig();
                localConfig.setBAutoLogin(false);
                localConfig.setPhone(phone);
                localConfig.setPassword(password);
                localConfig.setToken(loginBean.getToken());
                DbHelper.getInstance().configLongDBManager().deleteAll();
                DbHelper.getInstance().configLongDBManager().insert(localConfig);

                App.getInstance().setUserBean(localConfig);
                sDialog.dismissWithDelay(500);
            }
        });
    }

    private void doBindEquit(){
        //绑定
        String carNumb = mEditCarNumb.getText().toString();
        String vin = mEditVin.getText().toString();
        String equitId = mEditEquitId.getText().toString();
        if (TextUtils.isEmpty(carNumb) || TextUtils.isEmpty(vin) || TextUtils.isEmpty(equitId)){
            showTip(R.mipmap.warning_blue,"请补全必要信息");
            return;
        }

        if (!AccountValidatorUtil.isPlateNumb(carNumb)){
            showTip(R.mipmap.warning_blue,"请输入正确的车牌号码，注意区分大小写");
            return;
        }

        final SweetAlertDialog sDialog = showDialog("正在绑定，请稍后‥");
        SmartCarApi.getInstance().startBindCar(getContext(), carNumb, vin, equitId, new ApiCallback<BindCarBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                ViseLog.e(e);
                sDialog.setTitleText("绑定失败")
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
            public void onNext(BindCarBean bindCarBean) {
                sDialog.dismissWithDelay(500);
                showToast("绑定成功");
                if (bInit){
                    setFragmentResult(Constant.RESULT_CODE_OK,null);
                    pop();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(_mActivity, MainActivity.class);
                _mActivity.startActivity(intent);
                _mActivity.finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        setBackBtnVisible(View.VISIBLE);
        setTitleText("绑定设备");
        final SweetAlertDialog sDialog = showDialog("正在加载，请稍后‥");
        SmartCarApi.getInstance().getCompanyInfo(_mActivity, new ApiCallback<CompanyMsgBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                if (filterHttpCallback(sDialog,e))
                    return;
                ViseLog.e(e);
                sDialog.setTitleText("加载失败")
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
            public void onNext(CompanyMsgBean.DataBean dataBean) {
                sDialog.dismissWithDelay(500);
                mCompanyPhone = dataBean.getTel();
            }
        });

        String phone = getArguments().getString("phone");
        String password = getArguments().getString("password");
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
            doBackLogin(phone, password);
        }

        enqueueAction(new Runnable() {
            @Override
            public void run() {
                getView().requestFocus();
            }
        });
    }
}
