package action.url;

import global.UrlGlobalDeal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tools.Logger;
import tools.HttpConnectionUtils;

public class IISMethod {
	public void run(String target) {
		target = standardization(target);
		
		if (check(target)) {
			Logger.getInstance().insertResult(target);
		}
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	private boolean check(String url) {
		String result = "";
		HttpConnectionUtils utils = new HttpConnectionUtils();
		if (!utils.openConnection(url, "OPTIONS"))
			return false;
	        
		if (!utils.findStringInHeader("Server", "Microsoft-IIS"))
			return false;
	        
		if (!utils.findRegexpInHeader("Allow", "^(?i).*(PUT|DELETE).*"))
			return false;
		
		return true;
	}
}
