package action.ip;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tools.Logger;

public class DomainSameIp {
	//ip
	public void run(String target) {
		getBing(target);
	}
	
	public void getBing(String ip) {
		Map res = new HashMap();
		int first = 1;
		while (true) {
			try {
				String url = "http://cn.bing.com/search?q=ip:"+ip+"&go=%E6%8F%90%E4%BA%A4&qs=n&first="+new Integer(first).toString()+"&form=QBRE&pq=ip:"+ip+"&sc=0-0&sp=-1&sk=&cvid=5e52385772e24683a0bdf047de60abfc";
				first += 10;
				if (first > 100)
					break;

				Document doc = Jsoup.connect(url).userAgent("User-Agent: BaiduSpider").header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3").get();
				Elements tag = doc.select("h2 > a[target=_blank]");
				Iterator localIterator = tag.iterator();
				while (localIterator.hasNext()) {
					Element t = (Element) localIterator.next();
					URL urls = new URL(t.attr("href"));
					String key = urls.getProtocol() + "://" + urls.getAuthority();
					String value = t.text();
					
					if (!res.containsKey(key)) {
						Logger.getInstance().insertResult(key);
						Logger.getInstance().insertLog("DomainSameIp", key+":"+value);
					}
				}

				Elements next = doc.select("div[class=sw_next]");
				if (!next.isEmpty()) {
					continue;
				}
			} catch (Exception e) {
			}
		}
	}
}
