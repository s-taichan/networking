package sample;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class EchoServer {

	public static final int ECHO_PORT = 10007;

	public static void main(String args[]) {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(ECHO_PORT);
			System.out.println("EchoServerÇ™ãNìÆÇµÇ‹ÇµÇΩ(port="
					+ serverSocket.getLocalPort() + ")");

			while (true) {
				Socket socket = serverSocket.accept();
				new EchoThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {}
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {}
		}
	}
}

class EchoThread extends Thread {
	private Socket socket;

	public EchoThread(Socket socket) {
		this.socket = socket;
		System.out.println("ê⁄ë±Ç≥ÇÍÇ‹ÇµÇΩ "
				+ socket.getRemoteSocketAddress() );
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String line;
			while ( (line = in.readLine()) != null ) {
				System.out.println(socket.getRemoteSocketAddress()
						+ " éÛêM: " + line);
				out.println(line);
				System.out.println(socket.getRemoteSocketAddress()
						+ " ëóêM: " + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {}
			System.out.println("êÿífÇ≥ÇÍÇ‹ÇµÇΩ "
					+ socket.getRemoteSocketAddress());
		}
	}
}
