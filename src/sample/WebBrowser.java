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

	// �A�v���b�g�p�̉�ʍ쐬
	public void init(){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(urlLabel, BorderLayout.WEST);
		panel1.add(urlField, BorderLayout.CENTER);

		// ActionListener��JTextField�ɑg�ݍ���ŁAEnter���ɃC�x���g����
		urlField.addActionListener(this);

		// �T�C�Y���w�肳�ꂽ���ƍ����ɐݒ�
		jePane.setPreferredSize(new Dimension(800,600));
		// �ҏW�s��
		jePane.setEditable(false);
		// HyperlinkListener��JEditorPane�ɑg�ݍ���ŁA�����N���쎞�ɃC�x���g�𔭐�
		jePane.addHyperlinkListener(this);

		// �X�N���[���o�[�̐ݒ�
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

	// Enter�������ꂽ�Ƃ��AURL��ǂݍ���ŁAJEditorPane�ɃZ�b�g
	@Override
	public void actionPerformed(ActionEvent e) {
		// �A�N�Z�X�Ɏ��s�����Ƃ��̗�O����
		try {
			// Enter����URL��ǂݍ���ŁAJEditorPane�ɃZ�b�g
			jePane.setPage(urlField.getText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
			// �A�N�Z�X�Ɏ��s�����Ƃ��̗�O����
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