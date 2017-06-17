package com.yhkj.smartcar;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.xsnow.event.BusFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/2/28.
 */

public abstract class BaseToolbarFragment extends BaseFragment {
//    @Bind(R.id.mt_btn_back)
    ImageView mBtnBack;

//    @Bind(R.id.mt_tv_title)
    TextView mTvTitle;

    ImageView mBtnRight;

    RelativeLayout mToolBar;

    @CallSuper
    protected void initData() {

    }

    @CallSuper
    protected void initView(View contentView, Bundle savedInstanceState) {
        mToolBar = (RelativeLayout)mRootView.findViewById(R.id.mt_toolbar);
        mBtnBack = (ImageView)mRootView.findViewById(R.id.mt_btn_back);
        mTvTitle = (TextView) mRootView.findViewById(R.id.mt_tv_title);
        mBtnRight = (ImageView)mRootView.findViewById(R.id.mt_img_right);
    }

    @CallSuper
    protected void bindEvent() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    protected void setToolbarVisiable(int visibility){
        if (mToolBar!=null)
            mToolBar.setVisibility(visibility);
    }

    protected void setTitleText(String text){
        if (mTvTitle!=null){
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(text);
        }
    }

    protected void setBackBtnVisible(int visibility){
        if (mBtnBack!=null)
            mBtnBack.setVisibility(visibility);
    }

    protected void setBtnBackLisiten(View.OnClickListener onClickListener){
        if (mBtnBack!=null)
            mBtnBack.setOnClickListener( onClickListener );
    }

    protected void setRightBtnResourse(@DrawableRes int resid){
        mBtnRight.setImageResource(resid);
    }

    protected void setBtnRightVisible(int visibility){
        if (mBtnRight!=null){
            mBtnRight.setVisibility(visibility);
        }
    }

    protected void setBtnRightLisitien(View.OnClickListener onClickListener){
        if (mBtnRight!=null)
            mBtnRight.setOnClickListener( onClickListener );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
