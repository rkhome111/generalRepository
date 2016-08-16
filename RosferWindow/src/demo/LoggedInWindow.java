package demo;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

public class LoggedInWindow extends JPanel implements FocusListener
{

	private static final long serialVersionUID = 1L;
	JPanel top_right,bltop_left,bltop_right,shadowPanel;
	JLabel blbottom,blmid,mid_mid,mid_left,mid_right,left,leftStop,logout;
	JButton  min,close;
	JLabel hour,time,frame_shadow,name,cmp;
	JTextField comment;
	JLabel select_project;
	JScrollPane jsp;
//	JComboBox myList;
	static	Color bg_color=new Color(0x2D313C);
	static	Color text_color=new Color(0x1A1701);
	Rosfer rosfer;
	public LoggedInWindow(Rosfer rosfer)
	{
		this.rosfer=rosfer;
		initialize();
		design1();
		addFocusListener(rosfer);
//		top_right.setVisible(true);
	
		select_project.setFocusable(true);
	}
	
	public void propertyChange(PropertyChangeEvent e)
	{
		System.out.println(e.getNewValue());
		
	}

	JScrollBar sc;
	private void design1()
	{
		setLayout(null);
		setBackground(new Color(0,0,0,0));

		

		top_right.setBackground(bg_color);
		top_right.setBounds(195,0,115,20);
		top_right.setLayout(null);
		add(top_right);
		
		
		min.setBounds(75, 0, 20, 20);
		close.setBounds(95,0, 20, 20);
		close.setBackground(bg_color);
		top_right.add(min);
		top_right.add(close);
		close.setBorder(BorderFactory.createEmptyBorder());
		min.setBorder(BorderFactory.createEmptyBorder());

		logout.setBounds(53, 0,20,20);
		logout.setBackground(bg_color);
//		logout.setBorder(BorderFactory.createEmptyBorder());
		top_right.add(logout);
		
		name.setBounds(2, 0,48, 20);
		name.setForeground(new Color(0xe0e5e9));
		name.setBackground(bg_color);
		if(name.getText().length()>5)
			name.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,7));
		else
			name.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,9));
		
		top_right.add(name);
		

		
		
		blmid.setBounds(0,20,310,45);
		blmid.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,1,0,1,new Color(0x293334)), BorderFactory.createMatteBorder(1, 1, 0, 1, new Color(0xd5b500))));
		add(blmid);
	
		
		mid_left.setBounds(2,2,38, 41);
		blmid.add(mid_left);
		left.setBounds(10, 13, 15,15);
		left.setBorder(BorderFactory.createEmptyBorder());
		left.setBackground(new Color(0xfee105));
		mid_left.add(left);
		mid_left.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(0xd7b800)), BorderFactory.createEmptyBorder()));
		
		leftStop.setBounds(10, 13, 15,15);
		leftStop.setBorder(BorderFactory.createEmptyBorder());
//		leftStop.setBackground(new Color(0xfee105));
		mid_left.add(leftStop);
		leftStop.setVisible(false);
		
		
		mid_mid.setBounds(40,2,220,41);
		mid_mid.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,1,1,new Color(0xd7b800)), BorderFactory.createEmptyBorder()));
		mid_mid.setLayout(null);
		mid_mid.setBackground(new Color(0xfee105));
		blmid.add(mid_mid);

		
		select_project.setBounds(10,8,170,15);
		mid_mid.add(select_project);
		select_project.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		select_project.setForeground(text_color);
		
		comment.setBounds(10,20,150,18);
		comment.setForeground(text_color);
		comment.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		comment.setBackground(new Color(0xFFDB07));
		comment.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,11));
		comment.addFocusListener(this);
		mid_mid.add(comment);
		
		comment.addKeyListener(rosfer.lw);

	
		cmp.setBounds(190, 8, 20, 15);
		cmp.setBackground(new Color(0xfee105));
		cmp.setBorder(BorderFactory.createEmptyBorder());
		mid_mid.add(cmp);
		
		mid_right.setBounds(260,2, 48, 41);
		hour.setBounds(10, 15, 40, 10);
		hour.setForeground(Color.black);
		hour.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,12));
		mid_right.add(hour);
		mid_right.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(0xd7b800)), BorderFactory.createEmptyBorder()));
		blmid.add(mid_right);
		
		
		
		//bottom pannel design
		blbottom.setLayout(null);
		blbottom.setBounds(0, 64, 310, 41);
		
		blbottom.setBackground(new Color(0xfee105));
		add(blbottom);
		time.setBounds(10, 5, 150, 30);
		time.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,30));
		time.setForeground(Color.black);
		blbottom.add(time);
		blbottom.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 1, 1, 1,new Color(0x293334)), BorderFactory.createMatteBorder(0, 1, 1, 1,new Color(0xd5b500))));



	}
	

	
	public void initialize()
	{
		top_right=new JPanel();
		shadowPanel=new JPanel();
		blmid=new JLabel(new ImageIcon("assets/image7.png"));
		blbottom=new JLabel(new ImageIcon("assets/image14.png"));
		mid_left=new JLabel(new ImageIcon("assets/image7.png"));
		mid_right=new JLabel(new ImageIcon("assets/image7.png"));
		bltop_left=new JPanel();
		bltop_right=new JPanel();

		
		
		mid_mid=new JLabel(new ImageIcon("assets/image7.png"));
		
		min=new JButton(new ImageIcon("assets/app_BUT_MIN.png"));
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.setToolTipText("Minimize");
		close=new JButton(new ImageIcon("assets/App_close.png"));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.setToolTipText("Close");
		left=new JLabel(new ImageIcon("assets/left_black.png"),JLabel.CENTER);
		left.setCursor(new Cursor(Cursor.HAND_CURSOR));
		left.setToolTipText("Start Project");
		leftStop=new JLabel(new ImageIcon("assets/image10.png"),JLabel.CENTER);
		leftStop.setCursor(new Cursor(Cursor.HAND_CURSOR));
		leftStop.setToolTipText("Stop Project");
		cmp=new JLabel(new ImageIcon("assets/image11.png"));
		cmp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cmp.setToolTipText("Select Project");
		logout=new JLabel(new ImageIcon("assets/logout.png"),JLabel.CENTER);
		logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logout.setToolTipText("Logout");
		
		hour=new JLabel("00:00");
		time=new JLabel("00:00:00");
//		if(rosfer.tu!=null && rosfer.tu.isAlive())
//			rosfer.tu.interrupt();
		
		
		
		 

		comment=new JTextField("Comment");
		select_project=new JLabel("Select Project");
		frame_shadow=new JLabel(new ImageIcon("assets/image19.png"));
		name=new JLabel(rosfer.user,new ImageIcon("assets/image8.png"),JLabel.RIGHT);
		name.setIconTextGap(7);
		
	
		
	}
	
	@Override
	public void focusGained(FocusEvent e)
	{
		String comment_text=comment.getText();
		if(comment_text.equals("Comment"))
			comment.setText("");
		comment.setBackground(Color.white);
	}
	
	@Override
	public void focusLost(FocusEvent e)
	{
		String comment_text=comment.getText();
		comment_text=comment.getText();
		if(comment_text.equals(""))
			comment.setText("Comment");

		comment.setBackground(new Color(0xfee105));
	}
//	int height,width;
//	@Override
//	public void mousePressed(MouseEvent e)
//	{
//		
//		if(rosfer.mycmp.isVisible())
//		{
//			rosfer.mycmp.setVisible(false);
//			rosfer.mycmpdialogvisible=false;
//		}
//		height=e.getY();
//		width=e.getX();
//	}
//	public void mouseClicked(MouseEvent e){}
//	public void mouseEntered(MouseEvent e){}
//	public void mouseExited(MouseEvent e){}
//	public void mouseReleased(MouseEvent e){}
//	
//	public void mouseDragged(MouseEvent e)
//	{
//		rosfer.setLocation(e.getXOnScreen()-(rosfer.getWidth()-bltop.getWidth())-width, e.getYOnScreen()-height+bltop.getHeight());
//		rosfer.adjust();
//	}
//	public void mouseMoved(MouseEvent e){}

}