package client.connection;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientTextArea extends JTextArea{
	private static final long serialVersionUID = -3761139989650401861L;
	private static JScrollPane jcp1=null;
	private static ClientTextArea clientJtextArea=new ClientTextArea();
	private ClientTextArea() {
		super();
		setFont(new Font("����",Font.PLAIN,16));//�����ı���ʽ
		setText("����:����,�ֺ�:16");
		setBackground(Color.orange);
		setForeground(Color.blue);
//		setRows(0);//�趨����
//		setColumns(0);
	}
	
	protected static JScrollPane getClientJScrollPanel() {//�������巵��1�����ı����JScrollPaneʵ��
		if(jcp1==null) {
			jcp1=new JScrollPane(clientJtextArea);
			//���ù���������ʱ��ʾ
			jcp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    jcp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jcp1.setBounds(5,5,350,555);//���ô�С
		}
		return jcp1;
	}
	protected static ClientTextArea getClientTextArea() {
		return clientJtextArea;
	}
}