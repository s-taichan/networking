package multipleClientChat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class ChatPanel {
	JFrame frame = new JFrame();
	JTextArea jtfChatNote = new JTextArea(20, 40);
	JTextField jtfName = new JTextField(33);
	JTextField jtfTweet = new JTextField(33);

	// アプレット用の画面作成
	public ChatPanel(String title) {

		// チェット履歴
		JPanel chatHistoryPanel1 = new JPanel();
		chatHistoryPanel1.setLayout(new GridLayout(1, 1));
		chatHistoryPanel1.add(new JLabel("履歴"));

		JPanel chatHistoryPanel2 = new JPanel();
		chatHistoryPanel2.setLayout(new GridLayout(1, 1));
		chatHistoryPanel2.add(jtfChatNote);
		jtfChatNote.setEnabled(false);


		JPanel chatHistoryPanel = new JPanel(new BorderLayout());
		chatHistoryPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		chatHistoryPanel.add(chatHistoryPanel1, BorderLayout.NORTH);
		chatHistoryPanel.add(chatHistoryPanel2, BorderLayout.CENTER);

		// チェット送信
		JPanel chatSubmitPanel2 = new JPanel(new BorderLayout());
		chatSubmitPanel2.setBorder(new BevelBorder(BevelBorder.RAISED));
		chatSubmitPanel2.add(new JLabel("名前"), BorderLayout.CENTER);
		chatSubmitPanel2.add(jtfName, BorderLayout.EAST);

		JPanel chatSubmitPanel3 = new JPanel(new BorderLayout());
		chatSubmitPanel3.setBorder(new BevelBorder(BevelBorder.RAISED));
		chatSubmitPanel3.add(new JLabel("つぶやく"), BorderLayout.CENTER);
		chatSubmitPanel3.add(jtfTweet, BorderLayout.EAST);

		JPanel chatSubmitPanel = new JPanel();
		chatSubmitPanel.setLayout(new GridLayout(2, 1));
//		chatSubmitPanel.add(chatSubmitPanel1);
		chatSubmitPanel.add(chatSubmitPanel2);
		chatSubmitPanel.add(chatSubmitPanel3);

		// 全体画面
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		panel.add(chatHistoryPanel, BorderLayout.NORTH);
		panel.add(chatSubmitPanel, BorderLayout.CENTER);

		Container cont = frame.getContentPane();
		cont.add(panel);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
