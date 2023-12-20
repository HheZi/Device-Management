package tablemode;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import entity.Supplier;

public final class SupplierTableModel {
	private DefaultTableModel model;
	
	public SupplierTableModel(DefaultTableModel model) {
		this.model = model;
	}
	
	public void setData(List<Supplier> list) {
		clearTable();
		for (Supplier supplier : list) {
			model.addRow(new Object[] {supplier.getId(), supplier.getName(), supplier.getAddress()});
		}
	}
	
	public void clearTable() {
		model.setRowCount(0);
	}
}
