package launch;

import global.ActionMap;
import global.IpThreadMap;

import java.awt.EventQueue;

import threadpool.TaskThreadpool;
import tools.Logger;
import UI.MainFrame;

public class Launch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Launch the application.
		 */
		try {
			ActionMap.getInstance().constructActionMap();
			IpThreadMap.getInstance().constructThreadpoolMap();
			MainFrame frame = new MainFrame();
			Logger.frame = frame;
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
