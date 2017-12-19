package com.avj.utils;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class AlphaVantageRequest implements Closeable {
	
	private Properties props;
	private String apiKey;
	
	private CloseableHttpResponse response;
	private CloseableHttpClient httpclient;
	
	public AlphaVantageRequest() throws IOException {
		this.loadProps();
		this.httpclient  = HttpClients.createDefault();
	}
	
	public void getDailyDataAsCsv(String dir) {
		String query = AlphaEndpoints.TIME_SERIES_DAILY + "&symbol=MSFT&apikey=" + this.apiKey + AlphaEndpoints.CSV_COMMAND;
		try {
			makeRequest(query);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makeRequest(String requestUrl) throws ClientProtocolException, IOException {
		HttpGet getClient = new HttpGet(requestUrl);
		System.out.println(requestUrl);
		this.response = this.httpclient.execute(getClient);
		System.out.println("Done!");
		System.out.println(this.response.getStatusLine());
		
	}

	public void close() throws IOException {
		if(this.response != null) {
			this.response.close();
		}
		if(this.httpclient != null) {
			this.httpclient.close();
		}	
	}
	
	/**
	 * Loads properties to be used. 
	 * @throws IOException	If the properties file cannot be loaded. 
	 */
	private void loadProps() throws IOException {
		props = new Properties();
		InputStream is = new FileInputStream("./resources/com/avj/resources/config.properties");
		props.load(is);
		this.apiKey = props.getProperty("AV_API_KEY");
	}

}
