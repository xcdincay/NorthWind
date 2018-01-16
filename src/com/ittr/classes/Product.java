package com.ittr.classes;

public class Product {
	
	private int ProductID;
	private String ProductName; 
	private int SupplierID;
	private int CategoryID;
	private String QuantityPerUnit;
	private double UnitPrice;
	private Short UnitsInStock;
	private Short UnitsOnOrder;
	private Short ReorderLevel;
	private boolean Discontinued;

	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		this.ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		this.ProductName = productName;
	}
	public int getSupplierID() {
		return SupplierID;
	}
	public void setSupplierID(int supplierID) {
		this.SupplierID = supplierID;
	}
	public int getCategoryID() {
		return CategoryID;
	}
	public void setCategoryID(int categoryID) {
		this.CategoryID = categoryID;
	}
	public String getQuantityPerUnit() {
		return QuantityPerUnit;
	}
	public void setQuantityPerUnit(String quantityPerUnit) {
		this.QuantityPerUnit = quantityPerUnit;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.UnitPrice = unitPrice;
	}
	public Short getUnitsInStock() {
		return UnitsInStock;
	}
	public void setUnitsInStock(Short unitsInStock) {
		this.UnitsInStock = unitsInStock;
	}
	public Short getUnitsOnOrder() {
		return UnitsOnOrder;
	}
	public void setUnitsOnOrder(Short unitsOnOrder) {
		this.UnitsOnOrder = unitsOnOrder;
	}
	public Short getReorderLevel() {
		return ReorderLevel;
	}
	public void setReorderLevel(Short reorderLevel) {
		this.ReorderLevel = reorderLevel;
	}
	public boolean isDiscontinued() {
		return Discontinued;
	}
	public void setDiscontinued(boolean discontinued) {
		this.Discontinued = discontinued;
	}

	public void setProductAttribute(String key, Object value) 
	{
		if (key.equals("ProductID")) {
			this.setProductID((int) value);
		} else if (key.equals("ProductName")) {
			this.setProductName((String) value);
		} else if (key.equals("SupplierID")) {
			this.setSupplierID((int) value);
		} else if (key.equals("CategoryID")) {
			this.setCategoryID((int) value);
		} else if (key.equals("QuantityPerUnit")) {
			this.setQuantityPerUnit((String) value);
		} else if (key.equals("UnitPrice")) {
			this.setUnitPrice(new Double(value.toString()));
		} else if (key.equals("UnitsInStock")) {
			this.setUnitsInStock((Short) value);
		} else if (key.equals("UnitsOnOrder")) {
			this.setUnitsOnOrder((Short) value);
		} else if (key.equals("ReorderLevel")) {
			this.setReorderLevel((Short) value);
		} else if (key.equals("Discontinued")) {
			this.setDiscontinued((boolean) value);
		}
	}
	
}
