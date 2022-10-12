package client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;

public class CThread extends Thread {
	private BufferedReader br = null;
	private String receivemessage = null;
	private Socket clientsocket = null;
	
	public CThread(Socket clientsocket) {
		this.clientsocket=clientsocket;
	}
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			while (true) {
				receivemessage = br.readLine();
				if (receivemessage != null) {
					System.out.println(receivemessage);
					ClientMainFrame.clientTextAreaappendnormalmessage(receivemessage);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����δ��֪��ԭ����������Ͽ�����\n"
					+ "����ϵ����Ա���Ժ�����","����",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}finally {
			if(br!=null)
			{
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("δ֪����");
				}
			}
		}

	}
}
