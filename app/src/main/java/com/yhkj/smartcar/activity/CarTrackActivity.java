package com.yhkj.smartcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseActivity;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.DayBean;
import com.yhkj.smartcar.bean.MonthBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.view.materialcalendarview.CalendarDay;
import com.yhkj.smartcar.view.materialcalendarview.DayViewDecorator;
import com.yhkj.smartcar.view.materialcalendarview.DayViewFacade;
import com.yhkj.smartcar.view.materialcalendarview.MaterialCalendarView;
import com.yhkj.smartcar.view.materialcalendarview.OnDateSelectedListener;
import com.yhkj.smartcar.view.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/6/10.
 */

public class CarTrackActivity extends BaseActivity {

    @Bind(R.id.fct_calendarView)
    MaterialCalendarView mCalendarView;

    @Bind(R.id.mt_btn_back)
    ImageView mImgBack;

    @Bind(R.id.mt_tv_title)
    TextView mTvTitle;

    private List<String> mMonthList = new ArrayList<>();
    private List<Integer> mDayInfoList;

    private boolean bInitCalendar = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartrack);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        final SweetAlertDialog sweetAlertDialog = showDialog("正在加载，请稍后‥");
        SmartCarApi.getInstance().getTrackMonth(CarTrackActivity.this, new ApiCallback<MonthBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                if (filterHttpCallback(sweetAlertDialog,e))
                    return;
                sweetAlertDialog.setTitleText("加载失败")
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
            public void onNext(MonthBean.DataBean dataBean) {
                mMonthList = dataBean.getMonth();
                getDayData(sweetAlertDialog,mMonthList.get(mMonthList.size()-1));
            }
        });
    }

    Boolean mOnceSwitch = true;
    @Override
    protected void bindEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
//                setMonthTextUi(String.valueOf(date.getMonth() +1));
                if (!mOnceSwitch){
                    SweetAlertDialog dialog = showDialog("正在加载，请稍后‥");
                    getDayData(dialog,date.getYear()+"-"+String.valueOf(date.getMonth()+1));
                }
                mOnceSwitch=false;

            }
        });
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent();
                intent.setClass(CarTrackActivity.this,TrackDetailActivity.class);
                intent.putExtra("date",date.getYear() + "-" + String.valueOf(date.getMonth()+1)
                        + "-" + String.valueOf(date.getDay()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mTvTitle.setText("车迹");
    }

    private void getDayData(final SweetAlertDialog sDialog,String month) {

        SmartCarApi.getInstance().getTrackDay(this, month, new ApiCallback<DayBean.DataBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                if (filterHttpCallback(sDialog,e))
                    return;
                sDialog.setTitleText("加载失败")
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
            public void onNext(DayBean.DataBean dataBean) {
                sDialog.dismissWithDelay(500);
                mDayInfoList = dataBean.getDays();
                initCalendar();
            }
        });
    }

    private void initCalendar(){
        if (bInitCalendar){
            mCalendarView.addDecorator(new PrimeDayDisableDecorator());
            return;
        }
        bInitCalendar = true;
        mCalendarView.setTransverter(mMonthList);
        mCalendarView.show();
        mCalendarView.setTileSize(LinearLayout.LayoutParams.MATCH_PARENT);
//					mCalendarView.setTopbarVisible(false);
        mCalendarView.setSelectionColor(getResources().getColor(R.color.bluefont));
        mCalendarView.addDecorator(new PrimeDayDisableDecorator());

//        setMonthTextUi(String.valueOf(mCalendarView.getCurrentDate().getMonth()+1));
    }

    private class PrimeDayDisableDecorator implements DayViewDecorator {

        public PrimeDayDisableDecorator(){}

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int d = day.getDay()-1;
            if ( mDayInfoList == null || d >= mDayInfoList.size() || mDayInfoList.get(d) ==null )
                return true;
            return mDayInfoList.get(d) == 0;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }
}
