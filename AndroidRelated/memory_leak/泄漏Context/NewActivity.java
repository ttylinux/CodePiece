package com.progressbar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;





public class NewActivity extends Activity
{
	private static Drawable sBackground;

	
	@Override
	protected void onCreate(Bundle state)
	{
		super.onCreate(state);

		TextView label = new TextView(this);
		label.setText("Leaks are bad");

		if (sBackground == null)
		{
			sBackground = getDrawable(R.drawable.large_bitmap);
		}
		label.setBackground(sBackground);

		setContentView(label);
	}

}

/**
 *"
 *When the screen orientation changes the system will,by default, 
 *destroy the current activity and create a new one while preserving its state.
 *In doing so, Android will reload the application's UI from the resources.
 *
 *Now imagine you wrote an application with a large bitmap that 
 *you don't want to load on every rotation.
 *
 *The easiest way to keep it around and not having to reload it on every rotation 
 *is to keep in a static field
 *"
 * 横向和竖向屏幕的转换，会导致销毁当前的Activity，然后创建新的Activity(并且加载
 * 当前的各种资源。
 * )。如果，当前要加载的位图是比较大的，为了提高效率，避免每次新建该Activity都重新
 * 加载，将位图对象设置为static，设置为static表示该Activity类，只有这个位图对象。
 * 即使当前Activity对象销毁了，该位图对象依然存在，这样就避免了位图对象的重新创建。
 * 代码如上。
 * 
 * 
 * 上面的代码出现了Context内存泄漏(Android中有两类Context，一种是Activity，一种是Application)
 * :
 * 1.onCreate方法执行完后,label所指向的对象不会被销毁(虽然是局部的)。
 * 因为，当前Activity持有指向它的引用setContentView(label);
 * Activity ---->TextView
 * 2.另外，label所指向的对象也持有指向当前Activity的引用，TextView label = new TextView(this)。
 * TextView ----->Activity
 * 
 * 3.而静态的Drawable对象持有指向TextView的引用，label.setBackground(sBackground)。
 * 在这个方法中，sBackground会根据指向TextView的引用，回调TextView。
 * Drawable---->TextView---->Activity
 * 
 * 4.当回调Activity的onDestroy方法时，将不会销毁当前的Activity对象。原因是：
 * 1)sBackground它是静态的，是NewActivity类的成员。所以实例化对象的销毁不会导致sBackground的销毁。
 * 2)因为sBackground没有销毁，那么当前Activity对象就依然被其他对象引用，因为当前存在
 * “Drawable---->TextView---->Activity”。这样，onDestroy执行完后，GC也销毁不了当前Activity对象。
 * 
 * 3)当重新创建NewActivity的实例的时候，sBackground会指向新的实例，那么之前的NewActivity实例
 * 就没有引用指向它了。按道理，这时，GC就可以销毁之前的Activity了。但是，因为此时的Activity
 * 是在它的生命周期之外，所以会导致它不能被GC回收，销毁。
 * 
 * 
 * 
 * 避免Context的泄漏：
 * “
 * 
 * There are two easy ways to avoid context-related memory leaks.
 * The most obvious one is //to avoid escaping the context outside of its own scope.
 * The example above showed the case of a static reference
 * but inner classes and their implicit reference to the outer class can be equally dangerous.
 * 
 * The second solution is to use the Application context. 
 * This context will live as long as your application is alive 
 * and does not depend on the activities life cycle.
 * If you plan on keeping long-lived objects that need a context, remember the application object.
 * You can obtain it easily by calling Context.getApplicationContext() or Activity.getApplication().
 * 
 * In summary, to avoid context-related memory leaks, remember the following:
 * 1)
 * Do not keep long-lived references to a context-activity (a reference to an activity 
 * should have the same life cycle as the activity itself)
 * 2)
 * Try using the context-application instead of a context-activity
 * 3)
 * Avoid non-static inner classes in an activity if you don't control their life cycle, 
 * use a static inner class and make a weak reference to the activity inside.
 * The solution to this issue is to use a static inner class with a WeakReference to the outer class, 
 * as done in ViewRoot and its W inner class for instance
 * 4)
 * A garbage collector is not an insurance against memory leaks
 * 
 * ”
 * 
 * 
 * 
 */
/*******************************************************************************/
//避免的办法是，当系统回调当前Activity的onDestroy的时候，要将Drawable所指向的引用设置为Null。
//也就是，避免形成“Drawable---->TextView---->Activity”。Drawable没有指向TextView，则TextView
//就可以被销毁，从而Activtiy没有被其他对象引用，从而GC也能回收当前Activity所占用的内存。

class  NewActivity02 extends Activity{
	
private static Drawable sBackground;

	
	@Override
	protected void onCreate(Bundle state)
	{
		super.onCreate(state);

		TextView label = new TextView(this);
		label.setText("Leaks are bad");

		if (sBackground == null)
		{
			sBackground = getDrawable(R.drawable.large_bitmap);
		}
		label.setBackground(sBackground);

		setContentView(label);
	}
	
	@Override
	protected void onDestroy()
	{
		//....
		sBackground.setCallback(null);
	}
	
}
/**
 * 参考资料： http://android-developers.blogspot.com/2009/01/avoiding-memory-leaks.html
 */

