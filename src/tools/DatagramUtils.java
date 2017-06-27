package tools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DatagramUtils {
	private DatagramSocket ds = null;
	private byte[] buffer = new byte[1024];
	
    public DatagramPacket send(String addr, int port, byte[] bytes) {
    	if (ds == null)
			try {
				ds = new DatagramSocket();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				return null;
			}
    	
        DatagramPacket dp = null;
		try {
			dp = new DatagramPacket(bytes,bytes.length,InetAddress.getByName(addr),port);
			ds.send(dp);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}  
        return dp;
    }
  
    public DatagramPacket receive() {
    	if (ds == null)
    		return null;
    	
        DatagramPacket dp = new DatagramPacket(buffer,buffer.length);  
        try {
			ds.receive(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}  
        return dp;
    }  
}
