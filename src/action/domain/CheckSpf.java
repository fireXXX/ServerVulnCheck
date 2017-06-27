package action.domain;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import tools.Logger;

public class CheckSpf {
	//xx.xx.xx
	public void run(String target) {
		ArrayList<String> list = new ArrayList<String>();
		
		Record[] records;
		try {
			records = new Lookup(target, Type.TXT).run();
			
			if (records == null) {
				Logger.getInstance().insertResult(target);
				Logger.getInstance().insertLog("CheckSpf", target);
			} else if (records.length == 0) {
	        	Logger.getInstance().insertResult(target);
	        	Logger.getInstance().insertLog("CheckSpf", target);
			} else {
				Logger.getInstance().insertLog("CheckSpf", target+":"+records[0].toString());
			}
		} catch (TextParseException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertResult(target);
		}
	}
}
