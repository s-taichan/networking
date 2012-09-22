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
			// �T�[�o�[�����w�肵�āA�T�[�o�[�ɐڑ�
			Socket socket = new Socket(host, port);
			System.out.println("�ڑ�: " + socket.getRemoteSocketAddress() );

			// �\�P�b�g�ɑ΂��ē��͂��邽�߂�Reader���쐬
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// �\�P�b�g�ɑ΂��ďo�͂��邽�߂�Writer���쐬
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// �L�[�{�[�h����̓��͂���t
			BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.print("���M: ");

				// �L�[�{�[�h�ɓ��͂�����������T�[�o�[�ɑ��M
				out.println(keyIn.readLine());

				// �T�[�o�[����󂯎������������擾���ĕ\������
				System.out.println("��M: " + in.readLine());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
