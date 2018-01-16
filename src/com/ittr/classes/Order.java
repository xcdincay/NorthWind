package com.ittr.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.core.ep.feed.ODataDeltaFeedImpl;

import com.ittr.services.ServicesForOrder;

public class Order {
	List<Order_Detail> orderDetails;
	private Invoice invoice = null;
	private int OrderID;
	private String CustomerID;
	private int EmployeeID;
	private int ShipVia;
	private double Freight;
	private String ShipName;
	private String ShipAddress;
	private String ShipCity;
	private String ShipRegion;
	private String ShipPostalCode;
	private String ShipCountry;

	public int getOrderID() {
		return OrderID;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public int getEmployeeID() {
		return EmployeeID;
	}

	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}

	public int getShipVia() {
		return ShipVia;
	}

	public void setShipVia(int shipVia) {
		ShipVia = shipVia;
	}

	public double getFreight() {
		return Freight;
	}

	public void setFreight(double freight) {
		Freight = freight;
	}

	public String getShipName() {
		return ShipName;
	}

	public void setShipName(String shipName) {
		ShipName = shipName;
	}

	public String getShipAddress() {
		return ShipAddress;
	}

	public void setShipAddress(String shipAddress) {
		ShipAddress = shipAddress;
	}

	public String getShipCity() {
		return ShipCity;
	}

	public void setShipCity(String shipCity) {
		ShipCity = shipCity;
	}

	public String getShipRegion() {
		return ShipRegion;
	}

	public void setShipRegion(String shipRegion) {
		ShipRegion = shipRegion;
	}

	public String getShipPostalCode() {
		return ShipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		ShipPostalCode = shipPostalCode;
	}

	public String getShipCountry() {
		return ShipCountry;
	}

	public void setShipCountry(String shipCountry) {
		ShipCountry = shipCountry;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public void setOrderAttribute(String key, Object value, boolean selectFromOdata, Invoice invoice)
			throws IOException, ODataException {

		if (key.equals("OrderID")) {
			// boolean bir değişken yaratıyoruz
			boolean retrieveFromDB;
			// eğer invoice objemiz doluysa Servisimizi çağırmayacak önceden
			// doldurmuş olduğumuz
			// invoice u set edicek
			if (invoice != null) {
				retrieveFromDB = false;
			} else {
				// Eğer invoice objemiz boşsa Servisimizi çağırıp invoice
				// objesini dolduracak
				retrieveFromDB = true;
			}
			this.setOrderID((int) value);
			if (retrieveFromDB == true) {
				//Okuma işlemini yapıcak olan servisi çağırıyoruz
				ServicesForOrder servicesForInvoice = new ServicesForOrder();
				//Entrylerin bulunacağı bir Hashmap yaratıyoruz
				Map<String, Object> hmapForInvoice = new HashMap<String, Object>();
				//Servisten gelen bütün entrylerin bulunacağı arraylisti oluşturuyoruz
				List<ODataEntry> arrlistForInvoice = new ArrayList<ODataEntry>();
				
				String serviceUrl = "http://services.odata.org/V2/Northwind/Northwind.svc";
				String usedFormat = ServicesForOrder.APPLICATION_JSON;
				Edm edm = servicesForInvoice.readEdm(serviceUrl);
				ODataFeed feedForOrder = servicesForInvoice.readFeed(edm, serviceUrl, usedFormat, "Invoices", null,
						null, (int) value);
				arrlistForInvoice = feedForOrder.getEntries();
				for (int i = 0; i < arrlistForInvoice.size(); i++) {
					hmapForInvoice = arrlistForInvoice.get(i).getProperties();
					Invoice instance = new Invoice();
					for (Entry<String, Object> entry : hmapForInvoice.entrySet()) {
						String key1 = entry.getKey();
						Object value1 = entry.getValue();
						instance.setInvoiceAttribute(key1, value1, selectFromOdata);
					}
					this.setInvoice(invoice);
				}
			} else {
				this.setInvoice(invoice);
			}
		} else if (key.equals("CustomerID")) {
			this.setCustomerID((String) value);
		} else if (key.equals("EmployeeID")) {
			this.setEmployeeID((int) value);
		} else if (key.equals("ShipVia")) {
			this.setShipVia((int) value);
		} else if (key.equals("Freight")) {
			this.setFreight(new Double(value.toString()));
		} else if (key.equals("ShipName")) {
			this.setShipName((String) value);
		} else if (key.equals("ShipAddress")) {
			this.setShipAddress((String) value);
		} else if (key.equals("ShiptCity")) {
			this.setShipCity((String) value);
		} else if (key.equals("ShipRegion")) {
			this.setShipRegion((String) value);
		} else if (key.equals("ShipPostalCode")) {
			this.setShipPostalCode((String) value);
		} else if (key.equals("ShipCountry")) {
			this.setShipCountry((String) value);
		} else if (key.equals("Order_Details")) {
			// Order_Detaildan instance alıyoruz
			Order_Detail orderDetail = new Order_Detail();
			// Servisten dönen OData tipi olarak instanceı alıyoruz
			ODataDeltaFeedImpl orderDetailFeed = (ODataDeltaFeedImpl) value;
			// Propertylerimizi oturtabilmek için HashMap yaratıyoruz
			Map<String, Object> orderDetailProperties = new HashMap<String, Object>();
			// Entrylerimizin bulunacağı bir liste oluşturuyoruz
			List<ODataEntry> orderDetailEntry = orderDetailFeed.getEntries();
			// Order_Details objemiz Order Objemizle 1'e (n) ilişkisi olduğu
			// için
			// OrderDetails tipinde bir arraylist yaratıyoruz
			this.orderDetails = new ArrayList<Order_Detail>();

			for (int i = 0; i < orderDetailEntry.size(); i++) {
				orderDetailProperties = orderDetailEntry.get(i).getProperties();
				for (Entry<String, Object> entry : orderDetailProperties.entrySet()) {
					// Objemizi doldurduktan sonra daha nce oluşturduğumuz
					// arraylistimizin içine atıyoruz
					orderDetail.setOrderDetailsAttribute(entry.getKey(), entry.getValue());
					this.orderDetails.add(orderDetail);
				}
			}

		}
	}

}
