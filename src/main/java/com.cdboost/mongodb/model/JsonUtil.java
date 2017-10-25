package com.cdboost.mongodb.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class JsonUtil {
	public static HashMap<String, Object> toHashMap(Object obj)  
	   {  
	       HashMap<String, Object> data = new HashMap<>();  
	       // 将json字符串转换成jsonObject  
	       JSONObject jsonObject = JSONObject.fromObject(obj);  
	       Iterator<?> it = jsonObject.keys();  
	       // 遍历jsonObject数据，添加到HashMap对象  
	       while (it.hasNext())  
	       {  
	           String key = String.valueOf(it.next());  
	           Object value = jsonObject.get(key);
	           if(value instanceof JSONNull){
				   data.put(key, null);
			   }else {
				   data.put(key, value);
			   }

	       }  
	       return data;  
	   }  
	public static ArrayList<Object> toArrayList(Object obj)  
	   {  
	       ArrayList<Object> data = new ArrayList<>();  
	       // 将json字符串转换成jsonArray  
	       JSONArray jsonArray = JSONArray.fromObject(obj);
	       Iterator<?> it = jsonArray.listIterator();
	       while (it.hasNext())  
	       {
			   Object object = it.next();
				if(!(object instanceof JSONNull)){
					data.add(object);
				}
	       } 
	       return data;
	   }  
}
