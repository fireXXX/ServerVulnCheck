package action.bruter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import tools.Logger;

public class SnmpBruter {
	public static final int DEFAULT_VERSION = SnmpConstants.version2c;  
    public static final String DEFAULT_PROTOCOL = "udp";  
    public static final int DEFAULT_PORT = 161;  
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;  
    public static final int DEFAULT_RETRY = 3;
    private static ArrayList<String> passlist = new ArrayList<String>();
	
    //ip
	public void run(String target) {
		if (!getPass()) {
			Logger.getInstance().insertLog("Error", "SnmpBruter getPass error!");
			return;
		}
		
		for (int i=0; i<passlist.size(); i++) {
			if (checkSnmp(target, passlist.get(i), ".1.3.6.1.2.1.1")) {
				Logger.getInstance().insertResult(target+":"+passlist.get(i));
			}
		}
	}
	
	private boolean getPass() {
		if (passlist.size() > 0)
			return true;
		
		File directory = new File("");
		String courseFile = "";
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
			return false;
		}
		
		String fileName = courseFile+"\\options\\snmp_pass.txt";
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
	
		return true;
	}
	
	private CommunityTarget createDefault(String ip, String community) {  
        Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);  
        CommunityTarget target = new CommunityTarget();  
        target.setCommunity(new OctetString(community));  
        target.setAddress(address);  
        target.setVersion(DEFAULT_VERSION);  
        target.setTimeout(DEFAULT_TIMEOUT); // milliseconds  
        target.setRetries(DEFAULT_RETRY);  
        return target;  
	}  
	
	private boolean checkSnmp(String ip, String community, String oid) {  
    	CommunityTarget target = createDefault(ip, community);  
    	Snmp snmp = null;  
    	try {  
			PDU pdu = new PDU();  
			pdu.add(new VariableBinding(new OID(oid)));  
			
			DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();  
			snmp = new Snmp(transport);  
			snmp.listen();  
			pdu.setType(PDU.GET);  
			ResponseEvent respEvent = snmp.send(pdu, target);  
			PDU response = respEvent.getResponse();  
     
	        if (response != null) {  
	        	return true;
	        }
    	} catch (Exception e) {  
    		return false;  
    	} finally {  
    		if (snmp != null) {  
    			try {  
    				snmp.close();  
    			} catch (IOException ex1) {  
    				snmp = null;  
    			}  
    		}  
    	}  
    	
    	return false;
    }
}
