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
	
	//���Ǵ���ָ��ֵ
	public void change(String str, char[] ch)
	{
		System.out.println(str); //strָ��good�ַ�������ʼ��ַ��������ַ�������
		str = "test ok"; //str��ָ��good�ַ�������ʼ��ַ��ָ��test ok�ַ�������ʼ��
		                 
		System.out.println(str); //�����ָ��ĵ�Ԫ������
		ch[0]='f'; //�޸�ch��ָ��ĵ�Ԫ������
		
		//string�ĸ�ֵ�����ñ���ָ�������ַ�������ʼ��ַ
		//ch[0]�ĸ�ֵ�����޸�ch��ָ��Ԫ�ĵ�����
	}
//��������
//good
//test ok
//good
//fde
}