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
		// ActionListener��JTextField�ɑg�ݍ���ŁAEnter���ɃC�x���g����
		webPanel.urlField.addActionListener(this);
		// HyperlinkListener��JEditorPane�ɑg�ݍ���ŁA�����N���쎞�ɃC�x���g�𔭐�
		webPanel.jePane.addHyperlinkListener(this);
	}

	// Enter�������ꂽ�Ƃ��AURL��ǂݍ���ŁAJEditorPane�ɃZ�b�g
	@Override
	public void actionPerformed(ActionEvent e) {
		// �A�N�Z�X�Ɏ��s�����Ƃ��̗�O����
		try {
			URL url = new URL(webPanel.urlField.getText().trim());
			// Enter����URL��ǂݍ���ŁAJEditorPane�ɃZ�b�g
			webPanel.jePane.setPage(url);
			System.out.println(url);

			htmlFileToEditor(url);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Link�������ꂽ�Ƃ��AURL���Z�b�g���āAJEditorPane�ɃZ�b�g
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
			// �A�N�Z�X�Ɏ��s�����Ƃ��̗�O����
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

	// URL��ǂݍ���ŁA����HTML�t�@�C����HTMLEditor�ɏo��
	public void htmlFileToEditor(URL url) {
		try {
			java.io.InputStream is = url.openStream();
			BufferedReader infile = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String inLine;
			webPanel.htmlEdit.setText("");

			while ((inLine = infile.readLine()) != null) {
				webPanel.htmlEdit.append(inLine + "\n");
			}

			// JTextArea�ɍX�V���������Ƃ��ɁA�C�x���g�𔭐�
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
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		// �X�V�������HTML���擾
		String str = webPanel.htmlEdit.getText();
		// Web��ʂɏo��
		webPanel.jePane.setText(str);

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
}