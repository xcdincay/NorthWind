package com.ittr.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;

import com.google.gson.Gson;
import com.ittr.classes.Invoice;
import com.ittr.classes.Product;
import com.ittr.services.ServicesForInvoice;
import com.ittr.services.ServicesForOrder;

public class Main {
	public static void main(String[] args) throws MalformedURLException, IOException, ODataException {

		//Okuma işlemini yapıcak olan servisi çağırıyoruz
		ServicesForInvoice servicesForInvoice = new ServicesForInvoice();
		
		//Entrylerin bulunacağı bir Hashmap yaratıyoruz
		Map<String, Object> hmapForInvoice = new HashMap<String, Object>();
		
		//Servisten gelen bütün entrylerin bulunacağı arraylisti oluşturuyoruz
		List<ODataEntry> arrlistForInvoice = new ArrayList<ODataEntry>();
		
		//İşlemler bittikten sonra oluşturduğumuz objeleri beraber tutabilmek için
		//Obje tipimizde bir arraylist oluşturuyoruz
		List<Invoice> InvoiceList = new ArrayList<Invoice>();
		
		//Base Urlimi veriyoruz
		String serviceUrl = "http://services.odata.org/V2/Northwind/Northwind.svc";
		
		//Kullanmak istediğimiz formatı belirliyoruz
		String usedFormat = ServicesForInvoice.APPLICATION_JSON;
		//Verdiğimiz Url'e bağlanmak için servisimizden readEdm i çağırıyoruz
		Edm edm = servicesForInvoice.readEdm(serviceUrl);
		//Databaseden veri seti geleceği için ODataFeed ile , EntitySetimizin ismin vererek verileri okuyoruz
		ODataFeed feedForInvoice = servicesForInvoice.readFeed(edm, serviceUrl, usedFormat, "Invoices");
		arrlistForInvoice = feedForInvoice.getEntries();
		//Aldığımız entryleri tek tek döngüyle çağırıyoruz
		for (int i = 0; i < arrlistForInvoice.size(); i++) {
			//Her yeni entry için bir instance alıyoruz
			Invoice Invoice = new Invoice();
			//Gelen entrynin alanlarını okuyarak HashMapimizin içine atıyoruz
			hmapForInvoice = arrlistForInvoice.get(i).getProperties();
			//HashMapin içindeki Propertyleri tek tek dönüyoruz
			for (Entry<String, Object> entry : hmapForInvoice.entrySet()) {
				//Gelen propertylerin sol kısmını "Key" olarak alıyoruz
				String key = entry.getKey();
				// Sağ tarafını Value diye alıyoruz
				Object value = entry.getValue();
				// Bu değerleri aldıktan sonra Invoice classının içinde yarattığımız methodu çağırıyoruz ve Set ediyoruz
				Invoice.setInvoiceAttribute(key, value,true);
			}
			//Oluşan objeyi daha önce yarattığımız listemize ekliyoruz
			InvoiceList.add(Invoice);
		}

	}

}
