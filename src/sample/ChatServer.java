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

	ChatPanel chatPanel = new ChatPanel("サーバー側のチャット画面");

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 8000;

		// アプレットのインスタンスを作成
		ChatServer applet = new ChatServer();

		try {
			// 待ち受けのポート番号8000を指定して、サーバーソケットを生成
			serverSocket = new ServerSocket(port);
			System.out.println("Server起動: (port=" + serverSocket.getLocalPort() + ")");

			while (true) {
				// acceptメソッドを呼び出し、クライアントからの接続待機状態に入る
				Socket socket = serverSocket.accept();
				System.out.println("接続: " + socket.getRemoteSocketAddress() );

				// 新しくスレッドを作成する
				HandleChatClients task = new HandleChatClients(socket, applet);

				// スレッドを起動
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

	// コンストラクタ
	public HandleChatClients(Socket socket, ChatServer applet) {
		this.socket = socket;
		this.applet = applet;

		// Submitボタンを押したときの処理
		applet.chatPanel.chatHundleButton.addActionListener(new ButtonListener());
	}

	public void run() {
		try {
			while (true) {
				// ソケットに対して入力するためのReaderを作成
				fromClient = new ObjectInputStream(socket.getInputStream());

				// クライアントが送信したAddressオブジェクトを取得
				Chat chatObj = (Chat) fromClient.readObject();
				System.out.println("Nameから送信されました : " + chatObj.getName());

				// Addressオブジェクトからユーザー名とアドレスの情報を取得して、テキストエリアに書き込み
				applet.chatPanel.jtfChatNote.append(chatObj.getName() + " ＞ " + chatObj.getChat() + "\n");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	// Submitボタンを押したときの処理
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// ソケットに対して入力するためのReaderを作成
				ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());

				// テキストフィールドの値を取得する
				String name = applet.chatPanel.jtfName.getText().trim();
				String tweet =applet.chatPanel.jtfTweet.getText().trim();

				// Addressオブジェクトをサーバに送信
				Chat chatObj = new Chat(name, tweet);
				toClient.writeObject(chatObj);

				// textareaに取得した値を書き込み
				applet.chatPanel.jtfChatNote.append(tweet + "\n");
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}

	}
}
