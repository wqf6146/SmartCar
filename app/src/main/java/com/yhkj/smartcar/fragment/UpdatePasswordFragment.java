package com.yhkj.smartcar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.utils.AccountValidatorUtil;
import com.yhkj.smartcar.view.CountDownButton;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/22.
 */

public class UpdatePasswordFragment extends BaseToolbarFragment {

    @Bind(R.id.fu_ll_tip)
    LinearLayout mLlTip;

    @Bind(R.id.fu_btn_cancel)
    TextView mTvCancel;

    @Bind(R.id.fu_tv_receivephone)
    TextView mTvRecPhone;

    @Bind(R.id.fu_btn_next)
    TextView mTvNext;

    @Bind(R.id.fu_edit_phone)
    EditText mEditPhone;

    @Bind(R.id.fu_edit_code)
    EditText mEditCode;

    @Bind(R.id.fu_btn_clear)
    ImageView mBtnPhoneClear;

    @Bind(R.id.fu_tv_getcode)
    CountDownButton mCdbtn;

    @Bind(R.id.fu_rl_container)
    RelativeLayout mRlLayout;

    private String mCode = "1234";
    private String mPhone;
    private String mTitle;
    public static UpdatePasswordFragment getInstance() {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_updatepwd;
    }

    @Override
    protected void initData() {
        super.initData();
        mTitle = getArguments().getString("title");
        setTitleText(mTitle);
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
        mCdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码
                mPhone = mEditPhone.getText().toString();
                if (TextUtils.isEmpty(mPhone) ||
                        !AccountValidatorUtil.isMobile(mPhone)){
                    mCdbtn.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCdbtn.removeCountDown();
                        }
                    },300);
                    showTip(R.mipmap.warning_blue,"请输入正确格式的手机号");
                    return;
                }
                mTvRecPhone.setText(mPhone);
                mLlTip.setVisibility(View.VISIBLE);
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone = mEditPhone.getText().toString();
                String code = mEditCode.getText().toString();
                if (TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(code)) {
                    showTip(R.mipmap.warning_blue, "请填写手机号与获取的验证码");
                    return;
                }
                if (!code.equals(mCode)){
                    showTip(R.mipmap.warning_blue,"验证码错误");
                    return;
                }
                NewPwdFragment fragment = NewPwdFragment.getInstance();
                Bundle bundle = fragment.getArguments();
                bundle.putString("code",String.valueOf(mCode));
                bundle.putString("title",mTitle);
                bundle.putString("phone",mPhone);
                start(fragment);
            }
        });

        mBtnPhoneClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPhone.getText().clear();
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
    }
}
