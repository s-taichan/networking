package sample;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IdentifyIPAddressFromHostName {
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			try {
				InetAddress address = InetAddress.getByName(args[i]);
				System.out.println("ホスト名: " + address.getHostName());
				System.out.println("IPアドレス: " + address.getHostAddress());
			} catch (UnknownHostException e) {
				System.err.println(args[i]);
			}
		}
	}
}
