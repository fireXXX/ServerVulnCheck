package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMatcher {
	public static boolean findString(String target, String model, boolean caseSensitive) {
		if (!caseSensitive)
			target = target.toLowerCase();
		
		int idx = target.indexOf(model);
		if (idx != -1) {
        	return true;
        }
		
		return false;
	}
	
	public static boolean findRegexp(String target, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matches = pattern.matcher(target);

        if (matches.find()) {
        	return true;
        }
        
        return false;
	}
}
