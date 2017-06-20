package com.yhkj.smartcar.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.vise.log.ViseLog;
import com.vise.xsnow.event.BusFactory;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhkj.smartcar.App;
import com.yhkj.smartcar.BaseFragment;
import com.yhkj.smartcar.R;
import com.yhkj.smartcar.activity.LoginActivity;
import com.yhkj.smartcar.bean.BannerBean;
import com.yhkj.smartcar.bean.HomeMsgBean;
import com.yhkj.smartcar.bean.db.DbHelper;
import com.yhkj.smartcar.event.JumpFragmentWithResultEvent;
import com.yhkj.smartcar.http.SmartCarApi;
import com.yhkj.smartcar.utils.FileUtils;
import com.yhkj.smartcar.view.alertview.AlertView;
import com.yhkj.smartcar.view.alertview.OnItemClickListener;
import com.vise.xsnow.util.takephoto.app.TakePhoto;
import com.vise.xsnow.util.takephoto.app.TakePhotoImpl;
import com.vise.xsnow.util.takephoto.model.CropOptions;
import com.vise.xsnow.util.takephoto.model.InvokeParam;
import com.vise.xsnow.util.takephoto.model.TContextWrap;
import com.vise.xsnow.util.takephoto.model.TImage;
import com.vise.xsnow.util.takephoto.model.TResult;
import com.vise.xsnow.util.takephoto.permission.InvokeListener;
import com.vise.xsnow.util.takephoto.permission.PermissionManager;
import com.vise.xsnow.util.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MineFragment extends BaseFragment implements TakePhoto.TakeResultListener,InvokeListener {

    @Bind(R.id.fm_tv_out)
    TextView mTvOut;

    @Bind(R.id.fm_tv_phone)
    TextView mTvPhone;

    @Bind(R.id.fm_ll_equitment)
    LinearLayout mLlEquitMent;

    @Bind(R.id.fm_ll_message)
    LinearLayout mLlMessage;

    @Bind(R.id.fm_ll_problem)
    LinearLayout mLlProblem;

    @Bind(R.id.fm_ll_settting)
    LinearLayout mLlSetting;

    @Bind(R.id.fm_ll_aboutus)
    LinearLayout mLlAboutus;

    @Bind(R.id.fm_tv_carnumb)
    TextView mTvCarNumb;

    @Bind(R.id.fm_img_user)
    ImageView mImgUser;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    public static MineFragment getInstance() {
        MineFragment fragment = new MineFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }
    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(TResult result) {
        TImage image = result.getImage();
        ViseLog.e(result);
        final File imgFile = new File(image.getOriginalPath());
        Glide.with(_mActivity).load(imgFile).asBitmap().centerCrop().into(new BitmapImageViewTarget(mImgUser) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(_mActivity.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mImgUser.setImageDrawable(circularBitmapDrawable);
            }
        });

        enqueueAction(new Runnable() {
            @Override
            public void run() {
                final SweetAlertDialog sDialog = showDialog("正在上传，请稍后‥");
                SmartCarApi.getInstance().uplodeUserImg(_mActivity, imgFile, new ApiCallback<Object>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onError(ApiException e) {
                        sDialog.setTitleText("上传失败")
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
                    public void onNext(Object o) {
                        sDialog.dismissWithDelay(500);
                    }
                });
            }
        });

    }
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }
    @Override
    public void takeCancel() {}

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(_mActivity,type,invokeParam,this);
    }
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getTakePhoto().onSaveInstanceState(outState);
    }

    @Override
    protected void bindEvent() {
        mImgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectImgDialog();
            }
        });
        mTvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertView("提示", "是否退出登录", "取消", new String[]{"确定"},
                        null, mContext, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0){
                            DbHelper.getInstance().configLongDBManager().deleteAll();
                            Intent intent = new Intent();
                            intent.setClass(_mActivity, LoginActivity.class);
                            startActivity(intent);
                            _mActivity.finish();
                        }
                    }
                }).setCancelable(true).show();

            }
        });

        mLlEquitMent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusFactory.getBus().post(new JumpFragmentWithResultEvent(EquitManageFragment.getInstance()));
            }
        });
        mLlMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusFactory.getBus().post(new JumpFragmentWithResultEvent(MessageFragment.getInstance()));
            }
        });
        mLlProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusFactory.getBus().post(new JumpFragmentWithResultEvent(FaqFragment.getInstance()));
            }
        });
        mLlSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusFactory.getBus().post(new JumpFragmentWithResultEvent(SetFragment.getInstance()));
            }
        });

        mLlAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusFactory.getBus().post(new JumpFragmentWithResultEvent(AboutUsFragment.getInstance()));
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void initData() {
        mTvPhone.setText(App.getInstance().getUser().getPhone());
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
                Glide.with(_mActivity).load(bannerBean.getPic()).asBitmap().centerCrop().thumbnail(0.1f).into(new BitmapImageViewTarget(mImgUser) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(_mActivity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mImgUser.setImageDrawable(circularBitmapDrawable);
                    }
                });
            }
        });
    }

    public void setData(HomeMsgBean.DataBean data){
        mTvCarNumb.setText(data.getPlatenum());
    }

    Boolean bCanBack;
    private void showSelectImgDialog() {
        // TODO Auto-generated method stub
        bCanBack = true;
        new AlertView("上传头像", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                _mActivity, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int i) {
                if (i == 0){
                    getTakePhoto().onPickFromCaptureWithCrop(getLocalImgUri(),getCropOptions());
                }else if (i == 1){
                    //相册
                    getTakePhoto().onPickFromGalleryWithCrop(getLocalImgUri(),getCropOptions());
                }
                bCanBack = false;
            }
        }).setCancelable(true).show();

    }

    //获取裁剪参数
    private CropOptions getCropOptions(){
        int height= 800;
        int width= 800;
        boolean withWonCrop=true;

        CropOptions.Builder builder=new CropOptions.Builder();

        builder.setAspectX(width).setAspectY(height);
//
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
    private Uri getLocalImgUri(){
        File file=new File(FileUtils.getCacheDirectory(_mActivity, Environment.DIRECTORY_PICTURES), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }
}
