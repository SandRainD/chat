package mainpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

class MyThread extends Thread {
	private String joinmessage = null;
	private String normalmessage = null;
	private String indexmessage=null;
	private Socket clientsocket = null;
	private List<Socket> ClientList = null;
	private BufferedReader br = null;
	public MyThread(Socket clientsocket, List<Socket> ClientList) {
		this.clientsocket = clientsocket;
		this.ClientList = ClientList;
	}

	@Override
	public void run() {
		try {
			ServerMainFrame.CheckCloseCliet();
			System.out.println("***************");
			joinmessage = new String(clientsocket.getInetAddress() + "����������" );
			ServerMainFrame.send_no_message(joinmessage);
			joinmessage=new String("��ǰ�����ҹ���" + ClientList.size() + "��" );
			ServerMainFrame.send_no_message(joinmessage);
			br = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			while (true) {
				indexmessage = br.readLine();
				switch (indexmessage) {
				case "1":
					normalmessage = br.readLine();
					if (normalmessage != null) {
						System.out.println("���յ��ͻ�����Ϣ" + normalmessage + "\n");
						ServerMainFrame.send_no_message(normalmessage);
					}
					break;
				case "2":
					normalmessage = br.readLine();
					if (normalmessage != null) {
						ServerMainFrame.CheckCloseCliet();
					}
					break;
				case "3":
					normalmessage = br.readLine();
					if (normalmessage != null) {
						System.out.println("���յ��ͻ�����Ϣ" + normalmessage + "\n");
						ServerMainFrame.send_no_message(normalmessage);
					}
					break;
				case "4":
					OutputStream testoutput = clientsocket.getOutputStream();
					System.out.println("���յ����Կͻ��˵�����***************");
					testoutput.write(new String("4\n").getBytes());
					indexmessage="1";
					break;
				default:
					 System.out.println("����ͻ���");
				}
			}
		} catch (IOException e) {
			System.out.println(clientsocket.getInetAddress()+"�Ͽ�����");
			ServerMainFrame.CheckCloseCliet();
//			ServerMainFrame.send_no_message("���˶Ͽ�����");
//			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("2");
					e.printStackTrace();
				}
			}
			if(clientsocket!=null) {
				try {
					clientsocket.close();
				} catch (IOException e) {
					System.out.println("3");
					e.printStackTrace();
				}
			}
		}
	}
	
}
