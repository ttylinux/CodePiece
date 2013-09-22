package chapter8;

/**
 *Practice 10 
 */
public class Child extends Parent{

	
	@Override
	public String methodTwo()
	{
		return "Child methodTwo";
	}
	
	public static void main(String[] args)
	{
		Child one = new Child();
		Parent two = one;
		two.methodOne();
	}
	
	/**
	 * 
	 *输出结果是
	 * Parent methodOne and Child methodTwo
	 * 
	 * 这说明，methodOne使用的methodTwo是导出类覆盖的methodTwo。
	 * 
	 * 导出类(当前对象)与它当前的方法(覆盖父类的methodTwo)绑定，所以，
	 * 在选择调用哪个methodTwo时，若有覆盖，则采用覆盖后的。
	 */
}

class Parent {
	
	public void methodOne()
	{
		
		System.out.println("Parent methodOne and "+methodTwo());
	}
	
	public String methodTwo()
	{
		
		return "Parent medhodTwo";
	}
	
	
	
}