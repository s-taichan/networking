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


	// アプレット用の画面作成
	public void init() {

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 1));
		panel1.add(new JLabel("アドレス帳"));

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

		// アプレットのインスタンスを作成
		AddressServer applet = new AddressServer();
		applet.init();

		try {
			// 待ち受けのポート番号8000を指定して、サーバーソケットを生成
			serverSocket = new ServerSocket(port);
			System.out.println("Server起動: (port=" + serverSocket.getLocalPort() + ")");


			while (true) {
				// acceptメソッドを呼び出し、クライアントからの接続待機状態に入る
				Socket socket = serverSocket.accept();
				System.out.println("接続: " + socket.getRemoteSocketAddress() );

				// 新しくスレッドを作成する
				HandleClients task = new HandleClients(socket, applet);
				// スレッドを起動
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

	// コンストラクタ
	public HandleClients(Socket socket, AddressServer applet) {
		this.socket = socket;
		this.applet = applet;
	}

	public void run() {

		try {
			// ソケットに対して入力するためのReaderを作成
			fromClient = new ObjectInputStream(socket.getInputStream());

			// クライアントが送信したAddressオブジェクトを取得
			Address addressObj = (Address) fromClient.readObject();

			// Addressオブジェクトからユーザー名とアドレスの情報を取得して、テキストエリアに書き込み
			applet.jtfAddressNote.append("ユーザ名 : " + addressObj.getName()
					+ "\nアドレス : " + addressObj.getAddress() + "\n--------------\n");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
