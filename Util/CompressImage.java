package com.yanhuo_01.compoments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;





/**
 * 提供压缩服务的类
 */
public class CompressImage
{

	private static String TAG = "CompressImage";

	public CompressImage()
	{

	}

	/**
	 * 执行压缩
	 * 
	 * @param sourceFilePath 要压缩的文件的路径
	 * @return 返回压缩后的文件大小，单位为KB(大于200KB则进行压缩)
	 */
	public Long performCompress(String sourcePath)
	{
		try
		{
			if (ifToCompress(sourcePath))
			{
				return compressImage(sourcePath);
			}
			else
			{
				return null;
			}
		}
		catch (FileNotExistException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getString() + ",要压缩的文件不存在");
			return null;
		}

	}

	/**
	 * 若文件大小大于200KB则进行压缩，否则不压缩
	 * 
	 * @param 要检测的文件的路径
	 * @return 返回true，表示大于200KB，要进行压缩；false表示不压缩
	 * @throws FileNotExistException --文件不存在时，抛出这个异常
	 */

	public boolean ifToCompress(String filePath) throws FileNotExistException
	{
		File file = new File(filePath);

		Log.e(TAG, "file:" + file.getAbsolutePath() + ";file size :" + file.length());
		if (file.exists())
		{
			if (file.length() / (1024) > 200)
				return true;
			else
				return false;
		}
		else
		{
			Log.e(TAG, "In ifToCompress(),file not exists");
			throw new FileNotExistException("file not exist");

		}

	}

	/**
	 * 自定义一个文件不存在的异常 类
	 */
	public class FileNotExistException extends Exception
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String error = "";

		public FileNotExistException(String error)
		{
			this.error = error;
		}

		public String getString()
		{
			return error;
		}
	}

	/**
	 * 将指定图片文件进行压缩 过程：1.按比例采样 --inSampleSize; 2.进行图片失真 compress
	 * 
	 * @param filePath 将被压缩的文件的存放路径
	 * @return 返回压缩后的图片文件大小，单位为KB
	 */

	public Long compressImage(String filePath)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();

		// 设置为true，那么，就无需将生成Bitmap加载到内存中；
		// 只为该文件，生成对应Bitmap的信息；信息存在options中
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		int width, length;
		if (options.outWidth > options.outHeight)
		{
			width = options.outHeight;
			length = options.outWidth;

		}
		else
		{
			width = options.outWidth;
			length = options.outHeight;

		}

		int wRatio = width / 720;
		int hRatio = length / 1280;

		// 如果在某一方向上的像素数，低于规定的，那就没有必要压缩
		// 设置压缩参数---采样，对原图进行采样
		if (wRatio > 1 && hRatio > 1)
		{
			if (wRatio > hRatio)
			{
				options.inSampleSize = wRatio;
			}
			else
			{
				options.inSampleSize = hRatio;
			}

		}
		// options.inSampleSize = Ratio;
		options.inJustDecodeBounds = false;
		// Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		Bitmap bitmap = IOHelper.loadBitmap(filePath, true, options);
		File file = new File(filePath);
		FileOutputStream fos;
		try
		{
			fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 60, fos);

		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file.length() / 1024;
	}

	/**
	 * 压缩，转存操作 首先判定是否需要压缩，不需要的话，则只进行转存 将指定图片文件进行压缩 过程：
	 *  1.按比例采样 --inSampleSize; 2.进行图片失真 compress
	 * 
	 * @param sourceFilePath 将被压缩的文件的存放路径
	 * @param destPath 压缩后的文件的存放路径
	 * @return 返回压缩后的图片文件大小，单位为KB
	 * @throws FileNotFoundException ---目标文件不存在，意味着不能写数据，将抛出该异常
	 * @throws FileNotExistException
	 */
	public Long compressImage(String sourceFilePath, String destFilePath) throws FileNotFoundException, FileNotExistException
	{

		Bitmap finalBitmap = null;
		File dest = new File(destFilePath);

		if (ifToCompress(sourceFilePath))
		{

			// 取样操作

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeFile(sourceFilePath, options);

			int width, length;

			if (options.outWidth > options.outHeight)
			{
				width = options.outHeight;
				length = options.outWidth;

			}
			else
			{
				width = options.outWidth;
				length = options.outHeight;

			}

			int wRatio = width / 720;
			int hRatio = length / 1280;

			// 如果在某一方向上的像素数，低于规定的，那就没有必要压缩
			if (wRatio > 1 && hRatio > 1)
			{
				if (wRatio > hRatio)
				{
					options.inSampleSize = wRatio;
				}
				else
				{
					options.inSampleSize = hRatio;
				}

			}
			// options.inSampleSize = Ratio;
			options.inJustDecodeBounds = false;
			// finalBitmap = BitmapFactory.decodeFile(sourceFilePath, options);// 对文件进行采样，结果存放在bitmap
			finalBitmap = IOHelper.loadBitmap(sourceFilePath, true, options);
			// 将结果存放到目标文件--有失真的转存操作
			dest = new File(destFilePath);
			FileOutputStream fos;

			fos = new FileOutputStream(dest);
			finalBitmap.compress(CompressFormat.JPEG, 60, fos);

		}
		else
		{
			// 没有失真的转存操作--没有改变图片质量，但是会改变原有图片的大小
			finalBitmap = BitmapFactory.decodeFile(sourceFilePath);
			dest = new File(destFilePath);
			FileOutputStream fos;

			fos = new FileOutputStream(dest);
			finalBitmap.compress(CompressFormat.JPEG, 100, fos);

		}

		return dest.length() / 1024;

	}

}
