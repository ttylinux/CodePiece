package compress;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class IOHelper {

	/** 从给定路径加载图片*/
	public static Bitmap loadBitmap(String imgpath) {
		return BitmapFactory.decodeFile(imgpath,null);
	}
	
	public static Bitmap loadBitmap(String imgpath,Options opts){
		return BitmapFactory.decodeFile(imgpath, opts);
	}

	public static Bitmap loadBitmap(String imgpath, boolean adjustOritation,Options opts){
		if (!adjustOritation) {
			return loadBitmap(imgpath,opts);
		} else {
			Bitmap bm = loadBitmap(imgpath,opts);
			int digree = 0;
			ExifInterface exif = null;
			try {
				exif = new ExifInterface(imgpath);
			} catch (IOException e) {
				e.printStackTrace();
				exif = null;
			}
			if (exif != null) {
				// 读取图片中相机方向信息
				int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_UNDEFINED);
				// 计算旋转角度
				switch (ori) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					digree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					digree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					digree = 270;
					break;
				default:
					digree = 0;
					break;
				}
			}
			if (digree != 0) {
				// 旋转图片
				Matrix m = new Matrix();
				m.postRotate(digree);
				bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, true);
			}
			return bm;
		}
	}
	
	/** 从给定的路径加载图片，并指定是否自动旋转方向*/
	public static Bitmap loadBitmap(String imgpath, boolean adjustOritation) {
		return loadBitmap(imgpath,adjustOritation,null);
//		if (!adjustOritation) {
//			return loadBitmap(imgpath);
//		} else {
//			Bitmap bm = loadBitmap(imgpath);
//			int digree = 0;
//			ExifInterface exif = null;
//			try {
//				exif = new ExifInterface(imgpath);
//			} catch (IOException e) {
//				e.printStackTrace();
//				exif = null;
//			}
//			if (exif != null) {
//				// 读取图片中相机方向信息
//				int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//						ExifInterface.ORIENTATION_UNDEFINED);
//				// 计算旋转角度
//				switch (ori) {
//				case ExifInterface.ORIENTATION_ROTATE_90:
//					digree = 90;
//					break;
//				case ExifInterface.ORIENTATION_ROTATE_180:
//					digree = 180;
//					break;
//				case ExifInterface.ORIENTATION_ROTATE_270:
//					digree = 270;
//					break;
//				default:
//					digree = 0;
//					break;
//				}
//			}
//			if (digree != 0) {
//				// 旋转图片
//				Matrix m = new Matrix();
//				m.postRotate(digree);
//				bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
//						bm.getHeight(), m, true);
//			}
//			return bm;
//		}
	}

}
