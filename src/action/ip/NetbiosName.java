package action.ip;

import java.net.DatagramPacket;

import tools.DatagramUtils;
import tools.Logger;

public class NetbiosName {
	private static int default_port = 137;
	
	public void run(String target) {
		byte[] cmd = getQueryCmd();
		
		DatagramUtils utils = new DatagramUtils();
		if (utils.send(target, default_port, cmd) == null)
			Logger.getInstance().insertLog("Error", target+":http request error!");
		
		DatagramPacket dp = utils.receive();
		if (dp == null)
			Logger.getInstance().insertLog("Error", target+":http request error!");
		
		getBiosName(target, dp.getData());
	}
	
	private void getBiosName(String target, byte[] brevdata) {
		String name = "";
		int length = 0;
		
		if (brevdata==null || brevdata.length<56)
			return;
		
		length = (int)brevdata[56];
		
		for (int i=0; i<length; i++) {
			for(int j=0; j<18; j++)  
	        {
				if (brevdata[57+i*18+j] == 0x00) {
					name += "//";
					break;
				}
				
				name += (char)brevdata[57+i*18+j];
	        }
		}
		
		Logger.getInstance().insertResult(target+":"+name);
	}
	
    private byte[] getQueryCmd() {  
    	byte[] t_ns = {
			0x0,0x00,0x0,0x10,0x0,0x1,0x0,0x0,0x0,0x0,
			0x0,0x0,0x20,0x43,0x4b,0x41,0x41,0x41,0x41,0x41,
			0x41,0x41,0x41,0x41,0x41,0x41,0x41,0x41,0x41,0x41,
			0x41,0x41,0x41,0x41,0x41,0x41,0x41,0x41,0x41,0x41,
			0x41,0x41,0x41,0x41,0x41,0x0,0x0,0x21,0x0,0x1
    	};

        return t_ns;
    }
}
