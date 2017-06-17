package com.yhkj.smartcar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.MessageBean;
import com.yhkj.smartcar.utils.CommonUtils;
import com.yhkj.smartcar.view.alertview.AlertView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder>{

    private List<MessageBean.DataBean.ListBean> mData;

    public MessageAdapter(List<MessageBean.DataBean.ListBean> data){
        mData = data;
    }

    public int getEndId(){
        return mData.get(mData.size()-1).getId();
    }

    public void addData(List<MessageBean.DataBean.ListBean> data){
        mData.addAll(data);
        notifyItemRangeInserted(mData.size()-1,mData.size()-1+data.size());
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_message,parent,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final MessageBean.DataBean.ListBean listBean = mData.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertView(null, "\n"+listBean.getContent(), null, new String[]{"确定"}, null, holder.itemView.getContext(), AlertView.Style.Alert, null)
                        .setCancelable(true).show();
                if (listBean.getStatus() == 0){
                    holder.mImgMessage.setImageResource(R.mipmap.ic_nor_graymes);
                }
            }
        });
        holder.mTvDate.setText(CommonUtils.timeStamp2Date(String.valueOf(listBean.getCreate_time()),null));
        String msg = listBean.getContent();
        holder.mTvContent.setText(msg.length() >= 80 ? msg.substring(0,80) + "...." : msg);
        holder.mImgMessage.setImageResource(listBean.getStatus() == 0 ? R.mipmap.ic_nor_bluemessage : R.mipmap.ic_nor_graymes);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView mImgMessage;
        TextView mTvContent;
        TextView mTvDate;
        public Holder(View view){
            super(view);
            mImgMessage = (ImageView)itemView.findViewById(R.id.im_img_message);
            mTvContent = (TextView)itemView.findViewById(R.id.im_tv_content);
            mTvDate = (TextView)itemView.findViewById(R.id.im_tv_date);
        }
    }
}
