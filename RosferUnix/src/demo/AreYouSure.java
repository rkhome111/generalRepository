package demo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AreYouSure extends JDialog implements MouseListener
{

	private static final long serialVersionUID = 1L;

	JLabel sure,sure_text1,sure_text2,cancel,ok;
	JPanel back;
	Rosfer rosfer;
	public AreYouSure(Rosfer rosfer,JFrame parrent)
	{
		
		super(parrent);
		setSize(206, 128);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,0));
		this.getContentPane().setBackground(new Color(1f, 1f, 1f,0f));
		this.rosfer=rosfer;
		sure=new JLabel(new ImageIcon("assets/AlertBack.png"));
		sure.setBounds(0, 0, 206,128);
		add(sure);
		back=new JPanel(null);
		back.setBounds(5, 3, 195,80);
		back.setBackground(this.getBackground());
//		back.setForeground(Color.black);
		sure_text1=new JLabel("Are you sure to");
		sure_text2=new JLabel("continue with this project");
		sure_text1.setBounds(55, 10, 170, 20);
		sure_text2.setBounds(25, 30, 170, 20);
//		sure_text1.setForeground(Color.black);
//		sure_text.setForeground(new Color(0x252525));
		sure_text1.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		sure_text2.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		back.add(sure_text1);
		back.add(sure_text2);
		back.setDoubleBuffered(false);
		ok=new JLabel(new ImageIcon("assets/ok.png"));
		cancel=new JLabel(new ImageIcon("assets/cancel.png"));
		ok.setForeground(Color.yellow);
//		ok.setBorder(BorderFactory.createEmptyBorder());
		cancel.setBorder(BorderFactory.createEmptyBorder());
		ok.setBounds(50,55, 52,21);
		cancel.setBounds(110,55,52,21);
		back.add(ok);
		back.add(cancel);
		sure.add(back);
		
		ok.addMouseListener(this);
		cancel.addMouseListener(this);
		this.setVisible(false);
		
		back.setDoubleBuffered(true);
	
		

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getSource()==ok)
			ok.setIcon(new ImageIcon("assets/ok-down.png"));
		else
			cancel.setIcon(new ImageIcon("assets/cancel-down.png"));
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		if(e.getSource()==ok)
			ok.setIcon(new ImageIcon("assets/ok-hover.png"));
		else
			cancel.setIcon(new ImageIcon("assets/cancel-hover.png"));
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		if(e.getSource()==ok)
			ok.setIcon(new ImageIcon("assets/ok.png"));
		else
			cancel.setIcon(new ImageIcon("assets/cancel.png"));
	}
	
	
	
	public void mouseReleased(MouseEvent e){
		
		if(e.getSource()==ok)
		{
			rosfer.liw.startProject();
		}
		
		else if(e.getSource()==cancel)
		{
			rosfer.liw.cmp.addMouseListener(rosfer.liw);
			rosfer.liw.cmp.setEnabled(true);
				setVisible(false);
			
		}
		
		
	}
	public void mouseClicked(MouseEvent e){}
}

	




