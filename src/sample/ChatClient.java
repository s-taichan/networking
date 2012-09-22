package sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient {
	String host = "localhost";
	int port = 8000;
	Socket socket = null;

	ChatPanel chatPanel = new ChatPanel("�N���C�A���g���̃`���b�g���");

	public static void main(String[] args) {
		String host = "localhost";
		int port = 8000;

		// �A�v���b�g�̃C���X�^���X���쐬
		ChatClient applet = new ChatClient();

		try {
			// �҂��󂯂̃|�[�g�ԍ�8000���w�肵�āA�T�[�o�[�\�P�b�g�𐶐�
			Socket socket = new Socket(host, port);


			// �V�����X���b�h���쐬����
			HandleChatServer task = new HandleChatServer(socket, applet);

			// �X���b�h���N��
			new Thread(task).start();

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}

class HandleChatServer implements Runnable {
	private Socket socket;
	private ChatClient applet;
	private ObjectInputStream fromServer;

	// �R���X�g���N�^
	public HandleChatServer(Socket socket, ChatClient applet) {
		this.socket = socket;
		this.applet = applet;

		// Submit�{�^�����������Ƃ��̏���
		applet.chatPanel.chatHundleButton.addActionListener(new ButtonListener());
	}

	public void run() {
		try {

			while (true) {
				// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
				fromServer = new ObjectInputStream(socket.getInputStream());

				// �T�[�o�[�����M����Address�I�u�W�F�N�g���擾
				Chat chatObj = (Chat) fromServer.readObject();

				// Address�I�u�W�F�N�g���烆�[�U�[���ƃA�h���X�̏����擾���āA�e�L�X�g�G���A�ɏ�������
				applet.chatPanel.jtfChatNote.append(chatObj.getName() + " �� " + chatObj.getChat() + "\n");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	// Submit�{�^�����������Ƃ��̏���
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
				ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());

				// �e�L�X�g�t�B�[���h�̒l���擾����
				String name = applet.chatPanel.jtfName.getText().trim();
				String tweet =applet.chatPanel.jtfTweet.getText().trim();

				// Address�I�u�W�F�N�g���T�[�o�ɑ��M
				Chat chatObj = new Chat(name, tweet);
				toServer.writeObject(chatObj);

				// textarea�Ɏ擾�����l����������
				applet.chatPanel.jtfChatNote.append(tweet + "\n");
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
}