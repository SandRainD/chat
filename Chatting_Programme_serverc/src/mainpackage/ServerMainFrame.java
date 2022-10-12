package mainpackage;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
public class ServerMainFrame extends JFrame {
	/**
	 * 服务器主窗口
	 */
	private static final long serialVersionUID = 646639569886976982L;
	public static final int port = 12345;// 设置端口号
	public static List<Socket> ClientList = null;
	public static List<MyThread> threadlist = null;
	private ServerSocket serversocket = null;

	private static ServerMainFrame mainwindows = null;
	private JScrollPane jcp1 = null;
	private static ServerTextArea serverTextArea = null;
	private SenderJPanel senderjpanel = null;
	private static PrintWriter output = null;
	private static String name=null;
	private static File file1=new File("a.txt");
	private static FileWriter fw=null;
	private ServerMainFrame() {
		super();
		setTitle("服务器程序");// 设置标题
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 设置关闭动作，关闭窗体，停止程序
		setBounds(300, 100, 750, 600);// 设置窗体大小和位置
		setResizable(false);// 不可更改窗体大小
		Container c = getContentPane();// 获取窗体容器
		c.setLayout(null);// 设置绝对布局
		c.setBackground(Color.gray);// 设置背景颜色

		// 添加组件
		jcp1 = ServerTextArea.getServerJScrollPanel();// 添加1个文本域，设置为聊天接收消息面板
		serverTextArea = ServerTextArea.getServerTextArea();// 获取文本域，以便追加内容
		getInternetAddress();// 输出一下地址
		senderjpanel = SenderJPanel.getSenderJPanel();// 获取输入面板

		//为主窗体添加窗体事件监听
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				//JOptionPane.showMessageDialog(ServerMainFrame.this, "欢迎使用");
			}
			@Override
			public void windowDeiconified(WindowEvent e) {

			}
			@Override
			public void windowDeactivated(WindowEvent e) {

			}
			@Override
			public void windowClosing(WindowEvent e) {// 窗体将要关闭时触发
				int option = JOptionPane.showConfirmDialog(ServerMainFrame.this, "确定退出系统? ", "提示 ",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == JOptionPane.YES_OPTION)
					if (e.getWindow() == ServerMainFrame.this) {
						System.exit(0);
					} else {
						return;
					}
			}
			@Override
			public void windowClosed(WindowEvent e) {
			}
			@Override
			public void windowActivated(WindowEvent e) {
			}
			@Override
			public void windowIconified(WindowEvent e) {
			}
		});
		//添加组件
		c.add(senderjpanel);
		c.add(jcp1);
		setVisible(true);
		threadlist = new ArrayList<MyThread>();
		ClientList = new ArrayList<Socket>();
		if(!file1.exists()) {
			try {
				boolean f=file1.createNewFile();
				System.out.println("创建文件"+f);
				fw=new FileWriter(file1,true);
			} catch (IOException e1) {
				System.out.println("无法创建文件");
			}
		}else {
			try {
				fw=new FileWriter(file1,true);
			} catch (IOException e1) {
				System.out.println("文件错误");
			}
		}
		
		try {
			serversocket = new ServerSocket(port);
			while (true) {// 开启多个线程
				Socket client = serversocket.accept();// 接收来自客户端的连接，且是阻塞处
				ClientList.add(client);
				// 创建线程
				MyThread thread = new MyThread(client, ClientList);
				threadlist.add(thread);
				thread.start();// 开启线程任务
			}
		} catch (IOException e1) {
//				e1.printStackTrace();
			System.out.println("创建服务器套接字异常");
			System.exit(0);
		}
		
	}
	protected static void serverTextAreaappend(String message) {//消息框追加内容
		serverTextArea.append(message+"\n");
	}
	private void getInternetAddress() {// 获取服务器地址
		try {
			InetAddress host = InetAddress.getLocalHost();
			String hostAddress = host.getHostAddress();
			System.out.println("服务器(本机)地址：" + hostAddress + "\n");
			serverTextAreaappend("服务器地址：" + hostAddress + "\n-------------------------------------------");
		} catch (UnknownHostException e) {
			System.out.println("获取本地地址异常");
		}
	}
	public static void main(String[] args) {
		mainwindows = new ServerMainFrame();
	}
	protected static ServerMainFrame getmainwindows() {// 它类获取主窗体的方法
		return mainwindows;
	}
	protected static void send_group_message(String buttonmessage) {// 向客户端群发消息
		if (buttonmessage != "") {
			System.out.println("输入框消息："+buttonmessage);
			serverTextAreaappend("服务器消息：" + buttonmessage);
			appenddata("服务器消息"+buttonmessage);
			for (int i = 0; i < ClientList.size(); i++) {
				try {
					output = new PrintWriter(ClientList.get(i).getOutputStream(), true);
					output.println("服务器消息：" + buttonmessage);
					output.flush();//刷新缓冲区
				} catch (IOException e) {
//					e.printStackTrace();
					serverTextAreaappend("客户端："+ClientList.get(i).getInetAddress()+"断开连接");
					try {
						ClientList.get(i).close();
						ClientList.remove(i);
						threadlist.get(i).interrupt();
						threadlist.remove(i);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(mainwindows, "未知错误");
						System.exit(0);
					}
				}
				
			}
		}
	}
	protected static void send_no_message(String buttonmessage) {// 向客户端群发消息（不加修饰信息）
		if (buttonmessage != "") {
			serverTextAreaappend(buttonmessage);
			appenddata(buttonmessage);
			for (int i = 0; i < ClientList.size(); i++) {
				try {
					output = new PrintWriter(ClientList.get(i).getOutputStream(), true);
					output.println(buttonmessage);
					output.flush();//刷新缓冲区
				} catch (IOException e) {
//					e.printStackTrace();
					System.out.println("客户端："+ClientList.get(i).getInetAddress()+"断开连接");
					try {
						ClientList.get(i).close();
						ClientList.remove(i);
						threadlist.get(i).interrupt();
						threadlist.remove(i);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(mainwindows,"错误" ,"未知错误",JOptionPane.WARNING_MESSAGE);
						System.exit(0);
					}
				}	
			}
		}
	}
	protected static void CheckCloseCliet() {//检查服务器与客户端的连接
		for (int i = 0; i < ClientList.size(); i++) {
			if(ClientList.get(i).isConnected()) {
				
			}else {
				try {
					ClientList.get(i).close();
					ClientList.remove(i);
					threadlist.get(i).interrupt();
					threadlist.remove(i);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(mainwindows,"错误" ,"未知错误",JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}
				serverTextAreaappend("客户端："+ClientList.get(i).getInetAddress()+name+"断开连接");
				send_no_message("客户端："+ClientList.get(i).getInetAddress()+name+"断开连接");
			}
		}
	}
	private static void appenddata(String message) {
		Calendar d1=new GregorianCalendar();
		int month =d1.get(Calendar.MONTH)+1;
		int date1=d1.get(Calendar.DAY_OF_MONTH);
		int hour=d1.get(Calendar.HOUR_OF_DAY);
		int min=d1.get(Calendar.MINUTE);
		int sec=d1.get(Calendar.SECOND);
		String date=month+" "+date1+" "+hour+":"+min+":"+sec+"记录:"+message+"\n";
		try {
			fw.write(date);
			fw.flush();
		} catch (IOException e) {
			System.out.println("无法将聊天记录写入文件中");
		}
	}
}