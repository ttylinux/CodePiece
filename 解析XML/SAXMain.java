package xmlParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 使用SAX解析的主控方法
 */
public class SAXMain {

	public static void main(String[] args) {
	
		FileInputStream fis;

		String str2 = "E:\\android_workspace\\ThinkInJava\\test.xml";
		String location = str2.replace("\\", "/");

		System.out.println(location);

		try {
			fis = new FileInputStream(location);

			 BufferedReader reader= new BufferedReader(new
			 InputStreamReader(fis));
			 performSAXParse(reader);
			//performSAXParse(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void performSAXParse(Reader reader) {
		try {

			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			
			SAXHandler saxParseHandler = new SAXHandler();
			xmlReader.setContentHandler(saxParseHandler);
			xmlReader.parse(new InputSource(reader));
			
			
			displayResult(saxParseHandler.getBooks());
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	private static void displayResult(ArrayList<Book> books)
	{
		for(Book item: books)
		{
			System.out.println(item.toString());
		}
	}

}
