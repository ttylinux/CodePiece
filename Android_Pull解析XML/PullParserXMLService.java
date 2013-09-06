package com.testing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Xml;





/**
 * 要解析的XML文件的内容： <?xml version="1.0" encoding="UTF-8"?>
 *  <books> <book id="12"> <name>thinking in java</name> <price>85.5</price> </book> <book id="15"> <name>Spring in Action</name>
 * <price>39.0</price> </book> </books>
 */

public class PullParserXMLService
{

	public static List<Book> getBooks(InputStream is)
	{

		List<Book> books = null;
		Book book = null;
		XmlPullParser parser = Xml.newPullParser();
		try
		{
			parser.setInput(is, "UTF-8");
			int event = parser.getEventType(); // 产生第一个事件

			while (event != XmlPullParser.END_DOCUMENT) // 只要不是XML文档的结束事件，就不断解析
			{
				switch (event)
				// 根据标签来确定，要做什么
				{

					case XmlPullParser.START_DOCUMENT: // 整个XML文件的开始
						books = new ArrayList<Book>();
						break;

					case XmlPullParser.START_TAG:// 标签头的开始事件
						if ("book".equals(parser.getName()))
						{
							book = new Book();
							book.setId(Integer.parseInt(parser.getAttributeName(0)));

						}

						if (book != null)
						{
							if ("name".equals(parser.getName()))
							{
								book.setName(parser.nextText());

							}

							if ("price".equals(parser.getName()))
							{
								book.setPrice(Float.parseFloat(parser.nextText()));
							}
						}

						break;

					case XmlPullParser.END_TAG:
						if ("book".equals(parser.getName())) // 判断结束的标签是否是book
						{
							books.add(book);
							book = null;
						}
						break;

				} // switch结束---XML中的一个内部元素处理完毕
				event = parser.next(); // 处理下一个元素

			}//整个XML文件处理完毕
			
			return books;

		}
		catch (XmlPullParserException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
