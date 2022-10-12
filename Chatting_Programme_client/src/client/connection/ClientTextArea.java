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
		setFont(new Font("楷体",Font.PLAIN,16));//设置文本格式
		setText("字体:楷体,字号:16");
		setBackground(Color.orange);
		setForeground(Color.blue);
//		setRows(0);//设定行数
//		setColumns(0);
	}
	
	protected static JScrollPane getClientJScrollPanel() {//给主窗体返回1个带文本域的JScrollPane实例
		if(jcp1==null) {
			jcp1=new JScrollPane(clientJtextArea);
			//设置滚动条可用时显示
			jcp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    jcp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jcp1.setBounds(5,5,350,555);//设置大小
		}
		return jcp1;
	}
	protected static ClientTextArea getClientTextArea() {
		return clientJtextArea;
	}
}