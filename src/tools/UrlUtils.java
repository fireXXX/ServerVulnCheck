package tools;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {
	public static int getPort(String target) {
		String[] s = target.split(":");
		
		if (s.length >= 3)
			return Integer.parseInt(s[2]);
		
		return 80;
	}
}
