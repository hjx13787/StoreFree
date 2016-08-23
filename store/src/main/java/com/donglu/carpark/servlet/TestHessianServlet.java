package com.donglu.carpark.servlet;

import com.alibaba.fastjson.JSONObject;
import com.donglu.carpark.SessionCache;
import com.donglu.carpark.model.storemodel.FreeInfo;
import com.donglu.carpark.model.storemodel.Info;
import com.donglu.carpark.model.storemodel.LoginInfo;
import com.donglu.carpark.model.storemodel.SearchCarInInfo;
import com.donglu.carpark.model.storemodel.SearchFreeInfo;
import com.donglu.carpark.model.storemodel.SearchPayInfo;
import com.donglu.carpark.model.storemodel.SessionInfo;
import com.donglu.carpark.model.storemodel.TreeInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 固定指向凯龙酒店服务器的servlet
 * @author Michael
 *
 */
public class TestHessianServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3659485366918503478L;
	final static Logger LOGGER = LoggerFactory.getLogger(KailongStoreServlet.class);
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setMaxInactiveInterval(999999);
		String method = req.getParameter("method");
		LOGGER.debug("dispatcher method:{}",method);

		if (method==null) {
			return;
		}
		if (method.equals("tree")) {
			tree(req,resp);
		}else if (method.equals("login")) {
			login(req,resp);
		}else if (method.equals("add")) {
			add(req,resp);
		}else if (method.equals("getFreeById")) {
			getFreeById(req,resp);
		}
		else if (method.equals("edit")) {
			edit(req,resp);
		}
		else if (method.equals("searchFree")) {
			searchFree(req,resp);
		}
		else if (method.equals("searchPay")) {
			searchPay(req,resp);
		}else if(method.equals("searchCarIn")){
			searchCarIn(req, resp);
		}else if(method.equals("getInOutById")){
			getInOutById(req, resp);
		}
	}
	

	private void getInOutById(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String id = req.getParameter("id");
			FreeInfo info=new FreeInfo();
			info.setId(Long.valueOf(id));
			info.setLoginName(SessionCache.get(req).getLoginName());
			info.setUseType(4);
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void searchCarIn(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String plateNO = req.getParameter("searchPlateNO");
			String page = req.getParameter("page");
			String rows = req.getParameter("rows");
			SearchCarInInfo info=new SearchCarInInfo();
			info.setLoginName(SessionCache.get(req).getLoginName());
			info.setPlateNO(plateNO);
			info.setPage(Integer.valueOf(page));
			info.setRows(Integer.valueOf(rows));
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void getFreeById(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		String storeName = SessionCache.get(req).getStoreName();
		FreeInfo info=new FreeInfo();
		info.setStoreName(storeName);
		info.setId(Long.valueOf(id));
		info.setLoginName(SessionCache.get(req).getLoginName());
		info.setUseType(3);
		try {
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void searchPay(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<String, String[]> map = req.getParameterMap();
			String storeName = SessionCache.get(req).getStoreName();
			String[] operaNames = map.get("searchOperaName");
			String[] startTimes = map.get("searchStartTime");
			String[] endTimes = map.get("searchEndTime");
			String operaName=operaNames==null?null:operaNames[0];
			String start = startTimes==null?null:startTimes[0];
			String end = endTimes==null?null:endTimes[0];
			SearchPayInfo info=new SearchPayInfo();
			info.setOperaName(operaName);
			info.setStoreName(storeName);
			info.setLoginName(SessionCache.get(req).getLoginName());
			try {
				info.setStart(df.parse(start));
			} catch (Exception e) {
				
			}
			try {
				info.setEnd(df.parse(end));
			} catch (Exception e) {
				
			}
			String page = map.get("page")[0];
			String rows = map.get("rows")[0];
			info.setPage(Integer.valueOf(page));
			info.setRows(Integer.valueOf(rows));
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	private void searchFree(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<String, String[]> map = req.getParameterMap();
			String[] plateNOs = map.get("searchPlateNO");
			String[] useds = map.get("searchUsed");
			String[] startTimes = map.get("searchStartTime");
			String[] endTimes = map.get("searchEndTime");
			String start = startTimes==null?null:startTimes[0];
			String end = endTimes==null?null:endTimes[0];
			String plateNO = plateNOs==null?null:plateNOs[0];
			String used = useds==null?null:useds[0];
			String storeName = SessionCache.get(req).getStoreName();
			SearchFreeInfo info=new SearchFreeInfo();
			info.setStoreName(storeName);
			info.setLoginName(SessionCache.get(req).getLoginName());
			if(plateNO==null||plateNO.equals("")){
				plateNO="";
			}else{
				info.setPlateNO(plateNO);
			}
			if(used==null||used.equals("")){
				used="";
			}else{
				info.setUsed(used);
			}
			
			if(start==null||start.equals("")){
				start="";
			}else{
				try {
					info.setStart(df.parse(start));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(end==null||end.equals("")){
				end="";
			}else{
				try {
					info.setEnd(df.parse(end));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			String page = map.get("page")[0];
			String rows = map.get("rows")[0];
			info.setPage(Integer.valueOf(page));
			info.setRows(Integer.valueOf(rows));
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void edit(HttpServletRequest req, HttpServletResponse resp) {
		
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Map<String, String[]> map = req.getParameterMap();
			String storeName= SessionCache.get(req).getStoreName();
			String id=map.get("id")==null?null:map.get("id")[0];
			String plateNo = map.get("plateNo")==null?null:map.get("plateNo")[0];
			String hour = map.get("freehours")==null?null:map.get("freehours")[0];
			String money=map.get("freeMoney")==null?null:map.get("freeMoney")[0];
			String freeType = map.get("freeType") == null ? null : map.get("freeType")[0];
			FreeInfo info=new FreeInfo();
			info.setPlateNo(plateNo);
			info.setLoginName(SessionCache.get(req).getLoginName());
			info.setStoreName(storeName);
			info.setUseType(1);
			info.setFreeType(freeType);
			if(id==null||id.equals("")){
			}else{
				info.setId(Long.valueOf(id));
			}
			if(hour==null||hour.equals("")){
				hour="";
			}else{
				info.setHour(Float.valueOf(hour));
			}
			
			if(money==null||money.equals("")){
				money="";
			}else{
				info.setMoney(Float.valueOf(money));
			}
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void tree(HttpServletRequest req, HttpServletResponse resp) {
		try {
			SessionInfo sessionInfo = SessionCache.get(req);
			String loginName = sessionInfo.getLoginName();
			TreeInfo info=new TreeInfo();
			info.setLoginName(loginName);
			String upload = sendInfo(info).getMsg();
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private Info sendInfo(Info info) {
		return StoreHessianServlet.setInfo(info);
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = req.getParameter("data.loginname");
		String pwd = req.getParameter("data.pwd");
		LoginInfo info=new LoginInfo();
		info.setLoginName(name);;
		info.setName(name);
		info.setPwd(pwd);
		
		String upload = sendInfo(info).getMsg();
		JSONObject object=JSONObject.parseObject(upload);
		
		String success=object.getString("success");
		if(success != null && success.equalsIgnoreCase("true")){
			String storeName=object.getJSONObject("obj").getString("storeName");
			String userName=object.getJSONObject("obj").getString("userName");
			SessionInfo sessionInfo = new SessionInfo(name,pwd,storeName,userName);
			SessionCache.put(req.getSession().getId(),sessionInfo);
			resp.addCookie(new Cookie("sessionId",req.getSession().getId()));
			req.getSession().setAttribute("sessionInfo", sessionInfo);
		}
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
