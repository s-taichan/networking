package sample;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class DatagramSender {

	public static final int SERVER_PORT = 10007;
	public static final int PACKET_SIZE = 1024;

	public static void main(String args[]) {
		DatagramSocket socket = null;

		// パケットの宛先に利用するソケットアドレスを設定
		InetSocketAddress remoteAddress =
				new InetSocketAddress(args[0], SERVER_PORT);

		try {
			BufferedReader keyIn =
					new BufferedReader(new InputStreamReader(System.in));
			socket = new DatagramSocket();
			String message;
			while ( (message = keyIn.readLine()).length() > 0 ) {
				byte[] buf = message.getBytes();
				DatagramPacket packet =
						new DatagramPacket(buf, buf.length, remoteAddress);
				socket.send(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}

}