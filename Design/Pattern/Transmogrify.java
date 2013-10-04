package pattern;

public class Transmogrify
{
	public static void main(String[] args)
	{
		Stage stage = new Stage();
		stage.performPlay();
		stage.change();
		stage.performPlay();
	}
	//输出：
	//HappyActor
	//SadActor

}

/**
 *Stage使用状态模式来设计(当然，Stage还是用了组合方式)---
 *
 *修改Stage的成员值，即改变了Stage的状态；
 *
 *然后Stage的方法(即行为)的也会发生变化，即Stage的行为发生变化。
 *
 * 修改actor的值，即改变Stage的状态，就会带啦Stage的performPlay的
 * 行为变化。
 * ------因为，根据多态，actor所访问的方法会跟实际所属的对象中的方法绑定，
 * 即HappyActor的实例，访问act时，访问的是HappyActor类的实现；
 * SadActor的实例，访问act方法时，访问的是SadActor类的实现；
 * 虽然二者均是Actor类型，但是执行actor.act时，系统会根据动态绑定的结果，
 * 访问正确的实现。
 * 
 * -----------上述的设计，是这一准则的表现：“用继承表达行为间的差异，用字段表达状态上的变化”
 */
//例子取自《Think in Java》
class Stage{
	
	private Actor actor = new HappyActor(); //修改该成员的值
	
	//改变Stage的状态
	public void change(){
		
		actor = new SadActor();
	}
	
	//Stage的状态变了，则其行为也会发生变化
	public void performPlay()
	{
		actor.act();
	}
}

class Actor{
	
	public void act()
	{
		
	}
	
}

class HappyActor extends Actor{
	
	@Override
	public void act()
	{
		System.out.println("HappyActor");
	}
	
}

class SadActor extends Actor{
	
	@Override
	public void act()
	{
		System.out.println("SadActor");
	}
}

