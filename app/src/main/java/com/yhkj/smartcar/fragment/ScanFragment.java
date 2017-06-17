package com.yhkj.smartcar.fragment;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.yhkj.smartcar.BaseToolbarFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.utils.Constant;

import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by Administrator on 2017/6/14.
 */

public class ScanFragment extends BaseToolbarFragment implements QRCodeView.Delegate {

    private static final String TAG = ScanFragment.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    @Bind(R.id.as_btn_light)
    ImageView mBtnLight;

    @Bind(R.id.zbarview)
    ZBarView mZbarView;

    private boolean mOpenLight = false;

    public static ScanFragment getInstance() {
        ScanFragment fragment = new ScanFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(_mActivity, result, Toast.LENGTH_SHORT).show();
        vibrate();
//        mZbarView.startSpot();
        Bundle bundle = new Bundle();
        bundle.putString("res",result);
        setFragmentResult(Constant.RESULT_CODE_OK,bundle);
        pop();

    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    public void onStart() {
        super.onStart();
        mZbarView.startCamera();
        mZbarView.showScanRect();
        mZbarView.changeToScanBarcodeStyle();
        mZbarView.startSpot();
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        super.initView(contentView, savedInstanceState);
        mZbarView.setDelegate(this);
    }

    @Override
    public void onStop() {
        mZbarView.stopCamera();
        super.onStop();
    }
    @Override
    public void onDestroy() {
        mZbarView.onDestroy();
        super.onDestroy();
    }
    @Override
    protected void bindEvent() {
        super.bindEvent();
        setBtnBackLisiten(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });


        mBtnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOpenLight){
                    mOpenLight = false;
                    mZbarView.closeFlashlight();
                    mBtnLight.setBackgroundResource(R.drawable.circle_uncheck_scan);
                    mBtnLight.setImageResource(R.drawable.ic_nor_lightning);
                }else{
                    mOpenLight = true;
                    mZbarView.openFlashlight();
                    mBtnLight.setBackgroundResource(R.drawable.circle_check_scan);
                    mBtnLight.setImageResource(R.drawable.ic_nor_lighting_gray);
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleText("扫一扫");
    }
}
