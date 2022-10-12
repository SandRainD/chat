package client.FunctionPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import client.connection.ClientMainFrame;

public class ThreadPort extends Thread {
	private int address;
	private BufferedReader br=null;
	private ProtScannnerPanel p1=null;
	public ThreadPort(int address) {
		this.address = address;
	}
	@Override
	public void run() {
		Socket clientsocket;
		for (int i = 0; i < 5; i++) {
			try {
				clientsocket = new Socket();
				SocketAddress socketAddress = new InetSocketAddress("192.168.1."+address,12345); //获取sockaddress对象
				clientsocket.connect(socketAddress, 1000);
				p1=ProtScannnerPanel.getpscpanenl();
				p1.append("\n搜索到服务器192.168.1."+address);
				ClientMainFrame.getConnectionyet(clientsocket);
//				ClientMainFrame.sendindexnumber=4;
//				ClientMainFrame.send_normal_message("");
//				ClientMainFrame.sendindexnumber=1;
//				ClientMainFrame.getconnetiontoserver("192.168.1."+address);
				address++;
			} catch (IOException e) {
				System.out.println("192.168.1." + address+"未找到");
				address++;
			}

		}
	}
	protected Socket usefulsocket(Socket clientsocket) {
		return clientsocket;
	}
}
