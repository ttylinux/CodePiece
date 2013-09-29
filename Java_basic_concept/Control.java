package chapter8;

/**
 *Practice 16
 *
 * 利用 Java的多态，来实现 --状态模式---
 * 
 * 状态模式：
 * 1.1个父类，可以有不同的子类，每个子类代表一种状态。
 * 2.状态变化了(父类所指向的子类实例不同了)，那么相应的行为(即方法)也会改变.
 * 3.子类对父类所定义的方法，有不同的实现
 */
public class Control {
	
	public static void main(String[] args)
	{
		StatusControl control = new StatusControl();
		control.changeTo(1);
		control.performMove();
		control.changeTo(2);
		control.performMove();
	}

}

/**
 *StatusControl中的
 *status成员具有两种状态；
 *status可以通过changeTo来改变自身的状态 
 * 
 */
class StatusControl {
	private AlertStatus status = new NormalStatus();
	
	/**
	 * 改变状态的方法
	 *@param i
	 *1 ---NormalStatus
	 *2---SpeedStatus
	 * default: NormalStatus
	 */
	public void changeTo(int i)
	{
		switch(i)
		{
		case 1:
			status = new NormalStatus();
			break;
		case 2:
			status = new SpeedStatus();
			break;
			
		default:
			status = new NormalStatus();
		}
	}
	
	/**
	 *status会根据它所属的实际对象，调用正确的move方法。
	 *也就是move方法是和一个实际的status对象绑定的。
	 *比方，是一个NormalStatus的status，那么，就和NormalStatus中的move方法绑定
	 * 
	 */
	public void performMove()
	{
		status.move();
	}

	public void performStop()
	{
		status.stop();
	}
}

/**
 *父类定义了两个方法；不同的子类，对这两个方法有不同的实现 
 */
class AlertStatus {

	protected void move() {

	}

	protected void stop() {

	}

}

class NormalStatus extends AlertStatus {

	@Override
	protected void move() {
		System.out.println("move in  NormalStatus");
	}

	@Override
	protected void stop() {
		System.out.println("stop in  NormalStatus");
	}
}

class SpeedStatus extends AlertStatus {

	@Override
	protected void move() {
		System.out.println("move in SpeedStatus");
	}

	@Override
	protected void stop() {
		System.out.println("stop in SpeedStatus");
	}
}