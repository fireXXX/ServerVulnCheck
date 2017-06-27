package tools.UrlGrab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import tools.Logger;

public class WebsiteGrabUrl {
	private static final int MAX_GRAB = 500;
	private static Map<String, Map<String, String>> websites = new HashMap<String, Map<String, String>>();
	private Map<String, Boolean> tmp_urls = null;
	private Queue<String> queue = new LinkedList<String>();
	private String root = null;
	
	public WebsiteGrabUrl(String root) {
		this.root = root;
		queue.offer(root);
	}
	
	public Map<String, String> grabWebsiteUrl() {
		if (websites.containsKey(root))
			return websites.get(root);
		Map<String, String> urls = new HashMap<String, String>();
		websites.put(root, urls);
		tmp_urls = new HashMap<String, Boolean>();
		tmp_urls.put(root, true);
		
		Map<String, Boolean> searched = new HashMap<String, Boolean>();
		
		while (!queue.isEmpty()) {
			String url = queue.poll();
			if (searched.containsKey(url))
				continue;
			
			searched.put(url, true);
			PageGrabUrl grab = new PageGrabUrl(url, queue, tmp_urls);
			grab.grabPageUrl();
			
			if (tmp_urls.size() >= MAX_GRAB)
				break;
			
			System.out.println("grab:"+tmp_urls.size()+"\tleft:"+queue.size());
		}
		
		filterUrls(urls);
		return urls;
	}
	
	private void filterUrls(Map<String, String> urls) {
		for (String url : tmp_urls.keySet()) {  
			String base = UrlParam.getBasePath(url);
			if (base == null)
				continue;
			String[] queries = UrlParam.getSortedQuery(url);
			if (queries == null)
				continue;
			for (int i=0; i<queries.length; i++)
				base += queries[i];
			urls.put(base, url);
		}
	}
}
