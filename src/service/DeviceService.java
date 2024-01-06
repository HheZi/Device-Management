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
	private final ConnectionManager connectionManager = ConnectionManager.getInstance();
	private final SupplierService supplierService = SupplierService.getInstance();
	private static final DeviceService INSTANCE = new DeviceService();
	
	private DeviceService() {}
	
	public static DeviceService getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Optional<Device> getById(String id) {
		return Optional.empty();
	}

	@Override
	public List<Device> getAll() {
		final String SQL = """
				select id, name, quantity, price, imported_date, supplier_id 
				from manager.device order by id;
				""";
		List<Device> list = new ArrayList<Device>();
		
		try(Connection c = connectionManager.getConnection();
			Statement stmt = c.createStatement();
			ResultSet res = stmt.executeQuery(SQL);){
			while(res.next()) {
				list.add(new Device(res.getString("id"), res.getString("name"), 
						res.getInt("quantity"), res.getFloat("price"), 
						res.getDate("imported_date"), supplierService.getById(res.getString("supplier_id"))));
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
		try(Connection c = connectionManager.getConnection();
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
				select id, name, quantity, price, imported_date, supplier_id 
				from manager.device where id = ? and 1 = 1 
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
	public void update(Device device, String id) {
		final String SQL = """
				update manager.device 
				set name = ?, quantity = ?, price = ?, 
				imported_date = ?, supplier_id = ? where id = ?
				""";
		try(Connection c = connectionManager.getConnection();
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
	public int delete(Device device) {
		final String SQL = """
				delete from manager.device 
				where id = ? and name = ? and quantity = ? and price = ?  
				and imported_date = ? and supplier_id = ?
				""";
		try(Connection c = connectionManager.getConnection();
			PreparedStatement stmt = c.prepareStatement(SQL);){
				stmt.setString(1, device.getId());
				stmt.setString(2, device.getName());
				stmt.setInt(3, device.getQuantity());
				stmt.setFloat(4, device.getPrice());
				stmt.setDate(5, device.getImportedDate());
				stmt.setString(6, device.getSupplier().getId());
				return stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return 2;
	}

}
