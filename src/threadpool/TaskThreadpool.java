package threadpool;

import global.GlobalSetting;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import tools.Logger;

public class TaskThreadpool {
	private static ArrayList<TaskThread> workers = new ArrayList<TaskThread>();
	private static ConcurrentLinkedQueue<TaskParam> queue = new ConcurrentLinkedQueue<TaskParam>();
	private static int runThreadnum = 0;
	private static int totalThreadnum = GlobalSetting.threadnum;
	private static int doneTasknum = 0;
	private static int totalTasknum = 0;
	
	private static TaskThreadpool threadpool = null; 
	private static Lock lockThreadnum = new ReentrantLock();
	private static Lock lockTasknum = new ReentrantLock();
	
	public static boolean isRunning() {
		if (workers.size() != 0)
			return true;
		
		return false;
	}
	
	public static boolean hasTask() {
		if (queue.size() > 0)
			return true;
		
		return false;
	}
	
	public static void startAllThread() {
		if (workers.size() != 0)
			return;
		
		if (queue.isEmpty())
			return;
		
		for (int i=0; i<totalThreadnum; i++) {
			TaskThread thread = new TaskThread(queue);
			thread.start();
			workers.add(thread);
		}
		
		Logger.getInstance().insertLog("threadpool", "add "+workers.size()+" thread");
		Logger.getInstance().insertLog("threadpool", "add "+queue.size()+" task");
		modifyRunThreadnum(0);
		modifyDoneTasknum(0);
	}
	
	public static TaskThreadpool getInstance() {
		if (threadpool == null)
			threadpool = new TaskThreadpool();
		
		return threadpool;
	}
	
	public static void addTask(TaskParam param) {
		queue.add(param);
		totalTasknum++;
	}
	
	public static void stopAllThread() {
		for (int i=0; i<workers.size(); i++) {
			workers.get(i).stopTaskThread();
			workers.get(i).stop();
		}
		
		workers.clear();
		lockThreadnum.unlock();
		lockTasknum.unlock();
		Logger.getInstance().insertLog("threadpool", "stop all thread");
	}
	
	public static void modifyRunThreadnum(int delta) {
		lockThreadnum.lock();
		runThreadnum += delta;
		String info = runThreadnum+"/"+totalThreadnum+"threads";
		System.out.println(info);
		lockThreadnum.unlock();
		
		if (queue.size() == 0)
			workers.clear();
		
		Logger.getInstance().showThreadnum(info);
	}
	
	public static void modifyDoneTasknum(int delta) {
		lockTasknum.lock();
		doneTasknum += delta;
		String info = doneTasknum+"/"+totalTasknum+"tasks";
		System.out.println(info);
		if (doneTasknum > totalTasknum) 
			stopAllThread();
		lockTasknum.unlock();
		
		Logger.getInstance().showQueuenum(info);
	}
	
	public static void clearTask() {
		lockTasknum.lock();
		doneTasknum = 0;
		totalTasknum = 0;
		queue.clear();
		lockTasknum.unlock();
	}
}
