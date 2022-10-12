package client.FunctionPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class FunctionMainJPanel extends JPanel{
	/**
	 * 工具面板，操作面板
	 */
	private static final long serialVersionUID = 8062637495723674346L;
	private JTextField jtextfield=null;
	private static String name=null;
	private static FunctionMainJPanel functionmainjpanel=new FunctionMainJPanel();
	
	private ButtonScanner b1=null;
	private ProtScannnerPanel p1=ProtScannnerPanel.getpscpanenl();
	private JPanel jpanel1=null; 
	private FunctionMainJPanel() {
		super();
		setLayout(new BorderLayout());//设置布局方式
		
		jtextfield=new JTextField("请在此输入你的名字");//添加文本框
		jtextfield.setFont(new Font("黑体",Font.PLAIN,15));//设置文本框字体
		jtextfield.setColumns(5);//设置文本框长度
		jtextfield.setForeground(Color.RED);
		jtextfield.addMouseListener(new MouseListener() {//名字文本框添加监听
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("鼠标松开");
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("鼠标按下");
				jtextfield.setText("");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				name=jtextfield.getText();
				jtextfield.setForeground(Color.green);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				jtextfield.setForeground(Color.orange);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		b1=ButtonScanner.getButtonScanner();
		jpanel1=new JPanel();
		jpanel1.add(b1);
		
		add(jtextfield,BorderLayout.NORTH);
		add(p1,BorderLayout.CENTER);
		add(jpanel1,BorderLayout.SOUTH);
		setBounds(358,5,380,390);
		setVisible(true);
	}
	public static FunctionMainJPanel getFunctionMainJPanel() {
		return functionmainjpanel;
	}
	public static String getnametext() {
		return name;
	}	
}