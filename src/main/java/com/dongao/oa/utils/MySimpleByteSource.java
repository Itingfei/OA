package com.dongao.oa.utils;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

/**
 *  由于SimpleByteSource 没有实现 Serializable接口
 *  在序列化时候会保存，采用继承Serializable 实现序列化 
 */
public class MySimpleByteSource extends SimpleByteSource implements Serializable {

	private static final long serialVersionUID = 1L;

	public MySimpleByteSource(byte[] bytes) {
		super(bytes);
	}

	/**
	 *  由于SimpleByteSource 没有实现 Serializable接口
	 *  在序列化时候会保存，采用继承Serializable 实现序列化 
	 */
	public MySimpleByteSource(String string) {
		super(string);
	}

}