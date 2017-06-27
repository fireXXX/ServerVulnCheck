package action.url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tools.HttpConnectionUtils;
import tools.Logger;
import tools.StringMatcher;
import tools.UrlGrab.Query;
import tools.UrlGrab.UrlParam;

public class ErrbaseSqlInject {
	private static ArrayList<String> errors = new ArrayList<String>();
	
	public void run(String target) {
		getErrorsInfo();
		target = standardization(target);
		
		check(target);
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	public void check(String target) {
		String baseurl = UrlParam.getBasePath(target);
		baseurl += "?";
		Query[] params = UrlParam.getQuery(target);
		
		for (int j=0; j<params.length; j++) {
			String url = baseurl;
			String p = params[j].name+"=";
			for (int k=0; k<params.length; k++) {
				if (k == j)
					url += p;
				else
					url += params[k].name+"="+params[k].value;
				
				if (k < params.length-1)
					url += "&";
			}
			
			//check
			Logger.getInstance().insertLog("ErrbaseSqlInject", url);
			if (errbaseInject(url)) {
				return;
			}
		}
		
		for (int j=0; j<params.length; j++) {
			String url = baseurl;
			String p = params[j].name+"="+params[j].value+"'";
			for (int k=0; k<params.length; k++) {
				if (k == j)
					url += p;
				else
					url += params[k].name+"="+params[k].value;
				
				if (k < params.length-1)
					url += "&";
			}
			
			//check
			Logger.getInstance().insertLog("ErrbaseSqlInject", url);
			if (errbaseInject(url)) {
				return;
			}
		}
	}
	
	private boolean errbaseInject(String url) {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:47.0) Gecko/20100101 Firefox/47.0");
		
		if (!utils.openConnection(url, "GET", headers))
			return false;
		
		String res = utils.readAllResponse();
		if (res == null) {
			return false;
		}
		
		for (int i=0; i<errors.size(); i++) {
			String reg = errors.get(i);
			if (StringMatcher.findRegexp(res, reg)) {
				Logger.getInstance().insertLog("ErrbaseSqlInject", url+":"+reg);
				Logger.getInstance().insertResult(url+":"+errors.get(i));
	        	return true;
			}	
		}
		
		return false;
	}
	
	private void getErrorsInfo() {
		if (errors.size() == 0) {
			errors.add("SQL syntax.*MySQL");
			errors.add("Warning.*mysql_.*");
			errors.add("MySqlException \\(0x");
			errors.add("valid MySQL result");
			errors.add("check the manual that corresponds to your (MySQL|MariaDB) server version");
			errors.add("MySqlClient\\.");
			errors.add("com\\.mysql\\.jdbc\\.exceptions");
			
			errors.add("PostgreSQL.*ERROR");
			errors.add("Warning.*\\Wpg_.*");
			errors.add("valid PostgreSQL result");
			errors.add("Npgsql\\.");
			errors.add("PG::SyntaxError:");
			errors.add("org\\.postgresql\\.util\\.PSQLException");
			errors.add("ERROR:\\s\\ssyntax error at or near ");
			
			errors.add("Driver.* SQL[\\-\\_\\ ]*Server");
			errors.add("OLE DB.* SQL Server");
			errors.add("\\bSQL Server.*Driver");
			errors.add("Warning.*mssql_.*");
			errors.add("\\bSQL Server.*[0-9a-fA-F]{8}");
			errors.add("(?s)Exception.*\\WSystem\\.Data\\.SqlClient\\.");
			errors.add("(?s)Exception.*\\WRoadhouse\\.Cms\\.");
			errors.add("Microsoft SQL Native Client.*[0-9a-fA-F]{8}");
			errors.add("Microsoft .NET Framework");
			
			errors.add("Microsoft Access (\\d+ )?Driver");
			errors.add("JET Database Engine");
			errors.add("Access Database Engine");
			errors.add("ODBC Microsoft Access");
			
			errors.add("\\bORA-\\d{5}");
			errors.add("Oracle error");
			errors.add("Oracle.*Driver");
			errors.add("Warning.*\\Woci_.*");
			errors.add("Warning.*\\Wora_.*");
		}
	}
}
