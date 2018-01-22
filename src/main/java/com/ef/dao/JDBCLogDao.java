package com.ef.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ef.domain.LogEntity;

@Repository
@Qualifier("logDao")
public class JDBCLogDao implements LogDao {
	
	private static final String INSERT_TABLE_LOG = "INSERT INTO wallet_hub.log "
			+ "(date,ip,request,status,agent) VALUES (?,?,?,?,?)";
	
	private static final String SELECT_IP_TOO_MANY_REQUESTS =
			"SELECT ip FROM wallet_hub.log WHERE (date BETWEEN '%s' AND '%s') "
			+ "GROUP BY ip HAVING COUNT(*) > '%s';";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int insert(LogEntity logEntity) {
		
		int id = jdbcTemplate.update(INSERT_TABLE_LOG,
	            logEntity.getDate(), logEntity.getIp(), 
	            logEntity.getRequest(), logEntity.getStatus(), logEntity.getAgent());
		
		return id;
	}

	@Override
	public List<String> queryIpsThatExceedThreshold(String initial, String end, int threshold) {
		
		String sql = String.format(SELECT_IP_TOO_MANY_REQUESTS, initial, end, threshold);
		System.out.println(sql);
		return jdbcTemplate.queryForList(sql, String.class);
	}


	
	

}
