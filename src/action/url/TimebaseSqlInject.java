package action.url;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.HttpConnectionUtils;
import tools.Logger;
import tools.UrlGrab.UrlParam;
import tools.UrlGrab.Query;

public class TimebaseSqlInject {
	private static int timeout = 5000;
	private static double delta = 0.7;
	private static ArrayList<String> payloads = new ArrayList<String>();
	private static ArrayList<String> queue = new ArrayList<String>();
	
	public void run(String target) {
		if (!getPayloads()) {
			Logger.getInstance().insertLog("Error", "SqlInjectCheck getOptions error!");
			return;
		}
		
		for (int i=0; i<queue.size(); i++) {
			target = queue.get(i);
			target = standardization(target);
			check(target);
		}
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	public static void insertQueue(String data) {
		queue.add(data);
	}
	
	public static void clearQueue() {
		queue.clear();
	}
	
	private boolean getPayloads() {
		if (payloads.size() > 0)
			return true;
		
		File directory = new File("");
		String courseFile = "";
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			return false;
		}
		
		String fileName = courseFile+"\\options\\time_inject_payloads.txt";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String str = "";
			fis = new FileInputStream(fileName);
		    isr = new InputStreamReader(fis);
		    br = new BufferedReader(isr);
		    while ((str = br.readLine()) != null) {
		    	payloads.add(str);
		    }
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				return false;
			}
		}
	
		return true;
	}
	
	private void check(String target) {
		int requesttime = getRequestTime(target);
		if (requesttime == 0)
			return;
		
		String baseurl = UrlParam.getBasePath(target);
		baseurl += "?";
		Query[] params = UrlParam.getQuery(target);
		
		for (int i=0; i<payloads.size(); i++) {
			String payload = payloads.get(i);
			payload = replaceTimeout(payload);
			
			for (int j=0; j<params.length; j++) {
				String url = baseurl;
				String p = params[j].name+"="+params[j].value+payload;
				for (int k=0; k<params.length; k++) {
					if (k == j)
						url += p;
					else
						url += params[k].name+"="+params[k].value;
					
					if (k < params.length-1)
						url += "&";
				}
				
				//check
				Logger.getInstance().insertLog("TimebaseSqlInject", url);
				if (timeInject(url, timeout)) {
					Logger.getInstance().insertResult(url);
					return;
				}
			}
		}
	}
	
	private int getRequestTime(String target) {
		long start = System.currentTimeMillis();
		
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:47.0) Gecko/20100101 Firefox/47.0");
		
		if (!utils.openConnection(target, "GET", headers))
			return 0;
		
		String res = utils.readAllResponse();
		if (res == null)
			return 0;
		
		long end = System.currentTimeMillis();
		
		return (int)(end-start);
	}
	
	private int temp(String url) {
		long start = System.currentTimeMillis();
		
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:47.0) Gecko/20100101 Firefox/47.0");
		
		if (!utils.openConnection(url, "GET", headers, timeout, timeout))
			return 0;
		
		String res = utils.readAllResponse();
		if (res == null)
			return 0;
		
		long end = System.currentTimeMillis();
		
		return (int) (end-start);
	}
	
	private boolean timeInject(String url, int timeout) {
		int time = temp(url);
		int ttimeout = (int)(timeout*delta);
		
		if (time > ttimeout)
			return true;
		
		return false;
	}
	
	private String replaceTimeout(String payload) {
		return payload.replace("timeout", String.valueOf(timeout/1000));
	}
}
