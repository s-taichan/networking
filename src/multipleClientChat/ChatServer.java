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

		// アプレットのインスタンスを作成
		ChatServer applet = new ChatServer();

		try {
			// 待ち受けのポート番号8000を指定して、サーバーソケットを生成
			serverSocket = new ServerSocket(port);
			System.out.println("Server起動: (port=" + serverSocket.getLocalPort() + ")");

			while (true) {
				// acceptメソッドを呼び出し、クライアントからの接続待機状態に入る
				Socket socket = serverSocket.accept();
				applet.chatPanel.jtfChatNote.append("新たな接続: " + socket.getRemoteSocketAddress() + "\n" );

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
class HandleChatClients implements Runnable, DocumentListener {
	private Socket socket;
	private ChatServer applet;
	private ObjectInputStream fromClient;

	// コンストラクタ
	public HandleChatClients(Socket socket, ChatServer applet) {
		this.socket = socket;
		this.applet = applet;

		// JTextAriaの更新でイベントを呼び出し
		applet.chatPanel.jtfChatNote.getDocument().addDocumentListener(this);
	}

	public void run() {
		try {
			while (true) {
				// ソケットに対して入力するためのReaderを作成
				fromClient = new ObjectInputStream(socket.getInputStream());

				// クライアントが送信したChatオブジェクトを取得
				Chat chatObj = (Chat) fromClient.readObject();
				System.out.println("Nameから送信されました : " + chatObj.getName());

				// Chatオブジェクトからユーザー名とチャットの情報を取得して、テキストエリアに書き込み
				applet.chatPanel.jtfChatNote.append(chatObj.getName() + " ＞ " + chatObj.getChat() + "\n");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
		}
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		try {
			// ソケットに出力するためのReaderを作成
			ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());

			String str = applet.chatPanel.jtfChatNote.getText();
			String[] strs = str.split("\n");
			String lastLines = strs[applet.chatPanel.jtfChatNote.getLineCount() - 2];

			// Chatオブジェクトをサーバに送信
			Chat chatObj = new Chat(lastLines);
			toClient.writeObject(chatObj);

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
