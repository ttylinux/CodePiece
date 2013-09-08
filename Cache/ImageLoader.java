package com.yanhuo_01.compoments.imaCache;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class ImageLoader {
	
	//一个map，用于缓存下载过的图片
	private HashMap<String, SoftReference<Bitmap>> caches;
	//任务队列
	private ArrayList<Task> taskQueue;
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//子线程中返回的下载完成的任务
			Task task = (Task)msg.obj;
			//调用callback对象的loadedImage方法，将图片路径和图片回传给adapter
			task.callback.loadedImage(task.path, task.bitmap);
		}
		
	};
	//任务下载线程
	private Thread thread = new Thread(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//任务轮询
			while(true){
				//当任务队列中还有未处理任务时，执行下载任务
				while(taskQueue.size()>0){
					
					//获取第一条任务，并将之从任务队列移除
					Task task = taskQueue.remove(0);
					
					//将下载的图片添加到缓存
					task.bitmap=PicUtill.getbitmap(task.path);

					caches.put(task.path, new SoftReference<Bitmap>(task.bitmap));
					//如果handler对象不为null
					if(handler!=null){
						//创建消息对象，并将完成的任务添加到消息对象中
						Message msg = handler.obtainMessage();
						msg.obj = task;
						//发送消息回主线程
						handler.sendMessage(msg);
					}
				}
				//如果任务队列为空，则令线程等待
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	};
	//创建对象时，初始化map
	public ImageLoader(){
		caches =new  HashMap<String, SoftReference<Bitmap>>();
		taskQueue = new ArrayList<ImageLoader.Task>();
		//启动下载任务线程
		thread.start();
	}
   //判断当前cache中是否含有该键；含有该键不等于一定存在相应的bitmap引用地址
	public boolean ishavekey(String url){
		return caches.containsKey(url);
	}
	//图片加载方法
	public Bitmap loadImage(final String path,final ImageCallback callback,final String viewString){
		
		//如果图片路径，在缓存中存在则不再下载
		if(caches.containsKey(path)){
			//取出软引用
			SoftReference<Bitmap> rf = caches.get(path);
			//通过软引用，获取图片
			Bitmap bitmap = rf.get();
			//如果该图片已经被释放，则将该path对应的键值对从map中移除
			if(bitmap==null){
				caches.remove(path);				
			}else{
				//如果该图片未被释放，则返回该图片
				return bitmap;
			}
		}
		
		if(!caches.containsKey(path)){
			
			//如果该路径的图片未在缓存中
			//则创建新任务，添加到任务队列
			Task task = new Task();
			task.path = path;
			//Log.i("path", path);
			task.callback = callback;
			task.viewString = viewString;
			
			if(!taskQueue.contains(task)){
				Log.w(" not in taskqueue", task.path);
				taskQueue.add(task);
				//唤醒任务下载线程
				synchronized(thread){
					thread.notify();
				}
			}
			else {
				Log.w("already in taskqueue", task.path);
			}
		}
		//如果缓存中没有图片则返回null
		return null;
	}
	
	public interface ImageCallback{
		void loadedImage(String path,Bitmap bitmap);
	}
	//任务类
	class Task{
		String path;//下载任务的下载路径
		Bitmap bitmap;//下载的图片
		ImageCallback callback;//回调对象
		String viewString="";
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			try{
				return (((Task)o).path.equals(path) && ((Task)o).viewString.equals(viewString));
			}
			catch(Exception e){
				return true;
			}
		}
		
		
	}
}
