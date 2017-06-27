package action.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import tools.HttpConnectionUtils;
import tools.Logger;

public class GetSubdomain {
	private int depth = 10;
	private int pageurlnum = 50;
	
	//xx.xx.xx
	public void run(String target) {
		HashMap<String, Integer> map = new HashMap<String, Integer>(); 
		
		for (int i=0; i<depth; i++) {
			getOnePageSubdoman(i, target, map);
		}
		
		Logger.getInstance().insertLog("GetSubdomain", target+":"+map.size());
	}
	
	private void getOnePageSubdoman(int depth, String target, HashMap<String, Integer> map) {
		String re = "[0-9A-Za-z]*?\\."+target;
		Pattern pattern = Pattern.compile(re);
		String url_pre = "https://www.baidu.com/s?wd=site%3A%22"+target+"%22&pn=";
		String url_suf = "&oq=site%3A%22"+target+"%22&tn=baiduadv&rn=50&ie=utf-8&rsv_pq=fe2b57d100033e4b&rsv_t=644fIRBG6l6tMS2vSMGMQ%2FIa7M50f9%2F7ug99%2BMneR5oVCck0gd9xI3PkPmAbjFU";
		String url = url_pre+(depth*pageurlnum)+url_suf;
		
		HttpConnectionUtils utils = new HttpConnectionUtils();
		if (!utils.openConnection(url, "GET"))
			return;

		int code = utils.getResponseCode();
		
		if (code == 200) {
			String result = utils.readAllResponse();
            Matcher matches = pattern.matcher(result);

            while (matches.find()) {
            	String subdomain = matches.group();
            	
            	if (subdomain.startsWith("2F"))
            		continue;
            	
            	if (!map.containsKey(subdomain)) {
            		map.put(subdomain, 1);
            		Logger.getInstance().insertResult(subdomain);
            	}
            }
		}
	}
}
