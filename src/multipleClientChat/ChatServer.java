package multipleClientChat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChatServer {
	String host = "localhost";
	int port = 8000;

	ServerLogPanel chatPanel = new ServerLogPanel();

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
				applet.chatPanel.jtfChatNote.append("�V���Ȑڑ�: " + socket.getRemoteSocketAddress() + "\n" );

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
class HandleChatClients implements Runnable, DocumentListener {
	private Socket socket;
	private ChatServer applet;
	private ObjectInputStream fromClient;

	// �R���X�g���N�^
	public HandleChatClients(Socket socket, ChatServer applet) {
		this.socket = socket;
		this.applet = applet;

		// JTextAria�̍X�V�ŃC�x���g���Ăяo��
		applet.chatPanel.jtfChatNote.getDocument().addDocumentListener(this);
	}

	public void run() {
		try {
			while (true) {
				// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
				fromClient = new ObjectInputStream(socket.getInputStream());

				// �N���C�A���g�����M����Chat�I�u�W�F�N�g���擾
				Chat chatObj = (Chat) fromClient.readObject();
				System.out.println("Name���瑗�M����܂��� : " + chatObj.getName());

				// Chat�I�u�W�F�N�g���烆�[�U�[���ƃ`���b�g�̏����擾���āA�e�L�X�g�G���A�ɏ�������
				applet.chatPanel.jtfChatNote.append(chatObj.getName() + " �� " + chatObj.getChat() + "\n");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
		}
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		try {
			// �\�P�b�g�ɏo�͂��邽�߂�Reader���쐬
			ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());

			String str = applet.chatPanel.jtfChatNote.getText();
			String[] strs = str.split("\n");
			String lastLines = strs[applet.chatPanel.jtfChatNote.getLineCount() - 2];

			// Chat�I�u�W�F�N�g���T�[�o�ɑ��M
			Chat chatObj = new Chat(lastLines);
			toClient.writeObject(chatObj);

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}
