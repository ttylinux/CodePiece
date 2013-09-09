package xmlParser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 覆盖DefaultHandler中的回调方法，定义自己的回调方法
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
	 *ch存放的是整个文件的内容。 
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
		tagName = ""; //因为，SAX的解析顺序是：1.碰到标签之后(调用startElement或者endElement),2.然后调用character方法
                           //而在结束标签之后，调用characters方法，输出的并不是标签的内容。
		
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
