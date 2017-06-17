package com.yhkj.smartcar.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.EquitBean;
import com.yhkj.smartcar.bean.NorBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.CommonUtils;
import com.yhkj.smartcar.view.SwitchView;
import com.yhkj.smartcar.view.alertview.AlertView;
import com.yhkj.smartcar.view.alertview.OnDismissListener;
import com.yhkj.smartcar.view.alertview.OnItemClickListener;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/5/26.
 */

public class EquitMentAdapter extends RecyclerView.Adapter<EquitMentAdapter.EquitHolder> {

    private List<EquitBean.DataBean.ListBean> mDataList;
    private Activity mContext;
    private RecyclerView mRecycleView;
    public EquitMentAdapter(Activity context,List<EquitBean.DataBean.ListBean> list){
        mContext = context;
        mDataList = list;
    }

    @Override
    public EquitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EquitHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equit,parent,false));
    }

    public void setRecycleView(RecyclerView recycleView){
        mRecycleView = recycleView;
    }

    private void updateOrder(int position){
        int fromPosition=0;
        for (int i=0; i<mDataList.size();i++){
            EquitBean.DataBean.ListBean bean = mDataList.get(i);
            if (bean.getId()!= position) {
                bean.setStatus(0);
                notifyItemChanged(i);
            } else {
                mDataList.remove(i);
                fromPosition = i;
                bean.setStatus(1);
                mDataList.add(0,bean);

            }
        }
        notifyItemMoved(fromPosition,0);

        mRecycleView.scrollToPosition(0);
    }

    @Override
    public void onBindViewHolder(final EquitHolder holder,final int position) {
        final EquitBean.DataBean.ListBean bean = mDataList.get(position);
        holder.mStatus.setText(bean.getNet_status() == 1? "在线" : "离线");
        holder.mSwitchBtn.setOpened(bean.getStatus() == 1);
        holder.mSwitchBtn.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                holder.mImgLoad.setVisibility(View.VISIBLE);
                ((AnimationDrawable) holder.mImgLoad.getBackground()).start();
                SmartCarApi.getInstance().setDefaultEquit(mContext, String.valueOf(bean.getId()), new ApiCallback<NorBean>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        ((AnimationDrawable) holder.mImgLoad.getBackground()).stop();
                        holder.mImgLoad.setVisibility(View.GONE);
                        CommonUtils.showTip(mContext,R.mipmap.warning_blue,e.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(NorBean norBean) {
                        ((AnimationDrawable) holder.mImgLoad.getBackground()).stop();
                        holder.mImgLoad.setVisibility(View.GONE);
                        holder.mSwitchBtn.toggleSwitch(true);
                        updateOrder(bean.getId());
                    }
                });
            }

            @Override
            public void toggleToOff(SwitchView view) {
                CommonUtils.showTip(mContext,R.mipmap.warning_blue,"至少指定一个默认设备");
                holder.mSwitchBtn.toggleSwitch(true);
            }
        });

        holder.mVin.setText(bean.getVin());
        holder.mCarNumb.setText(bean.getPlatenum());
        holder.mEquitId.setText(bean.getDevicekey());
        holder.mServerid.setText(bean.getServerid());
        holder.mBtnReLieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解除bind
                new AlertView("提示", "是否解绑ID为"+bean.getDevicekey()+"的设备", "取消", new String[]{"确定"},
                        null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int p) {
                        if (p == 0){
                            final SweetAlertDialog sDialog = new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE)
                                    .setTitleText("正在解除，请稍后‥")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null);
                            sDialog.getProgressHelper().setBarColor(mContext.getResources().getColor(R.color.car_blue));
                            sDialog.show();

                            SmartCarApi.getInstance().reliveEquit(mContext, bean.getId(), new ApiCallback<NorBean>() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onError(ApiException e) {
                                    sDialog.setTitleText("解除失败")
                                            .setContentText(e.getMessage())
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.WARNING_TYPE,false,true);
                                }

                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onNext(NorBean norBean) {
                                    CommonUtils.showTip(mContext,R.mipmap.warning_blue,"解除成功");
                                    sDialog.dismissWithDelay(300);
                                    updateRemove(position);
                                    if (mDataList !=null && mDataList.size() == 0){
                                        
                                    }
                                }
                            });
                        }

                    }
                }).setCancelable(true).show();
            }
        });
    }

    private void updateRemove(int position){
        notifyItemRemoved(position);
        mDataList.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class EquitHolder extends RecyclerView.ViewHolder{

        SwitchView mSwitchBtn;
        ImageView mImgLoad;
        TextView mCarNumb;
        TextView mVin;
        TextView mEquitId;
        TextView mStatus;
        TextView mServerid;
        TextView mBtnReLieve;
        public EquitHolder(View view){
            super(view);
            mSwitchBtn = (SwitchView)itemView.findViewById(R.id.ie_btn_switch);
            mImgLoad = (ImageView)itemView.findViewById(R.id.ie_img_load);
            mCarNumb = (TextView)itemView.findViewById(R.id.ie_tv_carnumb);
            mVin = (TextView)itemView.findViewById(R.id.ie_tv_vin);
            mEquitId = (TextView)itemView.findViewById(R.id.ie_tv_equitid);
            mStatus = (TextView)itemView.findViewById(R.id.ie_tv_equitstatus);
            mServerid = (TextView)itemView.findViewById(R.id.ie_tv_accreditid);
            mBtnReLieve = (TextView)itemView.findViewById(R.id.ie_btn_relieve);
        }
    }
}
