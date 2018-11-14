package com.pablo.innocvexam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserUtil {
	
	public static Date parseDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Can not parse birth date", e);
		}
	}
	
}