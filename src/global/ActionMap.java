package global;

import java.util.HashMap;

public class ActionMap {
	private static ActionMap cls = null;
	private static HashMap<String, String> map = new HashMap<String, String>();
	
	public static ActionMap getInstance() {
		if (cls == null)
			cls = new ActionMap();
		
		return cls;
	}
	
	public static void constructActionMap() {
		map.put("domain.get_subdomain", "action.domain.GetSubdomain");
		map.put("domain.zone_transfer", "action.domain.ZoneTransfer");
		map.put("domain.check_spf", "action.domain.CheckSpf");
		
		map.put("ip.c_segment", "action.ip.CSegment");
		map.put("ip.domain_same_ip", "action.ip.DomainSameIp");
		map.put("ip.netbios_name", "action.ip.NetbiosName");
		
		map.put("subdomain.get_ip", "action.subdomain.GetIp");
		map.put("subdomain.get_homepage", "action.subdomain.GetHomepage");
		
		map.put("url.errbase_sqlinject", "action.url.ErrbaseSqlInject");
		map.put("url.timebase_sqlinject", "action.url.TimebaseSqlInject");
		map.put("url.sqlmap_sqlinject", "action.url.SqlmapSqlInject");
		map.put("url.s2_045", "action.url.S2_045");
		map.put("url.iis_method", "action.url.IISMethod");
		map.put("url.jboss_deserialize", "action.url.JbossDeserialize");
		map.put("url.jboss_jmxconsole", "action.url.JbossJmxconsole");
		map.put("url.weblogic_deserialize", "action.url.WeblogicDeserialize");
		map.put("url.weblogic_uddissrf", "action.url.WeblogicUddiSsrf");
		map.put("url.websphere_deserialize", "action.url.WebsphereDeserialize");
		map.put("url.qibo_jobdownload", "action.url.QiboJobdownload");
		map.put("url.grab_parameterized_url", "action.url.GrabParameterizedUrl");
		
		map.put("bruter.rsync_bruter", "action.bruter.RsyncBruter");
		map.put("bruter.snmp_bruter", "action.bruter.SnmpBruter");
		map.put("bruter.ftp_bruter", "action.bruter.FtpBruter");
		map.put("bruter.ssh_bruter", "action.bruter.SshBruter");
		map.put("bruter.telnet_bruter", "action.bruter.TelnetBruter");
		map.put("bruter.smtp_bruter", "action.bruter.SmtpBruter");
		map.put("bruter.pop3_bruter", "action.bruter.Pop3Bruter");
		map.put("bruter.imap_bruter", "action.bruter.ImapBruter");
		map.put("bruter.smb_bruter", "action.bruter.SmbBruter");
	}
	
	public static HashMap<String, String> getMap() {
		return map;
	}
	
	public static String getActionClass(String action) {
		return map.get(action);
	}
}
