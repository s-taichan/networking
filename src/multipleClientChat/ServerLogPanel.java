package multipleClientChat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class ServerLogPanel {
	JFrame frame = new JFrame();
	JTextArea jtfChatNote = new JTextArea(20, 40);

	// アプレット用の画面作成
	public ServerLogPanel() {

		// チェット履歴
		JPanel chatHistoryPanel1 = new JPanel();
		chatHistoryPanel1.setLayout(new GridLayout(1, 1));
		chatHistoryPanel1.add(new JLabel("チャットログ"));

		JPanel chatHistoryPanel2 = new JPanel();
		chatHistoryPanel2.setLayout(new GridLayout(1, 1));
		chatHistoryPanel2.add(jtfChatNote);
		jtfChatNote.setEnabled(false);


		JPanel chatHistoryPanel = new JPanel(new BorderLayout());
		chatHistoryPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		chatHistoryPanel.add(chatHistoryPanel1, BorderLayout.NORTH);
		chatHistoryPanel.add(chatHistoryPanel2, BorderLayout.CENTER);


		// 全体画面
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel.add(chatHistoryPanel, BorderLayout.NORTH);

		Container cont = frame.getContentPane();
		cont.add(panel);
		frame.setTitle("チャットログ履歴");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
