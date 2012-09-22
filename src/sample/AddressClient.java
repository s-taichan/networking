package sample;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class AddressClient {
	private JFrame frame = new JFrame();
	private JTextField jtfName = new JTextField(20);
	private JTextField jtfAddress = new JTextField();
	private JButton jbtSubmit = new JButton("submit");

	int port = 8000;
	String host = "localhost";


	// �A�v���b�g�p�̉�ʍ쐬
	public void init() {

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2, 1));
		panel1.add(new JLabel("���O : "));
		panel1.add(new JLabel("�A�h���X : "));

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2, 1));
		panel2.add(jtfName);
		panel2.add(jtfAddress);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel.add(panel1, BorderLayout.WEST);
		panel.add(panel2, BorderLayout.CENTER);
		panel.add(jbtSubmit, BorderLayout.SOUTH);

		Container cont = frame.getContentPane();
		cont.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		jbtSubmit.addActionListener(new ButtonListener());
	}

	// Submit�{�^�����������Ƃ��̏���
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Socket socket = null;
			try {
				// �T�[�o�[�����w�肵�āA�T�[�o�[�ɐڑ�
				socket = new Socket(host, port);

				// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
				ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());

				// �e�L�X�g�t�B�[���h�̒l���擾����
				String name = jtfName.getText().trim();
				String address = jtfAddress.getText().trim();

				// Address�I�u�W�F�N�g���T�[�o�ɑ��M
				Address addressObj = new Address(name, address);
				toServer.writeObject(addressObj);

			} catch (IOException ex) {
				System.err.println(ex);
			}
		}

	}

	public static void main(String[] args) {
		// �A�v���b�g�̃C���X�^���X���쐬
		AddressClient applet = new AddressClient();
		applet.init();
	}
}
