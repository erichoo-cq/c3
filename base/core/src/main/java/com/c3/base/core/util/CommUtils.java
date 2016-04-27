package com.c3.base.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


public class CommUtils {

	private static CommUtils instance = new CommUtils();

	public static CommUtils getInstance(){
	    return instance;
	}

	public static Locale getLocale( HttpServletRequest request ) {

		Locale locale = (Locale)request.getSession().getAttribute(
			SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME );
		if (locale == null) {
			locale = request.getLocale();
		}
		return locale;
	}

//	public static String getUserName() {
//		ServletRequestAttributes sqa = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		if (sqa == null) {
//			return "";
//		}
//		HttpServletRequest request = sqa.getRequest();
//		if (request != null) {
//			Operator ov = (Operator) request.getSession().getAttribute(Constant.OPERATOR_KEY);
//			if (ov != null) {
//				return ov.getUserName();
//			}
//		}
//		return "";
//	}


	public static String getIp() {

		HttpServletRequest request = getHttpRequest();
		if(request==null){
			return "";
		}

		 String ip = request.getHeader("x-forwarded-for");
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("Proxy-Client-IP");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("WL-Proxy-Client-IP");
		 }
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getRemoteAddr();
		 }

		 return ip;
	}


	public static String getServerIp() {

		String serverIp = "";

		try {
			serverIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return serverIp;
	}

	public  String getWebClassesPath() {
	   return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	}

	public  String getWebInfPath() throws IllegalAccessException{
	   String path = getWebClassesPath();
	   if (path.indexOf("WEB-INF") > 0) {
	    path = path.substring(0, path.indexOf("WEB-INF")+8);
	   } else {
	    throw new IllegalAccessException("路径获取错误");
	   }
	   return path;
	 }

	public  String getWebRoot() throws IllegalAccessException{
	   String path = getWebClassesPath();
	   if (path.indexOf("WEB-INF") > 0) {
	    path = path.substring(0, path.indexOf("WEB-INF/classes"));
	   } else {
	    throw new IllegalAccessException("路径获取错误");
	   }
	   return path;
	 }

	public static String getRequestAppName(){

		HttpServletRequest request = getHttpRequest();

		 if (request == null) {
			return null;
		}

		 return request.getContextPath().substring(1);
	 }

//	public static Operator getOperatorVo() {
//
//		HttpServletRequest request = getHttpRequest();
//		if (request != null) {
//			return (Operator) request.getSession().getAttribute(Constant.OPERATOR_KEY);
//		}
//
//		return null;
//	}

	public static HttpServletRequest getHttpRequest(){

		ServletRequestAttributes sqa = (ServletRequestAttributes)
			RequestContextHolder.getRequestAttributes();

		if(sqa==null){
			 return null;
		 }

		return sqa.getRequest();


	}

	public static Date getDatetime(String format){
        SimpleDateFormat dateFormatter =new SimpleDateFormat(format);
        GregorianCalendar gc=new GregorianCalendar();
        Date date=new Date();;
        try {
           date =dateFormatter.parse((dateFormatter.format(gc.getTime())));
        } catch (ParseException e) {
           e.printStackTrace();
        }
        return date;
    }

	public static String dateToString(SimpleDateFormat format, Date date) {
		if(date == null){
			return "";
		}
		return format.format(date);
	}

	public static Date stringToDate(SimpleDateFormat format, String strDate) {
		if (strDate == null || "".equals(strDate)) {
			return null;
		} else {
			try {
				return format.parse(strDate);
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	public static String genFileName() {
		return Num62.longToN62(System.currentTimeMillis()) + RandomStringUtils.random(4, Num62.N36_CHARS);
	}

	public static String genFileName(String ext) {
		return genFileName() + "." + ext;
	}

	public static String genFileName(String fileName, String ext) {
		return fileName + "." + ext;
	}

	public static String getFileRealName(String fileName) {
		return FilenameUtils.getName(fileName);
	}

	public static String getExtension(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

}
