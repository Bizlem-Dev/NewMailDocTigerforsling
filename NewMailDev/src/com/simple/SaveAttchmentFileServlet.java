package com.simple;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Servlet implementation class SaveAttchmentFileServlet
 */
@WebServlet("/SaveAttchmentFileServlet")
public class SaveAttchmentFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ResourceBundle bundle = ResourceBundle.getBundle("config");
	static ResourceBundle bundleststic = ResourceBundle.getBundle("config");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveAttchmentFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("Save Attachment");
		String savepath = bundleststic.getString("DocGenServerPDFFilePath");
//		String savepath = "D:\\DOCTIGER114IPProject\\testing docx\\abhishek\\excel\\new.xls";
//		SaveAttachmentFromURL.trustAllCertificate();
		String resp = "";
		BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();

		while (result != -1) {
			buf.write((byte) result);
			result = bis.read();
		}
		String res = buf.toString("UTF-8");

//		JSONObject obj;
		try {
//			obj = new JSONObject(res);
//		
//		JSONArray urljs = obj.getJSONArray("attachmenturl");

		out.println("urljs.length()= " + res);
//		for (int i = 0; i < urljs.length(); i++) {
			
			String fileurl = res;
			out.println(" fileurl: " + fileurl);
			String filename = "";
			if (fileurl != null && fileurl != "") {
				int o = fileurl.lastIndexOf("/");
				filename = fileurl.substring(o + 1, fileurl.length());
				resp = resp + filename;
				out.println(" filename: " + filename);
			}
	
		  try {
	            CloseableHttpClient client = HttpClientBuilder.create().build();
	            HttpGet request1 = new HttpGet(
	            		fileurl);
	 
	            HttpResponse response1 = client.execute(request1);
	            HttpEntity entity = response1.getEntity();
	 
	            int responseCode = response1.getStatusLine().getStatusCode();
	 
	            out.println("Request Url: " + request1.getURI());
	            out.println("Response Code: " + responseCode);
	 
	            InputStream is = entity.getContent();
	 
	            String filePath = savepath;
	            FileOutputStream fos = new FileOutputStream(new File(filePath+filename));
	 
	            int inByte;
	            while ((inByte = is.read()) != -1) {
	                fos.write(inByte);
	            }
	 
	            is.close();
	            fos.close();
	 
	            client.close();
	            System.out.println("File Download Completed!!!");
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (UnsupportedOperationException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
//		}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			/*BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result = bis.read();

			while (result != -1) {
				buf.write((byte) result);
				result = bis.read();
			}
			String res = buf.toString("UTF-8");

			JSONObject obj = new JSONObject(res);
			JSONArray urljs = obj.getJSONArray("attachmenturl");

			out.println("urljs.length()= " + urljs.length());
			for (int i = 0; i < urljs.length(); i++) {
				try {
//
					String fileurl = urljs.getString(i);
					out.println(" fileurl: " + fileurl);
					String filename = "";
					if (fileurl != null && fileurl != "") {
						int o = fileurl.lastIndexOf("/");
						filename = fileurl.substring(o + 1, fileurl.length());
						resp = resp + filename;
						out.println(" filename: " + filename);
					}
			
					SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
					URL url = new URL(fileurl);
					HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
					conn.setSSLSocketFactory(sslsocketfactory);
					InputStream inputstream = conn.getInputStream();
//					InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
//					BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

//					String string = null;
//					while ((string = bufferedreader.readLine()) != null) {
//					    System.out.println("Received " + string);
//					}
					BufferedInputStream in = new BufferedInputStream(inputstream);
					  FileOutputStream fileOutputStream= new FileOutputStream(savepath + filename) ;
					    byte dataBuffer[] = new byte[1024];
					    int bytesRead;
					    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					        fileOutputStream.write(dataBuffer, 0, bytesRead);
					    }
					out.println("done");
//					saveUrl(filename, fileurl);
//
//					try {
//						URL url = new URL(fileurl);
////					URLConnection conn = url.openConnection();
////					URL url = new URL(fileurl);
//	         		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////					  url = new URL(https_url);
////						HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
////					     conn.getResponseCode();
//						resp = resp + "23";
////					out.println("getResponseCode: " +	conn.getResponseCode());
//
//						String contentType = conn.getContentType();
//						int contentLength = conn.getContentLength();
//						if (contentType.startsWith("text/") || contentLength == -1) {
//							// out.println("This is not a binary file.");
//						}
//						resp = resp + "1";
//						System.out.println("1");
//
////						InputStream raw = conn.getInputStream();
////						InputStream in = new BufferedInputStream(raw);
//						byte[] data = new byte[contentLength];
//					
//						resp = "56";
//						File file = new File(savepath + filename);
//						FileOutputStream streamout = new FileOutputStream(file);
//						streamout.write(data);
//						streamout.close();
////						raw.close();
////						in.close();
//						out.println("file " + i + "saved");
//						resp = resp + i + "done";
//						System.out.println("done ");
//					} catch (Exception e) {
//						// TODO: handle exception
//						resp = resp + "3" + e;
////					resp = e.getMessage();
//						out.println("exc- " + e);
//					}
////				resp =resp+ "Save Successfully";
//				} catch (Exception e) {
//					out.println("exc- " + e);
//				}
//			}
			 
//				String savepath = "D:\\DOCTIGER114IPProject\\testing docx\\abhishek\\excel\\";
//				BufferedInputStream in = null;
//				FileOutputStream fout = null;
				try {
//					in = new BufferedInputStream(new URL(fileurl).openStream());
//					out.println("files saved1"+savepath + filename);
//					File file = new File(savepath + filename);
//					out.println("files saved2");
//					fout = new FileOutputStream(file);
//					out.println("files saved3");
//					final byte data[] = new byte[1024];
//					out.println("files saved4");
//					int count;
//					out.println("files saved5");
//					while ((count = in.read(data, 0, 1024)) != -1) {
//						fout.write(data, 0, count);
//						out.println("files saved6");
//					}
//					BufferedInputStream in = new BufferedInputStream(new URL(fileurl).openStream());
//					  FileOutputStream fileOutputStream= new FileOutputStream(savepath + filename) ;
//					    byte dataBuffer[] = new byte[1024];
//					    int bytesRead;
//					    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//					        fileOutputStream.write(dataBuffer, 0, bytesRead);
//					    }
					out.println("files saved"+i);
				}catch (Exception e) {
					// TODO: handle exception
					out.println("e :: "+e);
				} finally {
					
				}
				}catch (Exception e) {
					// TODO: handle exception
					out.println("e 1:: "+e);
				}
			} */
			out.println("files saved");
		} catch (Exception e) {
			// TODO: handle exception
			out.println("exc- " + e);
		}

		System.out.println("resp- " + resp);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
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
			JSONArray urlarr = obj.getJSONArray("attachmenturl");
			SaveAttachmentFromURL sta = new SaveAttachmentFromURL();
			String savepath = bundleststic.getString("DocGenServerPDFFilePath");

			String resp = sta.SaveFile(urlarr, savepath);
			out.println(resp);

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
	}

}
