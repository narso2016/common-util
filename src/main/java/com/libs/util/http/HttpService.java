package com.libs.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class HttpService {
	public static String getRestConfigVal(String ipAddress, String port, String key, String rest, String requestMethod) {
		String ret = null;
		try {
			URL url = new URL("http://"+ipAddress+":"+port+"/"+rest+"/"+key);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod.toUpperCase());
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				ret = output;
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;

	}
	
	public static Map<String, Object>  getDataRestAPI(String uri, String requestMethod, Object key) {
		Map<String, Object> retResponse = new HashMap<String, Object>();
		String ret = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod.toUpperCase());
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			String jsonData = new Gson().toJson(key);
			byte[] outputInBytes = jsonData.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
			
			if (conn.getResponseCode() != 200) {
				retResponse.put("code",  conn.getResponseCode());
				retResponse.put("message", conn.getResponseMessage());
				return retResponse;
			}else {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output = "";
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
//					System.out.println(output);
					ret = output;
				}
				
				conn.disconnect();
				
				retResponse.put("code", 200);
				retResponse.put("data", ret);
				return retResponse;
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			retResponse.put("code", 500);
			retResponse.put("message", e.getMessage());
			return retResponse;
		} catch (IOException e) {
			e.printStackTrace();
			retResponse.put("code", 500);
			retResponse.put("message", e.getMessage());
			return retResponse;
		}


	}
	
	public static Map<String, Object>  getRestWithHeaderAuth(String uri, String requestMethod, Object key, String authorization) {
		Map<String, Object> retResponse = new HashMap<String, Object>();
		String ret = null;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod.toUpperCase());
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("Authorization", authorization);
			String jsonData = new Gson().toJson(key);
			byte[] outputInBytes = jsonData.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
			
			if (conn.getResponseCode() != 200) {
				retResponse.put("code",  conn.getResponseCode());
				retResponse.put("message", conn.getResponseMessage());
				return retResponse;
			}else {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					ret = output;
				}
				
				conn.disconnect();
				
				retResponse.put("code", 200);
				retResponse.put("data", ret);
				return retResponse;
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			retResponse.put("code", 500);
			retResponse.put("message", e.getMessage());
			return retResponse;
		} catch (IOException e) {
			e.printStackTrace();
			retResponse.put("code", 500);
			retResponse.put("message", e.getMessage());
			return retResponse;
		}


	}
	
	
	public static String getDataFromService(String ipAddress, String port, String key, String rest, String requestMethod) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		String json = getRestConfigVal(ipAddress, port, key, rest, requestMethod);
		@SuppressWarnings("unchecked")
		Map<String, Object> o = mapper.readValue(json, HashMap.class);
		String ret = null;
		if(o.containsKey("data")){
			ret = (String)o.get("data");
		}
		return ret;
	}
}
