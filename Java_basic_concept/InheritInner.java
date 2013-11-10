package chapter10;

/**
 *1.内部类实例化的时候，要拥有一个指向其外围类对象(outer)的引用。
 *所以，在实例化内部类的时候， 使用outer.new 来实例化内部类。
 *
 *2.内部类的导出类，在实例化的时候，不会拥有父类所在外围类的对象引用。
 * 导致类的实例化过程是：
 *1).先实例化父类(父类需要持有一个指向其外围类对象的引用)
 *2).然后实例化导出类自己。
 *基于这个原因，就要在导出类实例化的时候，告诉其父类，它可以持有哪个外部类的对象引用。
 *wi.super(); 的作用，就是，告诉父类它持有的外围类对象的引用是wi。
 *
 *
 *输出：
 *instance Inner first
  Instance InheritInner after Inner
 * 
 */
public class InheritInner extends WithInner.Inner {
	
	{
		
		System.out.println("Instance InheritInner after Inner");
	}

	public InheritInner(WithInner wi) {
		wi.super();  //这句话的作用，就是让父类Inner在实例化的时候，知道它可以持有的外围类对象是谁。
		             //父类Inner对象持有外围类对象引用是wi，这样父类Inner对象可以访问的外围类对象成员是
		             //wi指向的对象成员。
	}
	
	
   public static void main(String[] args)
   {
	   WithInner wi = new WithInner();
	   InheritInner one  = new InheritInner(wi);
   }
	
}

class WithInner {
	
	private int ok;
	class Inner {
		
		{
			System.out.println("instance Inner first");
		}
	}
}
