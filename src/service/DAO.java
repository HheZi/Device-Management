package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
	Optional<T> getById(String id);
	
	List<T> getAll();
	
	int save(T t) throws SQLException;
	
	void update(T t, String str);
	
	int delete(T t);
}
