package htmlEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WebPanel {
	JFrame frame = new JFrame();
	JLabel urlLabel = new JLabel("URL");
	JTextArea htmlEdit = new JTextArea(30,40);
	JTextField urlField = new JTextField();
	JEditorPane jePane = new JEditorPane();

	// WebBrowser用の画面作成
	public  WebPanel(){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(urlLabel, BorderLayout.WEST);
		panel1.add(urlField, BorderLayout.CENTER);


		// サイズを指定された幅と高さに設定
		jePane.setPreferredSize(new Dimension(600,600));
		// 編集不可に
		jePane.setEditable(false);

		// Web画面用のスクロールバーの設定
		JScrollPane scroll = new JScrollPane(jePane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
				);

		// Web画面用のパネル
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(new JLabel("Web確認画面のJEditorPane"), BorderLayout.NORTH);
		panel2.add(scroll, BorderLayout.CENTER);

		// WebEditor用の折り返し有効の設定
		htmlEdit.setLineWrap(true);
		htmlEdit.setWrapStyleWord(true);

		// WebEditor用のスクロール画面設定
		JScrollPane htmlEditor = new JScrollPane(htmlEdit,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// WebEditor用のパネル
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add(new JLabel("HtmlEditor用のJTextArea"), BorderLayout.NORTH);
		panel3.add(htmlEditor, BorderLayout.CENTER);


		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel3, BorderLayout.WEST);
		frame.add(panel2,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("WebHtmlEditor");
		frame.pack();
		frame.setVisible(true);
	}
}
