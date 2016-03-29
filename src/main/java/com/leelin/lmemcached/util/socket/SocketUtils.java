package com.leelin.lmemcached.util.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketUtils.class);
	

	
	
	public static void socketRequest(Socket socket, String params){
		
		write(socket, params);
		String response = readResponse(socket);
		LOGGER.info("response:" + response);

	}
	
	
	private static void write(Socket s, String params){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			writer.print(params);
			/**
			 * flush is important. if no flush Exception will happen!
			 *  java.net.SocketException: Connection reset
			 */
			writer.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
	}
	
	private static String readResponse(Socket s){
		char[] cs = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			reader.read(cs);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		
		/**
		 * flush is important. if no flush Exception will happen!
		 *  java.net.SocketException: Connection reset
		 */
		return new String(cs);
	}
}
