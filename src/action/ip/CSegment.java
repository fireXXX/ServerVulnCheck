package action.ip;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tools.Logger;

public class CSegment {
	//ip
	public void run(String target) {
		String[] strs = target.split("\\.");
		String pre = strs[0]+"."+strs[1]+"."+strs[2]+".";
		for (int i=1; i<255; i++) {
			String ip = pre+String.valueOf(i);
			
			if (getBing(ip))
				Logger.getInstance().insertResult(ip);
		}
	}
	
	public boolean getBing(String ip) {
		try {
			String url = "http://cn.bing.com/search?q=ip:"+ip+"&go=%E6%8F%90%E4%BA%A4&qs=n&first="+new Integer(1).toString()+"&form=QBRE&pq=ip:"+ip+"&sc=0-0&sp=-1&sk=&cvid=5e52385772e24683a0bdf047de60abfc";

			Document doc = Jsoup.connect(url).userAgent("User-Agent: BaiduSpider").header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").get();
			Elements tag = doc.select("h2 > a[target=_blank]");
			Iterator localIterator = tag.iterator();
			if (localIterator.hasNext()) {
				return true;
			}
		} catch (Exception e) {
		}
		
		return false;
	}
}
