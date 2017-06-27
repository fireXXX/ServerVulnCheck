package tools;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import UI.MainFrame;

public class Logger {
	private static Logger log = null;
	private static Lock lock = new ReentrantLock();
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
	public static MainFrame frame = null;
	
	public static Logger getInstance() {
		if (log == null) 
			log = new Logger();
		
		return log;
	}
	
	public static void insertTarget(String str) {
		lock.lock();
		frame.insertTarget(str);
		lock.unlock();
	}
	
	public static void insertResult(String str) {
		lock.lock();
		frame.insertResult(str);
		lock.unlock();
	}
	
	public static void insertLog(String tag, String info) {
		lock.lock();
		frame.insertLog("【"+tag+"】【"+df.format(new Date(System.currentTimeMillis()))+"】"+info);
		lock.unlock();
	}
	
	public static void showThreadnum(String info) {
		lock.lock();
		frame.showThreadnum(info);
		lock.unlock();
	}
	
	public static void showQueuenum(String info) {
		lock.lock();
		frame.showQueuenum(info);
		lock.unlock();
	}
}
