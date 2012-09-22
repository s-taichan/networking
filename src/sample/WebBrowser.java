package sample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class WebBrowser implements ActionListener,HyperlinkListener {
	private JFrame frame = new JFrame();
	private JLabel urlLabel = new JLabel("URL");
	private JTextField urlField = new JTextField();
	private JEditorPane jePane = new JEditorPane();

	// アプレット用の画面作成
	public void init(){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(urlLabel, BorderLayout.WEST);
		panel1.add(urlField, BorderLayout.CENTER);

		// ActionListenerをJTextFieldに組み込んで、Enter時にイベント発生
		urlField.addActionListener(this);

		// サイズを指定された幅と高さに設定
		jePane.setPreferredSize(new Dimension(800,600));
		// 編集不可に
		jePane.setEditable(false);
		// HyperlinkListenerをJEditorPaneに組み込んで、リンク操作時にイベントを発生
		jePane.addHyperlinkListener(this);

		// スクロールバーの設定
		JScrollPane scroll = new JScrollPane(jePane,
											 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
											 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
											 );

		frame.add(panel1, BorderLayout.NORTH);
		frame.add(scroll,BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	// Enterが押されたとき、URLを読み込んで、JEditorPaneにセット
	@Override
	public void actionPerformed(ActionEvent e) {
		// アクセスに失敗したときの例外処理
		try {
			// Enter時にURLを読み込んで、JEditorPaneにセット
			jePane.setPage(urlField.getText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
			// アクセスに失敗したときの例外処理
			try {
				URL url = e.getURL();
				urlField.setText(url.toString());
				jePane.setPage(url);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		WebBrowser applet = new WebBrowser();
		applet.init();
	}
}