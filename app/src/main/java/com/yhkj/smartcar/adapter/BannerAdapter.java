package com.yhkj.smartcar.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class BannerAdapter extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
