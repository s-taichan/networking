package sample;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class AddressServer {
	JFrame frame = new JFrame();
	JTextArea jtfAddressNote = new JTextArea(20, 40);


	// �A�v���b�g�p�̉�ʍ쐬
	public void init() {

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 1));
		panel1.add(new JLabel("�A�h���X��"));

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 1));
		panel2.add(jtfAddressNote);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel.add(panel1, BorderLayout.NORTH);
		panel.add(panel2, BorderLayout.SOUTH);

		Container cont = frame.getContentPane();
		cont.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 8000;

		// �A�v���b�g�̃C���X�^���X���쐬
		AddressServer applet = new AddressServer();
		applet.init();

		try {
			// �҂��󂯂̃|�[�g�ԍ�8000���w�肵�āA�T�[�o�[�\�P�b�g�𐶐�
			serverSocket = new ServerSocket(port);
			System.out.println("Server�N��: (port=" + serverSocket.getLocalPort() + ")");


			while (true) {
				// accept���\�b�h���Ăяo���A�N���C�A���g����̐ڑ��ҋ@��Ԃɓ���
				Socket socket = serverSocket.accept();
				System.out.println("�ڑ�: " + socket.getRemoteSocketAddress() );

				// �V�����X���b�h���쐬����
				HandleClients task = new HandleClients(socket, applet);
				// �X���b�h���N��
				new Thread(task).start();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
class HandleClients implements Runnable {
	private Socket socket;
	private AddressServer applet;
	private ObjectInputStream fromClient;

	// �R���X�g���N�^
	public HandleClients(Socket socket, AddressServer applet) {
		this.socket = socket;
		this.applet = applet;
	}

	public void run() {

		try {
			// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
			fromClient = new ObjectInputStream(socket.getInputStream());

			// �N���C�A���g�����M����Address�I�u�W�F�N�g���擾
			Address addressObj = (Address) fromClient.readObject();

			// Address�I�u�W�F�N�g���烆�[�U�[���ƃA�h���X�̏����擾���āA�e�L�X�g�G���A�ɏ�������
			applet.jtfAddressNote.append("���[�U�� : " + addressObj.getName()
					+ "\n�A�h���X : " + addressObj.getAddress() + "\n--------------\n");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
