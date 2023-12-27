package main;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.*;
import service.*;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;

import tablemode.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainProgram {

	private final int WIDTH_OF_THE_WINDOW = 900, HEIGHT_OF_THE_WINDOW  = 530;
	
	private JFrame frmDeviceManagment;
	private JTable deviceTable;
	private DefaultTableModel modelDeviceTableModel;
	private DefaultTableModel modelSupplierTableModel;
	private JTextField textFieldForDeviceID;
	private JTextField textFieldForDeviceName;
	private JTextField textFieldForDeviceQuantity;
	private JTextField textFieldForDevicePrice;
	private JTextField textFieldForImportedDate;
	private JComboBox<String> comboBoxForSupplier;
	
	private DeviceTableModel deviceModel;
	private SupplierTableModel supplierModel;
	
	private DeviceService deviceService;
	private SupplierService supplierService;
	private JTable supplierTable;
	private JTextField textFieldSupplierId;
	private JTextField textFieldSupplierName;
	private JTextField textFieldSupplierAddress;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProgram window = new MainProgram();
					window.frmDeviceManagment.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public MainProgram() {
		deviceService = new DeviceService();
		supplierService = new SupplierService();
		initialize();
		deviceModel = new DeviceTableModel(modelDeviceTableModel); 
		supplierModel = new SupplierTableModel(modelSupplierTableModel);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDeviceManagment = new JFrame();
		frmDeviceManagment.setForeground(new Color(255, 255, 255));
		frmDeviceManagment.setTitle("Device Managment");
		frmDeviceManagment.setSize(WIDTH_OF_THE_WINDOW, HEIGHT_OF_THE_WINDOW);
		frmDeviceManagment.setLocationRelativeTo(null);
		frmDeviceManagment.setResizable(false);
		frmDeviceManagment.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmDeviceManagment.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 255, 255));
		frmDeviceManagment.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		tabbedPane.addTab("Devices", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 501, 421);
		panel.add(scrollPane);
		
		DefaultComboBoxModel<String> modelBoxModel = new DefaultComboBoxModel<String>(supplierService.getAllId());
		comboBoxForSupplier = new JComboBox<String>(modelBoxModel);
		comboBoxForSupplier.setBounds(651, 288, 218, 22);
		panel.add(comboBoxForSupplier);
		
		modelDeviceTableModel = new DefaultTableModel(new Object[][] {
		},
		new String[] {
			"Id", "Name", "Quantity", "Price", "Imported date", "Supplier id"
		}
		); 
		deviceTable = new JTable(modelDeviceTableModel);
		deviceTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = deviceTable.getSelectedRow();
				textFieldForDeviceID.setText(modelDeviceTableModel.getValueAt(index, 0).toString());
				textFieldForDeviceName.setText(modelDeviceTableModel.getValueAt(index, 1).toString());
				textFieldForDeviceQuantity.setText(modelDeviceTableModel.getValueAt(index, 2).toString());
				textFieldForDevicePrice.setText(modelDeviceTableModel.getValueAt(index, 3).toString());
				textFieldForImportedDate.setText(modelDeviceTableModel.getValueAt(index, 4).toString());
				comboBoxForSupplier.setSelectedIndex(modelBoxModel.getIndexOf(modelDeviceTableModel.getValueAt(index, 5).toString()));
			}
		});
		deviceTable.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(deviceTable);
		
		JLabel textDivecesID = new JLabel("Device ID");
		textDivecesID.setFont(new Font("Arial", Font.PLAIN, 14));
		textDivecesID.setBounds(527, 31, 77, 35);
		panel.add(textDivecesID);
		
		textFieldForDeviceID = new JTextField();
		textFieldForDeviceID.setBounds(651, 39, 131, 20);
		panel.add(textFieldForDeviceID);
		textFieldForDeviceID.setColumns(10);
		
		JLabel textDeviceName = new JLabel("Device name");
		textDeviceName.setFont(new Font("Arial", Font.PLAIN, 14));
		textDeviceName.setBounds(527, 88, 92, 14);
		panel.add(textDeviceName);
		
		textFieldForDeviceName = new JTextField();
		textFieldForDeviceName.setColumns(10);
		textFieldForDeviceName.setBounds(651, 86, 191, 20);
		panel.add(textFieldForDeviceName);
		
		JButton searchButton = new JButton("Find All");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceModel.setData(deviceService.getAll());
			}
		});
		searchButton.setBounds(521, 335, 89, 35);
		panel.add(searchButton);
		
		JLabel textDeviceQuantity = new JLabel("Device quantity");
		textDeviceQuantity.setFont(new Font("Arial", Font.PLAIN, 14));
		textDeviceQuantity.setBounds(527, 139, 114, 14);
		panel.add(textDeviceQuantity);
		
		textFieldForDeviceQuantity = new JTextField();
		textFieldForDeviceQuantity.setColumns(10);
		textFieldForDeviceQuantity.setBounds(651, 137, 120, 20);
		panel.add(textFieldForDeviceQuantity);
		
		JLabel textDevicePrice = new JLabel("Device price");
		textDevicePrice.setFont(new Font("Arial", Font.PLAIN, 14));
		textDevicePrice.setBounds(527, 193, 114, 14);
		panel.add(textDevicePrice);
		
		textFieldForDevicePrice = new JTextField();
		textFieldForDevicePrice.setColumns(10);
		textFieldForDevicePrice.setBounds(651, 191, 120, 20);
		panel.add(textFieldForDevicePrice);
		
		JLabel textImportedDate = new JLabel("Imported date");
		textImportedDate.setFont(new Font("Arial", Font.PLAIN, 14));
		textImportedDate.setBounds(526, 240, 114, 14);
		panel.add(textImportedDate);
		
		textFieldForImportedDate = new JTextField();
		textFieldForImportedDate.setColumns(10);
		textFieldForImportedDate.setBounds(650, 238, 166, 20);
		panel.add(textFieldForImportedDate);
		
		JLabel textSuplier = new JLabel("Supplier");
		textSuplier.setFont(new Font("Arial", Font.PLAIN, 14));
		textSuplier.setBounds(527, 291, 77, 14);
		panel.add(textSuplier);
		
		
		
		JButton clearTableButton = new JButton("Clear table");
		clearTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceModel.clearTable();
			}
		});
		clearTableButton.setBounds(556, 397, 104, 35);
		panel.add(clearTableButton);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Device device = getDevice();
					if(device != null) {
						if(!deviceService.isDeviceExisted(device.getId())) {
							if(deviceService.save(device) != 1) {
								JOptionPane.showMessageDialog(frmDeviceManagment, "Cannot insert new device", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else {
								deviceModel.setData(deviceService.getAll());
							}
						}
						else {
							int choiceUpdate = JOptionPane.showConfirmDialog(frmDeviceManagment, "Device with the same id is existed. You want to update?", "Already exist", JOptionPane.YES_NO_OPTION);
							if(choiceUpdate == JOptionPane.YES_OPTION) {
								deviceService.update(device, device.getId());
								deviceModel.setData(deviceService.getAll());
							}
						}
					}
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		saveButton.setBounds(649, 335, 89, 35);
		panel.add(saveButton);
		
		JButton removeButton = new JButton("Delete");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choiceDel = JOptionPane.showConfirmDialog(frmDeviceManagment, "Are you sure you want to delete?", "Delete?", JOptionPane.YES_NO_OPTION);
				if(choiceDel == JOptionPane.YES_OPTION) {
					deviceService.delete(getDevice());
					deviceModel.setData(deviceService.getAll());
					setEmptyTextToTextFieldsOfDevice();
				}
			}
		});
		removeButton.setBounds(766, 335, 89, 35);
		panel.add(removeButton);
		
		JButton clearTextButton = new JButton("Clear text");
		clearTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEmptyTextToTextFieldsOfDevice();
			}
		});
		clearTextButton.setBounds(725, 397, 104, 35);
		panel.add(clearTextButton);
		
		JPanel panelSupplier = new JPanel();
		tabbedPane.addTab("Supplier", null, panelSupplier, null);
		panelSupplier.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 501, 423);
		panelSupplier.add(scrollPane_1);
		
		modelSupplierTableModel = new DefaultTableModel(new Object[][] {
		},
		new String[] {
			"Id", "Name", "Address"
		}
		); 
		supplierTable = new JTable(modelSupplierTableModel);
		supplierTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = supplierTable.getSelectedRow();
				
				textFieldSupplierId.setText(supplierTable.getValueAt(row, 0).toString());
				textFieldSupplierName.setText(supplierTable.getValueAt(row, 1).toString());
				textFieldSupplierAddress.setText(supplierTable.getValueAt(row, 2).toString());
			}
		});
		supplierTable.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(supplierTable);
		
		JLabel lblSupplierId = new JLabel("Supplier Id");
		lblSupplierId.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSupplierId.setBounds(540, 78, 87, 28);
		panelSupplier.add(lblSupplierId);
		
		JLabel lblSupplierName = new JLabel("Supplier Name");
		lblSupplierName.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSupplierName.setBounds(540, 164, 97, 28);
		panelSupplier.add(lblSupplierName);
		
		textFieldSupplierId = new JTextField();
		textFieldSupplierId.setBounds(679, 83, 136, 20);
		panelSupplier.add(textFieldSupplierId);
		textFieldSupplierId.setColumns(10);
		
		textFieldSupplierName = new JTextField();
		textFieldSupplierName.setColumns(10);
		textFieldSupplierName.setBounds(679, 169, 136, 20);
		panelSupplier.add(textFieldSupplierName);
		
		JLabel lblSupplierAddress = new JLabel("Supplier Address");
		lblSupplierAddress.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSupplierAddress.setBounds(540, 246, 117, 28);
		panelSupplier.add(lblSupplierAddress);
		
		textFieldSupplierAddress = new JTextField();
		textFieldSupplierAddress.setColumns(10);
		textFieldSupplierAddress.setBounds(679, 251, 136, 20);
		panelSupplier.add(textFieldSupplierAddress);
		
		JButton supplierSearchButton = new JButton("Find All");
		supplierSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supplierModel.setData(supplierService.getAll());
			}
		});
		supplierSearchButton.setBounds(521, 337, 89, 35);
		panelSupplier.add(supplierSearchButton);
		
		JButton supplierClearTableButton = new JButton("Clear table");
		supplierClearTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				supplierModel.clearTable();
			}
		});
		supplierClearTableButton.setBounds(556, 399, 104, 35);
		panelSupplier.add(supplierClearTableButton);
		
		JButton supplierSaveButton = new JButton("Save");
		supplierSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Supplier supplier = getSupplier();
					if(supplier != null) {
						if(!supplierService.isSupplierExisted(supplier.getId())) {
							if(supplierService.save(supplier) != 1) {
								JOptionPane.showMessageDialog(frmDeviceManagment, "Cannot insert new supplier", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else {
								supplierModel.setData(supplierService.getAll());
								updateComboBoxValues();
							}
						}
						else {
							int choiceUpdate = JOptionPane.showConfirmDialog(frmDeviceManagment, "Supplier with the same id is existed. You want to update?", "Already exist", JOptionPane.YES_NO_OPTION);
							if(choiceUpdate == JOptionPane.YES_OPTION) {
								supplierService.update(supplier, supplier.getId());
								supplierModel.setData(supplierService.getAll());
							}
						}
					}
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		supplierSaveButton.setBounds(649, 337, 89, 35);
		panelSupplier.add(supplierSaveButton);
		
		JButton supplierRemoveButton = new JButton("Delete");
		supplierRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choiceDel = JOptionPane.showConfirmDialog(frmDeviceManagment, "Are you sure you want to delete?", "Delete?", JOptionPane.YES_NO_OPTION);
				if(choiceDel == JOptionPane.YES_OPTION) {
					if(supplierService.delete(getSupplier()) == 2) {
						JOptionPane.showMessageDialog(frmDeviceManagment, "This supplier tied to one of the devices", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						supplierModel.setData(supplierService.getAll());
						setEmptyTextToTextFieldsOfSupplier();
						updateComboBoxValues();
					}
				}
			}
		});
		supplierRemoveButton.setBounds(766, 337, 89, 35);
		panelSupplier.add(supplierRemoveButton);
		
		JButton supplierCleanTextButton = new JButton("Clear text");
		supplierCleanTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEmptyTextToTextFieldsOfSupplier();
			}
		});
		supplierCleanTextButton.setBounds(725, 399, 104, 35);
		panelSupplier.add(supplierCleanTextButton);
	}
	
	private Device getDevice() {
		try {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		
		String id = textFieldForDeviceID.getText().trim();
		String name = textFieldForDeviceName.getText().trim();
		int quantity = Integer.parseInt(textFieldForDeviceQuantity.getText().trim());
		float price = Float.parseFloat(textFieldForDevicePrice.getText().trim());
		java.sql.Date sqlDate = new java.sql.Date(formatter.parse(textFieldForImportedDate.getText().trim()).getTime());
		Supplier supplier = supplierService.findById(comboBoxForSupplier.getSelectedItem().toString());
		
		return new Device(id, name, quantity, price, sqlDate, supplier);
		
		} catch(ParseException | NumberFormatException e2) {
			JOptionPane.showMessageDialog(frmDeviceManagment, "Incorrect entering values", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}
	
	private Supplier getSupplier() {
		String id = textFieldSupplierId.getText().trim();
		String name = textFieldSupplierName.getText().trim();
		String address = textFieldSupplierAddress.getText().trim();
		if(id.equals("") || name.equals("") || address.equals("") ) {
			JOptionPane.showMessageDialog(frmDeviceManagment, "Incorrect entering values", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return new Supplier(id, name, address);
	}
	
	private void updateComboBoxValues() {
		comboBoxForSupplier.removeAllItems();
		for (String id : supplierService.getAllId()) {
			comboBoxForSupplier.addItem(id);
		}
	}
	
	private void setEmptyTextToTextFieldsOfDevice() {
		textFieldForDeviceID.setText("");
		textFieldForDeviceName.setText("");
		textFieldForDeviceQuantity.setText("");
		textFieldForDevicePrice.setText("");
		textFieldForImportedDate.setText("");
		comboBoxForSupplier.setSelectedIndex(0);
	}
	
	private void setEmptyTextToTextFieldsOfSupplier() {
		textFieldSupplierId.setText("");
		textFieldSupplierName.setText("");
		textFieldSupplierAddress.setText("");
	}
}
