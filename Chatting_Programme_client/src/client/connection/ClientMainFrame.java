package client.connection;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import client.FunctionPanel.FunctionMainJPanel;

public class ClientMainFrame extends JFrame {
	/**
	 * 
	 */
	public static int sendindexnumber=1;
	private static final long serialVersionUID = -471378893438086724L;
	private static String serveraddress = "171.13.36.210";// 服务期地址
	public static final int port = 12345;// 设置端口号
	private static Socket clientsocket = null;//服务器接收到的客户端套接字
	private static ClientMainFrame mainwindows = null;//主窗口
	private JScrollPane jcp1 = null;
	private static ClientTextArea clientTextArea = null;
	private SenderJPanel senderjpanel = null;
	private static PrintWriter output = null;//发送信息的输出流
	private FunctionMainJPanel functionpanel=null;
	private ClientMainFrame() {
		super();
		setTitle("客户端程序");// 设置标题
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 设置关闭动作，关闭窗体，停止程序
		setBounds(300, 100, 750, 600);// 设置窗体大小和位置
		setResizable(false);// 不可更改窗体大小
		Container c = getContentPane();// 获取窗体容器
		c.setLayout(null);// 设置绝对布局
		c.setBackground(Color.gray);// 设置背景颜色
		// 添加组件
		jcp1 = ClientTextArea.getClientJScrollPanel();// 添加1个文本域，设置为聊天接收消息面板
		clientTextArea = ClientTextArea.getClientTextArea();// 获取文本域，以便追加内容
		getInternetAddress(clientTextArea);// 输出一下地址
		senderjpanel = SenderJPanel.getSenderJPanel();// 获取输入面板
		functionpanel=FunctionMainJPanel.getFunctionMainJPanel();
		// 为主窗体添加窗体事件监听
		addWindowListener(new WindowListener() {//窗口监听
			@Override
			public void windowOpened(WindowEvent e) {
				// JOptionPane.showMessageDialog(ClientMainFrame.this, "欢迎使用");
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			@Override
			public void windowClosing(WindowEvent e) {// 窗体将要关闭时触发
				int option = JOptionPane.showConfirmDialog(ClientMainFrame.this, "确定退出系统? ", "提示 ",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == JOptionPane.YES_OPTION)
					if (e.getWindow() == ClientMainFrame.this) {
//						InetAddress host;
						try {
							sendindexnumber=2;
							send_normal_message("");
//							host = InetAddress.getLocalHost();
//							String hostAddress = host.getHostAddress();
//							send_normal_message(hostAddress+"断开连接");
						}
						finally {
							System.exit(0);
						}
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
		c.add(functionpanel);
		setVisible(true);
		getconnnetion_server();// 创建与服务器的连接
	}
	private void getInternetAddress(ClientTextArea clientTextArea) {// 获取服务器地址
		try {
			InetAddress host = InetAddress.getLocalHost();
			String hostAddress = host.getHostAddress();
			System.out.println("本机地址：" + hostAddress + "\n");
			clientTextArea.append("本机地址：" + hostAddress + "\n------------------------------------------\n");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		mainwindows = new ClientMainFrame();
	}
	public static ClientMainFrame getmainwindows() {// 它类获取主窗体的方法
		return mainwindows;
	}
	public static void send_normal_message(String getsendermessage) {// 向服务器发送某某的消息
		if(clientsocket!=null) {
			try {
				String name=FunctionMainJPanel.getnametext();
				if(name==null|name=="请在此输入你的名字") {
					JOptionPane.showMessageDialog(mainwindows, "请输入您的昵称");
					return;
				}
				output = new PrintWriter(clientsocket.getOutputStream(), true);
				switch(sendindexnumber) {
				case 1:
					output.println("1");
					output.println(name+":"+getsendermessage);
					System.out.println("发送了：" + getsendermessage);
					break;
				case 2:
					output.println("2");
					output.println(name);
					break;
				case 3:
					break;
				case 4:
					output.println("4");
					BufferedReader br = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
					String backmessage = br.readLine();
					System.out.println(backmessage);
					sendindexnumber=1;
					break;
				default:
					JOptionPane.showMessageDialog(null, "error","请检查客户端error ox",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				if(sendindexnumber==1) {
					
				}else if(sendindexnumber==2) {
					
				}else {
					
				}
			} catch (IOException e) {
				System.out.println("未连接");
			}
			
		}
	}
	protected static void clientTextAreaappendnormalmessage(String message) {
		clientTextArea.append(message+"\n");
	}
	private void getconnnetion_server() {// 创建与服务器的连接
		try {
			clientsocket = new Socket(serveraddress, port);
			CThread thread = new CThread(clientsocket);
			thread.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "由于未可知的原因无法与服务器建立连接\n"
					+ "请联系管理员或稍后重试","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void getconnetiontoserver(String address) {//它类创建连接...
		try {
			clientsocket = new Socket(address, port);
			CThread thread = new CThread(clientsocket);
			thread.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "尝试连接失败，请检查客户端","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void getConnectionyet(Socket socket) {
		clientsocket = socket;
		CThread thread = new CThread(clientsocket);
		thread.start();
	}
}