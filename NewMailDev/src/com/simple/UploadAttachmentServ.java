package com.simple;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.UploadTemplateServer;

/**
 * Servlet implementation class UploadAttachmentServ
 */
@WebServlet("/UploadAttachmentServ")
public class UploadAttachmentServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       //http://35.243.163.58:8080/NewMail/UploadAttachmentServ
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadAttachmentServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out=response.getWriter();
		out.print("upload");
		try {
		UploadTemplateServer ftp = new UploadTemplateServer("34.74.142.84",22,"root","B!zL3M786");
	      String a=ftp.connect();
out.print(a);///
		 String servp="/usr/local/tomcat8/apache-tomcat-8.5.35/webapps/ROOT/scorpioexcel/";        
		 String rp="/home/ubuntu/apache-tomcat-8.5.31/webapps/ROOT/Attachment/";
		ftp.download(servp+"TonnageData.xls", rp);
		out.print("done");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
