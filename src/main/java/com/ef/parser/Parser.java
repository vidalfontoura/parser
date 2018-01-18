package com.ef.parser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
	
	/**
	 * ) This is how the tool works:

    java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
	
	The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 (one hour) and print them to console AND also load them to another MySQL table with comments on why it's blocked.

	java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250

	The tool will find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 to 2017-01-02.13:00:00 (24 hours) and print them to console AND also load them to another MySQL table with comments on why it's blocked.

	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public void read(String filePath, Date initialDate, String duration, int threshold) {
		
		try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
			Map<String, Long> ipRequestCount = lines.map(line -> line.split("\\|")).filter(line -> {
				try {
					Date dateFromFile = DATE_FORMAT.parse(line[0]);
					Date finalDate = calculateFinalDate(initialDate, duration);
					if (dateFromFile.after(initialDate) && dateFromFile.before(finalDate)) {
						return true;
					}
				} catch (ParseException e) {
					throw runtime(e);
				}
				return false;
			}).collect(Collectors.groupingBy(line -> line[1], Collectors.counting()));
			
			for(String ip: ipRequestCount.keySet()) {
				Long numberOfRequets = ipRequestCount.get(ip);
				if (numberOfRequets >= threshold) {
					System.out.println(ip);
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Error occurred while parsing the file");
		}
		
		
	}
	
	public Date calculateFinalDate(Date initial, String duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(initial);
		if (duration.equals("daily")) {
			cal.add(Calendar.HOUR_OF_DAY, 24);
			return cal.getTime();
		} else if (duration.equals("hourly")) {
			cal.add(Calendar.HOUR_OF_DAY, 1);
			return cal.getTime();
		}
		throw new RuntimeException("Not supported");
	}
	
	 public RuntimeException runtime(Throwable e) {
	        if (e instanceof RuntimeException) {
	            return (RuntimeException)e;
	        }

	        return new RuntimeException(e);
	    }

}
