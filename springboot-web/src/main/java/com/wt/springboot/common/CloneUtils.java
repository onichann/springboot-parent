package com.wt.springboot.common;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CloneUtils {
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T obj){
		
		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return clonedObj;
	}
	
	public static void main(String[] args) {
		//SerializationUtils.clone()
		//SerializationUtils.serialize()

		List<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(200);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		//放基本类型数据
		map.put("basic", 100);
		//放对象
		map.put("list", list);
		
		HashMap<String,Object> mapNew = new HashMap<String,Object>();
		mapNew.putAll(map);
		
		System.out.println("----数据展示-----");
		System.out.println(map);
		System.out.println(mapNew);
		
		System.out.println("----更改基本类型数据-----");
		map.put("basic", 200);
		System.out.println(map);
		System.out.println(mapNew);
		
		System.out.println("----更改引用类型数据-----");
		list.add(300);
		System.out.println(map);
		System.out.println(mapNew);
		
		
		System.out.println("----使用序列化进行深拷贝-----");
		mapNew = CloneUtils.clone(map);
		list.add(400);
		System.out.println(map);
		System.out.println(mapNew);
		}
}
