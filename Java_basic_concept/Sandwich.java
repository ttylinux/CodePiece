package chapter8;

/**
 *Practice 11 
 */
public class Sandwich extends PortableLunch {

	private Bread b = new Bread();
	private Cheese c = new Cheese();
	private Lettuce l = new Lettuce();

	public Sandwich() {
		System.out.println("Sandwich");
	}

	public static void main(String[] args) {
		new Sandwich();
	}

	
	/**
	 *输出结果：
	 *Meal
      display in Lunch
      Lunch
      PortableLunch
      Bread
      Cheese
      Lettuce
      Sandwich 
	 * 
	 * 由此，初始化的过程是：
	 * 1.不断递归，直到最上层的父类，初始化父类的成员变量，然后，调用父类的构造方法。
	 * 每一层都这样做，从高到底，直到当前的类层次。
	 */
}

class Meal {

	Meal() {
		System.out.println("Meal");
	}
}

class Bread {

	Bread() {
		System.out.println("Bread");
	}
}

class Cheese {

	Cheese() {
		System.out.println("Cheese");
	}
}

class Lettuce {

	Lettuce() {
		System.out.println("Lettuce");
	}
}

class Pickle {

	Pickle() {
		System.out.println("Pickle");
	}
}

class Lunch extends Meal {

	int i = display();

	public int display() {
		System.out.println("display in Lunch");
		return 0;
	}

	Lunch() {
		System.out.println("Lunch");
	}
}

class PortableLunch extends Lunch {

	PortableLunch() {
		System.out.println("PortableLunch");
	}
}
