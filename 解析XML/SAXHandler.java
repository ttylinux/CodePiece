package xmlParser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ����DefaultHandler�еĻص������������Լ��Ļص�����
 * @author ttylinux(lushuwei)
 */
public class SAXHandler extends DefaultHandler {

	private ArrayList<Book> books = null;
	private Book book = null;
	private String tagName="";


	@Override
	public void startDocument() {
		if (null == books) {
			books = new ArrayList<Book>();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) {
		
		if(qName.equals("book"))
		{
			book = new Book();
			book.setId(Integer.parseInt(attributes.getValue(0)));
			
		}	
		tagName = qName;
		System.out.println("localName: "+localName);
		
		
	}
	
	/**
	 *ch��ŵ��������ļ������ݡ� 
	 */
	@Override
	public void characters (char[] ch, int start, int length)
	{	
		if(tagName.equals("price"))
		{
			book.setPrice(Float.parseFloat(new String(ch,start,length) ));
		}
		
		if(tagName.equals("name"))
		{
			book.setName(new String(ch,start,length));
		}
		
		
	}
	
	@Override
	public void endElement (String uri, String localName, String qName)
	{
		
		if(qName.equals("book"))
		{
			books.add(book);
			book = null;	
		}
		tagName = ""; //��Ϊ��SAX�Ľ���˳���ǣ�1.������ǩ֮��(����startElement����endElement),2.Ȼ�����character����
                           //���ڽ�����ǩ֮�󣬵���characters����������Ĳ����Ǳ�ǩ�����ݡ�
		
	}
	
	@Override
	public void endDocument()
	{
	}
	
	
	public ArrayList<Book> getBooks()
	{
		return books;
	}

}
