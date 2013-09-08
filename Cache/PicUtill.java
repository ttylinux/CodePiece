package com.yanhuo_01.compoments.imaCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.yanhuo_01.YHApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;

public class PicUtill {
	
	public static String sdCard_path = Environment.getExternalStorageDirectory()+"/download/yh/";
	
	/**
	 * 根据一个网络连接(URL)获取bitmapDrawable图像
	 * @param imageUri
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static BitmapDrawable getfriendicon(URL imageUri) {

		BitmapDrawable icon = null;
		try {
			HttpURLConnection hp = (HttpURLConnection) imageUri
					.openConnection();
			icon = new BitmapDrawable(hp.getInputStream());// 将输入流转换成bitmap
			hp.disconnect();// 关闭连接
		} catch (Exception e) {
		}
		return icon;
	}

	/**
	 * 根据一个网络连接(String)获取bitmapDrawable图像
	 * @param imageUri
	 * @return
	 */
	public static BitmapDrawable getcontentPic(String imageUri) {
		URL imgUrl = null;
		try {
			imgUrl = new URL(imageUri);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		BitmapDrawable icon = null;
		try {
			HttpURLConnection hp = (HttpURLConnection) imgUrl.openConnection();
			icon = new BitmapDrawable(hp.getInputStream());// 将输入流转换成bitmap
			hp.disconnect();// 关闭连接
		} catch (Exception e) {
		}
		return icon;
	}

	/**
	 *  根据一个网络连接(URL)获取bitmap图像
	 * @param imageUri
	 * @return
	 */
	public static Bitmap getusericon(URL imageUri) {
		// 显示网络上的图片
		URL myFileUrl = imageUri;
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 *  根据一个网络连接(String)获取bitmap图像
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException 
	 */
	public static Bitmap getbitmap(String imageUri) {
		// 显示网络上的图片
		Bitmap bitmap = null;
		
		try {
			String filepath = PicUtill.findFile(imageUri);
			if(filepath!=null){
				bitmap = BitmapFactory.decodeFile(filepath);
				if(bitmap!=null)
					return bitmap;
			}
			Log.w("Picutll web", imageUri);
			
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			
			filepath = PicUtill.writefile(YHApplication.context, imageUri, is);
			bitmap = BitmapFactory.decodeFile(filepath);
			//bitmap = BitmapFactory.decodeStream(is);
			
			is.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	/**
	 * 下载图片 同时写到本地缓存文件中--写在应用自己的文件目录中
	 * @param context  --指定存放图片的应用;存放在该应用目录中的文件名为:String name=MD5Util.MD5(imageUri);
	 * @param imageUri  --要下载的图片的网络URL
	 * @return  返回Bitmap
	 * @throws MalformedURLException
	 */
	public static Bitmap getbitmapAndwrite(Context context,String imageUri) throws MalformedURLException {
		// 显示网络上的图片
		URL myFileUrl = new URL(imageUri);
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			String name=MD5Util.MD5(imageUri);
			BufferedOutputStream bufferedOutputStream=null;
			bufferedOutputStream=new BufferedOutputStream(context.openFileOutput(name, Context.MODE_PRIVATE));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100,bufferedOutputStream);
			is.close();
			bufferedOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 * @param  bitmap --要存放到文件中的Bitmap
	 *@param picName  --存放Bitmap数据的文件 
	 */
	public static boolean  downpic(String picName,Bitmap bitmap) {
		boolean nowbol=false;
		try {
			File saveFile = new File("/mnt/sdcard/download/yh/"+picName+".png");
			if(!saveFile.exists()){
				saveFile.createNewFile();
			}
			FileOutputStream saveFileOutputStream;
			saveFileOutputStream = new FileOutputStream(saveFile);
		    nowbol = bitmap.compress(Bitmap.CompressFormat.PNG, 100, saveFileOutputStream);
			saveFileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
	}
     return nowbol;
	} 
	/**
	 *将Bitmap写到给定的文件中，该文件存放在该应用下的私有目录
	 *@param context --指定的应用
	 *@param bitmap --要写到文件的Bitmap
	 *@param filename --文件名字 
	 * 
	 */
	public static void writeTofiles(Context context,Bitmap bitmap,String filename){
		BufferedOutputStream outputStream = null;
		try {
			outputStream = new BufferedOutputStream(context.openFileOutput(
					filename, Context.MODE_PRIVATE));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将文件写入缓存系统中
	 * 
	 * @param context --指定的应用，其用处为：文件，存放到该应用中的私有目录
	 * @param filename --将从is来的数据写到该文件中
	 * @param is --数据从该流来
	 * @return  文件的绝对路径，该文件包含来自is的数据
	 */
	public static String writefile(Context context,String fileurl, InputStream is) {
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		String imagePath = "";
		try {
			inputStream = new BufferedInputStream(is);
			
			File folder = new File(PicUtill.sdCard_path);
			if(!folder.exists())
				folder.mkdirs();
			
			String filename = MD5Util.MD5(fileurl);
        	imagePath = PicUtill.sdCard_path + filename;
			
			FileOutputStream fileoutputStream = new FileOutputStream(imagePath);
			outputStream = new BufferedOutputStream(fileoutputStream);
			
			//outputStream = new BufferedOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}
		} catch (Exception e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//inputStream的善后工作
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//outputStream的善后工作
		}
		return imagePath;
	}
	
	public static String findFile(String fileurl){
		try{
			String filename = MD5Util.MD5(fileurl);
			String filePath = PicUtill.sdCard_path + filename;
        	File curfile = new File(filePath);
        	if(curfile.exists())
        		return filePath;
		}
		catch(Exception e){
			
			Log.e("Pic util ", "in findFile() error");
			return null;
			
		}
		
		return null;
	}
	
	
	

	
	
}
