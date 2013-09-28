package chapter8;

/**
 * finalize的使用---- 下述代码逻辑：
 * 
 * 1.一个shared对象被多个对象使用用，即很多其他对象持有指向同一个Shared对象的引用
 * 2.shared对象是多个对象的共享变量，其他对象每次持有shared对象的引用时，都要调用 shared对象的方法addRef()
 * 3.通过refCount，Shared对象就可以知道，现在有多少个对象指向它。
 * 4.在销毁Shared对象时，必须确保，现在没有对象持有指向Shared对象的引用
 * -----在finalize方法中执行shared对象的终结条件检查(符合条件：已经没有其他对象持有
 * 指向Shared对象的引用，那么就可以正常结束Shared对象；否则，则要提示错误信息)。
 * 
 */
public class ReferenceCounting {

	public static void main(String[] args) {

		Shared shared = new Shared();
		Composing[] composing = { new Composing(shared), new Composing(shared),
				new Composing(shared), new Composing(shared),
				new Composing(shared) };

	}
}

class Shared {

	private int refcount = 0;
	private static int counter = 0;
	private final long id = counter++;

	public Shared() {
		System.out.println("Creating " + this);
	}

	public void addRef() {
		refcount++;
	}

	public int getRefCount() {
		return refcount;
	}

	protected void dispose() {
		if (--refcount == 0) {
			System.out.println("Disposing  " + this);
		}
	}

	@Override
	public String toString() {

		return "Shared  " + id;
	}

	// 垃圾回收器，回收该对象的内存空间时，会调用该对象的finalize方法。
	// 在下次垃圾回收动作发生时，才真正回收该对象的内存空间。
	// 因为Shared是被多个其他对象所使用的，所以，在回收Shared对象时，
	// 要确保其他对象没有持有指向Shared对象的引用，这样才不会出现潜在的错误
	@Override
	protected void finalize() {
		if (refcount != 0) {

			System.out.println("Error, there are still some instance holding"
					+ " a reference pointing to Shared. \n"
					+ "The refcount is :" + refcount);

		}
	}
}

class Composing {

	private Shared shared;
	private static long counter = 0;
	private final long id = counter++;

	public Composing(Shared mShared) {
		System.out.println("Creating " + this);
		this.shared = mShared;
		this.shared.addRef();
	}

	protected void dispose() {
		System.out.println("disposing " + this);
		shared.dispose();
	}

	@Override
	public String toString() {
		return "Composing " + id;
	}
}