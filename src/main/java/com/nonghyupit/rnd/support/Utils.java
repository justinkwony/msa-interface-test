package com.nonghyupit.rnd.support;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
	public static SimpleDateFormat dateShortFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateLongFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat millisecFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * @param calendar
	 * @return 지정일의 00:00:00.000
	 */
	public static Date trunc(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date trunc(Date date) {
		Calendar calendar = Calendar.getInstance(); // locale-specific
		calendar.setTime(date);
		return trunc(calendar);
	}

	/**
	 * @param calendar
	 * @return 지정일의 23:59:59.998
	 */
	public static Date ceil(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 998);
		return calendar.getTime();
	}

	public static Date ceil(Date date) {
		Calendar calendar = Calendar.getInstance(); // locale-specific
		calendar.setTime(date);
		return ceil(calendar);
	}

	/**
	 * @param calendar
	 * @return 해단월의 1일 YYYY-MM-01 00:00:00.000
	 */
	public static Date firstDate(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date firstDate(Date date) {
		Calendar calendar = Calendar.getInstance(); // locale-specific
		calendar.setTime(date);
		return firstDate(calendar);
	}

	/**
	 * @param calendar
	 * @return 해단월의 말일 YYYY-MM-DD 23:59:59.998
	 */
	public static Date lastDate(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 998);
		return calendar.getTime();
	}

	public static Date lastDate(Date date) {
		Calendar calendar = Calendar.getInstance(); // locale-specific
		calendar.setTime(date);
		return lastDate(calendar);
	}

	public static Date utcToDate(Long utc) {
		Date date = null;
		try {
			if(utc != null && utc > 0) date = new Date(utc);
			else return null;
		} catch(Exception e) {
			date = new Date();
			e.printStackTrace();
		}
		return date;
	}

	public static String getAsString(Object object) {
		return getAsString(object, null);
	}

	public static String getAsString(Object object, String defaultValue) {
		String value;
		if(object == null)
			value = defaultValue;
		else {
			if(object instanceof String) {
				value = (String)object;
				value = value.trim();
				if(value.isEmpty())
					value = defaultValue;
			} else
				value = defaultValue;
		}
		return value;
	}

	public static long getAsLong(Object object) {
		return getAsLong(object, 0L);
	}

	public static Long getAsLong(Object object, Long defaultValue) {
		Long value;
		if(object == null)
			value = defaultValue;
		else {
			if(object instanceof Long) {
				value = (Long)object;
			} else if(object instanceof Integer) {
				value = Long.valueOf((Integer)object);
			} else if(object instanceof String) {
				try {
					value = Long.parseLong((String)object);
				} catch (NumberFormatException nfe) {
					value = defaultValue;
				}
			} else
				value = defaultValue;
		}
		return value;
	}

	public static int getAsInteger(Object object) {
		return getAsInteger(object, 0);
	}

	public static Integer getAsInteger(Object object, Integer defaultValue) {
		Integer value = null;
		if(object == null)
			value = defaultValue;
		else {
			if(object instanceof Integer) {
				value = (Integer)object;
			} else if(object instanceof Long) {
				value = (Integer)object;
			} else if(object instanceof String) {
				try {
					value = Integer.parseInt((String)object);
				} catch (NumberFormatException nfe) {
					value = defaultValue;
				}
			} else
				value = defaultValue;
		}
		return value;
	}

	public static boolean getAsBoolean(Object object) {
		return getAsBoolean(object, false);
	}

	public static Boolean getAsBoolean(Object object, Boolean defaultValue) {
		Boolean value;
		if(object == null)
			value = defaultValue;
		else {
			if(object instanceof Boolean) {
				value = (Boolean)object;
			} else if(object instanceof Integer) {
				value = (Integer)object == 1;
			} else if(object instanceof String) {
				value = Boolean.parseBoolean((String)object);
			} else
				value = defaultValue;
		}
		return value;
	}

	public static Date getAsDate(Object object) {
		return getAsDate(object, null);
	}

	public static Date getAsDate(Object object, Date defaultValue) {
		Date value;
		if(object == null)
			value = defaultValue;
		else {
			if(object instanceof Long) {
				value = utcToDate((Long)object);
			} else if(object instanceof Integer) {
				value = utcToDate(Long.valueOf((Integer)object));
			} else if(object instanceof String) {
				try {
					value = dateLongFormat.parse((String)object);
				} catch (ParseException e) {
					value = defaultValue;
				}
			} else
				value = defaultValue;
		}
		return value;
	}

	public static String emptyToNull(String text) {
		if(text != null && text.isEmpty())
			return null;
		else
			return text;
	}

	public static String substring(String s, int length) {
		if(s == null) {
			return s;
		}
		byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
		if(bytes.length <= length) {
			return s;
		}
		int totalByteCount = 0;
		for(int i = 0; i < s.length(); ) {
			int cp = s.codePointAt(i);
			int n = Character.charCount(cp);
			int byteCount = s.substring(i, i + n).getBytes(StandardCharsets.UTF_8).length;
			if((totalByteCount + byteCount) > length) {
				break;
			}
			totalByteCount += byteCount;
			i += n;
		}
		return new String(bytes, 0, totalByteCount);
	}

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_X_FORWARDED");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_FORWARDED_FOR");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_FORWARDED");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("HTTP_VIA");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("REMOTE_ADDR");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getRemoteAddr();
		return ip;
	}

	public static boolean isInternalRequest(HttpServletRequest request) {
		for(String s : Collections.list(request.getHeaderNames())) {
			log.debug("{} {}", s, request.getHeader(s));
		}
		String agent = request.getHeader("User-Agent");
		if(agent != null) {
			return agent.toLowerCase().contains("java");
		}
		return false;
	}
}
