package reuse;

/**
 * 组合--- 一个类中的成员，是其他类的对象。这样就可以使用其他类的功能了。 
 * 这样的情况，就说该类使用了组合方式。
 */
public class Car
{
	private Engine oneEngine = new Engine();
	private String name = "MotoCar";

	public Car()
	{

	}

}





class Engine
{

	private String name = "Moto";

	public Engine()
	{

	}

	@Override
	public String toString()
	{
		return name;
	}

}
