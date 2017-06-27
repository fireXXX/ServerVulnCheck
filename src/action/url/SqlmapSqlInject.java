package action.url;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tools.Logger;
import tools.SqlInjectUtils;

public class SqlmapSqlInject {
	private static ArrayList<String> options = new ArrayList<String>();
	
	public void run(String target) {
		target = standardization(target);
		
		if (!getOptions()) {
			Logger.getInstance().insertLog("Error", "SqlmapSqlInject getOptions error!");
			return;
		}
		
		String data = "{";
		for (int i=0; i<options.size(); i++) {
			data += options.get(i);
			data += ",";
		}
		data += "\"url\":"+"\""+target+"\"";
		data += "}";
		SqlInjectUtils utils = new SqlInjectUtils(options, data);
		String result = utils.run();
		if (result!=null && result.length()>2)
			Logger.getInstance().insertResult(target+"\n"+result);
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	private boolean getOptions() {
		if (options.size() > 0)
			return true;
		
		File directory = new File("");
		String courseFile = "";
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			return false;
		}
		
		String fileName = courseFile+"\\options\\sqlmapsqlinject.txt";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			String str = "";
			fis = new FileInputStream(fileName);
		    isr = new InputStreamReader(fis);
		    br = new BufferedReader(isr);
		    while ((str = br.readLine()) != null) {
		    	options.add(str);
		    }
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				return false;
			}
		}
	
		return true;
	}
	
	private void sqlInject() {
		
	}
}
