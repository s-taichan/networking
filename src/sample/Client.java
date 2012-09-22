package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		int port = 8000;
		String host = "localhost";

		try {
			// サーバー情報を指定して、サーバーに接続
			Socket socket = new Socket(host, port);
			System.out.println("接続: " + socket.getRemoteSocketAddress() );

			// ソケットに対して入力するためのReaderを作成
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// ソケットに対して出力するためのWriterを作成
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// キーボードからの入力を受付
			BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.print("送信: ");

				// キーボードに入力した文字列をサーバーに送信
				out.println(keyIn.readLine());

				// サーバーから受け取った文字列を取得して表示する
				System.out.println("受信: " + in.readLine());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
