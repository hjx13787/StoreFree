package com.donglu.carpark.servlet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.caucho.hessian.server.HessianServlet;
import com.donglu.carpark.model.storemodel.Info;
import com.donglu.carpark.service.StoreHessianServiceI;

public class StoreHessianServlet extends HessianServlet implements StoreHessianServiceI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2447961339721680438L;
	public interface ObjectInvoke{
		public void invoke(Info info);
	}
	static long timeout=5000;
//	private Lock lock = new ReentrantLock();// 锁对象
	static Map<String,Info> mapServerInfo=new HashMap<>();
	static Map<String,Info> mapClientInfo=new HashMap<>();
	ObjectInvoke invoke;
	{
		invoke = new ObjectInvoke() {
			@Override
			public void invoke(Info info) {
				mapClientInfo.put(info.toString(), info);
			}
		};
	}
	/**
	 * 
	 */
	public static final Set<Object> sets=new HashSet<>();
	@Override
	public void send(Info info) {
		if (info!=null) {
			System.out.println("客户端返回数据"+info);
			invoke.invoke(info);
		}
	}
	@Override
	public Info keepLink() {
		long start = System.currentTimeMillis();
		System.out.println("客户端建立连接");
		while (true) {
			if (mapServerInfo.keySet().size()>0) {
				for (String c : mapServerInfo.keySet()) {
					System.out.println("服务器返回数据："+c);
					return mapServerInfo.remove(c);
				}
			}else{
				if (System.currentTimeMillis()-start>60000) {
					System.out.println("超时返回空");
					return null;
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Info setInfo(Info info){
		long start = System.currentTimeMillis();
		String key = info.toString();
		mapServerInfo.put(key, info);
		System.out.println("服务器获取消息====="+key);
		while(mapClientInfo.get(key)==null){
			if (System.currentTimeMillis()-start>timeout) {
				return null;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mapServerInfo.remove(key);
		return mapClientInfo.remove(key);
	}

	
}
