package com.simple;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.cert.X509Certificate;
//import java.net.URLConnection;
import java.util.ResourceBundle;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;
import org.json.JSONException;

public class SaveAttachmentFromURL {
	ResourceBundle bundle = ResourceBundle.getBundle("config");
	static ResourceBundle bundleststic = ResourceBundle.getBundle("config");

	public static void main(String args[]) throws IOException, JSONException {
		SaveAttachmentFromURL sta = new SaveAttachmentFromURL();
		String fileurl = "https://dev.bizlem.io:8082/scorpioexcel/TonnageData.xls";
//		sta.SaveFile(fileurl) ;
		JSONArray js = new JSONArray();
		/*
		 * ":["https://dev.bizlem.io:8082/scorpioexcel/TonnageData.xls",
		 * "https://dev.bizlem.io:8082/scorpioexcel/SpotData.xls",
		 * "https://dev.bizlem.io:8082/scorpioexcel/TimeCharterReportsData.xls",
		 * "https://dev.bizlem.io:8082/scorpioexcel/BrokerTcRate.xls"]}
		 */
		js.put("https://dev.bizlem.io:8082/scorpioexcel/TonnageData.xls");
		js.put("https://dev.bizlem.io:8082/scorpioexcel/SpotData.xls");
		js.put("https://dev.bizlem.io:8082/scorpioexcel/TimeCharterReportsData.xls");
		js.put("https://dev.bizlem.io:8082/scorpioexcel/BrokerTcRate.xls");
		js.put("https://dev.bizlem.io:8082/FailedMailJson/abc.txt");
		String savepath = "D:\\DOCTIGER114IPProject\\testing docx\\abhishek\\excel\\";
//		sta.SaveFile(js, savepath);
		sta.cert(fileurl,savepath,"azt.xls");
		
		 
	}

	/* get attachment url and save to root */
	public String SaveFile(JSONArray urljs, String savepath) throws IOException, JSONException {
		// String savepath = bundleststic.getString("DocGenServerPDFFilePath");
//		String savepath = "D:\\DOCTIGER114IPProject\\testing docx\\abhishek\\excel\\";

		String resp = "";
		for (int i = 0; i < urljs.length(); i++) {
			try {
				
				String fileurl = urljs.getString(i);
				String filename = "";
				if (fileurl != null && fileurl != "") {
					int o = fileurl.lastIndexOf("/");
					filename = fileurl.substring(o + 1, fileurl.length());
					resp = resp + filename;
//		        out.println("comma separated ooooo: " +generatedfile);
				}
				
				cert(fileurl,savepath,filename);
//				saveUrl(filename, fileurl,  savepath );
//
//				try {
//					URL url = new URL(fileurl);
////					URLConnection conn = url.openConnection();
////					URL url = new URL(fileurl);
////	         		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////					conn.getResponseCode();
////					  url = new URL(https_url);
//					HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//					resp = resp + "23";
//
//					String contentType = conn.getContentType();
//					int contentLength = conn.getContentLength();
//					if (contentType.startsWith("text/") || contentLength == -1) {
//						// out.println("This is not a binary file.");
//					}
//					resp = resp + "contentLength:" + contentLength;
//					System.out.println("1");
//					InputStream raw = conn.getInputStream();
//					InputStream in = new BufferedInputStream(raw);
//					byte[] data = new byte[contentLength];
//				
//					resp = "56";
//					File file = new File(savepath + filename);
//					FileOutputStream streamout = new FileOutputStream(file);
////					FileOutputStream streamout = new FileOutputStream(savepath + filename);
//					streamout.write(data);
//					streamout.close();
//					raw.close();
//					in.close();
//					resp = resp + i + "done";
//					System.out.println("done ");
//				} catch (Exception e) {
//					// TODO: handle exception
//					resp = resp + "3" + e;
////					resp = e.getMessage();
//					System.out.println("exc- " + e.getMessage());
//				}
//				resp = resp + "Save Successfully";
			} catch (Exception e) {
				resp = resp + e;
			}
		}

		System.out.println("resp- " + resp);
		return resp;

	}
	

	public void saveUrl( String filename,  String urlString, String savepath ){
		SaveAttachmentFromURL.trustAllCertificate();
//		 String savepath = bundleststic.getString("DocGenServerPDFFilePath");
//		String savepath = "D:\\DOCTIGER114IPProject\\testing docx\\abhishek\\excel\\";
//		BufferedInputStream in = null;
//		FileOutputStream fout = null;
		try {
//			in = new BufferedInputStream(new URL(urlString).openStream());
//			File file = new File(savepath + filename);
//			fout = new FileOutputStream(file);
//			
//			final byte data[] = new byte[1024];
//			int count;
//			while ((count = in.read(data, 0, 1024)) != -1) {
//				fout.write(data, 0, count);
//			}
			
			BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream());
			  FileOutputStream fileOutputStream= new FileOutputStream(savepath + filename) ;
			    byte dataBuffer[] = new byte[1024];
			    int bytesRead;
			    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			        fileOutputStream.write(dataBuffer, 0, bytesRead);
			    }
//			in.close();
//			fout.close();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception in saveurl :: "+e.getMessage().toString());
		} finally {
			
		}
	}
	
	public static void trustAllCertificate() {
		try {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
		return true;
		}
		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		} catch (Exception e) {
		// TODO: handle exception
		}
		}
	
	public void cert(String fileurl,String savepath,String filename) throws IOException {
//		
//		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//		URL url = new URL(fileurl);
//		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
//		conn.setSSLSocketFactory(sslsocketfactory);
//		InputStream inputstream = conn.getInputStream();
////		InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
////		BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
//
////		String string = null;
////		while ((string = bufferedreader.readLine()) != null) {
////		    System.out.println("Received " + string);
////		}
//		BufferedInputStream in = new BufferedInputStream(inputstream);
//		  FileOutputStream fileOutputStream= new FileOutputStream(savepath + filename) ;
//		    byte dataBuffer[] = new byte[1024];
//		    int bytesRead;
//		    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
//		        fileOutputStream.write(dataBuffer, 0, bytesRead);
//		    }
		
		URL website = new URL(fileurl);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(savepath+filename);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		
	}
}
