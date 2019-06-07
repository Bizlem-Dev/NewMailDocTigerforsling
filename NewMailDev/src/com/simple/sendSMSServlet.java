package com.simple;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class getFileAttachmentServlet
 */
@WebServlet("/sendSMSServlet")
public class sendSMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String status="";

    /**
     * Default constructor. 
     */
    public sendSMSServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		PrintWriter out= response.getWriter();
	
		//out.println("In Servlet");
		
		String to="";
		String msg="";
		
		//String maildata= request.getParameter("maildata");
		try {
			BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result = bis.read();

			while (result != -1) {
				buf.write((byte) result);
				result = bis.read();
			}
			String res = buf.toString("UTF-8");

		
			JSONObject obj = new JSONObject(res);
			
			to= obj.getString("to");
			msg= obj.getString("message");
				status= new SendSMSDemo().SendSMS(to,msg);
				
				out.println("status: "+status);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
