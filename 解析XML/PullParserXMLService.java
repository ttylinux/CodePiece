package com.testing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Xml;







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
			int event = parser.getEventType(); // �����һ���¼�

			while (event != XmlPullParser.END_DOCUMENT) // ֻҪ����XML�ĵ��Ľ����¼����Ͳ��Ͻ���
			{
				switch (event)
				// ��ݱ�ǩ��ȷ����Ҫ��ʲô
				{

					case XmlPullParser.START_DOCUMENT: // ���XML�ļ��Ŀ�ʼ
						books = new ArrayList<Book>();
						break;

					case XmlPullParser.START_TAG:// ��ǩͷ�Ŀ�ʼ�¼�
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
						if ("book".equals(parser.getName())) // �жϽ���ı�ǩ�Ƿ���book
						{
							books.add(book);
							book = null;
						}
						break;

				} // switch����---XML�е�һ���ڲ�Ԫ�ش������
				event = parser.next(); // ������һ��Ԫ��

			}//���XML�ļ��������
			
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
