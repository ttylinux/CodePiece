package com.compressanddisplay.tool;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 *
 * 
 * 该类将图片的保存路径通过PhotoCapture接口中的方法返回
 */
public class TakePhoto {


	/**
	 * 启动手机上的摄像机程序进行拍照
	 * @author LuShuWei
	 * 
	 * @param mActivity --某个需要使用拍照的Activity
	 * 
	 * @param requestCode
	 *            标识请求者的ID，在onActivityResult中拦截对应的intent数据
	 * @param saveFile
	 *            拍完照片，照片数据存放在该文件
	 * @return void
	 */
	public static void takePicture(Activity  mActivity, int requestCode, File saveFile) {

		if (saveFile == null) {
			Log.e("TakePicture", " param file is null");
			return;
		}

		if (isIntentAvailable(mActivity, MediaStore.ACTION_IMAGE_CAPTURE)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile));
			mActivity.startActivityForResult(intent, requestCode);
		} else {
			Log.e("TakePicture", "CameraApp is not available");
		}

	}

	/**
	 * 检查当前环境中是否有相应的程序，来相应对应的Action
	 * 
	 * @param context
	 *            当前的环境
	 * @param action
	 *            能相应的action
	 * @return void
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

}
