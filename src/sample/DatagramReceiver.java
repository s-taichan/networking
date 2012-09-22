package sample;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.IOException;

public class DatagramReceiver {

	public static final int SERVER_PORT = 10007;
	public static final int PACKET_SIZE = 1024;

	public static void main(String args[]) {

		DatagramSocket socket = null;
		byte[] buf = new byte[PACKET_SIZE];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		try {
			socket = new DatagramSocket(SERVER_PORT);
			System.out.println("DatagramReceiverÇ™ãNìÆÇµÇ‹ÇµÇΩ(port="
					+ socket.getLocalPort() + ")");
			while (true) {
				socket.receive(packet);
				String message = new String(buf, 0, packet.getLength());
				System.out.println(packet.getSocketAddress()
						+ " éÛêM: " + message);
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