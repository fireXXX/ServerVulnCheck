package action.url;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import tools.Logger;
import tools.UrlGrab.WebsiteGrabUrl;

public class GrabParameterizedUrl {
	public void run(String target) {
		target = standardization(target);
		
		WebsiteGrabUrl grab = new WebsiteGrabUrl(target);
		Map<String, String> urls = grab.grabWebsiteUrl();
		
		Iterator<Map.Entry<String, String>> entries = urls.entrySet().iterator();  
		while (entries.hasNext()) {  
		    Map.Entry<String, String> entry = entries.next();  
		    Logger.getInstance().insertResult(entry.getValue());
		}
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
}
