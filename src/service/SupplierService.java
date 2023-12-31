package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import entity.Supplier;
import sqlmanager.ConnectionManager;

public class SupplierService implements DAO<Supplier>{
	private final ConnectionManager connectionManager = ConnectionManager.getInstance();
	private static final SupplierService INSTANCE = new SupplierService();
	
	private SupplierService() {}
	
	public static SupplierService getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Optional<Supplier> getById(String id) {
		final String SQL = """
				select id, name, address 
				from manager.supplier where id = ?
				""";
		Optional<Supplier> sup = null;
		try(Connection c = connectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, id);
			try(ResultSet res = stmt.executeQuery();){
				if(res.next()) {
					sup = Optional.of(new Supplier(res.getString("id"), res.getString("name"), res.getString("address")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sup;
	}
	
	public boolean isSupplierExisted(String id) throws SQLException {
		final String SQL = """
				select id, name, address 
				from manager.supplier where id = ?
				""";
		try(Connection c = connectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, id);
			try(ResultSet res = stmt.executeQuery();){
				return res.next();
			}
		} 
	}
	
	@Override
	public List<Supplier> getAll() {
		final String SQL = """
				select id, name, address
				from manager.supplier order by id
				""";
		List<Supplier> list = new ArrayList<Supplier>();
		
		try(Connection c = connectionManager.getConnection();
			Statement stmt = c.createStatement();
			ResultSet res = stmt.executeQuery(SQL);){
			while(res.next()) {
				list.add(new Supplier(res.getString("id"), res.getString("name"), res.getString("address")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Vector<String> getAllId(){
		final String SQL = """
				select id from manager.supplier order by id
				""";
		Vector<String> vector = new Vector<String> ();
		try(Connection c = connectionManager.getConnection();
			Statement stmt = c.createStatement();
			ResultSet res = stmt.executeQuery(SQL);){
			while(res.next()) {
				vector.add(res.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;
	}
	
	@Override
	public int save(Supplier supplier) throws SQLException{
		final String SQL = """
				insert into manager.supplier values (?,?,?)
				""";
		try(Connection c = connectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL)){
			stmt.setString(1, supplier.getId());
			stmt.setString(2, supplier.getName());
			stmt.setString(3, supplier.getAddress());
			return stmt.executeUpdate();
		}
		
	}

	@Override
	public void update(Supplier supplier, String id) {
		final String SQL = """
				update manager.supplier 
				set name = ?, address = ?
				where id = ?
				""";
		try(Connection c = connectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, supplier.getName());
			stmt.setString(2, supplier.getAddress());
			stmt.setString(3, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int delete(Supplier supplier) {
		final String SQL = """
				delete from manager.supplier
				where id = ? and name = ? and address = ?
				""";
		try(Connection c = connectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, supplier.getId());
			stmt.setString(2, supplier.getName());
			stmt.setString(3, supplier.getAddress());
			return stmt.executeUpdate();
		} catch (SQLException e) {
			return 2;
		}
	}

}
