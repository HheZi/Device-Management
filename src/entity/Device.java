package entity;

import java.sql.Date;

public class Device {
	private String id;
	private String name;
	private int quantity;
	private float price;
	private Date imported_date;
	private Supplier supplier;
	
	
	 public Device(String id, String name, int quantity, 
	            float price, Date imported_date, Supplier supplier) {
	        this.id = id;
	        this.name = name;
	        this.quantity = quantity;
	        this.price = price;
	        this.imported_date = imported_date;
	        this.supplier = supplier;
	    }


		public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public float getPrice() {
	        return price;
	    }

	    public void setPrice(float price) {
	        this.price = price;
	    }

	    public Date getImportedDate() {
	        return imported_date;
	    }

	    public void setImportedDate(Date importedDate) {
	        this.imported_date = importedDate;
	    }

	    public Supplier getSupplier() {
	        return supplier;
	    }
	    public void setSupplier(Supplier supplier) {
	        this.supplier = supplier;
	    }
	}
