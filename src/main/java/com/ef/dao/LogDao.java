package com.ef.dao;

import java.util.List;

import com.ef.domain.LogEntity;

public interface LogDao {
	
	int insert(LogEntity logEntity);
	
	List<String> queryIpsThatExceedThreshold(String initial, String end, int threshold);
	
	
}
