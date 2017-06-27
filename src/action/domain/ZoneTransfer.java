package action.domain;

import global.GlobalSetting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

import org.apache.http.conn.DnsResolver;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import threadpool.TaskThreadpool;
import tools.Logger;

public class ZoneTransfer {
	//xx.xx.xx
	public void run(String target) {
		ArrayList<String> alldns = getAllDns(target);
		
		if (alldns == null)
			return;
		
		for (int i=0; i<alldns.size(); i++) {
			try {
				String ip = InetAddress.getByName(alldns.get(i)).getHostAddress();
				
				Socket sock = new Socket(ip, 53);
				
				sock.setSoTimeout(GlobalSetting.timeout);
				
				String qdata = constructQuery(alldns.get(i));
				OutputStream ops = sock.getOutputStream();    
			    OutputStreamWriter opsw = new OutputStreamWriter(ops);
			    BufferedWriter bw = new BufferedWriter(opsw);

			    bw.write(qdata);
			    bw.flush();
			    
			    String s;
			    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));  
			    while ((s = in.readLine()) != null) {  
			        int len = Integer.parseInt(s.substring(0, 2));
			        
			        while (s.length() < len)
						s += in.readLine();
			        
			        if (decodeData(s.substring(2))) {
			        	Logger.getInstance().insertResult(target);
			        	Logger.getInstance().insertLog("ZoneTransfer", target+":"+alldns.get(i));
			        	return;
			        }
			    }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				continue;
			} 
		}
	}
	
	private ArrayList<String> getAllDns(String domain) {
		ArrayList<String> list = new ArrayList<String>();
		
		Record[] records;
		try {			
			records = new Lookup(domain, Type.NS).run();
			if (records == null) 
				return null;
			for (int i = 0; i < records.length; i++) {
				NSRecord ns = (NSRecord)records[i];
				list.add(ns.getTarget().toString());
			}
		} catch (TextParseException e) {
			// TODO Auto-generated catch block
			return list;
		}
		
		return list;
	}
	
	private String constructQuery(String domain) {
		Random random = new Random();
		int transid = random.nextInt(65535)+1;
		String data = ""+String.format("%04x", transid);
		
		data += "00000001000000000000";
		
		String s[] = domain.split("\\.");
		for (int i=0; i<s.length; i++) {
			data += String.format("%02x", s[i].length());
			
			int d = 0;
			byte[] bs = s[i].getBytes();
			for (int j=0; j<bs.length; j++) {
				data += String.format("%02x", bs[j]);
			}
		}
		
		data += "00";
		data += "00fc";
		data += "0001";
		
		String ret = "\\x"+String.format("%04x", data.length()/2)+data;
		
		return ret;
	}
	
	private boolean decodeData(String data) {
		String flag = data.substring(3, 4);
		if (flag.matches("1")) {
			return false;
		}
		
		int answer = Integer.parseInt(data.substring(6, 8), 16);
		if (answer > 0)
			return true;
		
		return false;
	}
}
