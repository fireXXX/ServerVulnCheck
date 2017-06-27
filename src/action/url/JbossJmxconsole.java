package action.url;

import java.util.HashMap;
import java.util.Map;

import tools.HttpConnectionUtils;
import tools.Logger;
import tools.StringMatcher;

public class JbossJmxconsole {
	public void run(String target) {
		String targetHost = "";
		target = standardization(target);
		if (target.endsWith("/")) {
			targetHost = target+"jmx-console/HtmlAdaptor?action=inspectMBean&name=jboss.deployment%3Aflavor%3DURL%2Ctype%3DDeploymentScanner";
		} else {
			targetHost = target+"/jmx-console/HtmlAdaptor?action=inspectMBean&name=jboss.deployment%3Aflavor%3DURL%2Ctype%3DDeploymentScanner";
		}
		
		if (check(targetHost)) 
			Logger.getInstance().insertResult(target);
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	private boolean check(String targetHost) {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		if (!utils.openConnection(targetHost, "GET"))
			return false;
		
		String result = utils.readAllResponse();
		if (result == null)
			return false;
		
		if (StringMatcher.findString(result, "addURL", false)) {
        	return true;
		}
		
		return false;
	}
}
