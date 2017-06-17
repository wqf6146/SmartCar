package com.yhkj.smartcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseActivity;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.PosBean;
import com.yhkj.smartcar.http.SmartCarApi;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/6/10.
 */

public class PositionActivity extends BaseActivity implements AMap.OnMapLoadedListener,GeocodeSearch.OnGeocodeSearchListener {


    @Bind(R.id.fp_mapview)
    MapView mMapView;

    private AMap aMap;

    @Bind(R.id.fp_tv_location)
    TextView mTvLocationPos;

    @Bind(R.id.fp_tv_temperature)
    TextView mTvTemperature;

    @Bind(R.id.fp_tv_humidity)
    TextView mTvHumidity;

    @Bind(R.id.fn_btn_cartrack)
    TextView mBtnCartrack;

    @Bind(R.id.fn_btn_cancel)
    TextView mBtnCancel;

    @Bind(R.id.mt_btn_back)
    ImageView mImgBack;

    @Bind(R.id.mt_tv_title)
    TextView mTvTitle;

    private GeocodeSearch geocoderSearch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        mMapView.onCreate(savedInstanceState);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        init();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setOnMapLoadedListener(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
            aMap.getUiSettings().setZoomControlsEnabled(false);
        }


        mTvTitle.setText("定位");

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnCartrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PositionActivity.this,CarTrackActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {}

    @Override
    protected void bindEvent() {}

    @Override
    protected void initData() {}

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                mTvLocationPos.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress() + "附近");
//                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                        AMapUtil.convertToLatLng(latLonPoint), 15));
//                regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
//                showTip(R.mipmap.warning_blue,addressName);
            } else {
//                showTip(R.mipmap.warning_blue,addressName);
//                ToastUtil.show(ReGeocoderActivity.this, R.string.no_result);
            }
        } else {
//            ToastUtil.showerror(this, rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
        SmartCarApi.getInstance().getPosition(this, new ApiCallback<PosBean.DataBean>() {
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
            public void onNext(PosBean.DataBean dataBean) {
//                sDialog.dismissWithDelay(500);
                LatLng latLng = new LatLng(Double.parseDouble(dataBean.getLat()), Double.parseDouble(dataBean.getLog()));
                sDialog.dismissWithDelay(500);
                BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.mipmap.ic_nor_position_car);
                aMap.addMarker(new MarkerOptions().position(latLng).icon(des)
                        .anchor(0.5f, 0.5f));
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));
                aMap.moveCamera(CameraUpdateFactory.changeTilt(40f));
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(Double.parseDouble(dataBean.getLat()),
                        Double.parseDouble(dataBean.getLog())), 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
            }
        });
    }

    @Override
    public void onMapLoaded() {

    }
}
