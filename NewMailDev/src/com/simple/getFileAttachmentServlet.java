package com.simple;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.service.UploadTemplateServer;

/**
 * Servlet implementation class getFileAttachmentServlet
 */
@WebServlet("/getFileAttachServlet")
public class getFileAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
	static ResourceBundle bundleststic = ResourceBundle.getBundle("config");
	
	String status="";

    /**
     * Default constructor. 
     */
    public getFileAttachmentServlet() {
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
		/*  {"to":["tejal.jabade@bizlem.com"],"fromId":"doctigertest@gmail.com","fromPass":"doctiger@123","subject":"Testing12 Send Mail From MailTemlate","body":
"<p>Hello  Tejal ,<\/p>\n\n<p>How are you?<\/p>\n\n <p><strong>This is test mail sent from DocTiger.<\/strong><\/p>\n\n <p><u>hiiiiiiiiii<\/u<\/p>\n\n <p> 1 <\/p>\n\n <p>Thanks<\/p>\n\n<p>&nbsp;<\/p>\n","cc":[ "anagha.rane@bizlem.com"],"bcc":["tejal.jabade@bizlem.com"],"attachments":[],"attachmentPath":""}*/
//		String to="";
		String subject="";
		String body="";
	
		String fromId="";
		String fromPass="";
		String attachmentPath="";
		
		String[] to= {};
		String[] cc={};
		String[] bcc={};
		String[] attachments={};
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
			
			//save attachment
			try {
				if(obj.has("attachmentScorpio")) {
					UploadTemplateServer ftp = new UploadTemplateServer(bundleststic.getString("ScorpioIp"),22,bundleststic.getString("Scorpiousername"),bundleststic.getString("Scorpiopass"));
				      String a=ftp.connect();
				JSONArray jsc=obj.getJSONArray("attachmentScorpio");
				
					for(int i=0;i<jsc.length();i++) {
						try {
							String serpath="";
							String fileurl = jsc.getString(i);
							String filename = "";
							if (fileurl != null && fileurl != "") {
								int o = fileurl.lastIndexOf("/");
								filename = fileurl.substring(o + 1, fileurl.length());
								 serpath= fileurl.substring(0, o+1);
					        out.println("serpath: " +serpath);
					        out.println("filename: " +filename);
							}
			
							out.print(a);
//				 String servp="/usr/local/tomcat8/apache-tomcat-8.5.35/webapps/ROOT/scorpioexcel/";        
//				 String rp="D:\\DOCTIGER114IPProject\\testing docx\\reports\\";
				String pth=ftp.download(serpath+filename, bundleststic.getString("GenAttachmentpath"));//bundleststic.getString("GenAttachmentpath"));
				out.print("resp download "+pth);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
				
//			if(obj.has("attachmenturl")) {
//			JSONArray urlarr = obj.getJSONArray("attachmenturl");
//			SaveAttachmentFromURL sta = new SaveAttachmentFromURL();
//			String savepath = bundleststic.getString("DocGenServerPDFFilePath");
//
//			String resp = sta.SaveFile(urlarr, savepath);
//			out.println(resp);
//			}
			}catch (Exception e) {
				// TODO: handle exception
				e.getStackTrace();
			}
//			out.println("obj: "+obj);
			
			
			
			//to= obj.getString("to");
			subject= obj.getString("subject");
			body= obj.getString("body");
			fromId= obj.getString("fromId");
			fromPass= obj.getString("fromPass");
			attachmentPath=obj.getString("attachmentPath");
			JSONArray toarr=obj.getJSONArray("to");
			JSONArray ccarr=obj.getJSONArray("cc");
			JSONArray bccarr=obj.getJSONArray("bcc");
			JSONArray attarr=obj.getJSONArray("attachments");
			if(toarr.length()>0) {
			to=new String[toarr.length()];
			for(int i=0;i<toarr.length();i++) {
			to[i]=toarr.getString(i);
			}
			}
			if(ccarr.length()>0) {
			cc=new String[ccarr.length()];
			for(int i=0;i<ccarr.length();i++) {
				cc[i]=ccarr.getString(i);
				}
			
			}
			if(bccarr.length()>0) {
		    bcc=new String[bccarr.length()];
			for(int i=0;i<bccarr.length();i++) {
				bcc[i]=bccarr.getString(i);
				}
			
			}
			if(attarr.length()>0) {
		    attachments=new String[attarr.length()];
			for(int i=0;i<attarr.length();i++) {
				attachments[i]=attarr.getString(i);
				}
			}
		
			status=	new SendMailCCAndBccAttachment().sendFromGMail(fromId,fromPass,to, cc, bcc, subject, body,  attachments,attachmentPath);
			
			out.println("status: "+status);
			
//			if(obj.has("attachFilePath")) {
//				attachFilePath= obj.getString("attachFilePath");
//				status= new SendAttachmentInEmail().sendMail(to, subject, body, attachFilePath, fromId, fromPass, response);
//				
//				out.println("status: "+status);
//			}else {
//				status= new SendAttachmentInEmail().sendMail(to, subject, body, fromId, fromPass, response);
//				
//				out.println("status: "+status);
//			}
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
