package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		int port = 8000;

		try {
			// 待ち受けのポート番号8000を指定して、サーバーソケットを生成
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server起動: (port=" + serverSocket.getLocalPort() + ")");

			// acceptメソッドを呼び出し、クライアントからの接続待機状態に入る
			Socket socket = serverSocket.accept();
			System.out.println("接続: " + socket.getRemoteSocketAddress() );

			// ソケットに対して入力するためのReaderを作成
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// ソケットに対して出力するためのWriterを作成
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
				// クライアントから受け取った文字列を取得
				String line = in.readLine();
				System.out.println("受信: " +  line);

				// 受け取った文字列をクライアントに送信
				out.println(line);
				System.out.println("送信: " + line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
