package com.yanhuo_01.compoments.imaCache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PortraitLodar {
	// 任务队列
	Context context;
	// 一个map，用于缓存下载过的图片
	private HashMap<String, SoftReference<Bitmap>> caches;
	private ArrayList<DownTask> DownTaskQueue;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 子线程中返回的下载完成的任务
			DownTask DownTask = (DownTask) msg.obj;
			// 调用callback对象的loadedImage方法，将图片路径和图片回传给adapter
			DownTask.callback.loadedImage(DownTask.path, DownTask.bitmap);
		}

	};
	// 任务下载线程
	private Thread thread = new Thread() {

		@Override
		public void run() {
			// 任务轮询
			while (true) {
				// 当任务队列中还有未处理任务时，执行下载任务
				while (DownTaskQueue.size() > 0) {
					// 获取第一条任务，并将之从任务队列移除
					DownTask downTask = DownTaskQueue.remove(0);
					try {
						// 下载图片
						// 将下载的图片添加到文件缓存 downTak.path为网络URL，下载的路径
						downTask.bitmap = PicUtill.getbitmapAndwrite(context,
								downTask.path);
						// 如果handler对象不为null
						// 一个网络URL和对应的图片，为caches中的一组键值对
						caches.put(downTask.path, new SoftReference<Bitmap>(
								downTask.bitmap));
						if (handler != null) {
							// 创建消息对象，并将完成的任务添加到消息对象中
							Message msg = handler.obtainMessage();
							msg.obj = downTask;
							// 发送消息回主线程
							handler.sendMessage(msg);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// 如果任务队列为空，则令线程等待
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};
	// 创建对象时，初始化map
	public PortraitLodar(Context context) {
		caches = new HashMap<String, SoftReference<Bitmap>>();
		DownTaskQueue = new ArrayList<PortraitLodar.DownTask>();
		// 启动下载任务线程
		this.context = context;
		thread.start();
	}
	// 图片加载方法
	/**
	 * 判断要下载的网络连接是否已经存在本手机中：
	 * 1.检查是否存在于HashMap<String, SoftReference<Bitmap>> caches，这个是在内存中
	 * 2.检查是否存在于应用的私有目录中
	 * 如果两个地方都没有存在该图片，则新建一个下载任务，执行下载(下载的时候，会将图片添加到上述两个地方)
	 * 否则，则返回对应的Bitmap
	 * @param path --要下载的图片的网络连接
	 * 
	 * 
	 */
	public Bitmap loadImage(final String path,
			final PortraitImgCallback callback) {
		// 如果图片路径，在缓存中存在则不再下载
		Bitmap bitmap = null;
		bitmap = getbitmapMap(path);
		if (bitmap == null) {
			bitmap = getFromFile(path);

			if (bitmap != null) {
				caches.put(path, new SoftReference<Bitmap>(bitmap));
				return bitmap;
			} else {
				// 如果该路径的图片未在缓存中
				// 则创建新任务，添加到任务队列
				DownTask DownTask = new DownTask();
				DownTask.path = path;
				Log.i("path", path);
				DownTask.callback = callback;
			  //ArrayList根据对象的存放地址来区分存放其中的对象?
				if (!DownTaskQueue.contains(DownTask)) {
					DownTaskQueue.add(DownTask);
					// 唤醒任务下载线程
					synchronized (thread) {
						thread.notify();
					}
				}
			}
		} else {
			return bitmap;
		}
		return null;
	}

	//
	/**
	 * path为网络连接，确定对应的网络连接，是否已经执行了下载
	 * 
	 * @param path
	 *            --要确定是否执行过下载的网络连接
	 * @return 返回结果；已经下载过了，则返回对应的Bitmap；没有下载过，或者Bitmap已经被释放，则返回null
	 * 
	 */
	public Bitmap getbitmapMap(String path) {
		Bitmap bitmap = null;
		if (caches.containsKey(path)) {
			SoftReference<Bitmap> rf = caches.get(path);
			// 通过软引用，获取图片
			bitmap = rf.get();
			// 如果该图片已经被释放，则将该path对应的键值对从map中移除
			if (bitmap == null) {
				caches.remove(path);
				return null;
			} else {
				// 如果该图片未被释放，则返回该图片
				return bitmap;
			}
		} else {
			return null;
		}

	}

	// 是一个接口，供客户使用者实现
	public interface PortraitImgCallback {
		void loadedImage(String path, Bitmap bitmap);
	}
	/**
	 * 
	 * downTask.bitmap=PicUtill.getbitmapAndwrite(context, downTask.path);
	 * getbitmapAndwrite(Context context,String imageUri)中执行的操作，存放文件的时候，
	 * 文件名的生成方法是：String name=MD5Util.MD5(imageUri);所以，在这里可以根据url生成
	 * 对应的文件名，然后查找该文件是否存在
	 * 
	 * 如果某个url指向的内容已经保存在应用的私有目录中，则返回对应的Bitmap，否则返回null
	 * 
	 * @param url
	 *            --要生成文件名的url地址，利用MD5生成对应的文件名
	 * 
	 * @return
	 */
	private Bitmap getFromFile(String url) {

		String name = MD5Util.MD5(url);
		FileInputStream inputStream = null;
		try {
			inputStream = context.openFileInput(name);
			return BitmapFactory.decodeStream(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// 任务类
	class DownTask {
		String path;// 下载任务的下载路径
		Bitmap bitmap;// 下载的图片
		PortraitImgCallback callback;// 回调对象
		@Override
		public boolean equals(Object o) {
			return ((DownTask) o).path.equals(path);
		}
	}
}
