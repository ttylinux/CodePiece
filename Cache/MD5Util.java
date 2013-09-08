package com.yanhuo_01.compoments.imaCache;

import java.security.MessageDigest;

public class MD5Util {

	/**
	 * @author lushuwei(ttylinux)
	 * 
	 * public static void main(String[] args) { 
	 * 
	 * int i = -1; byte j = -1; </br>
	 * 
	 * System.out.println( "i is :" + i + ", j is :"+j);</br>
	 * System.out.println("i hexstr: " +Integer.toHexString(i));</br>
	 * System.out.println("j hexstr:"+Integer.toHexString(j));//自动出现向上转型，即byte自动转为int </br>
	 * System.out.println("j & 0xff,can get the real result : " + Integer.toHexString(j&0xff)); } </br> 
	 * *
	 * output: </br>
	 * i is :-1, j is :-1 </br> 
	 * i hexstr: ffffffff </br> 
	 * j hexstr:ffffffff </br>
	 * j & 0xff,can get the real result : ff </br>
	 * 
	 * 
	 */

	public final static String MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			// 将MD5输出的二进制结果转换为十六进制
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {

				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
