/**
 *@author LuShuWei 
 */
public class SimpleClassA implements Cloneable {

	private String name = "A class";
	private int count = 0;

	public SimpleClassA() {

	}

	public String getName()
	{
		return name;
	}
	
	public int getCount()
	{
		return count;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	
   public static void main(String[] args)
   {
	   SimpleClassA one = new SimpleClassA();
	   try {
		SimpleClassA two = (SimpleClassA) one.clone();
		 if(one.equals(two))
		   {
			   System.out.println("one 和 two是同一个对象，没有发生克隆");
		   }else
		   {
			   System.out.println("one 和 two 是两个不同的对象，发生了克隆。");
			   System.out.println("one.name:"+one.getName()+";one.count:"+one.getCount());
			   System.out.println("two.name:"+two.getName()+";two.count:"+two.getCount());
		   }
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	  
   }
}
