package com.lovebear.entity;

public class Result<T> {
	
	public static final int STATE_SUC = 1;
	public static final int STATE_FAIL = 2;
	
	public int state;
	public String descript;
	public T data;
}
