package chapter10;

/**
 *Exercise 26 
 */
public class SecondOuter {

	public class InnerSecond extends FirstOuter.InnerFirst {

		public InnerSecond(String name, FirstOuter firstOuter) {
			firstOuter.super(name);  //该语句的作用，就是告诉InnerSecond的父类对象，
			                         //它可以持有的外部类对象引用是firstOuter
			                         //父类InnerFirst在实例化时，需要持有外围类对象的引用。

		}
	}
}
