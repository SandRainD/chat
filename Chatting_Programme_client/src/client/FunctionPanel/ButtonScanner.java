package client.FunctionPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import client.connection.ClientMainFrame;

public class ButtonScanner extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5074907916122459345L;
	protected static int indexnumber = 0;
	private static int startindex = 100;
	private final int endindex = 150;
	private static ButtonScanner b1 = new ButtonScanner();

	public ButtonScanner() {
		super();
		setText("搜索服务器");
//		setBounds(2, 2, 15, 10);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(indexnumber==1) {
					JOptionPane.showMessageDialog(ClientMainFrame.getmainwindows(), "提示", "无法连接到服务器，请稍后重试", endindex);
					indexnumber=0;
				}
				if (indexnumber == 0) {
					indexnumber = 1;
					while (true) {
						if (endindex - startindex > 0) {
							ThreadPort thread = new ThreadPort(startindex);
							thread.start();
							startindex += 5;
						} else if (endindex - startindex <= 0) {
							break;
						}
					}
				} else {
					return;
				}
			}
		});
	}
	protected static ButtonScanner getButtonScanner() {
		return b1;
	}
}
