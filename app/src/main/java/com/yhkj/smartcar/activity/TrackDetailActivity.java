package com.yhkj.smartcar.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.trace.LBSTraceClient;
import com.amap.api.trace.TraceListener;
import com.amap.api.trace.TraceLocation;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.BaseActivity;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.bean.PosBean;
import com.yhkj.smartcar.bean.TrackBean;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.TraceRePlay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/6/10.
 */

public class TrackDetailActivity extends BaseActivity implements AMap.OnMapLoadedListener,TraceListener{


    @Bind(R.id.atd_mapview)
    MapView mMapView;

    private AMap aMap;

    @Bind(R.id.atd_tv_location)
    TextView mTvLocationPos;

    @Bind(R.id.atd_tv_date)
    TextView mTvDate;

    @Bind(R.id.atd_tv_time)
    TextView mTvTime;

    @Bind(R.id.atd_tv_kilmo)
    TextView mTvKilmo;

    @Bind(R.id.atd_tv_ori)
    TextView mTvOri;

    @Bind(R.id.atd_btn_cancel)
    TextView mTvCancel;

    @Bind(R.id.mt_btn_back)
    ImageView mImgBack;

    @Bind(R.id.mt_tv_title)
    TextView mTvTitle;

    private Marker mOriginStartMarker, mOriginEndMarker, mOriginRoleMarker;
    private Marker mGraspStartMarker, mGraspEndMarker, mGraspRoleMarker;
    private Polyline mOriginPolyline, mGraspPolyline;

    private List<LatLng> mOriginLatLngList = new ArrayList<>();
    private List<LatLng> mGraspLatLngList;
    private ExecutorService mThreadPool;
    private TraceRePlay mRePlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackdetail);
        mMapView.onCreate(savedInstanceState);
        init();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        final SweetAlertDialog sDialog = showDialog("正在获取，请稍后‥");
        SmartCarApi.getInstance().getTraclDetail(this, getIntent().getStringExtra("date"), new ApiCallback<TrackBean.DataBean>() {
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
            public void onNext(TrackBean.DataBean trackBean) {
                sDialog.dismissWithDelay(500);
                mTvKilmo.setText(String.format(getResources().getString(R.string.kilo),trackBean.getDistance()));
                setupRecord(trackBean);
            }
        });
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
        if (mThreadPool != null) {
            mThreadPool.shutdownNow();
        }
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    private void init() {

        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 3;
        mThreadPool = Executors.newFixedThreadPool(threadPoolSize);

        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setOnMapLoadedListener(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
            aMap.getUiSettings().setZoomControlsEnabled(false);
        }


        mTvTitle.setText("车迹详情");

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initView() {}

    @Override
    protected void bindEvent() {}

    @Override
    protected void initData() {}

    /**
     * 轨迹回放方法
     */
    private void rePlayTrace(List<LatLng> list, final Marker updateMarker) {
//        TraceRePlay replay = new TraceRePlay(list, 200,
//                new TraceRePlay.TraceRePlayListener() {
//
//                    @Override
//                    public void onTraceUpdating(LatLng latLng) {
//                        if (updateMarker != null) {
//                            updateMarker.setPosition(latLng); // 更新小人实现轨迹回放
//                        }
//                    }
//
//                    @Override
//                    public void onTraceUpdateFinish() {
////                        mDisplaybtn.setChecked(false);
////                        mDisplaybtn.setClickable(true);
//                    }
//                });
//        mThreadPool.execute(replay);
//        return replay;
//        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
//        // 设置滑动的图标
//        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.ic_nor_position_car));
//
//        LatLng drivePoint = mGraspLatLngList.get(0);
//        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(mGraspLatLngList, drivePoint);
//        mGraspLatLngList.set(pair.first, drivePoint);
//        List<LatLng> subList = mGraspLatLngList.subList(pair.first, mGraspLatLngList.size());
//
//        // 设置滑动的轨迹左边点
//        smoothMarker.setPoints(subList);
//        // 设置滑动的总时间
//        smoothMarker.setTotalDuration(5);
//        // 开始滑动
//        smoothMarker.startSmoothMove();
    }

    @Override
    public void onMapLoaded() {

    }

    /**
     * 轨迹数据初始化
     *
     */
    private void setupRecord(TrackBean.DataBean trackBean) {
        // 轨迹纠偏初始化
        LBSTraceClient mTraceClient = new LBSTraceClient(
                getApplicationContext());

        analysisPoint(trackBean.getPath());

        if (mOriginLatLngList != null && mOriginLatLngList.size() > 0 ) {

            LatLng startLoc = mOriginLatLngList.get(0);
            LatLng endLoc = mOriginLatLngList.get(mOriginLatLngList.size()-1);

            LatLng startLatLng = new LatLng(startLoc.latitude, startLoc.longitude);
            LatLng endLatLng = new LatLng(endLoc.latitude, endLoc.longitude);

//            addOriginTrace(startLatLng, endLatLng, mOriginLatLngList);

            List<TraceLocation> mGraspTraceLocationList = parseTraceLocationList();
            // 调用轨迹纠偏，将mGraspTraceLocationList进行轨迹纠偏处理
            mTraceClient.queryProcessedTrace(1, mGraspTraceLocationList,
                    LBSTraceClient.TYPE_AMAP, this);
        } else {
        }

    }

    private void analysisPoint(String pointsourse){
        String[] points = pointsourse.split("\\$");
        for (int j=0; j<points.length; j++){
            String[] latlont = points[j].split(",");
            try{
                LatLng latLng = new LatLng(Double.parseDouble(latlont[0]),Double.parseDouble(latlont[1]));
                mOriginLatLngList.add(latLng);
//                mBuilder.include(latLng);
            }catch (Exception e){
                ViseLog.e(e);
                continue;
            }
        }
    }

    /**
     *  转为TraceLocation list
     *
     * @param
     * @return
     */
    public List<TraceLocation> parseTraceLocationList() {
        List<TraceLocation> traceList = new ArrayList<TraceLocation>();

        for (int i = 0; i < mOriginLatLngList.size(); i++) {
            TraceLocation location = new TraceLocation();
            LatLng latLng = mOriginLatLngList.get(i);
//            location.setBearing(amapLocation.getBearing());
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
//            location.setSpeed(amapLocation.getSpeed());
//            location.setTime(amapLocation.getTime());
            traceList.add(location);
        }
        return traceList;
    }

    /**
     * 地图上添加原始轨迹线路及起终点、轨迹动画小人
     *
     * @param startPoint
     * @param endPoint
     * @param originList
     */
    private void addOriginTrace(LatLng startPoint, LatLng endPoint,
                                List<LatLng> originList) {
        mOriginPolyline = aMap.addPolyline(new PolylineOptions().color(
                Color.BLUE).addAll(originList));
        mOriginStartMarker = aMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        mOriginEndMarker = aMap.addMarker(new MarkerOptions().position(
                endPoint).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_nor_position_car)));

        try {
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(),
                    80));
            aMap.moveCamera(CameraUpdateFactory.changeTilt(30f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mOriginRoleMarker = aMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.ic_car))));
    }

    private LatLngBounds getBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        if (mOriginLatLngList == null) {
            return b.build();
        }
        for (int i = 0; i < mOriginLatLngList.size(); i++) {
            b.include(mOriginLatLngList.get(i));
        }
        return b.build();

    }

    @Override
    public void onTraceProcessing(int i, int i1, List<LatLng> list) {

    }


    /**
     * 地图上添加纠偏后轨迹线路及起终点、轨迹动画小人
     *
     */
    private void addGraspTrace(List<LatLng> graspList, boolean mGraspChecked) {
        if (graspList == null || graspList.size() < 2) {
            return;
        }
        LatLng startPoint = graspList.get(0);
        LatLng endPoint = graspList.get(graspList.size() - 1);
        mGraspPolyline = aMap.addPolyline(new PolylineOptions()
                .setCustomTexture(
                        BitmapDescriptorFactory
                                .fromResource(R.mipmap.grasp_trace_line))
                .width(40).addAll(graspList));
        try {
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(),
                    80));
            aMap.moveCamera(CameraUpdateFactory.changeTilt(30f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGraspStartMarker = aMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        mGraspEndMarker = aMap.addMarker(new MarkerOptions()
                .position(endPoint).icon(
                        BitmapDescriptorFactory.fromResource(R.mipmap.ic_nor_deepcar)));
//        mGraspRoleMarker = aMap.addMarker(new MarkerOptions().position(
//                startPoint).icon(
//                BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                        .decodeResource(getResources(), R.mipmap.ic_nor_position_car))));
        if (!mGraspChecked) {
            mGraspPolyline.setVisible(false);
            mGraspStartMarker.setVisible(false);
            mGraspEndMarker.setVisible(false);
//            mGraspRoleMarker.setVisible(false);
        }
    }

    /**
     * 轨迹纠偏完成数据回调
     */
    @Override
    public void onFinished(int i, List<LatLng> list, int i1, int i2) {
        addGraspTrace(list, true);
        mGraspLatLngList = list;

//        rePlayTrace(mGraspLatLngList, mGraspRoleMarker);
    }

    @Override
    public void onRequestFailed(int i, String s) {
        showTip(R.mipmap.warning_blue,"轨迹纠偏失败:" + s);
    }
}
