package client.connection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyEventListener implements KeyListener {
	private String entermessage=null;
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("����������ˡ�" + e.getKeyChar() + "��");
	}
	@Override
	public void keyReleased(KeyEvent e) {
		String keyText = KeyEvent.getKeyText(e.getKeyCode());
		System.out.println("�ɿ���" + keyText + "����");
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			SenderJPanel.clearsendermessage();//��հ���enter��������\n��
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			SenderJPanel.clearsendermessage();//��հ���esc�������������
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {// ����������ʱ����
		String keyText = KeyEvent.getKeyText(e.getKeyCode());// ��ȡ��������ֵ���ٻ�ȡ������Stringֵ
		if (e.isActionKey()) {
			System.out.println("���¶�������" + e.getKeyCode() + "��");
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("���¡�Enter��");
			// ������Ϣ�ķ���
			ClientMainFrame.send_normal_message(SenderJPanel.getsendermessage(entermessage));
		} else {
			System.out.println("���¡�"+keyText+"����");
		}
	}

}
