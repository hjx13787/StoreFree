package com.donglu.carpark.servlet;

import com.alibaba.fastjson.JSONObject;
import com.donglu.carpark.FileUtils;
import com.donglu.carpark.SessionCache;
import com.donglu.carpark.StrUtils;
import com.donglu.carpark.model.storemodel.SessionInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 固定指向凯龙酒店服务器的servlet
 * @author Michael
 *
 */
public class KailongStoreServlet extends HttpServlet {

	private static final String SERVER_NAME = "serverName";

	final static Logger LOGGER = LoggerFactory.getLogger(KailongStoreServlet.class);

	private String serverName="127.0.0.1";

	
	public KailongStoreServlet() {
		String s = (String) FileUtils.readObject(SERVER_NAME);
		if (s!=null&&!s.isEmpty()) {
			serverName=s;
			System.out.println("serverName========="+s);
		}
	}



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
		}else if(method.equals("updateIp")){
			updateIp(req, resp);
		}

		req.getSession().setAttribute("sessionInfo",SessionCache.get(req));
//		logger.error("没有找到方法为{}的方法",method);
	}
	
	private void updateIp(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String parameter = req.getParameter("updateIp");
			if (parameter!=null&&!parameter.isEmpty()) {
				String ip = StrUtils.decoderBase64String(parameter);
				if (ip!=null) {
					serverName=ip;
					FileUtils.writeObject(SERVER_NAME, serverName);
				}
			}
			System.out.println("serverName========"+serverName);
			write(resp, "true");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getInOutById(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String id = req.getParameter("id");
			id = fomatterStr("id", id);
//			serverName = req.getServerName();
			String actionUrl = "http://" + serverName + ":8899/store/?method=getInOutById" + id;
			String upload = FileuploadSend.upload(actionUrl, null);
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
			plateNO=fomatterStr("searchPlateNO", plateNO);
			page=fomatterStr("page", page);
			rows=fomatterStr("rows", rows);
//			String serverName = req.getServerName();
			String actionUrl = "http://" + serverName + ":8899/store/?method=searchCarIn"+plateNO+""+page+""+rows+"";
			String upload = FileuploadSend.upload(actionUrl,null );
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void getFreeById(HttpServletRequest req, HttpServletResponse resp) {
		String id = req.getParameter("id");
		String storeName = SessionCache.get(req).getStoreName();
		try {
//			String serverName = req.getServerName();
			String actionUrl = "http://" + serverName + ":8899/store/?method=getFreeById&id="+id+"&storeName="+storeName;
			String upload = FileuploadSend.upload(actionUrl,null );
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
			operaName=fomatterStr("searchOperaName",operaName);
			if(start==null||start.equals("")){
				start="";
			}else{
				start=encoder(start);
				start="&searchStartTime="+start;
			}
			if(end==null||end.equals("")){
				end="";
			}else{
				end=encoder(end);
				end="&searchEndTime="+end;
			}
			
			String actionUrl = "http://" + serverName + ":8899/store/?method=searchPay&storeName="+storeName+""+operaName
					+""+start+
					""+end+"&page="+map.get("page")[0]+"&rows="+map.get("rows")[0];
			String upload = FileuploadSend.upload(actionUrl,null );
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private String fomatterStr(String name, String value) throws UnsupportedEncodingException {
		if(value==null||value.equals("")){
			value="";
		}else{
			value=encoder(value);
			value="&"+name+"="+value;
		}
		return value;
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
			if(plateNO==null||plateNO.equals("")){
				plateNO="";
			}else{
				
				plateNO="&searchPlateNO="+plateNO;
			}
			if(used==null||used.equals("")){
				used="";
			}else{
				used=encoder(used);
				used="&searchUsed="+used;
			}
			
			if(start==null||start.equals("")){
				start="";
			}else{
				start=encoder(start);
				start="&searchStartTime="+start;
			}
			if(end==null||end.equals("")){
				end="";
			}else{
				end=encoder(end);
				end="&searchEndTime="+end;
			}
			
			
			String actionUrl = "http://" + serverName + ":8899/store/?method=searchFree&storeName="+storeName+""+plateNO
					+""+used+""+start+""+end+"&page="+map.get("page")[0]+"&rows="+map.get("rows")[0];
			String upload = FileuploadSend.upload(actionUrl,null );
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String encoder(String end) throws UnsupportedEncodingException {
		return StrUtils.encodeBase64String(end.getBytes("utf-8"));
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
			freeType=fomatterStr("freeType", freeType);
			if(id==null||id.equals("")){
				id="";
			}else{
				id="&id="+id;
			}
			if(hour==null||hour.equals("")){
				hour="";
			}else{
				hour="&freehours="+hour;
			}
			
			if(money==null||money.equals("")){
				money="";
			}else{
				money="&freeMoney="+money;
			}
			plateNo=fomatterStr("plateNo", plateNo);
			String actionUrl = "http://" + serverName + ":8899/store/?method=add"+id+"&storeName="+storeName+""+plateNo+""+hour+""+money+freeType;
			String upload = FileuploadSend.upload(actionUrl,null );
			write(resp, upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void tree(HttpServletRequest req, HttpServletResponse resp) {
		try {
			
			String actionUrl = "http://" + serverName + ":8899/store/?method=tree";
			String upload = FileuploadSend.upload(actionUrl,null );
			write(resp, upload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = req.getParameter("data.loginname");
		String pwd = req.getParameter("data.pwd");
		String actionUrl = "http://" + serverName + ":8899/store/?method=login&data.loginname="+name+"&data.pwd="+pwd;
		String upload = FileuploadSend.upload(actionUrl,null );
		JSONObject object=JSONObject.parseObject(upload);
		
		String success=object.getString("success");
		if(success != null && success.equalsIgnoreCase("true")){
			String storeName=object.getJSONObject("obj").getString("storeName");
			String userName=object.getJSONObject("obj").getString("userName");
			
			SessionInfo sessionInfo = new SessionInfo(name,pwd,storeName,userName);
			SessionCache.put(req.getSession().getId(),sessionInfo);
			resp.addCookie(new Cookie("sessionId",req.getSession().getId()));
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
