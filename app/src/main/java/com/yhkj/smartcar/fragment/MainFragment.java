package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vise.log.ViseLog;
import com.vise.xsnow.event.EventSubscribe;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.HomeMsgBean;
import com.yhkj.smartcar.event.JumpFragmentEvent;
import com.yhkj.smartcar.event.JumpFragmentWithResultEvent;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.Constant;
import com.yhkj.smartcar.view.BottomBar;
import com.yhkj.smartcar.view.BottomBarTab;

import butterknife.Bind;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MainFragment extends BaseToolbarFragment {

    @Bind(R.id.fm_bottombar)
    BottomBar mBottomBar;

    SupportFragment[] mFragments = new SupportFragment[3];

    private Boolean bNewMes = false;
    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private String[] mTitles = new String[]{
            "首页",
            "车"
    };

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnResumeRegisterBus(true);
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        mBottomBar.addItem(new BottomBarTab(_mActivity,R.mipmap.ic_nor_home,"首页"))
                .addItem(new BottomBarTab(_mActivity,R.mipmap.ic_nor_car_1,"车"))
                .addItem(new BottomBarTab(_mActivity,R.mipmap.ic_nor_mine_1,"我的"));

        mFragments[0] = HomeFragment.getInstance();
        mFragments[1] = CarFragment.getInstance();
        mFragments[2] = MineFragment.getInstance();
        loadMultipleRootFragment(R.id.fm_contanier,0,mFragments);
    }

    @Override
    protected void initData() {
        super.initData();

        setTitleText("首页");
        setBackBtnVisible(View.INVISIBLE);
        setBtnRightVisible(View.VISIBLE);
        mBottomBar.setCurrentItem(0);
        getHomeMes();
    }

    private void getHomeMes(){
        SmartCarApi.getInstance().getHomeMessage(_mActivity, new ApiCallback<HomeMsgBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                filterHttpCallback(null,e);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(HomeMsgBean.DataBean dataBean) {
                App.getInstance().setmHomeMsg(dataBean);
                ((HomeFragment)mFragments[0]).setHomeData(dataBean);
                ((CarFragment)mFragments[1]).setData(dataBean);
                ((MineFragment)mFragments[2]).setData(dataBean);
            }
        });
        SmartCarApi.getInstance().getNewMessage(_mActivity, new ApiCallback<Boolean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                ViseLog.e(e);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean){
                    bNewMes = aBoolean;
                    setRightBtnResourse(R.mipmap.ic_nor_home_mesdone);
                }
            }
        });
    }

    @EventSubscribe
    public void starJumpEvent(JumpFragmentEvent jumpFragmentEvent){
        start(jumpFragmentEvent.getFragment());
        SmartCarApi.getInstance().getNewMessage(_mActivity, new ApiCallback<Boolean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                ViseLog.e(e);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean){
                    bNewMes = aBoolean;
                    setRightBtnResourse(R.mipmap.ic_nor_home_mesdone);
                }
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        getHomeMes();
    }

    @EventSubscribe
    public void starForResultJumpEvent(JumpFragmentWithResultEvent jumpFragmentEvent){
        SupportFragment fragment = jumpFragmentEvent.getFragment();
        startForResult(fragment,fragment.getArguments().getInt("requestcode"));
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        setBtnRightLisitien(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForResult(MessageFragment.getInstance(), Constant.RESULT_CODE_NEWMES);
                if (bNewMes){
                    bNewMes = false;
                    setRightBtnResourse(R.mipmap.ic_nor_home_unreadmes);
                }
            }
        });
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                if (position == 0){
                    setToolbarVisiable(View.VISIBLE);
                    setBtnRightVisible(View.VISIBLE);
                    setTitleText(mTitles[position]);
                } else if (position == 1){
                    setToolbarVisiable(View.VISIBLE);
                    setBtnRightVisible(View.INVISIBLE);
                    setTitleText(mTitles[position]);
                }else{
                    setToolbarVisiable(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 这里推荐使用EventBus来实现 -> 解耦
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
//				EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }
}
