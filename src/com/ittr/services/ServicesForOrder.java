package com.ittr.services;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
    

public class ServicesForOrder {
	
	    public static final String HTTP_METHOD_PUT = "PUT";
	    public static final String HTTP_METHOD_POST = "POST";
	    public static final String HTTP_METHOD_GET = "GET";
	    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	    public static final String HTTP_HEADER_ACCEPT = "Accept";
	    public static final String APPLICATION_JSON = "application/json";
	    public static final String APPLICATION_XML = "application/xml";
	    public static final String APPLICATION_ATOM_XML = "application/atom+xml";
	    public static final String METADATA = "$metadata";
	    public static final String INDEX = "/index.jsp";
	    public static final String SEPARATOR = "/";
	    public static final boolean PRINT_RAW_CONTENT = true;
	        public Edm readEdm(String serviceUrl) throws IOException, ODataException {
	            InputStream content = execute(serviceUrl + SEPARATOR + METADATA, APPLICATION_XML, HTTP_METHOD_GET);
	            return EntityProvider.readMetadata(content, false);
	        }
	        public ODataFeed readFeed(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue, String keyValue2,int OrderID) 
	                throws IOException, ODataException {
	            EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
	            String absolutUri = createUri(serviceUri, entitySetName, keyValue, keyValue2,OrderID);
	            InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);
	            return EntityProvider.readFeed(contentType, entityContainer.getEntitySet(entitySetName), content,
	                    EntityProviderReadProperties.init().build());
	        }
	    
	        public ODataEntry readEntry(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue,int OrderID)
	                throws IOException, ODataException {
	            return readEntry(edm, serviceUri, contentType, entitySetName, keyValue, null,OrderID);
	        }
	        public ODataEntry readEntry(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue,
	                String expandRelationName,int OrderID) throws IOException, ODataException {
	           
	            EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
	            String absolutUri = createUri(serviceUri, entitySetName, keyValue, expandRelationName,OrderID);
	            InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);
	            return EntityProvider.readEntry(contentType, entityContainer.getEntitySet(entitySetName), content,
	                    EntityProviderReadProperties.init().build());
	        }
	        private HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod)
	                throws MalformedURLException, IOException {
	            URL url = new URL(absolutUri);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod(httpMethod);
	            connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
	            if (HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
	                connection.setDoOutput(true);
	                connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
	            }
	            return connection;
	        }
	        private HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
	            HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
	            if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
	                throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " "
	                        + httpStatusCode.toString());
	            }
	            return httpStatusCode;
	        }
	        private InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
	            HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);
	            connection.connect();
	            checkStatus(connection);
	            InputStream content = connection.getInputStream();
	           
	            return content;
	        }
	        private String createUri(String serviceUri, String entitySetName, String id, String keyValue2,int OrderID) {
	            return createUri(serviceUri, entitySetName, id, null, keyValue2,OrderID);
	        }
	        
	        private String createUri(String serviceUri, String entitySetName, String expand,  String id, String keyValue2,int OrderID) {
	            final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
	            if (id != null) {
	                absolutUri.append("(").append(id).append(")");
	            }
	            if(expand == null){
	            	absolutUri.append("?$filter=OrderID%20eq%20").append(OrderID);
	            }
	            if (expand != null) {
	                absolutUri.append("?$expand=").append(expand).append(SEPARATOR).append(keyValue2).append("&$filter=OrderID%20eq%20").append(OrderID);
	            }
	            return absolutUri.toString();
	        }
	}

