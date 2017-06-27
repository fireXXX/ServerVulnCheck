package tools;

import global.GlobalSetting;
import global.UrlGlobalDeal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpConnectionUtils {
	private HttpURLConnection conn = null;
	private String url = null;
	
	public boolean createConnection(String url) {
		try {
			this.url = url;
			URL realUrl = new URL(url);
			conn = UrlGlobalDeal.dealUrl(realUrl);
			if (conn == null) {
				Logger.getInstance().insertLog("Error", this.url+":http request error!");
				return false;
			}
			conn.setConnectTimeout(GlobalSetting.timeout);
			conn.setReadTimeout(GlobalSetting.timeout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
			return false;
		}
		
		return true;
	}
	
	public boolean createConnection(String url, int connectTimeout, int readTimeout) {
		try {
			this.url = url;
			URL realUrl = new URL(url);
			conn = UrlGlobalDeal.dealUrl(realUrl);
			if (conn == null) {
				Logger.getInstance().insertLog("Error", this.url+":http request error!");
				return false;
			}
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
			return false;
		}
		
		return true;
	}
	
	public boolean connect() {
		try {
			conn.connect();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
		}
		
		return false;
	}
	
	public boolean setMethod(String m) {
		try {
			if (m.equals("POST")) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
			}
			
			conn.setRequestMethod(m);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
		}
		
		return false;
	}
	
	public void setHeaders(Map<String, String> headers) {
		for (Map.Entry<String, String> entry : headers.entrySet()) {  
			conn.setRequestProperty(entry.getKey(), entry.getValue());
		}
	}
	
	public boolean findStringInHeader(String part, String str) {
		Map<String, List<String>> map = conn.getHeaderFields();
        
		if (!map.containsKey(part))
			return false;
		
		List<String> header = map.get(part);
		for (String values : header) {
			if (StringMatcher.findString(values, str, false))
				return true;
		}
		
		return false;
	}
	
	public boolean findRegexpInHeader(String part, String reg) {
		Map<String, List<String>> map = conn.getHeaderFields();
		
		if (!map.containsKey(part))
			return false;
		
		List<String> header = map.get(part);
		for (String values : header) {
			if (StringMatcher.findRegexp(values, reg))
				return true;
		}
		
		return false;
	}
	
	public int getResponseCode() {
		try {
			return conn.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
		}
		
		return -1;
	}
	
	public boolean postData(byte[] data) {
		try {
			conn.getOutputStream().write(data);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
		}
		
		return false;
	}
	
	public boolean postData(String data) {
		try {
			PrintWriter writer=new PrintWriter(conn.getOutputStream());
			writer.write(data);
			writer.flush();
			writer.close();
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
		}
		
		return false;
	}
	
	public byte[] getAllByteArrayResponse() {
		byte[] in2b = null;
		try {
			InputStream inStream = conn.getInputStream();
	    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
	    	byte[] buff = new byte[100];
	    	int rc = 0;
	    	int count = 0;
	    	while ((rc = inStream.read(buff, 0, 100)) > 0)
	    	{
	    		swapStream.write(buff, 0, rc);
	    		count++;
	    		if (count > 100)
	    			break;
	    	}
	    	in2b = swapStream.toByteArray();
	    	return in2b;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", this.url+":http request error!");
		}

		return in2b;
	}
	
	public String getAllStringResponse() {
		StringBuilder sb = new StringBuilder();    
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));      
			String line = null;
			while ((line = reader.readLine()) != null) {      
				sb.append(line + "\n");      
			}      
		} catch (IOException e) {      
			return null;      
		} finally {      
			try {      
				conn.getInputStream().close();      
			} catch (IOException e) {      
				return null;  
			}      
		}      

		return sb.toString();
	}
	
	public boolean openConnection(String url, String method) {
		if (!createConnection(url))
			return false;
		
		if (!setMethod(method))
			return false;
		
		if (!connect())
			return false;
		
		return true;
	}
	
	public boolean openConnection(String url, String method, int connectTimeout, int readTimeout) {
		if (!createConnection(url, connectTimeout, readTimeout))
			return false;
		
		if (!setMethod(method))
			return false;
		
		if (!connect())
			return false;
		
		return true;
	}
	
	public boolean openConnection(String url, String method, Map<String, String> headers, int connectTimeout, int readTimeout) {
		if (!createConnection(url, connectTimeout, readTimeout))
			return false;
		
		if (!setMethod(method))
			return false;
		
		if (headers != null)
			setHeaders(headers);
		
		if (!connect())
			return false;
		
		return true;
	}
	
	public boolean openConnection(String url, String method, Map<String, String> headers) {
		if (!createConnection(url))
			return false;
		
		if (!setMethod(method))
			return false;
		
		if (headers != null)
			setHeaders(headers);
		
		if (!connect())
			return false;
		
		return true;
	}
	
	public byte[] getPostResponse(byte[] payload) {
		byte[] r = null;
		
		if (!postData(payload))
			return r;
		
		r = getAllByteArrayResponse();
		
		return r;
	}
	
	public String getPostResponse(String data) {
		String result = null;
		if (!postData(data))
			return result;
		
		result = getAllStringResponse();
		
		return result;
	}
	
	public String readOneLineResponse() {
		String result = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			result = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", url+":http request error!");
			return null;
		}
		
		return result;
	}
	
	public String readAllResponse() {
		String result = "";
		try {
			String line = "";
			int code = conn.getResponseCode();
			InputStream ins = null;
			if (code == 200)
				ins = conn.getInputStream();
			else
				ins = conn.getErrorStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(ins));
			while((line = in.readLine()) != null){
                result += line;
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", url+":http request error!");
			return null;
		}
		
		return result;
	}
}
