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
	private static String serveraddress = "171.13.36.210";// �����ڵ�ַ
	public static final int port = 12345;// ���ö˿ں�
	private static Socket clientsocket = null;//���������յ��Ŀͻ����׽���
	private static ClientMainFrame mainwindows = null;//������
	private JScrollPane jcp1 = null;
	private static ClientTextArea clientTextArea = null;
	private SenderJPanel senderjpanel = null;
	private static PrintWriter output = null;//������Ϣ�������
	private FunctionMainJPanel functionpanel=null;
	private ClientMainFrame() {
		super();
		setTitle("�ͻ��˳���");// ���ñ���
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// ���ùرն������رմ��壬ֹͣ����
		setBounds(300, 100, 750, 600);// ���ô����С��λ��
		setResizable(false);// ���ɸ��Ĵ����С
		Container c = getContentPane();// ��ȡ��������
		c.setLayout(null);// ���þ��Բ���
		c.setBackground(Color.gray);// ���ñ�����ɫ
		// ������
		jcp1 = ClientTextArea.getClientJScrollPanel();// ���1���ı�������Ϊ���������Ϣ���
		clientTextArea = ClientTextArea.getClientTextArea();// ��ȡ�ı����Ա�׷������
		getInternetAddress(clientTextArea);// ���һ�µ�ַ
		senderjpanel = SenderJPanel.getSenderJPanel();// ��ȡ�������
		functionpanel=FunctionMainJPanel.getFunctionMainJPanel();
		// Ϊ��������Ӵ����¼�����
		addWindowListener(new WindowListener() {//���ڼ���
			@Override
			public void windowOpened(WindowEvent e) {
				// JOptionPane.showMessageDialog(ClientMainFrame.this, "��ӭʹ��");
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			@Override
			public void windowClosing(WindowEvent e) {// ���彫Ҫ�ر�ʱ����
				int option = JOptionPane.showConfirmDialog(ClientMainFrame.this, "ȷ���˳�ϵͳ? ", "��ʾ ",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (option == JOptionPane.YES_OPTION)
					if (e.getWindow() == ClientMainFrame.this) {
//						InetAddress host;
						try {
							sendindexnumber=2;
							send_normal_message("");
//							host = InetAddress.getLocalHost();
//							String hostAddress = host.getHostAddress();
//							send_normal_message(hostAddress+"�Ͽ�����");
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
		//������
		c.add(senderjpanel);
		c.add(jcp1);
		c.add(functionpanel);
		setVisible(true);
		getconnnetion_server();// �����������������
	}
	private void getInternetAddress(ClientTextArea clientTextArea) {// ��ȡ��������ַ
		try {
			InetAddress host = InetAddress.getLocalHost();
			String hostAddress = host.getHostAddress();
			System.out.println("������ַ��" + hostAddress + "\n");
			clientTextArea.append("������ַ��" + hostAddress + "\n------------------------------------------\n");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		mainwindows = new ClientMainFrame();
	}
	public static ClientMainFrame getmainwindows() {// �����ȡ������ķ���
		return mainwindows;
	}
	public static void send_normal_message(String getsendermessage) {// �����������ĳĳ����Ϣ
		if(clientsocket!=null) {
			try {
				String name=FunctionMainJPanel.getnametext();
				if(name==null|name=="���ڴ������������") {
					JOptionPane.showMessageDialog(mainwindows, "�����������ǳ�");
					return;
				}
				output = new PrintWriter(clientsocket.getOutputStream(), true);
				switch(sendindexnumber) {
				case 1:
					output.println("1");
					output.println(name+":"+getsendermessage);
					System.out.println("�����ˣ�" + getsendermessage);
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
					JOptionPane.showMessageDialog(null, "error","����ͻ���error ox",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				if(sendindexnumber==1) {
					
				}else if(sendindexnumber==2) {
					
				}else {
					
				}
			} catch (IOException e) {
				System.out.println("δ����");
			}
			
		}
	}
	protected static void clientTextAreaappendnormalmessage(String message) {
		clientTextArea.append(message+"\n");
	}
	private void getconnnetion_server() {// �����������������
		try {
			clientsocket = new Socket(serveraddress, port);
			CThread thread = new CThread(clientsocket);
			thread.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����δ��֪��ԭ���޷����������������\n"
					+ "����ϵ����Ա���Ժ�����","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void getconnetiontoserver(String address) {//���ഴ������...
		try {
			clientsocket = new Socket(address, port);
			CThread thread = new CThread(clientsocket);
			thread.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "��������ʧ�ܣ�����ͻ���","����",JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void getConnectionyet(Socket socket) {
		clientsocket = socket;
		CThread thread = new CThread(clientsocket);
		thread.start();
	}
}