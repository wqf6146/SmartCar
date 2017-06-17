package com.yhkj.smartcar.adapter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.FaqBean;
import com.yhkj.smartcar.fragment.FaqDetailFragment;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/5/27.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.Holder>{

    private List<FaqBean.DataBean.ListBean> mData;

    private SupportFragment mFragment;
    public FaqAdapter(SupportFragment fragment,List<FaqBean.DataBean.ListBean> data){
        mData = data;
        mFragment = fragment;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_faq,parent,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final FaqBean.DataBean.ListBean listBean = mData.get(position);
        holder.mTvFaq.setText(String.format("%s." + listBean.getTitle(),position+1));
        holder.mLlnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FaqDetailFragment faqDetailFragment = FaqDetailFragment.getInstance();
                Bundle bundle = faqDetailFragment.getArguments();
                bundle.putString("title",String.format("%s." + listBean.getTitle(),position+1));
                bundle.putString("faqid",String.valueOf(listBean.getId()));
                bundle.putString("num",String.valueOf(position+1));
                mFragment.start(faqDetailFragment);
            }
        });
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
        TextView mTvFaq;
        RelativeLayout mLlnext;
        public Holder(View view){
            super(view);
            mLlnext = (RelativeLayout)itemView.findViewById(R.id.if_rl_next);
            mTvFaq = (TextView)itemView.findViewById(R.id.if_tv_question);
        }
    }
}
