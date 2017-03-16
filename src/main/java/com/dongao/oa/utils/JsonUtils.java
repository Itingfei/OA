package com.dongao.oa.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class JsonUtils {
	public static String getString(String content){
		try {
			content = URLDecoder.decode(content, "UTF-8");
			if (content.contains("="))
				content = content.substring(0, content.length() - 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {
		T bean = JSON.parseObject(jsonString, beanCalss);
		return bean;
	}

	/**
	 * 将java对象转换成json字符串
	 *
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean) {
		String json = JSONObject.toJSONString(bean);
		return json;
	}
	public static JSONObject toJsonObject(String content) {
		return JSON.parseObject(content);
	}
//	public static JSONArray arrayToJsonObject(String content) {
//		return JSON.parseArray(content);
//	}
}