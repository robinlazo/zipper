package ZipCoder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Board extends JPanel implements ActionListener {
	private String[] filePath;
	private File files[];
	private Ziper ziper;
	private Font italicFont = new Font("Helvetica", Font.ITALIC, 14);
	
	Board() {
		initBoard();
	}

	public void initBoard() {
		setPreferredSize(new Dimension(400, 350));
		setBackground(new Color(0xf1c40f));
		setLayout(new FlowLayout());
		selectFileButton();
	}

	public ImageIcon changeSize(String img, int ratio) {
		ImageIcon icon = null;
		try {
			Image ic = ImageIO.read(new File(img)).getScaledInstance(ratio, ratio, Image.SCALE_DEFAULT);
			icon = new ImageIcon(ic);
		} catch (IOException e) {
			System.out.println("HOla");
		}

		return icon;
	}

	public void selectFileButton() {
		ImageIcon img = changeSize("src/ZipCoder/Directory.png", 13);
		JButton btn = createButton("Open A File", Color.white, Color.black, 
				new Font("Helvetica", Font.PLAIN, 11), true);
		btn.setIcon(img);
		add(btn);
		btn.addActionListener(this);
	}
	

	public void startCompression(ActionEvent e) {
		ziper = new Ziper();
		try {
			String zipName = files[0].isDirectory() ? files[0].getName() : 
				files[0].getName().substring(0, files[0].getName().indexOf('.'));//if the file is not a folder delete the extension
			
			ziper.zip(files, zipName);
		} catch(Throwable b) {
			b.printStackTrace();
		}
		
		System.out.println("done");
	}
	
	public void createFileNote(String type, int indexFile) {
		String fileSelected = "<html><p style='width: 200px; text-align:center;'>" + type + ": " + files[indexFile].getName() +"</p><html>";
		ImageIcon img = changeSize("src/ZipCoder/" + type + ".png", 15);
		JLabel note = new JLabel(fileSelected, img, SwingConstants.LEADING);
		note.setOpaque(true);
		note.setBackground(Color.white);
	    note.setBorder(new EmptyBorder(8, 20, 8, 20));
		note.setFont(italicFont);
		add(note);
	}

	public void askConfirmation() {
		removeAll();
		selectFileButton();
		
		JLabel filesSelected = new JLabel("You have selected the following files: ");
		filesSelected.setFont(new Font("Helvetica", Font.ITALIC, 20));
		filesSelected.setForeground(new Color(0x4b4b4b));
		Font font = new Font("Helvetica", Font.ITALIC, 14);
		add(filesSelected);
		
		int idx = 0;
		
		while(idx < filePath.length && idx < 4) {//avoid overflow of the files in the window
			String type = files[idx].isDirectory() ? "Directory" : "File";
			createFileNote(type, idx);
			idx++;
		}

		if(idx > 3) {
			JLabel more = new JLabel("<html><p style='width:120px; text-align:center;'>...</p></html>");
			more.setFont(new Font("Helvetica", Font.BOLD, 31));
			more.setBackground(Color.white);
			more.setOpaque(true);
			more.setBorder(new EmptyBorder(4, 80, 4, 80));
			add(more);
		}
		
		JButton compressButton = createButton("Start Compression", new Color(0x222f3e), Color.white, 
				italicFont, true);

		add(compressButton);
		compressButton.addActionListener(e -> startCompression(e));
	
		validate();
		repaint();
	}
	
	public JButton createButton(String text, Color background, Color foreground, Font fontStyle, boolean ui) {
		JButton button = new JButton(text);
		button.setBackground(background);
		button.setForeground(foreground);
		button.setFont(fontStyle);
		if(ui) button.setUI(new StyledButton());
		return button;
	}

	public void actionPerformed(ActionEvent ae) {
		var fileCho = new JFileChooser(new File(System.getProperty("user.dir")));
		fileCho.setMultiSelectionEnabled(true);
		fileCho.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int val = fileCho.showOpenDialog(this);

		if (val == JFileChooser.APPROVE_OPTION) {
			files = fileCho.getSelectedFiles();
			filePath = new String[files.length];

			for (int i = 0; i < files.length; i++) {
				filePath[i] = files[i].getAbsolutePath();
			}

			askConfirmation();
		}

	}

}
