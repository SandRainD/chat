package client.FunctionPanel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;

public class ProtScannnerPanel extends JTextArea{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4154675225976942019L;
	private static ProtScannnerPanel p1=new ProtScannnerPanel();
	private ProtScannnerPanel(){
		super();
		setFont(new Font("楷体",Font.PLAIN,16));//设置文本格式
		setBackground(Color.gray);
		setForeground(Color.WHITE);
	}
	protected static ProtScannnerPanel getpscpanenl() {
		return p1;
	}
}
