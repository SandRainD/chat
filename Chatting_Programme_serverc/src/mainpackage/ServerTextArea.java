package mainpackage;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/** 
	 * 为服务器窗口提供聊天信息文本域
	 */
public class ServerTextArea extends JTextArea{
	/*
	 * 继承JText类，设定为主窗体的接收消息面板
	 */
	private static final long serialVersionUID = -3761139989650401861L;
	private static JScrollPane jcp1=null;
	private static ServerTextArea serverJtextArea=new ServerTextArea();
	private ServerTextArea() {
		super();
		setFont(new Font("楷体",Font.PLAIN,16));//设置文本格式
		setText("字体:楷体,字号:16");
		setBackground(Color.orange);
		setForeground(Color.blue);
//		setRows(0);//设定行数
//		setColumns(0);
	}
	
	protected static JScrollPane getServerJScrollPanel() {//给主窗体返回1个带文本域的JScrollPane实例
		if(jcp1==null) {
			jcp1=new JScrollPane(serverJtextArea);
			//设置滚动条可用时显示
			jcp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    jcp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jcp1.setBounds(5,5,350,555);//设置大小
		}
		return jcp1;
	}
	protected static ServerTextArea getServerTextArea() {
		return serverJtextArea;
	}
}