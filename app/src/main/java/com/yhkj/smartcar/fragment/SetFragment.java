package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.utils.Constant;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/27.
 */

public class SetFragment extends BaseToolbarFragment {


    @Bind(R.id.fs_rl_updatepwd)
    RelativeLayout mRlUpdatePwd;

    @Bind(R.id.fs_rl_message)
    RelativeLayout mRlMessage;

    @Bind(R.id.fs_rl_checkupdate)
    RelativeLayout mRlCheckUpdate;

    public static SetFragment getInstance() {
        SetFragment fragment = new SetFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        mRlUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePasswordFragment fragment = UpdatePasswordFragment.getInstance();
                Bundle bundle = fragment.getArguments();
                bundle.putString("title", Constant.TITLE_UPDATE);
                start(fragment);
            }
        });
        mRlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MessageSetFragment.getInstance());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("设 置");

    }
}
