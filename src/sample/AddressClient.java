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


	// アプレット用の画面作成
	public void init() {

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2, 1));
		panel1.add(new JLabel("名前 : "));
		panel1.add(new JLabel("アドレス : "));

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

	// Submitボタンを押したときの処理
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Socket socket = null;
			try {
				// サーバー情報を指定して、サーバーに接続
				socket = new Socket(host, port);

				// ソケットに対して入力するためのReaderを作成
				ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());

				// テキストフィールドの値を取得する
				String name = jtfName.getText().trim();
				String address = jtfAddress.getText().trim();

				// Addressオブジェクトをサーバに送信
				Address addressObj = new Address(name, address);
				toServer.writeObject(addressObj);

			} catch (IOException ex) {
				System.err.println(ex);
			}
		}

	}

	public static void main(String[] args) {
		// アプレットのインスタンスを作成
		AddressClient applet = new AddressClient();
		applet.init();
	}
}
