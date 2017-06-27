package global;

import java.util.HashMap;

public class IpThreadMap {
	private enum TYPE{
		RANDOM,		//random
		SAME_IP,	//same ip one thread
		SINGLE_THREAD		//all ip one thread
	}
	
	private static IpThreadMap cls = null;
	private static HashMap<String, TYPE> map = new HashMap<String, TYPE>();
	
	public static IpThreadMap getInstance() {
		if (cls == null)
			cls = new IpThreadMap();
		
		return cls;
	}
	
	public static void constructThreadpoolMap() {
		map.put("domain.get_subdomain", TYPE.RANDOM);
		map.put("domain.zone_transfer", TYPE.RANDOM);
		map.put("domain.check_spf", TYPE.RANDOM);
		
		map.put("ip.rsync_bruter", TYPE.SINGLE_THREAD);
		map.put("ip.snmp_bruter", TYPE.SAME_IP);
		map.put("ip.c_segment", TYPE.RANDOM);
		map.put("ip.domain_same_ip", TYPE.RANDOM);
		
		map.put("subdomain.get_ip", TYPE.RANDOM);
		map.put("subdomain.get_homepage", TYPE.SINGLE_THREAD);
		
		map.put("url.sqlinject_check", TYPE.SAME_IP);
		map.put("url.sqlmap_sqlinject", TYPE.SAME_IP);
		map.put("url.s2_045", TYPE.RANDOM);
		map.put("url.iis_method", TYPE.RANDOM);
		map.put("url.jboss_deserialize", TYPE.RANDOM);
		map.put("url.jboss_jmxconsole", TYPE.RANDOM);
		map.put("url.weblogic_deserialize", TYPE.RANDOM);
		map.put("url.weblogic_uddissrf", TYPE.RANDOM);
		map.put("url.websphere_deserialize", TYPE.RANDOM);
		map.put("url.qibo_jobdownload", TYPE.RANDOM);
		map.put("url.grab_parameterized_url", TYPE.RANDOM);
	}
}
