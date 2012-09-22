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

	ChatPanel chatPanel = new ChatPanel("クライアント側のチャット画面");

	public static void main(String[] args) {
		String host = "localhost";
		int port = 8000;

		// アプレットのインスタンスを作成
		ChatClient applet = new ChatClient();

		try {
			// 待ち受けのポート番号8000を指定して、サーバーソケットを生成
			Socket socket = new Socket(host, port);


			// 新しくスレッドを作成する
			HandleChatServer task = new HandleChatServer(socket, applet);

			// スレッドを起動
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

	// コンストラクタ
	public HandleChatServer(Socket socket, ChatClient applet) {
		this.socket = socket;
		this.applet = applet;

		// Submitボタンを押したときの処理
		applet.chatPanel.chatHundleButton.addActionListener(new ButtonListener());
	}

	public void run() {
		try {

			while (true) {
				// ソケットに対して入力するためのReaderを作成
				fromServer = new ObjectInputStream(socket.getInputStream());

				// サーバーから受信したAddressオブジェクトを取得
				Chat chatObj = (Chat) fromServer.readObject();

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
				ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());

				// テキストフィールドの値を取得する
				String name = applet.chatPanel.jtfName.getText().trim();
				String tweet =applet.chatPanel.jtfTweet.getText().trim();

				// Addressオブジェクトをサーバに送信
				Chat chatObj = new Chat(name, tweet);
				toServer.writeObject(chatObj);

				// textareaに取得した値を書き込み
				applet.chatPanel.jtfChatNote.append(tweet + "\n");
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
}