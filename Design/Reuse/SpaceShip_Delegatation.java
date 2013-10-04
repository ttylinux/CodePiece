package reuse;

/**
 * 继承的使用场景是这样的：
 * 1.父类和导出类，二者存在通用-特殊的关系； 
 * 2.要使用多态时，要应用继承。
 *  而在其他语义情况下，是不适合使用继承的。
 *  
 *   但存在一种情况，具备组合和继承的部分特征： 
 *   1.我想在Child中使用feature类 
 *   2.Child对外提供方法，可以全部访问feature类中的public方法 
 *   
 *   这样一种，既使用了组合方法(Child中包含了feature类)；
 *   又有继承的特征(可以通过Child访问feature中的public方法，这样就好像Child是feature的子类， Child继承了feature的所有public方法)。 
 *   ------这种方式就叫做代理(Delagatation) 
 * 
 * 以《Think In Java》中提供的应用例子：
 * 1.SpaceShip并非SpaceShipControls类型，所以，在设计时SpaceShip不能继承SpaceShipControls;
 * 2.但是，对外，其他访问者要可以通过SpaceShip的公共方法实现控制，但是却不能直接访问SpaceShipControls
 * 的方法。
 *（ SpaceShip的使用者，当然要可以控制SpaceShip，从语义上。 ）
 * 
 *  这时，就可以使用代理的方式来设计---SpaceShip_Delegatation类暴露的方法，外部使用者可以访问；
 *  比如back；然后通过访问SpaceShip_Delegatation暴露的方法可以控制飞船；因为SpaceShip_Delegatation
 *  包含SpaceShipControls类型的成员，SpaceShip_Delegatation将接收控制的信息，然后，将信息传递给
 *  SpaceShipControls成员，由它来实际执行控制操作。
 *  ------这样，SpaceShip_Delegatation实际就是一个代理的角色。
 */
public class SpaceShip_Delegatation
{
  private String name;
  private SpaceShipControls controls = new SpaceShipControls();
  
  public void back(int velocity)
  {
	  controls.down(velocity);
  }
  //....
	
}





class SpaceShipControls
{
	public void up(int velocity)
	{

	}

	public void down(int velocity)
	{

	}

	public void left(int velocity)
	{

	}

	public void right(int velocity)
	{

	}

	public void forward(int velocity)
	{

	}

	public void back(int velocity)
	{

	}

	public void turboBoost()
	{

	}

}
