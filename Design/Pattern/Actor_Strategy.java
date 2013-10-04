package pattern;

/**
 *Strategy模式，策略模式：
 *一个类中，含有这样一个方法，该方法会因为其输入参数值的不同而具有不同的行为。
 *方法中要执行的算法，是固定不变的，而输入参数是会变化的(该输入参数是一个类的对象)
 *
 * process中的参数act代表一个策略。这样策略可以由用户自行去定义。
 * 由用户去定义如何处理给定的信息。
 * 
 */
public class Actor_Strategy
{
  public static void process(Actor act, String s)
  {
      
	  System.out.println("Doing something. Call act do. ");//这个表示process自身的固定的处理流程
	  act.perform(s);
  }
	
  public static void main(String[] args)
  {
     Actor_Strategy one = new Actor_Strategy();
     one.process(new HappyActor(), "onetwoThree");
 }
	
}


class Actor{
	
	public void act()
	{
		
	}
	
	public void perform(String s)
	{
		
	}
	
}

class HappyActor extends Actor{
	
	@Override
	public void act()
	{
		System.out.println("HappyActor");
	}
	
	@Override
	public void perform(String s)
	{
		System.out.println("Use HappyActor's way to deal the "+ s);
	}
	
	
}

class SadActor extends Actor{
	
	@Override
	public void act()
	{
		System.out.println("SadActor");
	}
	
	@Override
	public void perform(String s)
	{
		System.out.println("Use SadActor's way to deal the "+s);
	}
}


