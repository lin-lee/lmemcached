package com.leelin.lmemcached.client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leelin.lmemcached.util.socket.SocketUtils;

public class ClientTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);
	
	public static void main(String [] args) throws IOException, InterruptedException{
		//String url = "127.0.0.1:8080?method=put&key=1&value=a&time=60";
		//HttpUtils.httpGet(url, null);
		
		InetAddress addr = InetAddress.getByName("localhost");
		
		
		String s = "?method=put&key=1&value=a&time=3";
		Socket st2 = new Socket(addr, 9000);
		SocketUtils.socketRequest(st2, s);
		
		String s2 = "?method=get&key=1";
		
		Socket st = new Socket(addr, 9000);
		SocketUtils.socketRequest(st,s2);
		
		
		
		
    	
	
	}

}
