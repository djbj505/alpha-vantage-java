package com.avj.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.avj.exceptions.BadHTTPException;
import com.avj.exceptions.InvalidSymbolException;
import com.avj.utils.operations.AlphaDatatypes;
import com.avj.utils.operations.AlphaTimeSeries;

public class AlphaClient {
	private static int MAX_TICKER_CHAR = 4;
	private static final int BUFFER_SIZE = 4096;
	
	private String creds;
	private String function;
	private String datatype;
	private String endpoint;
	private String symbol;
	private HttpURLConnection conn;
	private String timeSeries;
	
	public AlphaClient(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public AlphaClient withCredentials(String credentials) {
		setCreds(credentials);
		return this;
	}
	
	public AlphaClient withSymbol(String symbol) {
		setSymbol(symbol);
		return this;
	}

	private void setSymbol(String symbol) {
		if(symbol.length() > MAX_TICKER_CHAR) {
			throw new InvalidSymbolException("The ticker " + symbol + " has too many characters!");
		}
		this.symbol = "symbol=" + symbol;
	}

	private void setCreds(String credentials) {
		this.creds = "apikey=" + credentials;		
	}
	
	public AlphaClient withFunction(AlphaTimeSeries function) {
		setFunction(function);
		return this;
	}

	private void setFunction(AlphaTimeSeries function) {
		this.function = "function=" + function.toString();
	}
	
	public AlphaClient withDatatype(AlphaDatatypes datatype) {
		setDatatype(datatype);
		return this;
	}

	private void setDatatype(AlphaDatatypes datatype) {
		this.datatype = "datatype=" + datatype.toString();
	}
	
	public String toString() {
		if(this.timeSeries == null) {
			return this.endpoint + "&" + this.function + "&" + this.symbol + "&" + this.datatype + "&" + this.creds;
		} else {
//			return this.endpoint + "&" + this.function + "&" + this.symbol + "&" + this.datatype + "&" + this.creds;
			//TODO: Inlude time series data. 
			return "";
		}
	}

	//TODO: build in functionality for .json files.
	public void executeRequest(String fileName, String outDir) throws IOException, BadHTTPException {
		URL url = new URL(this.toString());
		this.conn = (HttpURLConnection) url.openConnection();
		int responseCode = this.conn.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK) {
            // opens input stream from the HTTP connection
            InputStream inputStream = this.conn.getInputStream();
            
	        File f = new File(outDir);
	        f.mkdirs();
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(outDir + "/" + fileName);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
		} else {
			throw new BadHTTPException("HTTP request failed! " + responseCode);
		}
	}

	public AlphaClient withTimeSeries(AlphaTimeSeries ats) {
		// TODO Auto-generated method stub
		return this;
	}
}
