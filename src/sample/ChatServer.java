package sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	String host = "localhost";
	int port = 8000;

	ChatPanel chatPanel = new ChatPanel("�T�[�o�[���̃`���b�g���");

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 8000;

		// �A�v���b�g�̃C���X�^���X���쐬
		ChatServer applet = new ChatServer();

		try {
			// �҂��󂯂̃|�[�g�ԍ�8000���w�肵�āA�T�[�o�[�\�P�b�g�𐶐�
			serverSocket = new ServerSocket(port);
			System.out.println("Server�N��: (port=" + serverSocket.getLocalPort() + ")");

			while (true) {
				// accept���\�b�h���Ăяo���A�N���C�A���g����̐ڑ��ҋ@��Ԃɓ���
				Socket socket = serverSocket.accept();
				System.out.println("�ڑ�: " + socket.getRemoteSocketAddress() );

				// �V�����X���b�h���쐬����
				HandleChatClients task = new HandleChatClients(socket, applet);

				// �X���b�h���N��
				new Thread(task).start();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
class HandleChatClients implements Runnable {
	private Socket socket;
	private ChatServer applet;
	private ObjectInputStream fromClient;

	// �R���X�g���N�^
	public HandleChatClients(Socket socket, ChatServer applet) {
		this.socket = socket;
		this.applet = applet;

		// Submit�{�^�����������Ƃ��̏���
		applet.chatPanel.chatHundleButton.addActionListener(new ButtonListener());
	}

	public void run() {
		try {
			while (true) {
				// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
				fromClient = new ObjectInputStream(socket.getInputStream());

				// �N���C�A���g�����M����Address�I�u�W�F�N�g���擾
				Chat chatObj = (Chat) fromClient.readObject();
				System.out.println("Name���瑗�M����܂��� : " + chatObj.getName());

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
				ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());

				// �e�L�X�g�t�B�[���h�̒l���擾����
				String name = applet.chatPanel.jtfName.getText().trim();
				String tweet =applet.chatPanel.jtfTweet.getText().trim();

				// Address�I�u�W�F�N�g���T�[�o�ɑ��M
				Chat chatObj = new Chat(name, tweet);
				toClient.writeObject(chatObj);

				// textarea�Ɏ擾�����l����������
				applet.chatPanel.jtfChatNote.append(tweet + "\n");
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}

	}
}
