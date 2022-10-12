package client.connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SenderJPanel extends JPanel {
	/**
	 * 文本发送框
	 */
	private static final long serialVersionUID = -6230278867763713697L;	
	private static SenderJPanel senderjpanel=null;
	private static JTextArea messagejta1=null;
	private JPanel jp2=null;
	private JButton senderbutton=null;
	private JButton closebutton=null;
	private JButton colorchoosebutton=null;
	private JScrollPane jcp1=null;
	private String buttonmessage=null;
	public static Color newColor=null;
	private SenderJPanel() {
		super();
		setLayout(new BorderLayout());//设置边界布局
		messagejta1=new JTextArea();
		messagejta1.setFont(new Font("楷体",Font.PLAIN,20));
		messagejta1.setRows(5);//设置行数
        messagejta1.setColumns(20);//设置列数
        //为输入面板添加监听
        messagejta1.addKeyListener(new MyKeyEventListener());//为输入板添加键盘事件监听器
        messagejta1.setLineWrap(true);//设置文本框自动换行
        jcp1=new JScrollPane(messagejta1);
        
        JPanel jp2=new JPanel();
        jp2.setBounds(0,350,345,120);
        jp2.setLayout(new FlowLayout(FlowLayout.RIGHT,20,2));
        
        colorchoosebutton=new JButton("更改字体颜色");
        closebutton=new JButton("关闭");
        senderbutton=new JButton("发送");
        senderbutton.setBackground(Color.gray);
        closebutton.setBackground(Color.gray);
        closebutton.setBorder(BorderFactory.createLineBorder(Color.RED));//设置边框颜色
        senderbutton.setBorder(BorderFactory.createLineBorder(Color.green));
        closebutton.setToolTipText("关闭窗口端，停止程序");//鼠标悬停提示
        senderbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//调用主窗体消息群发方法
				ClientMainFrame.send_normal_message(getsendermessage(buttonmessage));
			}
		});
        closebutton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e){//监听触发的方法
	               JOptionPane.showMessageDialog(ClientMainFrame.getmainwindows(),"退出程序");//弹出小对话框
	               System.exit(0);
	           } 
		});
        colorchoosebutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colorchoosebuttonsetColorMethod(messagejta1);
			}
		});
        jp2.add(colorchoosebutton);
        jp2.add(closebutton);
        jp2.add(senderbutton);
        add(jcp1,BorderLayout.NORTH);
        add(jp2,BorderLayout.CENTER);
        setBounds(358, 400,380,160);
	}	
	protected void colorchoosebuttonsetColorMethod(JTextArea messagejta1) {
		Color color = messagejta1.getBackground();//获取原来的颜色对象
        //显示颜色选择对话框
        newColor = JColorChooser.showDialog(this, "选择颜色", color);
        messagejta1.setForeground(newColor);//把获取的颜色设置为标签的背景色
	}
	protected static SenderJPanel getSenderJPanel() {
		if(senderjpanel==null) {
			senderjpanel=new SenderJPanel();
			senderjpanel.setVisible(true);
		}
		return senderjpanel;
	}
	protected static String getsendermessage(String message) {//清空发送栏消息，返回消息内容
		message=messagejta1.getText();
		messagejta1.setText("");
		return message;
	}
	protected static void clearsendermessage() {//清空发送栏消息
		messagejta1.setText("");
	}

}
