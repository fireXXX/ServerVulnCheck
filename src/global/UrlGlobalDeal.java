package global;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;

public class UrlGlobalDeal {
	public static HttpURLConnection dealUrl(URL url) {
		HttpURLConnection conn = null;
		try {
			if (GlobalSetting.enable_proxy) {
				Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(GlobalSetting.proxy_ip, GlobalSetting.proxy_port));
				conn = (HttpURLConnection)url.openConnection(proxy);
				
				if (GlobalSetting.enable_timeout) {
					conn.setConnectTimeout(GlobalSetting.timeout);
					conn.setReadTimeout(GlobalSetting.timeout);	
				}
			} else {
				conn = (HttpURLConnection)url.openConnection();
				conn.setConnectTimeout(GlobalSetting.timeout);
				conn.setReadTimeout(GlobalSetting.timeout);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0");
		
		return conn;
	}
}
