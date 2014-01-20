package com.compressanddisplay.tool;


import java.util.Calendar;

public class GetDate {

	public static String getDate()
	{
		Calendar calendar = Calendar.getInstance();
		String result = calendar.get(calendar.YEAR)+""+
		(calendar.get(calendar.MONTH)+1)+calendar.get(calendar.DAY_OF_MONTH)
		+""+calendar.get(calendar.HOUR_OF_DAY)+calendar.get(calendar.MINUTE)+calendar.get(calendar.SECOND);
		
		
		return result;
	}
	

}
