package service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Device;
import sqlmanager.ConnectionManager;

public class DeviceService implements DAO<Device> {

	@Override
	public Optional<Device> get(String id) {
		return Optional.empty();
	}

	@Override
	public List<Device> getAll() {
		final String SQL = """
				select * from manager.device order by id;
				""";
		List<Device> list = new ArrayList<Device>();
		
		try(Connection c = ConnectionManager.getConnection();
			Statement stmt = c.createStatement();
			ResultSet res = stmt.executeQuery(SQL);){
			while(res.next()) {
				list.add(new Device(res.getString("id"), res.getString("name"), 
						res.getInt("quantity"), res.getFloat("price"), 
						res.getDate("imported_date"), SupplierService.findById(res.getString("supplier_id"), c)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int save(Device device) throws SQLException {
		final String SQL = """
				insert into manager.device values (?,?,?,?,?,?)
				""";
		try(Connection c = ConnectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, device.getId());
			stmt.setString(2, device.getName());
			stmt.setInt(3, device.getQuantity());
			stmt.setFloat(4, device.getPrice());
			stmt.setDate(5, device.getImportedDate());
			stmt.setString(6, device.getSupplier().getId());
			return stmt.executeUpdate();
		} 
	}
	
	public boolean isDeviceExisted(String id) throws SQLException {
		final String SQL = """
				select * from manager.device where id = ?
				""";
		try(Connection c = ConnectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, id);
			try(ResultSet res = stmt.executeQuery();){
				return res.next();
			}
		} 
	}
	
	@Override
	public void update(Device device, String id) {
		final String SQL = """
				update manager.device 
				set name = ?, quantity = ?, price = ?, 
				imported_date = ?, supplier_id = ? where id = ?
				""";
		try(Connection c = ConnectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
			stmt.setString(1, device.getName());
			stmt.setInt(2, device.getQuantity());
			stmt.setFloat(3, device.getPrice());
			stmt.setDate(4, device.getImportedDate());
			stmt.setString(5, device.getSupplier().getId());
			stmt.setString(6, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Device device) {
		final String SQL = """
				delete from manager.device 
				where id = ? and name = ? and quantity = ? and price = ?  
				and imported_date = ? and supplier_id = ?
				""";
		try(Connection c = ConnectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
				stmt.setString(1, device.getId());
				stmt.setString(2, device.getName());
				stmt.setInt(3, device.getQuantity());
				stmt.setFloat(4, device.getPrice());
				stmt.setDate(5, device.getImportedDate());
				stmt.setString(6, device.getSupplier().getId());
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
