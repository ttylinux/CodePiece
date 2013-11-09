package chapter10;

/**
 * 
 * 闭包(Closure)的定义: 闭包是一个可调用的对象，它记录了一些信息，这些信息来自与创建它的作用域。
 * 
 * -------内部类(Inner class)
 * 内部类是一个闭包。它是一个可调用的对象，它可以访问外部类对象成员的所有信息，包括外部类对象的private成员。
 * 这些信息，是属于创建它的作用域，创建它的作用域是外部类。
 * 
 * Callbacks可以使用成员callbackReference回调Incrmentable接口的实现者Closure。
 * 
 * 
 */
public class Callbacks {

	private Incrementable callbackReference;

	public Callbacks(Incrementable one) {
		this.callbackReference = one;
	}

	public void go() {
		if (callbackReference != null) {
			callbackReference.increment();
		}
	}

	public static void main(String[] args) {
		Callee2 one = new Callee2();
		Callbacks caller = new Callbacks(one.getCallBackReference());
		caller.go();
		caller.go();
	}

}

interface Incrementable {

	void increment();
}

class MyIncrement {

	public void increment() {
		System.out.println("Other operation");
	}

	static void f(MyIncrement mi) {
		mi.increment();
	}
}

class Callee2 extends MyIncrement {

	private int i = 0;

	@Override
	public void increment() {
		super.increment();
		i++;
		System.out.println(i);
	}

	private class Closure implements Incrementable {

		@Override
		public void increment() {
			// TODO Auto-generated method stub
			// 调用外部类对象的方法------
			Callee2.this.increment();

			// 也可以不调用，直接使用自己的实现
//			System.out
//					.println("use your own implementions, not call outer instance's method");
		}

	}

	public Incrementable getCallBackReference() {
		return new Closure();
	}

}
