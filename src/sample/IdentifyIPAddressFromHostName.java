package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IdentifyIPAddressFromHostName {
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			try {
				InetAddress address = InetAddress.getByName(args[i]);
				System.out.println("�z�X�g��: " + address.getHostName());
				System.out.println("IP�A�h���X: " + address.getHostAddress());
			} catch (UnknownHostException e) {
				System.err.println(args[i]);
			}
		}
	}
}
