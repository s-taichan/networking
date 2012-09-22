package sample;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class EchoClient {

	public static final int ECHO_PORT = 10007;

	public static void main(String args[]) {
		Socket socket = null;
		try {
			// 新しいソケットを作成
			// ここでは、String型のホスト名を指定するコンストラクタを利用
			// 接続先ホスト名は、コマンドライン引数で指定。ポート番号は10007
			socket = new Socket(args[0], ECHO_PORT);

			// getRemoteSocketAddressメソッドを使って、接続されたソケットの接続先情報を表示
			System.out.println("接続しました"
					+ socket.getRemoteSocketAddress());

			// ソケットに対して入出力を行うためのReaderを作成
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// ソケットに対して入出力を行うためのWriterを作成
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// キーボードから入出力を行うためのReaderを作成
			BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));


			// キーボードから入力された文字列をサーバに対して送信し、
			// 送り返されてきた文字列を表示。
			// ループによって空行が入力されるまで処理を繰り返す
			String input;
			while ( (input = keyIn.readLine()).length() > 0 ) {
				out.println(input);
				String line = in.readLine();
				if (line != null) {
					System.out.println(line);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

			// ソケット切断時は例外が発生する場合があるので、例外処理をきちんと記述
			// ソケット切断時の動作はOSによって異なるので注意が必要
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {}
			System.out.println("切断されました "
					+ socket.getRemoteSocketAddress());
		}
	}

}