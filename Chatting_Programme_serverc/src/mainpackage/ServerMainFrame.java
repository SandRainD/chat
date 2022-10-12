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
	 * ������������
	 */
	private static final long serialVersionUID = 646639569886976982L;
	public static final int port = 12345;// ���ö˿ں�
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
		setTitle("����������");// ���ñ���
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// ���ùرն������رմ��壬ֹͣ����
		setBounds(300, 100, 750, 600);// ���ô����С��λ��
		setResizable(false);// ���ɸ��Ĵ����С
		Container c = getContentPane();// ��ȡ��������
		c.setLayout(null);// ���þ��Բ���
		c.setBackground(Color.gray);// ���ñ�����ɫ

		// ������
		jcp1 = ServerTextArea.getServerJScrollPanel();// ���1���ı�������Ϊ���������Ϣ���
		serverTextArea = ServerTextArea.getServerTextArea();// ��ȡ�ı����Ա�׷������
		getInternetAddress();// ���һ�µ�ַ
		senderjpanel = SenderJPanel.getSenderJPanel();// ��ȡ�������

		//Ϊ��������Ӵ����¼�����
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				//JOptionPane.showMessageDialog(ServerMainFrame.this, "��ӭʹ��");
			}
			@Override
			public void windowDeiconified(WindowEvent e) {

			}
			@Override
			public void windowDeactivated(WindowEvent e) {

			}
			@Override
			public void windowClosing(WindowEvent e) {// ���彫Ҫ�ر�ʱ����
				int option = JOptionPane.showConfirmDialog(ServerMainFrame.this, "ȷ���˳�ϵͳ? ", "��ʾ ",
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
		//������
		c.add(senderjpanel);
		c.add(jcp1);
		setVisible(true);
		threadlist = new ArrayList<MyThread>();
		ClientList = new ArrayList<Socket>();
		if(!file1.exists()) {
			try {
				boolean f=file1.createNewFile();
				System.out.println("�����ļ�"+f);
				fw=new FileWriter(file1,true);
			} catch (IOException e1) {
				System.out.println("�޷������ļ�");
			}
		}else {
			try {
				fw=new FileWriter(file1,true);
			} catch (IOException e1) {
				System.out.println("�ļ�����");
			}
		}
		
		try {
			serversocket = new ServerSocket(port);
			while (true) {// ��������߳�
				Socket client = serversocket.accept();// �������Կͻ��˵����ӣ�����������
				ClientList.add(client);
				// �����߳�
				MyThread thread = new MyThread(client, ClientList);
				threadlist.add(thread);
				thread.start();// �����߳�����
			}
		} catch (IOException e1) {
//				e1.printStackTrace();
			System.out.println("�����������׽����쳣");
			System.exit(0);
		}
		
	}
	protected static void serverTextAreaappend(String message) {//��Ϣ��׷������
		serverTextArea.append(message+"\n");
	}
	private void getInternetAddress() {// ��ȡ��������ַ
		try {
			InetAddress host = InetAddress.getLocalHost();
			String hostAddress = host.getHostAddress();
			System.out.println("������(����)��ַ��" + hostAddress + "\n");
			serverTextAreaappend("��������ַ��" + hostAddress + "\n-------------------------------------------");
		} catch (UnknownHostException e) {
			System.out.println("��ȡ���ص�ַ�쳣");
		}
	}
	public static void main(String[] args) {
		mainwindows = new ServerMainFrame();
	}
	protected static ServerMainFrame getmainwindows() {// �����ȡ������ķ���
		return mainwindows;
	}
	protected static void send_group_message(String buttonmessage) {// ��ͻ���Ⱥ����Ϣ
		if (buttonmessage != "") {
			System.out.println("�������Ϣ��"+buttonmessage);
			serverTextAreaappend("��������Ϣ��" + buttonmessage);
			appenddata("��������Ϣ"+buttonmessage);
			for (int i = 0; i < ClientList.size(); i++) {
				try {
					output = new PrintWriter(ClientList.get(i).getOutputStream(), true);
					output.println("��������Ϣ��" + buttonmessage);
					output.flush();//ˢ�»�����
				} catch (IOException e) {
//					e.printStackTrace();
					serverTextAreaappend("�ͻ��ˣ�"+ClientList.get(i).getInetAddress()+"�Ͽ�����");
					try {
						ClientList.get(i).close();
						ClientList.remove(i);
						threadlist.get(i).interrupt();
						threadlist.remove(i);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(mainwindows, "δ֪����");
						System.exit(0);
					}
				}
				
			}
		}
	}
	protected static void send_no_message(String buttonmessage) {// ��ͻ���Ⱥ����Ϣ������������Ϣ��
		if (buttonmessage != "") {
			serverTextAreaappend(buttonmessage);
			appenddata(buttonmessage);
			for (int i = 0; i < ClientList.size(); i++) {
				try {
					output = new PrintWriter(ClientList.get(i).getOutputStream(), true);
					output.println(buttonmessage);
					output.flush();//ˢ�»�����
				} catch (IOException e) {
//					e.printStackTrace();
					System.out.println("�ͻ��ˣ�"+ClientList.get(i).getInetAddress()+"�Ͽ�����");
					try {
						ClientList.get(i).close();
						ClientList.remove(i);
						threadlist.get(i).interrupt();
						threadlist.remove(i);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(mainwindows,"����" ,"δ֪����",JOptionPane.WARNING_MESSAGE);
						System.exit(0);
					}
				}	
			}
		}
	}
	protected static void CheckCloseCliet() {//����������ͻ��˵�����
		for (int i = 0; i < ClientList.size(); i++) {
			if(ClientList.get(i).isConnected()) {
				
			}else {
				try {
					ClientList.get(i).close();
					ClientList.remove(i);
					threadlist.get(i).interrupt();
					threadlist.remove(i);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(mainwindows,"����" ,"δ֪����",JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}
				serverTextAreaappend("�ͻ��ˣ�"+ClientList.get(i).getInetAddress()+name+"�Ͽ�����");
				send_no_message("�ͻ��ˣ�"+ClientList.get(i).getInetAddress()+name+"�Ͽ�����");
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
		String date=month+" "+date1+" "+hour+":"+min+":"+sec+"��¼:"+message+"\n";
		try {
			fw.write(date);
			fw.flush();
		} catch (IOException e) {
			System.out.println("�޷��������¼д���ļ���");
		}
	}
}