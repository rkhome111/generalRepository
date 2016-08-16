package demo;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class MaxDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	JLabel max_button;
	public MaxDialog(Rosfer rosfer)
	{
		super(rosfer);
		setUndecorated(true);
		setLayout(null);
		
		max_button=new JLabel(new ImageIcon("assets/max.png"),JLabel.CENTER);
		max_button.setBounds(0, 0, 24,23);
		max_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		max_button.setToolTipText("Maximize");
		max_button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0x8B8D92)));
		setSize(24, 23);
		add(max_button);
		
		max_button.addMouseListener(rosfer);
		
	}
	


}
