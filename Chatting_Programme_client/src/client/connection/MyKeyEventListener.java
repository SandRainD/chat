package client.connection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyEventListener implements KeyListener {
	private String entermessage=null;
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("你这次输入了“" + e.getKeyChar() + "”");
	}
	@Override
	public void keyReleased(KeyEvent e) {
		String keyText = KeyEvent.getKeyText(e.getKeyCode());
		System.out.println("松开“" + keyText + "”键");
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			SenderJPanel.clearsendermessage();//清空按下enter键后残余的\n符
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			SenderJPanel.clearsendermessage();//清空按下esc键后清空输入区
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {// 按键被按下时触发
		String keyText = KeyEvent.getKeyText(e.getKeyCode());// 获取按键的数值，再获取案件的String值
		if (e.isActionKey()) {
			System.out.println("按下动作键“" + e.getKeyCode() + "”");
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("按下“Enter”");
			// 发送信息的方法
			ClientMainFrame.send_normal_message(SenderJPanel.getsendermessage(entermessage));
		} else {
			System.out.println("按下“"+keyText+"”键");
		}
	}

}
