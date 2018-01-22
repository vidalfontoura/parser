package com.ef.main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.ef.parser.Parser;

@ComponentScan(basePackages = "com.ef")
public class Main {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) throws ParseException, SQLException, IOException {
		
		
		Date initial =  DATE_FORMAT.parse("2017-01-01 15:00:00");
		String duration = "hourly";
		int threshold = 100;
		
		ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
		
		Parser parser = context.getBean(Parser.class);
		
		java.util.Date parse = DATE_FORMAT.parse("2017-01-01 15:00:00");
//		parser.parseFile("access.log");
		List<String> ipsManyRequests = parser.getIpsManyRequests(initial, duration, threshold);
		for(String ip: ipsManyRequests) {
			System.out.println(ip);
		}
		
		
		
		
	}
 
	

}

