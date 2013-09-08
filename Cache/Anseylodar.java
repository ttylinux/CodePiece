package com.yanhuo_01.compoments.imaCache;

import com.yanhuo_01.R;
import com.yanhuo_01.YHApplication;
import com.yanhuo_01.compoments.imaCache.ImageLoader.ImageCallback;
import com.yanhuo_01.compoments.imaCache.PortraitLodar.PortraitImgCallback;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

public class Anseylodar {
	
	ImageLoader imageLoader;
	public Anseylodar(){
		imageLoader=new ImageLoader();
	}
	
	public void showImageAsyn(ImageView imageView,String url,int defaultBmpRes){
		if(!(imageView!=null && url!=null))
			return;
		
		imageView.setTag(url);
		Bitmap bitmap=null;
		//Log.w("showImageAsyn", url);
		String imagestr = imageView.toString();
		bitmap=imageLoader.loadImage(url, getImagelodarcallback( imageView),imagestr);
		if (bitmap==null) {
			imageView.setImageResource(R.drawable.home_defaulticon);
		}
		else
			imageView.setImageBitmap(bitmap);
	}
	
	/**
	 * 主要加载头像显示
	 * @param imageView
	 * @param url
	 */
//	public void showportAnsy(ImageView imageView,String url){
//		imageView.setTag(url);
//		Bitmap bitmap=null;
//		bitmap=YHApplication.portraitLodar.loadImage(url, getporcallback(imageView));
//		if (bitmap!=null) {
//		imageView.setImageBitmap(bitmap);	
//		}else {
//			imageView.setImageResource(R.drawable.home_defaulticon);
//		}
//	}
	/**
	 * 加载内容图片图片
	 * @param imageView
	 * @param url
	 */
	public  void showimgAnsy(ImageView imageView,String url){
		showImageAsyn(imageView,url,R.drawable.home_defaulticon);
	}
	/**
	 * 获取 callback接口 
	 * @param url
	 * @param imageView
	 * @return
	 */
	private static ImageCallback getImagelodarcallback(final ImageView imageView){
		
	 return	new ImageCallback() {
		@Override
		public void loadedImage(String path, Bitmap bitmap) {
			if (path.equals(imageView.getTag().toString())) {
				if(bitmap!=null)
					imageView.setImageBitmap(bitmap);
			}else {
				imageView.setImageResource(R.drawable.home_defaulticon);
			}			
		}
	};
	}
	
	
//	public static PortraitImgCallback getporcallback(final ImageView imageView){
//		return new PortraitImgCallback() {
//			
//			@Override
//			public void loadedImage(String path, Bitmap bitmap) {
//				if (path.equals(imageView.getTag().toString())) {
//					imageView.setImageBitmap(bitmap);
//				}else {
//					imageView.setImageResource(R.drawable.home_defaulticon);
//				}
//			}
//		};
//	};
}
