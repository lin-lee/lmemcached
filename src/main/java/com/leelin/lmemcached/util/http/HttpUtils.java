package com.leelin.lmemcached.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
	
	
	public static void httpPost(String url, Map<String, String> params){
		HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = requestPost(url, params);
    	HttpResponse httpResponse = null;
    	try {
    		httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException ce) {
			LOGGER.error(ce.getMessage(), ce);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			httpPost.releaseConnection();
		}
    	
    	HttpEntity httpEntity = httpResponse.getEntity();
    	LOGGER.info(httpEntity.toString());
    	
    	
	}
	
	public static void httpGet(String url, Map<String, String> params){
		HttpClient httpClient = new DefaultHttpClient();
    	HttpGet httpGet = requestGet(url);
    	HttpResponse httpResponse = null;
    	try {
    		httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException ce) {
			LOGGER.error(ce.getMessage(), ce);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}finally{
			httpGet.releaseConnection();
		}
    	
    	HttpEntity httpEntity = httpResponse.getEntity();
    	LOGGER.info(httpEntity.toString());
    	
    	
	}
	
	private static HttpPost requestPost(String url, Map<String, String> params){
		 HttpPost post = new HttpPost(url);
		 
		 List<NameValuePair> nameValuePairs = new ArrayList <NameValuePair>();          
         Set<String> keySet = params.keySet();  
         for(String key : keySet) {  
        	 nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));  
         }  

        
		 HttpEntity entity = null;
		 try {
			 entity = new UrlEncodedFormEntity(nameValuePairs);
		 } catch (UnsupportedEncodingException e) {
			 LOGGER.error(e.getMessage(), e);
		 }
		 post.setEntity(entity);
		 return post;
	}
	
	private static HttpGet requestGet(String url){
		 HttpGet get = new HttpGet(url);
		 return get;
	}

}

