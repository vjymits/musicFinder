package com.scraping.db;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {
	
	
	int persist(T obj) throws SQLException;
	int update(long id,T obj) throws SQLException;
	int delete(long id);
	List<T> selectAll();
	List<T> selectOneById(long id);
	
	
	

}
