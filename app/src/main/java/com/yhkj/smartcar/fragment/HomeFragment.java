package com.yhkj.smartcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vise.log.ViseLog;
import com.vise.xsnow.net.api.ViseApi;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.adapter.BannerAdapter;
import com.yhkj.smartcar.bean.BannerBean;
import com.yhkj.smartcar.bean.HomeMsgBean;
import com.yhkj.smartcar.bean.NewMessageBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/23.
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.fh_banner)
    Banner mBanner;

    @Bind(R.id.fh_tv_cnwd)
    TextView mTvCnwd;

    @Bind(R.id.fh_tv_cnsd)
    TextView mTvCnsd;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {

    }

    @Override
    protected void bindEvent() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        SmartCarApi.getInstance().getBannerImg(_mActivity, new ApiCallback<BannerBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(BannerBean.DataBean bannerBean) {
                List<BannerBean.DataBean.ListBean> listBeen = bannerBean.getList();
                final List<String> list = new ArrayList<>();
                for (int i=0 ;i<listBeen.size();i++){
                    BannerBean.DataBean.ListBean bean = listBeen.get(i);
                    list.add(bean.getPicture());
                }
                mBanner.setImages(list).setImageLoader(new BannerAdapter()).start();
            }
        });
    }



    public void setHomeData(HomeMsgBean.DataBean data){
        mTvCnwd.setText(data.getTemp()+"°C");
        mTvCnsd.setText(data.getHumidity());
    }
}
