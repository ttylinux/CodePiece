package com.compressanddisplay;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

public class MyApp extends Application {

	public static ImageLoader loader;

	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());

	}

	/**
	 * 使用开源的ImageLoader。
	 * 
	 * @param context
	 * 
	 * 
	 *            DisplayImageOptions是用于设置图片显示的类
	 * 
	 *            多线程图片加载； 灵活更改ImageLoader的基本配置，包括最大线程数、缓存方式、图片显示选项等；
	 */
	public static void initImageLoader(Context context) {

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_stub)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_stub).cacheInMemory(false)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoaderConfiguration config3 = new ImageLoaderConfiguration.Builder(
				context).discCacheFileCount(100) // 超过100个，则会删除旧的
				.defaultDisplayImageOptions(defaultOptions).build();

		ImageLoader.getInstance().init(config3);
		loader = ImageLoader.getInstance();

	}

}
