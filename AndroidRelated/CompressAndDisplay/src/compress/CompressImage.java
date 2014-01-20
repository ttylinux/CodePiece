package compress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 
 * 提供压缩服务的类
 */
public class CompressImage {

	private static String TAG = "CompressImage";
	private static CompressImage oneCompresser;

	private CompressImage() {

	}

	public static CompressImage createCompresser() {
		if (oneCompresser == null) {
			oneCompresser = new CompressImage();
		}

		return oneCompresser;
	}

	/**
	 * 
	 * 
	 * @param 要检测的文件的路径
	 * @param sizeLimted
	 *            大小超过该值，则进行压缩
	 * @return 返回true，进行压缩；false表示不压缩
	 * @throws FileNotExistException
	 *             --文件不存在时，抛出这个异常
	 * 
	 */

	public boolean ifToCompress(String filePath, int sizeLimted)
			throws FileNotExistException {
		File file = new File(filePath);

		Log.e(TAG,
				"file:" + file.getAbsolutePath() + ";file size :"
						+ file.length());
		if (file.exists()) {
			if (file.length() / (1024) > sizeLimted)
				return true;
			else
				return false;
		} else {
			Log.e(TAG, "In ifToCompress(),file not exists");
			throw new FileNotExistException("file not exist");

		}

	}

	/**
	 * 自定义一个文件不存在的异常 类
	 */
	public class FileNotExistException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String error = "";

		public FileNotExistException(String error) {
			this.error = error;
		}

		public String getString() {
			return error;
		}
	}

	/**
	 * 压缩，转存操作
	 * 
	 * 首先判定是否需要压缩，不需要的话，则只进行转存 将指定图片文件进行压缩 过程： 1.按比例采样 --inSampleSize;
	 * 
	 * @param sourceFilePath
	 *            将被压缩的文件的存放路径
	 * @param destPath
	 *            压缩后的文件的存放路径
	 * @param sizeLimted
	 *            --超过该值，则进行压缩
	 * 
	 * @return 返回压缩后的图片文件大小，单位为KB
	 * 
	 * @throws FileNotFoundException
	 *             ---目标文件不存在，意味着不能写数据，将抛出该异常
	 * @throws FileNotExistException
	 * 
	 */
	public Long compressImage(String sourceFilePath, String destPath,
			int sizeLimted) throws FileNotFoundException, FileNotExistException {

		Bitmap finalBitmap = null;
		File dest = new File(destPath);

		if (ifToCompress(sourceFilePath, sizeLimted)) {

			// 获取图片的信息，保存在Options对象中；并对Options中的inSample属性进行设置
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(sourceFilePath, options);
			options.inSampleSize = calculateInSampleSize(options, 240, 400);

			// 按照设置好的配置，将图片从指定的文件中解码出来；解码出来的图片，是新的图片，然后，再将新的图片写到文件中
			options.inJustDecodeBounds = false;
			finalBitmap = BitmapFactory.decodeFile(sourceFilePath, options);

			// 将结果写到新的文件中
			FileOutputStream fos;
			fos = new FileOutputStream(dest);
			finalBitmap.compress(CompressFormat.JPEG, 100, fos);

		}

		return dest.length() / 1024;

	}

	/**
	 * 
	 * 在取样的时候，如果发现原始图片的宽长度，大于高，也就是原始图片是横的。为了避免高过度被压缩(高可能不需要压缩)，将宽设为高，
	 * 然后与目标高度进行计算。也就是，对换了宽和高。 ---------然后，看显示的图片，它的方向是否跟原始图片的不同。
	 * 
	 * 压缩，转存操作
	 * 
	 * 首先判定是否需要压缩，不需要的话，则只进行转存 将指定图片文件进行压缩 过程： 1.按比例采样 --inSampleSize;
	 * 
	 * @param sourceFilePath
	 *            将被压缩的文件的存放路径
	 * @param destPath
	 *            压缩后的文件的存放路径
	 * @param sizeLimted
	 *            --超过该值，则进行压缩
	 * 
	 * @return 返回压缩后的图片文件大小，单位为KB
	 * 
	 * @throws FileNotFoundException
	 *             ---目标文件不存在，意味着不能写数据，将抛出该异常
	 * @throws FileNotExistException
	 * 
	 */
	public Long compressImage2(String sourceFilePath, String destPath,
			int sizeLimted) throws FileNotFoundException, FileNotExistException {

		Bitmap finalBitmap = null;
		File dest = new File(destPath);

		if (ifToCompress(sourceFilePath, sizeLimted)) {

			// 获取图片的信息，保存在Options对象中；并对Options中的inSample属性进行设置
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(sourceFilePath, options);
			options.inSampleSize = calculateInSampleSize2(options, 240, 400);

			// 按照设置好的配置，将图片从指定的文件中解码出来；解码出来的图片，是新的图片，然后，再将新的图片写到文件中
			options.inJustDecodeBounds = false;
			finalBitmap = BitmapFactory.decodeFile(sourceFilePath, options);

			// 将结果写到新的文件中
			FileOutputStream fos;
			fos = new FileOutputStream(dest);
			finalBitmap.compress(CompressFormat.JPEG, 100, fos);

		}

		return dest.length() / 1024;

	}

	/**
	 * 计算取样的比例
	 * 
	 * 不管图片的宽，高数值怎样，都按照比例进行取样。 不对宽，高的数值，进行互换。
	 * 
	 */
	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * 对换高和宽，如果，出现宽大于高的话。
	 * 
	 * 采用下面的方式，宽，高，不会被置换，宽，高根本就没有被置换。
	 * 下面只是用来计算采样比例，这个采样比例是供原始图片的宽，高使用，而原始图片的宽，高是没有发生置换。
	 * 
	 * 当出现某个比例x，可以导致原始的宽小于目标宽，或者，原始的高小于目标的高，那么，就采用这个比例。
	 * 
	 * if( height/X > reqHeight && width/X > reqWidth)
	 * {
	 *    继续按照2的倍数，增加X
	 *    X *=2;
	 * }else
	 * {
	 *   采用这个比例
	 *   返回X。
	 * }
	 * 
	 * 但是，这种算法，不能避免这样一种情况：
	 * 通过除以比例X，可以导致原始宽小于目标宽，但是，除以比例X，并不能导致原始高小于目标高。
	 * 比如，目标宽和目标高是240*320.
	 * 
	 * 然后，出现，一个原始图片，它的宽是470，高是650.
	 * 那么，比例X是2时，可以导致470/2 = 235,  650/2 = 325。
	 * 那么，在这种情况下，这种计算方式，就不妥了。如果，高是很大值，比如 3000，
	 * 那么，进行计算后，该图片是235*1500/4 = .
	 * 
	 * 那么，它的大小，235*1500/1024 =344KB ； 规定的比较大小是240*320*4/1024 = 300KB.
	 * 
	 * 所以，在这种情况下，在计算取样比例的时候，就要这样做，将计算的宽，高值，换一下。
	 * 具体是这样：
	 * 3000/320 ; 235/240；然后，选择比例大的那个，也就是3000/320 = 9.375，在赋值给inSample时，它会取最靠近2的某个幂的那个值，
	 * 就是8.这样下来，计算出的结果是235*1500/64 = 5507, 5507/1024 = 5.37KB. 又太小了。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private int calculateInSampleSize2(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		int height;
		int width;
		if (options.outHeight < options.outWidth) {
			height = options.outWidth;
			width = options.outHeight;
		} else {
			height = options.outHeight;
			width = options.outWidth;
		}

		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
	
	
	/**
	 * 对换高和宽，如果，出现宽大于高的话。
	 * 
	 * 采用下面的方式，宽，高，不会被置换，宽，高根本就没有被置换。
	 * 下面只是用来计算采样比例，这个采样比例是供原始图片的宽，高使用，而原始图片的宽，高是没有发生置换。
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private int calculateInSampleSize3(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		int height;
		int width;
		if (options.outHeight < options.outWidth) {
			height = options.outWidth;
			width = options.outHeight;
		} else {
			height = options.outHeight;
			width = options.outWidth;
		}

		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

}
