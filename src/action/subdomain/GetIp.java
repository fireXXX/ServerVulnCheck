package action.subdomain;

import java.net.InetAddress;
import java.net.UnknownHostException;

import tools.Logger;

public class GetIp {
	public void run(String target) {
		try {
			target = standardization(target);
			
			InetAddress[] ips=InetAddress.getAllByName(target);
			for(InetAddress ip: ips){
				Logger.getInstance().insertResult(ip.getHostAddress());
				//System.out.println("Address:"+ip.getHostAddress());  
				//System.out.println("Name"+ip.getHostName());  
			}  
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public static String standardization(String target) {
		String[] strs = target.split("://");
		if (strs.length >= 2)
			target = strs[1];
		
		if (target.endsWith("/"))
			target = target.substring(0, target.length()-1);
		
		return target;
	}
}
