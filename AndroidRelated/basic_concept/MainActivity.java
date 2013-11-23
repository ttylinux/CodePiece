package com.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 1.方法中的所有非对象，在方法执行完之后，就会被清理出栈。
 * 2.在方法域中创建的对象-----对象的回收，释放，归虚拟机管；只要，其他对象持有引用指向该对象，那么
 * 该对象，就不会被释放。当其他对象，没有持有指向该对象的引用时，该对象才会被虚拟机回收。 此外，某对象的引用变量销毁了，不代表该对象就销毁了。
 * ----比如，下面的方法inital2(),执行完之后，btn引用变量，是会被清理回收的。然后，在其中创建的监听对象，
 * 不会被回收，因为btn变量所指向的对象持有该监听对象的引用地址。
 * 
 * 3.内部类(包括匿名内部类)对象和外部类对象- 在实例化的时候，要先实例化外部类，然后，再使用outer.new的方式实例化内部类
 * ----所以，内部类对象，能访问外部类对象的所有成员。 ----内部对象能访问定义它的地方的所有成员，所以，内部类对象，就是一个闭包。 ----
 * 
 * 4.不管点击btn1，多少次，显示的都是：Btn1 click, innerJ:11
 * ---因为，点击的时候，就执行方法onClick(View v)，执行完之后，在方法中的定义的变量，
 * 就被清理掉掉。下次，再执行的时候，又是按照定义时的值。---这就可以说明，方法执行完之后，
 * 定义在方法中的所有非对象都被清理出栈空间。
 * 
 * 5.而点击btn2时，每次点击，数字会增加1.
 * 因为，每次点击的时候，执行的方法onClick(View v),它访问的是匿名内部类对象的成员变量。
 * 只要对象存在，那么成员变量就一直存在。
 * ----这也说明了，在方法中创建的对象，如果其他对象持有指向该对象的引用地址，那么，该对象，就不会
 * 被回收，比如inital2()方法中的对象，因为，对象没有被回收，那么成员变量就可以一直存在，于是，每点击一次，
 * 就在原来的基础上修改一次。
 *
 *6. 方法区域定义的内部类，访问外部类对象的成员变量时，是通过外部类对象的引用地址进行的，
 *比如，访问i,是这样操作 outer.i 。outer是外部类对象的引用地址。
 *而访问方法区域中定义的变量，比如j,是这样进行的：编译器，在实例化内部类的时候，发现，
 *实例方法访问的是方法区域中的变量，那么，会给实例方法创建一个新的变量(假设是newJ)，值和类型是与j相同的。
 *这样，当方法执行完了，j被清理出栈空间也没关系；内部类的实例方法照样正常运行。
 *---因为是两个不同的变量，newJ的修改，不会导致j的改变。为了保持逻辑的一致性，所以，要使用final。
 *---这样，值就都是一样的。而这个，缺点就是，让人以为是同一个实例方法访问的，是j，实际上，实例方法访问的是
 *编译器为其创建的newJ，只不过值是从j拷贝过来。
 * 
 */
public class MainActivity extends Activity{

	private int i = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inital();
		inital2();

	}

	private void inital() {
		
		final TextView text = (TextView) findViewById(R.id.textView);
		Button btn = (Button) findViewById(R.id.btn1);
		btn.setOnClickListener(new View.OnClickListener() {
			TextView innerText = text;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int innerJ = 10;  
				innerJ++;
				innerText.setText("Btn1 click, innerJ:" + innerJ);
			}
		});

		return;

	}

	private void inital2() {

		final int j = 10;
		final TextView text = (TextView) findViewById(R.id.textView);
		Button btn = (Button) findViewById(R.id.btn2);
		btn.setOnClickListener(new View.OnClickListener() {
			int innerJ = 10;
			TextView innerText = text;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				innerJ++;
				innerText.setText("Btn2 click,innerJ:" + innerJ);

			}
		});
	}

	

}
