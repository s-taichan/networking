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
			// �V�����\�P�b�g���쐬
			// �����ł́AString�^�̃z�X�g�����w�肷��R���X�g���N�^�𗘗p
			// �ڑ���z�X�g���́A�R�}���h���C�������Ŏw��B�|�[�g�ԍ���10007
			socket = new Socket(args[0], ECHO_PORT);

			// getRemoteSocketAddress���\�b�h���g���āA�ڑ����ꂽ�\�P�b�g�̐ڑ������\��
			System.out.println("�ڑ����܂���"
					+ socket.getRemoteSocketAddress());

			// �\�P�b�g�ɑ΂��ē��o�͂��s�����߂�Reader���쐬
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// �\�P�b�g�ɑ΂��ē��o�͂��s�����߂�Writer���쐬
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// �L�[�{�[�h������o�͂��s�����߂�Reader���쐬
			BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));


			// �L�[�{�[�h������͂��ꂽ��������T�[�o�ɑ΂��đ��M���A
			// ����Ԃ���Ă����������\���B
			// ���[�v�ɂ���ċ�s�����͂����܂ŏ������J��Ԃ�
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

			// �\�P�b�g�ؒf���͗�O����������ꍇ������̂ŁA��O������������ƋL�q
			// �\�P�b�g�ؒf���̓����OS�ɂ���ĈقȂ�̂Œ��ӂ��K�v
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {}
			System.out.println("�ؒf����܂��� "
					+ socket.getRemoteSocketAddress());
		}
	}

}