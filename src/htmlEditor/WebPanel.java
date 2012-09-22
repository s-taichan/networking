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

	// WebBrowser�p�̉�ʍ쐬
	public  WebPanel(){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(urlLabel, BorderLayout.WEST);
		panel1.add(urlField, BorderLayout.CENTER);


		// �T�C�Y���w�肳�ꂽ���ƍ����ɐݒ�
		jePane.setPreferredSize(new Dimension(600,600));
		// �ҏW�s��
		jePane.setEditable(false);

		// Web��ʗp�̃X�N���[���o�[�̐ݒ�
		JScrollPane scroll = new JScrollPane(jePane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
				);

		// Web��ʗp�̃p�l��
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(new JLabel("Web�m�F��ʂ�JEditorPane"), BorderLayout.NORTH);
		panel2.add(scroll, BorderLayout.CENTER);

		// WebEditor�p�̐܂�Ԃ��L���̐ݒ�
		htmlEdit.setLineWrap(true);
		htmlEdit.setWrapStyleWord(true);

		// WebEditor�p�̃X�N���[����ʐݒ�
		JScrollPane htmlEditor = new JScrollPane(htmlEdit,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// WebEditor�p�̃p�l��
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add(new JLabel("HtmlEditor�p��JTextArea"), BorderLayout.NORTH);
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
