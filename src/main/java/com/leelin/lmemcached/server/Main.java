package com.leelin.lmemcached.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leelin.lmemcached.po.ValueObject;

/**
 * lmemached  entrance , when server startup, program run.
 * @author lilin
 * @since 2016.03.21
 * @version 0.0.1
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final int PORT = 9000;
	private static final int SIZE = 1 << 15;
	
	private static final ConcurrentHashMap<String, ValueObject> chm = new ConcurrentHashMap<String, ValueObject>();
	
	private static final String FORMAT = "yyyy-MM-dd hh:mm:ss";
	private static final String PUT = "put";
	private static final String GET = "get";
	
	
	public static void main(String [] args){
		try {
			
			ServerSocket serverSocket = new ServerSocket(PORT);
			while(true){
				Socket s = serverSocket.accept();
				
				StringBuilder sb = new StringBuilder();
				try{
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					int len = 0;
					char[] chars = new char[SIZE];
				    while ((len =in.read(chars))!=-1){  
				    	sb.append(new String(chars, 0, len)); 
						LOGGER.info(sb.toString());
						break;
					}
					
				    String response = parseRequest(sb.toString());
				    successResponse(s, response!=null?response:"");
				    
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
				}finally{
					s.close();
					
				}
			}
			
			
			
//			Reader reader = new InputStreamReader(s.getInputStream());
//			char[] chars = new char[SIZE];
//			
//			StringBuilder sb = new StringBuilder();  
//			int len = 0;
//		    while (len != -1){  
//		    	len =reader.read(chars);
//		        sb.append(new String(chars, 0, len));  
//			}  
		    
		   
			
		} catch (UnknownHostException e) {
			if(LOGGER.isDebugEnabled()){
				LOGGER.info("Server startup error, UnknownHostException happen!");
			}
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			if(LOGGER.isDebugEnabled()){
				LOGGER.info("Server startup error, IOException happen!");
			}
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private static void successResponse(Socket s, String response){
		 Writer writer;
		try {
			writer = new OutputStreamWriter(s.getOutputStream());
			writer.write(response);
			writer.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		   
	}
	//?method=put&key=1&value=a&time=60
	private static String parseRequest(String req){
		int i1 = req.indexOf("?");
		String params = req.substring(i1+1);
		String[] params_array = params.split("&");
		
		if(params_array.length < 2){
			return null;
		}
		if(params_array.length>=2){
			String method = params_array[0].split("=")[1];
			String key = params_array[1].split("=")[1];
			
			if(PUT.equals(method)){
				String value = params_array[2].split("=")[1];
				String time = params_array[3].split("=")[1];
				putAndStorage(key, value, Integer.valueOf(time));
				return "true";
			}else if(GET.equals(method))
				return takeAndGetValue(key);
			else{
				throw new IllegalArgumentException("this method params no support");
			}
		}
		return null;
		
	}
	
	//parse String src, set <tt>key<tt>, <tt>value<tt>
	private static void putAndStorage(String key, String value, int second){
		
		ValueObject valueObject;
	  
			valueObject = new ValueObject(value, second);
			chm.put(key, valueObject);
		
			if(LOGGER.isDebugEnabled()){
				LOGGER.info("the parse of Value error");
			}
			LOGGER.error("m");
		
	}
	
	private static String takeAndGetValue(String key){
		ValueObject valueObject = chm.get(key);
		if(valueObject!=null){
			Date exp = valueObject.getExp();
			long currentTime = System.currentTimeMillis();
			long tempTime = 0;
			if(exp != null)
				tempTime= exp.getTime();
			if(valueObject.getExpLong()!=0)
				tempTime = valueObject.getExpLong();
			if(tempTime>=currentTime)
				return valueObject.getValue();
			return "";
		}else{
			return "No find the value about key in lmemcached";
		}
	}
	

}
