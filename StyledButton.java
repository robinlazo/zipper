package ZipCoder;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class StyledButton extends BasicButtonUI{
	public void installUI(JComponent j) {
		super.installUI(j);
		AbstractButton btn = (AbstractButton) j;
		btn.setBorder(new EmptyBorder(7, 20, 7, 20));
		btn.setOpaque(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public void paint(Graphics g, JComponent j) {
		AbstractButton btn = (AbstractButton) j;
		paintButton(g, j);
		super.paint(g, btn);
	}

	
	public void paintButton(Graphics g, JComponent j) {
		Dimension size = j.getSize();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(j.getBackground());
		g.fillRoundRect(0, 0, size.width, size.height, 4, 4);
	}
}
