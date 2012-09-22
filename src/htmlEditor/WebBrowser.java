package htmlEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class WebBrowser implements ActionListener,HyperlinkListener, DocumentListener {

	WebPanel webPanel = new WebPanel();

	public WebBrowser() {
		// ActionListenerをJTextFieldに組み込んで、Enter時にイベント発生
		webPanel.urlField.addActionListener(this);
		// HyperlinkListenerをJEditorPaneに組み込んで、リンク操作時にイベントを発生
		webPanel.jePane.addHyperlinkListener(this);
	}

	// Enterが押されたとき、URLを読み込んで、JEditorPaneにセット
	@Override
	public void actionPerformed(ActionEvent e) {
		// アクセスに失敗したときの例外処理
		try {
			URL url = new URL(webPanel.urlField.getText().trim());
			// Enter時にURLを読み込んで、JEditorPaneにセット
			webPanel.jePane.setPage(url);
			System.out.println(url);

			htmlFileToEditor(url);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Linkが押されたとき、URLをセットして、JEditorPaneにセット
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
			// アクセスに失敗したときの例外処理
			try {
				URL url = e.getURL();
				webPanel.urlField.setText(url.toString());
				webPanel.jePane.setPage(url);

				htmlFileToEditor(url);
				System.out.println(url);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// URLを読み込んで、そのHTMLファイルをHTMLEditorに出力
	public void htmlFileToEditor(URL url) {
		try {
			java.io.InputStream is = url.openStream();
			BufferedReader infile = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String inLine;
			webPanel.htmlEdit.setText("");

			while ((inLine = infile.readLine()) != null) {
				webPanel.htmlEdit.append(inLine + "\n");
			}

			// JTextAreaに更新があったときに、イベントを発生
			webPanel.htmlEdit.getDocument().addDocumentListener(this);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args){
		new WebBrowser();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		// 更新した後のHTMLを取得
		String str = webPanel.htmlEdit.getText();
		// Web画面に出力
		webPanel.jePane.setText(str);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}