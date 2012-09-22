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
			// �҂��󂯂̃|�[�g�ԍ�8000���w�肵�āA�T�[�o�[�\�P�b�g�𐶐�
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server�N��: (port=" + serverSocket.getLocalPort() + ")");

			// accept���\�b�h���Ăяo���A�N���C�A���g����̐ڑ��ҋ@��Ԃɓ���
			Socket socket = serverSocket.accept();
			System.out.println("�ڑ�: " + socket.getRemoteSocketAddress() );

			// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// �\�P�b�g�ɑ΂��ďo�͂��邽�߂�Writer���쐬
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
				// �N���C�A���g����󂯎������������擾
				String line = in.readLine();
				System.out.println("��M: " +  line);

				// �󂯎������������N���C�A���g�ɑ��M
				out.println(line);
				System.out.println("���M: " + line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}