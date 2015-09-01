package com.example.devis.bestrecyclerviewdemo.lib;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.devis.bestrecyclerviewdemo.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Devis on 15/5/2.
 * UIL - ImageLoader
 */
public class MyImageLoader {

    private static DisplayImageOptions options;

    public static void init(Context context) {
        //初始化imageloader
        ImageLoader imageLoader = ImageLoader.getInstance();
        //默认图片参数
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.ARGB_8888).build();
        //缓存SD卡地址
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, context.getPackageName());// 缓存地址
        //初始化配置参数
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .threadPoolSize(5).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(8 * 1024 * 1024)).tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(1000).memoryCacheSize(3 * 1024 * 1024).discCacheSize(80 * 1024 * 1024);
        //构建配置
        ImageLoaderConfiguration config = builder.build();
        //初始化
        imageLoader.init(config);

        // 初始化图片设置
        options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.drawable.pic_load)
                //.showImageForEmptyUri(R.drawable.transparent).showImageOnFail(R.drawable.transparent)
                .cacheInMemory(true)
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.ARGB_8888).build();
    }

    public static DisplayImageOptions getOptions() {
        return options;
    }

    public void setOptions(DisplayImageOptions options) {
        this.options = options;
    }


}
