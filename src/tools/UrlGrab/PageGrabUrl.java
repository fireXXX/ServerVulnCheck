package tools.UrlGrab;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import tools.HttpConnectionUtils;

public class PageGrabUrl {
	private static final ArrayList<String> suffixes = new ArrayList<String>();
	private Map<String, Boolean> urls = null;
	private Queue queue = null;
	private String pageurl = null;
	private String domain = null;
	
	public PageGrabUrl(String pageurl, Queue queue, Map<String, Boolean> urls) {
		if (suffixes.size() == 0) {
			suffixes.add("");
			suffixes.add("jsp");
			suffixes.add("asp");
			suffixes.add("php");
			suffixes.add("action");
			suffixes.add("do");
			suffixes.add("html");
			suffixes.add("htm");
			suffixes.add("shtml");
			suffixes.add("aspx");
			suffixes.add("exml");
			suffixes.add("jhtml");
		}
		
		this.pageurl = pageurl;
		this.queue = queue;
		this.urls = urls;
	}
	
	public void grabPageUrl() {
		if (!checkSuffix())
			return;
		
		HttpConnectionUtils utils = new HttpConnectionUtils();
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:47.0) Gecko/20100101 Firefox/47.0");
		
		if (!utils.openConnection(this.pageurl, "GET", headers))
			return;
		
		String result = utils.getAllStringResponse();
		if (result == null)
			return;
		
		Document doc = Jsoup.parse(result);
		Elements eles = doc.getElementsByTag("a");
		for (int i=0; i<eles.size(); i++) {
			Element ele = eles.get(i);
			if (!ele.hasAttr("href"))
				continue;
			
			String href= ele.attr("href");
			if (href.startsWith(domain)) {
				if (!urls.containsKey(href)) {
					queue.add(href);
					urls.put(href, true);
				}
			}
			else if (!href.startsWith("http")) {
				String us = null;
				if (href.startsWith("/"))
					us = domain+href;
				else if (!href.toLowerCase().startsWith("javascript"))
					us = domain+"/"+href;
				else
					continue;
				if (!urls.containsKey(us)) {
					queue.add(us);
					urls.put(us, true);
				}
			}
		}
	}
	
	private boolean checkSuffix() {
		URL url;
		try {
			url = new URL(pageurl);
			
			domain = url.getProtocol()+"://"+url.getHost();
			
			String queries = url.getQuery();
			if (queries!=null && queries.length() > 0)
				return true;

			String path = url.getPath();
			if (path == null)
				return true;
			String[] split = path.split(".");
			if (split.length >= 2) {
				if (suffixes.contains(split[split.length-1]))
					return true;
				else
					return false;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return false;
		}

		return true;
	}
}
