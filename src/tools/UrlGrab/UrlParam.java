package tools.UrlGrab;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class UrlParam {
	public static String getBasePath(String u) {
		try {
			URL url = new URL(u);
			if (url.getPort() == -1)
				return url.getProtocol()+"://"+url.getHost()+url.getPath();
			else
				return url.getProtocol()+"://"+url.getHost()+url.getPort()+url.getPath();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static String[] getSortedQuery(String u) {
		try {
			URL url = new URL(u);
			
			String query = url.getQuery();
			if (query == null)
				return null;
			String[] queries = query.split("&");
			String[] names = new String[queries.length];
			for (int i=0; i<queries.length; i++) {
				String q = queries[i];
				names[i] = q.split("=")[0];
			}
			Arrays.sort(names);
			return names;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static Query[] getQuery(String u) {
		try {
			URL url = new URL(u);
			
			String query = url.getQuery();
			if (query == null)
				return null;
			String[] q = query.split("&");
			Query[] queries = new Query[q.length];
			for (int i=0; i<q.length; i++) {
				String qq = q[i];
				String[] temp = qq.split("=");
				queries[i] = new Query();
				queries[i].name = temp[0];
				queries[i].value = "";
				for (int j=1; j<temp.length; j++)
					queries[i].value += temp[j]; 
			}
			return queries;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
