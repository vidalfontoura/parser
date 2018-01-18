package com.ef.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ef.parser.Parser;

public class Main {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) throws ParseException {
		
		
//		
//		String startDate = args[0];
//		String duration = args[1];
//		String threshold = args[2];
		
		Parser parser = new Parser();
		
		java.util.Date parse = DATE_FORMAT.parse("2017-01-01 15:00:00");
		parser.read("access.log", parse, "hourly", 200);
		
	}

}
