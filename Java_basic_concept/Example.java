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
	
	//都是传递指针值
	public void change(String str, char[] ch)
	{
		System.out.println(str); //str指向good字符串的起始地址，并输出字符串内容
		str = "test ok"; //str不指向good字符串的起始地址，指向test ok字符串的起始处
		                 
		System.out.println(str); //输出所指向的单元的内容
		ch[0]='f'; //修改ch所指向的单元的内容
		
		//string的赋值，是让变量指向其他字符串的起始地址
		//ch[0]的赋值，是修改ch所指向单元的的内容
	}
//输出结果：
//good
//test ok
//good
//fde
}