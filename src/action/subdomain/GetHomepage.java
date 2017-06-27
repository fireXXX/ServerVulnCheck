package action.subdomain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tools.Logger;
import tools.StringMatcher;

public class GetHomepage {
	private static int ostype = -1;
	private static ArrayList<String> queue = new ArrayList<String>();
	
	
	public void run(String targets) {
		if (getOSType() == 0) {
			Logger.getInstance().insertLog("GetHomepage", "不支持的操作系统类型");
			return;
		}
		
		File directory = new File("");
		String courseFile = "";
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		
		String phantomjsPath = "";
		String grabPath = "";
		switch (ostype) {
		case 1:
			phantomjsPath = courseFile+"\\phantomjs\\phantomjs-win.exe";
			grabPath = courseFile+"\\phantomjs\\grab.js";
			break;
		case 2:
			phantomjsPath = courseFile+"\\phantomjs\\phantomjs-linux";
			grabPath = courseFile+"\\phantomjs\\grab.js";
			break;
		default:
			return;
		}
	
		targets = targets.substring(1, targets.length()-1);
		for (int i=0; i<queue.size(); i++) {
			String target = queue.get(i);
			
			target = standardization(target);
				
			String homepage = getHomepage(phantomjsPath, grabPath, target);
			if (homepage != null) {
				if (StringMatcher.findString(homepage.toLowerCase(), "error", false)) {
					Logger.getInstance().insertLog("Error", target+":"+homepage);
					Logger.getInstance().insertResult(target);
				}
				else
					Logger.getInstance().insertResult(homepage);
			}
			
			try {   
				Thread.currentThread().sleep(2000);
			} catch(Exception e){
				continue;
			}
		}
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target.trim();
		
		if (!target.endsWith("/"))
			target += "/";
		
		return target;
	}
	
	public static void insertQueue(String data) {
		queue.add(data);
	}
	
	public static void clearQueue() {
		queue.clear();
	}
	
	private int getOSType() {
		if (ostype != -1)
			return ostype;
		
		ostype = 0;
		String os = System.getProperty("os.name");  
		if (os.toLowerCase().startsWith("win")){
			ostype = 1;
			return ostype;
		} else if (os.toLowerCase().startsWith("linux")){
			ostype = 2;
		}
		
		return ostype;
	}
	
	private String getHomepage(String phantomjsPath, String grabPath, String url) {
		Runtime rt = Runtime.getRuntime();  
        Process p;
		try {
			p = rt.exec(phantomjsPath + " " + grabPath + " " + url);
	        InputStream is = p.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));  
	        StringBuffer sbf = new StringBuffer();  
	        String tmp = "";  
	        
	        while((tmp=br.readLine())!=null) {  
	            sbf.append(tmp + "\n");  
	        }  
	        
	        return sbf.toString();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", url+":http request error!");
		}  

		return null;
	}
}
