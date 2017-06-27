package UI;
import global.ActionMap;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import threadpool.TaskParam;
import threadpool.TaskThreadpool;
import tools.Logger;

import java.awt.Component;

import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel topPanel;
	private JTabbedPane actionPane;
	
	private JScrollPane domainPane;
	private JScrollPane subdomainPanel;
	private JScrollPane ipPanel;
	private JScrollPane urlPanel;
	private JScrollPane bruterPanel;
	
	private JScrollPane targetScrollPanel;
	private JScrollPane resultScrollPanel;
	private JScrollPane logScrollPanel;
	
	private JPanel toprightPanel;
	private JPanel toprighttopPanel;
	private	JPanel targetPanel;
	private JPanel toprightbottomPanel;
	private JPanel resultPanel;
	private JPanel bottomPanel;
	private JPanel logPanel;
	private JPanel statusPanel;
	
	private JTextField targetTextField;
	private JTextField threadnumTextField;
	private JTextField queuenumTextField;
	
	private JButton addtargetJButton;
	private JButton removetargetJButton;
	private JButton cleartargetJButton;
	private JButton filetargetJButton;
	private JButton starttargetJButton;
	private JButton stoptargetJButton;
	
	private JButton clearresultJButton;
	private JButton saveresultJButton;
	private JButton settargetresultJButton;
	private JButton updateresultJButton;
	
	private JButton savelogJButton;
	private JButton clearlogJButton;
	private JButton updatelogJButton;
	
	private JList domainJList;
	private JList subdomainJList;
	private JList ipJList;
	private JList urlJList;
	private JList bruterJList;
	
	private JList targetJList;
	private JList resultJList;
	private JList logJList;
	
	private DefaultListModel<String> targetModel;
	private DefaultListModel<String> resultModel;
	private DefaultListModel<String> logModel;
	private DefaultListModel<String> domainModel;
	private DefaultListModel<String> subdomainModel;
	private DefaultListModel<String> ipModel;
	private DefaultListModel<String> urlModel;
	private DefaultListModel<String> bruterModel;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 1, 1));
		
		topPanel = new JPanel();
		contentPane.add(topPanel);
		topPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		actionPane = new JTabbedPane(JTabbedPane.LEFT);
		topPanel.add(actionPane);
		
		domainModel = new DefaultListModel<>();
		domainJList = new JList(domainModel);
		domainJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		domainPane = new JScrollPane(domainJList);
		actionPane.addTab("domain", null, domainPane, null);
		
		subdomainModel = new DefaultListModel<>();
		subdomainJList = new JList(subdomainModel);
		subdomainJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		subdomainPanel = new JScrollPane(subdomainJList);
		actionPane.addTab("subdomain", null, subdomainPanel, null);
		
		ipModel = new DefaultListModel<>();
		ipJList = new JList(ipModel);
		ipJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ipPanel = new JScrollPane(ipJList);
		actionPane.addTab("ip", null, ipPanel, null);
		
		urlModel = new DefaultListModel<>();
		urlJList = new JList(urlModel);
		urlJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		urlPanel = new JScrollPane(urlJList);
		actionPane.addTab("url", null, urlPanel, null);
		
		bruterModel = new DefaultListModel<>();
		bruterJList = new JList(bruterModel);
		bruterJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		bruterPanel = new JScrollPane(bruterJList);
		actionPane.addTab("bruter", null, bruterPanel, null);
		
		toprightPanel = new JPanel();
		topPanel.add(toprightPanel);
		toprightPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		toprighttopPanel = new JPanel();
		toprightPanel.add(toprighttopPanel);
		toprighttopPanel.setLayout(new BorderLayout(0, 0));
		
		targetPanel = new JPanel();
		toprighttopPanel.add(targetPanel, BorderLayout.NORTH);
		
		targetTextField = new JTextField();
		targetPanel.add(targetTextField);
		targetTextField.setColumns(10);
		
		addtargetJButton = new JButton("Add");
		addtargetJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String text = targetTextField.getText();
				if (!text.isEmpty()) {
					targetModel.addElement(text);
				}
			}
			
		});
		
		targetPanel.add(addtargetJButton);
		
		removetargetJButton = new JButton("Remove");
		removetargetJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				targetModel.remove(targetJList.getSelectedIndex());
			}
			
		});
		targetPanel.add(removetargetJButton);
		
		cleartargetJButton = new JButton("Clear");
		cleartargetJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				targetModel.clear();
			}
			
		});
		targetPanel.add(cleartargetJButton);
		
		filetargetJButton = new JButton("...");
		filetargetJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.showDialog(new JLabel(), "Ñ¡Ôñ");  
		        File file=jfc.getSelectedFile();
		        
		        try {
			        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
			        BufferedReader bufferedReader = new BufferedReader(read);
		            String lineTxt = null;
	            
					while((lineTxt = bufferedReader.readLine()) != null) {
						if (lineTxt.equals("\r\n") || lineTxt.equals("\n"))
							continue;
						
						targetModel.addElement(lineTxt);
					}
					
					read.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		targetPanel.add(filetargetJButton);
		
		starttargetJButton = new JButton("Start");
		starttargetJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (TaskThreadpool.getInstance().isRunning()) {
					Logger.getInstance().insertLog("threadpool", "threadpool is running");
					return;
				}
				
				TaskThreadpool.getInstance().clearTask();
				
				int idx = actionPane.getSelectedIndex();
				
				switch (idx) {
				case 0:	//domain
				{
					int[] idxes = domainJList.getSelectedIndices();
					
					if (idxes.length != 0) 
						TaskThreadpool.getInstance().startAllThread();
					
					for (int i=0; i<idxes.length; i++) {
						String tmp = domainJList.getModel().getElementAt(idxes[i]).toString();
						String clsname = ActionMap.getInstance().getActionClass("domain."+tmp);
						for (int j=0; j<targetModel.size(); j++) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.get(j);
							param.clsname = clsname;
							
							TaskThreadpool.getInstance().addTask(param);
						}
					}
					
					TaskThreadpool.getInstance().startAllThread();
					
					break;
				}
				case 1:	//subdomain
				{
					int[] idxes = subdomainJList.getSelectedIndices();
					
					for (int i=0; i<idxes.length; i++) {
						String tmp = subdomainJList.getModel().getElementAt(idxes[i]).toString();
						String clsname = ActionMap.getInstance().getActionClass("subdomain."+tmp);
						
						if (tmp.equals("get_homepage")) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.toString();
							param.clsname = clsname;
							
							try {
								Class cls = Class.forName(param.clsname);
								Method method1 = cls.getMethod("clearQueue", new Class[] {});
								method1.invoke(cls.newInstance());
								Method method2 = cls.getMethod("insertQueue", new Class[] {String.class});
								
								for (int j=0; j<targetModel.size(); j++) {
									method2.invoke(cls.newInstance(), targetModel.get(j));	
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								continue;
							}
							
							TaskThreadpool.getInstance().addTask(param);
							
							continue;
						}
						
						for (int j=0; j<targetModel.size(); j++) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.get(j);
							param.clsname = clsname;
							
							TaskThreadpool.getInstance().addTask(param);
						}
					}
					
					TaskThreadpool.getInstance().startAllThread();
					
					break;
				}
				case 2:	//ip
				{
					int[] idxes = ipJList.getSelectedIndices();
					
					for (int i=0; i<idxes.length; i++) {
						String tmp = ipJList.getModel().getElementAt(idxes[i]).toString();
						String clsname = ActionMap.getInstance().getActionClass("ip."+tmp);
						
						for (int j=0; j<targetModel.size(); j++) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.get(j);
							param.clsname = clsname;
							
							TaskThreadpool.getInstance().addTask(param);
						}
					}
					
					TaskThreadpool.getInstance().startAllThread();
					
					break;
				}
				case 3:	//url
				{
					int[] idxes = urlJList.getSelectedIndices();
					
					for (int i=0; i<idxes.length; i++) {
						String tmp = urlJList.getModel().getElementAt(idxes[i]).toString();
						String clsname = ActionMap.getInstance().getActionClass("url."+tmp);
						
						if (tmp.equals("timebase_sqlinject")) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.toString();
							param.clsname = clsname;
							
							try {
								Class cls = Class.forName(param.clsname);
								Method method1 = cls.getMethod("clearQueue", new Class[] {});
								method1.invoke(cls.newInstance());
								Method method2 = cls.getMethod("insertQueue", new Class[] {String.class});
								
								for (int j=0; j<targetModel.size(); j++) {
									method2.invoke(cls.newInstance(), targetModel.get(j));	
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								continue;
							}
							
							TaskThreadpool.getInstance().addTask(param);
							
							continue;
						}
						
						for (int j=0; j<targetModel.size(); j++) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.get(j);
							param.clsname = clsname;
							
							TaskThreadpool.getInstance().addTask(param);
						}
					}
					
					TaskThreadpool.getInstance().startAllThread();
					
					break;
				}
				case 4:	//bruter
				{
					int[] idxes = bruterJList.getSelectedIndices();
					
					for (int i=0; i<idxes.length; i++) {
						String tmp = bruterJList.getModel().getElementAt(idxes[i]).toString();
						String clsname = ActionMap.getInstance().getActionClass("bruter."+tmp);
						
						if (tmp.equals("rsync_bruter")) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.toString();
							param.clsname = clsname;
							
							try {
								Class cls = Class.forName(param.clsname);
								Method method1 = cls.getMethod("clearQueue", new Class[] {});
								method1.invoke(cls.newInstance());
								Method method2 = cls.getMethod("insertQueue", new Class[] {String.class});
								
								for (int j=0; j<targetModel.size(); j++) {
									method2.invoke(cls.newInstance(), targetModel.get(j));	
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								continue;
							}
							
							TaskThreadpool.getInstance().addTask(param);
							
							continue;
						}
						
						for (int j=0; j<targetModel.size(); j++) {
							TaskParam param = new TaskParam();
							param.arg = targetModel.get(j);
							param.clsname = clsname;
							
							TaskThreadpool.getInstance().addTask(param);
						}
					}
					
					TaskThreadpool.getInstance().startAllThread();
					
					break;
				}
				default:
					break;
				}
			}
			
		});
		targetPanel.add(starttargetJButton);
		
		stoptargetJButton = new JButton("Stop");
		stoptargetJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TaskThreadpool.getInstance().stopAllThread();
			}
		});
		targetPanel.add(stoptargetJButton);
		
		targetModel = new DefaultListModel<String>();
		targetJList = new JList(targetModel);
		targetScrollPanel = new JScrollPane(targetJList);
		toprighttopPanel.add(targetScrollPanel, BorderLayout.CENTER);
		
		toprightbottomPanel = new JPanel();
		toprightPanel.add(toprightbottomPanel);
		toprightbottomPanel.setLayout(new BorderLayout(0, 0));
		
		resultPanel = new JPanel();
		toprightbottomPanel.add(resultPanel, BorderLayout.NORTH);
		
		saveresultJButton = new JButton("Save");
		saveresultJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.showDialog(new JLabel(), "Ñ¡Ôñ");  
		        File file=jfc.getSelectedFile();
		        
		        try {
		        	FileOutputStream out=new FileOutputStream(file,false);
		            StringBuffer sb=new StringBuffer();
		            
		            for (int i=0; i<resultModel.size(); i++) {
		            	sb.append(resultModel.get(i));
		            	sb.append("\n");
		            }
					
		            out.write(sb.toString().getBytes("utf-8"));
		            out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		resultPanel.add(saveresultJButton);
		
		clearresultJButton = new JButton("Clear");
		clearresultJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				resultModel.clear();
			}
			
		});
		resultPanel.add(clearresultJButton);
		
		settargetresultJButton = new JButton("SetTarget");
		settargetresultJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				targetModel.clear();
				for (int i=0; i<resultModel.size(); i++) {
					targetModel.addElement(resultModel.get(i));
				}
				
				JScrollBar bar=targetScrollPanel.getVerticalScrollBar();
				bar.setValue(bar.getMaximum());
			}
			
		});
		resultPanel.add(settargetresultJButton);
		
		updateresultJButton = new JButton("Update");
		updateresultJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JScrollBar bar=resultScrollPanel.getVerticalScrollBar();
				bar.setValue(bar.getMaximum());
				
				resultJList.updateUI();
			}
			
		});
		resultPanel.add(updateresultJButton);
		
		resultModel = new DefaultListModel<String>();
		resultJList = new JList(resultModel);
		resultScrollPanel = new JScrollPane(resultJList);
		toprightbottomPanel.add(resultScrollPanel, BorderLayout.CENTER);
		topPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{actionPane}));
		
		bottomPanel = new JPanel();
		bottomPanel.setToolTipText("");
		contentPane.add(bottomPanel);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		logPanel = new JPanel();
		bottomPanel.add(logPanel, BorderLayout.NORTH);
		
		savelogJButton = new JButton("Save");
		savelogJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.showDialog(new JLabel(), "Ñ¡Ôñ");  
		        File file=jfc.getSelectedFile();
		        
		        try {
		        	FileOutputStream out=new FileOutputStream(file,false);
		            StringBuffer sb=new StringBuffer();
		            
		            for (int i=0; i<logModel.size(); i++) {
		            	sb.append(logModel.get(i));
		            	sb.append("\n");
		            }
					
		            out.write(sb.toString().getBytes("utf-8"));
		            out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		logPanel.add(savelogJButton);
		
		clearlogJButton = new JButton("Clear");
		clearlogJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logModel.clear();
			}
			
		});
		logPanel.add(clearlogJButton);
		
		updatelogJButton = new JButton("Update");
		updatelogJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JScrollBar bar=logScrollPanel.getVerticalScrollBar();
				bar.setValue(bar.getMaximum());
				
				logJList.updateUI();
			}
			
		});
		logPanel.add(updatelogJButton);
		
		logModel = new DefaultListModel<String>();
		logJList = new JList(logModel);
		logScrollPanel = new JScrollPane(logJList);
		bottomPanel.add(logScrollPanel, BorderLayout.CENTER);
		
		statusPanel = new JPanel();
		statusPanel.setLayout(new FlowLayout());
		bottomPanel.add(statusPanel, BorderLayout.SOUTH);
		
		threadnumTextField = new JTextField();
		threadnumTextField.setColumns(15);
		threadnumTextField.setEditable(false);
		statusPanel.add(threadnumTextField);
		
		queuenumTextField = new JTextField();
		queuenumTextField.setColumns(15);
		queuenumTextField.setEditable(false);
		statusPanel.add(queuenumTextField);
		
		insertActionMap();
	}
	
	private void insertActionMap() {
		HashMap<String, String> map = ActionMap.getInstance().getMap();
		
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			String key = (String)entry.getKey();
			
			String[] s = key.split("\\.");
			if (s[0].equals("domain")) {
				insertDomainAction(s[1]);
			} else if (s[0].equals("subdomain")) {
				insertSubdomainAction(s[1]);
			} else if (s[0].equals("ip")) {
				insertIpAction(s[1]);
			} else if (s[0].equals("url")) {
				insertUrlAction(s[1]);
			} else if (s[0].equals("bruter")) {
				insertBruterAction(s[1]);
			}
		}
	}
	
	private void insertDomainAction(String action) {
		domainModel.addElement(action);
	}
	
	private void insertSubdomainAction(String action) {
		subdomainModel.addElement(action);
	}
	
	private void insertIpAction(String action) {
		ipModel.addElement(action);
	}
	
	private void insertUrlAction(String action) {
		urlModel.addElement(action);
	}
	
	private void insertBruterAction(String action) {
		bruterModel.addElement(action);
	}
	
	public void insertTarget(String str) {
		if (!targetModel.contains(str)) {
			targetModel.addElement(str);
			JScrollBar bar=targetScrollPanel.getVerticalScrollBar();
			bar.setValue(bar.getMaximum());
		}
	}
	
	public void insertResult(String str) {
		if (!resultModel.contains(str))
		{
			resultModel.addElement(str);
			JScrollBar bar=resultScrollPanel.getVerticalScrollBar();
			bar.setValue(bar.getMaximum());
		}
	}
	
	public void insertLog(String str) {
		logModel.addElement(str);
		
		JScrollBar bar=logScrollPanel.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}
	
	public void showThreadnum(String value) {
		threadnumTextField.setText(value);
	}
	
	public void showQueuenum(String value) {
		queuenumTextField.setText(value);
	}
}
