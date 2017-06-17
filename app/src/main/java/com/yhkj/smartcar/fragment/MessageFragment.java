package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.adapter.MessageAdapter;
import com.yhkj.smartcar.bean.MessageBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.CommonUtils;

import butterknife.Bind;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MessageFragment extends BaseToolbarFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.fm_refreshLayout)
    BGARefreshLayout mRefreshLayout;

    @Bind(R.id.fm_recycleview)
    RecyclerView mRecycleView;

    MessageAdapter mMesAdapter;
    public static MessageFragment getInstance() {
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        BGAStickinessRefreshViewHolder stickinessRefreshViewHolder = new BGAStickinessRefreshViewHolder(_mActivity, true);
        stickinessRefreshViewHolder.setStickinessColor(R.color.colorPrimary);
        stickinessRefreshViewHolder.setRotateImage(R.mipmap.bga_refresh_stickiness);
        mRefreshLayout.setRefreshViewHolder(stickinessRefreshViewHolder);
        stickinessRefreshViewHolder.setLoadingMoreText("加载中");
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        mRefreshLayout.setDelegate(this);
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("消息列表");
        mRecycleView.setLayoutManager(new LinearLayoutManager(_mActivity));

    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        getMessage(null);
    }

    private void getMessage(final BGARefreshLayout refreshLayout){

        final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
        SmartCarApi.getInstance().getMessage(_mActivity, new ApiCallback<MessageBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                if (filterHttpCallback(sDialog,e))
                    return;
                if (refreshLayout!=null)
                    refreshLayout.endRefreshing();
                sDialog.setTitleText("获取失败")
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
            public void onNext(MessageBean.DataBean messageBean) {
                if (refreshLayout!=null){
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.endRefreshing();
                        }
                    },500);
                }
                sDialog.dismissWithDelay(500);
                mMesAdapter = new MessageAdapter(messageBean.getList());
                mRecycleView.setAdapter(mMesAdapter);
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(final BGARefreshLayout refreshLayout) {
        SmartCarApi.getInstance().getMessage(_mActivity, String.valueOf(mMesAdapter.getEndId()), new ApiCallback<MessageBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                if (filterHttpCallback(null,e))
                    return;
                showTip(R.mipmap.warning_blue,e.getMessage());
                refreshLayout.endLoadingMore();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(final MessageBean.DataBean dataBean) {
                if (dataBean == null || dataBean.getList().size() < 1){
                    showTip(R.mipmap.warning_blue,"没有更多了");
                    refreshLayout.endLoadingMore();
                    return;
                }
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMesAdapter.addData(dataBean.getList());
                        refreshLayout.endLoadingMore();
                    }
                },500);

            }
        });
        return true;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(final BGARefreshLayout refreshLayout) {
        SmartCarApi.getInstance().getNewMessage(_mActivity, new ApiCallback<Boolean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                if (filterHttpCallback(null,e))
                    return;
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(Boolean bres) {
                if (!bres){
                    showTip(R.mipmap.warning_blue,"没有新的消息");
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.endRefreshing();
                        }
                    },500);
                    return;
                }
                getMessage(refreshLayout);
            }
        });
    }
}
