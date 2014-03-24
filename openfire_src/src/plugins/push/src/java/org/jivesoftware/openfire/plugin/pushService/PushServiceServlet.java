package org.jivesoftware.openfire.plugin.pushService;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.admin.AuthCheckFilter;
import org.jivesoftware.openfire.plugin.PushServicePlugin;

public class PushServiceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String SERVICE_NAME = "push/pushservlet";
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		AuthCheckFilter.addExclude(SERVICE_NAME);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		AuthCheckFilter.removeExclude(SERVICE_NAME); 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String to="test5";
//		String from="admin";
//		String body="this is 订单";
//		String subject="admin";
		
		String to=req.getParameter("username");
		String from="admin";
		String body=req.getParameter("message");
		String type=req.getParameter("type");
		PushServicePlugin.pushMessage(to, from, body, type);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
