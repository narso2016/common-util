package com.libs.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HTTPResponseReader {
	public HTTPResponseReader(){}
	
	public String getResponseString(String requestUri) 
			throws MalformedURLException, IOException {
		StringBuffer result = new StringBuffer();

		Reader resultStream = this.openConnection(requestUri);
		BufferedReader br = new BufferedReader(resultStream);

		String temp = null;
		while ((temp = br.readLine()) != null) {
			result.append(temp);
		}

		br.close();

		return result.toString();
	}
	
	public Reader getResponseStream(String requestUri) throws MalformedURLException, IOException {
		Reader resultStream = null;		
		resultStream = this.openConnection(requestUri);
		return resultStream;
	}
	
	private Reader openConnection(String requestUri) throws IOException {
		if (requestUri == null || "".equals(requestUri))
			throw new IllegalArgumentException("Invalid request URL.");
		
		Reader result = null;
		try {
			URL url = new URL(requestUri);
			URLConnection urlConn = url.openConnection();
			result = new InputStreamReader(urlConn.getInputStream());
		}
		catch (MalformedURLException me) {
			throw me;
		}
		catch (IOException ioe) {
			throw ioe;
		}
		
		return result;
	}
}