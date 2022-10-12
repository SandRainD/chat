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
	 * �ı����Ϳ�
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
		setLayout(new BorderLayout());//���ñ߽粼��
		messagejta1=new JTextArea();
		messagejta1.setFont(new Font("����",Font.PLAIN,20));
		messagejta1.setRows(5);//��������
        messagejta1.setColumns(20);//��������
        //Ϊ���������Ӽ���
        messagejta1.addKeyListener(new MyKeyEventListener());//Ϊ�������Ӽ����¼�������
        messagejta1.setLineWrap(true);//�����ı����Զ�����
        jcp1=new JScrollPane(messagejta1);
        
        JPanel jp2=new JPanel();
        jp2.setBounds(0,350,345,120);
        jp2.setLayout(new FlowLayout(FlowLayout.RIGHT,20,2));
        
        colorchoosebutton=new JButton("����������ɫ");
        closebutton=new JButton("�ر�");
        senderbutton=new JButton("����");
        senderbutton.setBackground(Color.gray);
        closebutton.setBackground(Color.gray);
        closebutton.setBorder(BorderFactory.createLineBorder(Color.RED));//���ñ߿���ɫ
        senderbutton.setBorder(BorderFactory.createLineBorder(Color.green));
        closebutton.setToolTipText("�رմ��ڶˣ�ֹͣ����");//�����ͣ��ʾ
        senderbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//������������ϢȺ������
				ClientMainFrame.send_normal_message(getsendermessage(buttonmessage));
			}
		});
        closebutton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e){//���������ķ���
	               JOptionPane.showMessageDialog(ClientMainFrame.getmainwindows(),"�˳�����");//����С�Ի���
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
		Color color = messagejta1.getBackground();//��ȡԭ������ɫ����
        //��ʾ��ɫѡ��Ի���
        newColor = JColorChooser.showDialog(this, "ѡ����ɫ", color);
        messagejta1.setForeground(newColor);//�ѻ�ȡ����ɫ����Ϊ��ǩ�ı���ɫ
	}
	protected static SenderJPanel getSenderJPanel() {
		if(senderjpanel==null) {
			senderjpanel=new SenderJPanel();
			senderjpanel.setVisible(true);
		}
		return senderjpanel;
	}
	protected static String getsendermessage(String message) {//��շ�������Ϣ��������Ϣ����
		message=messagejta1.getText();
		messagejta1.setText("");
		return message;
	}
	protected static void clearsendermessage() {//��շ�������Ϣ
		messagejta1.setText("");
	}

}
