package chapter8;

public class Example{
	
	
	public String str = "good";
	public char[] ch = {'c','d','e'};
	
	public static void main(String[] args){
		
		Example ex = new Example();
		ex.change(ex.str, ex.ch);
		System.out.println(ex.str);
		System.out.println(ex.ch);
		
	}
	
	public void change(String str, char[] ch)
	{
		System.out.println(str);
		str = "test ok";
		System.out.println(str);
		ch[0]='f';
	}
}