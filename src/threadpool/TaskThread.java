package threadpool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import tools.Logger;

public class TaskThread extends Thread {
	private boolean running = true;
	private ConcurrentLinkedQueue<TaskParam> queue = null;
	
	public TaskThread(ConcurrentLinkedQueue<TaskParam> queue) {
		this.queue = queue;
	}
	
	public void run() {
        // compute primes larger than minPrime
		while (running) {
			if (queue.isEmpty())
				break;
			
			TaskParam param = queue.poll();
			if (param != null) {
				TaskThreadpool.getInstance().modifyRunThreadnum(1);
				try {
					Class cls = Class.forName(param.clsname);
					Method method = cls.getMethod("run", new Class[] {String.class});
					
					String name = param.clsname.split("\\.")[param.clsname.split("\\.").length-1];
					if (param.arg.length() > 1000)
						Logger.getInstance().insertLog(name, "start");
					else
						Logger.getInstance().insertLog(name, param.arg+":start");
					method.invoke(cls.newInstance(), param.arg);
					if (param.arg.length() > 1000)
						Logger.getInstance().insertLog(name, "end");
					else
						Logger.getInstance().insertLog(name, param.arg+":end");
					
				} catch (Exception e) {
				}
				
				TaskThreadpool.getInstance().modifyRunThreadnum(-1);
				TaskThreadpool.getInstance().modifyDoneTasknum(1);
			}
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
		
		Logger.getInstance().insertLog("threadpool", "thread exit");
    }

	public void stopTaskThread() {
		running = false;
	}
}
