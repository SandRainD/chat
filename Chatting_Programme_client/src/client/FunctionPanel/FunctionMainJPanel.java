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
	 * ������壬�������
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
		setLayout(new BorderLayout());//���ò��ַ�ʽ
		
		jtextfield=new JTextField("���ڴ������������");//����ı���
		jtextfield.setFont(new Font("����",Font.PLAIN,15));//�����ı�������
		jtextfield.setColumns(5);//�����ı��򳤶�
		jtextfield.setForeground(Color.RED);
		jtextfield.addMouseListener(new MouseListener() {//�����ı�����Ӽ���
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("����ɿ�");
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("��갴��");
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