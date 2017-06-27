package action.bruter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import threadpool.TaskParam;
import tools.Logger;

public class RsyncBruter {
	public static int default_port = 873;
	private static ArrayList<String> queue = new ArrayList<String>();
	
	//ip:port»òip
	public void run(String target) {
		for (int i=0; i<queue.size(); i++) {
			target = queue.get(i);
			String[] s = target.split(":");
			
			int port = this.default_port;
			ArrayList<String> modules = null;
			if (s.length == 2)
				port = Integer.parseInt(s[1]);
			
			getAllModule(s[0], port);
			if (modules == null)
				return;
			
			boolean suc = false;
			for (int j=0; j<modules.size(); j++) {
				String module = modules.get(j).split(" ")[0];
				
				if (rsyncCheck(s[0], port, module)) {
					suc = true;
					Logger.getInstance().insertLog("RsyncBruter", target+":"+module);
				}
			}
			
			if (suc)
				Logger.getInstance().insertResult(target);	
		}	
	}
	
	public static void insertQueue(String data) {
		queue.add(data);
	}
	
	public static void clearQueue() {
		queue.clear();
	}
	
	private ArrayList<String> getAllModule(String ip, int port) {
		String cmd = "rsync --port="+port+" "+ip+"::";
		Runtime run = Runtime.getRuntime();
		
		try {  
            Process p = run.exec(cmd);  
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            
            ArrayList<String> list = new ArrayList<String>();
            String lineStr = "";
            while ((lineStr = inBr.readLine()) != null)  
                list.add(lineStr);
            
            inBr.close();  
            in.close();
            
            return list;
        } catch (Exception e) {  
        }  
		
		return null;
	}
	
	private boolean rsyncCheck(String ip, int port, String module) {
		String cmd = "rsync --port="+port+" "+ip+"::"+module+"/ --password-file=/etc/rsyncd.passwd >/dev/null 2>&1";
		
		Process p;
		try {
			p = Runtime.getRuntime().exec(cmd);
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			p.waitFor();
			if (p.exitValue() != 0) {
			    return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}

		return false;
	}
}
