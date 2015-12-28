package com.donglu.carpark.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StoreServletTest extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String method = req.getParameter("method");
		System.out.println("method========"+method);
		if (method==null) {
			return;
		}
		if (method.equals("tree")) {
			tree(req,resp);
		}else if (method.equals("login")) {
			login(req,resp);
		}else if (method.equals("add")) {
			add(req,resp);
		}
		else if (method.equals("edit")) {
			edit(req,resp);
		}
		else if (method.equals("searchFree")) {
			searchFree(req,resp);
		}
		else if (method.equals("searchPay")) {
			searchPay(req,resp);
		}else if (method.equals("getFreeById")) {
			getFreeById(req,resp);
		}else if(method.equals("searchCarIn")){
			searchCarIn(req, resp);
		}else if(method.equals("getInOutById")){
			getInOutById(req, resp);
		}
		
//		logger.error("没有找到方法为{}的方法",method);
	}
	
	private void getInOutById(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String upload = "";
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void searchCarIn(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String upload = "{\"rows\":[],\"total\":0}";
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void getFreeById(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String upload = "";
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void searchPay(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String upload = "{\"rows\":[],\"total\":0}";
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void searchFree(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String upload = "{\"rows\":[],\"total\":0}";
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void edit(HttpServletRequest req, HttpServletResponse resp) {
		
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String upload = "";
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void tree(HttpServletRequest req, HttpServletResponse resp) {
		try {
			
			String upload = "[{\"attributes\":{\"url\":\"/securityJsp/base/Syuser.jsp\",\"target\":\"\"},\"checked\":false,\"state\":\"open\",\"text\":\"在线优惠\"},{\"attributes\":{\"url\":\"/securityJsp/base/payHistory.jsp\",\"target\":\"\"},\"checked\":false,\"state\":\"open\",\"text\":\"充值记录\"},{\"attributes\":{\"url\":\"/securityJsp/base/SyCar.jsp\",\"target\":\"\"},\"checked\":false,\"state\":\"open\",\"text\":\"场内车辆\"}]";
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String upload = "{\"msg\":\"\",\"obj\":{\"loginName\":\"His3Gw==\",\"storeName\":\"His3Gw==\",\"userName\":\"qqq\"},\"success\":true}";		
		write(resp, upload);
	}

	private void write(HttpServletResponse resp, String upload) throws IOException {
		resp.setContentType("text/html;charset=utf-8");
		if (upload==null||upload.isEmpty()) {
			upload="连接失败";
		}
		resp.getWriter().write(upload);
		resp.getWriter().flush();
		resp.getWriter().close();
	}
	public static Date parse(String dateStr,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
