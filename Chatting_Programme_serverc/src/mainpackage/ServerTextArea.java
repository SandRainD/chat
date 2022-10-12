package mainpackage;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/** 
	 * Ϊ�����������ṩ������Ϣ�ı���
	 */
public class ServerTextArea extends JTextArea{
	/*
	 * �̳�JText�࣬�趨Ϊ������Ľ�����Ϣ���
	 */
	private static final long serialVersionUID = -3761139989650401861L;
	private static JScrollPane jcp1=null;
	private static ServerTextArea serverJtextArea=new ServerTextArea();
	private ServerTextArea() {
		super();
		setFont(new Font("����",Font.PLAIN,16));//�����ı���ʽ
		setText("����:����,�ֺ�:16");
		setBackground(Color.orange);
		setForeground(Color.blue);
//		setRows(0);//�趨����
//		setColumns(0);
	}
	
	protected static JScrollPane getServerJScrollPanel() {//�������巵��1�����ı����JScrollPaneʵ��
		if(jcp1==null) {
			jcp1=new JScrollPane(serverJtextArea);
			//���ù���������ʱ��ʾ
			jcp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    jcp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jcp1.setBounds(5,5,350,555);//���ô�С
		}
		return jcp1;
	}
	protected static ServerTextArea getServerTextArea() {
		return serverJtextArea;
	}
}