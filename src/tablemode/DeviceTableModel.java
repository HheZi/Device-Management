package tablemode;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import entity.Device;

public final class DeviceTableModel {
	private DefaultTableModel model;
	
	public DeviceTableModel(DefaultTableModel model) {
		this.model = model;
	}
	
	public void setData(List<Device> list) {
		clearTable();
		for (Device device : list) {
			model.addRow(new Object[]{device.getId(), device.getName(), device.getQuantity(), 
					device.getPrice(), device.getImportedDate(), device.getSupplier().getId()});
			
		}
		
	}
	public void clearTable() {
		model.setRowCount(0);
	}
	
	
}
