package com.leelin.lmemcached.po;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ValueObject is a Object about <tt>Value<tt>
 * exp is the Expirydate of value
 * @author lilin
 * @since 2016.03.21
 * @version 0.0.1
 */
public class ValueObject {
	private static final int UNIT = 1000;
	
	private String value;
	private long exp_long;
	private Date exp;
	
	
	public ValueObject(String value, Date exp){
		this.value = value;
		this.exp = exp;
	}
	
	public ValueObject(String value, long exp_long){
		this.value = value;
		this.exp_long = exp_long*UNIT + System.currentTimeMillis();
	}
	
	public ValueObject(String value, String exp, String format) throws ParseException{
		this.value = value;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		this.exp = sdf.parse(exp);
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getExp() {
		return exp;
	}
	public void setExp(Date exp) {
		this.exp = exp;
	}

	public long getExpLong() {
		return exp_long;
	}

	public void setExpLong(long exp_long) {
		this.exp_long = exp_long;
	}
	
	
	

}
