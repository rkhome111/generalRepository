package demo;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.*;

public class Before_login_panel extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	JPanel bltop,top_left,top_right,blmid,mid_left,mid_right,mid_mid;//,shadowPanel;
	JButton  close,left,min;
	JLabel max_lb,rockittime,rockittime1,time,select_project,comment,name,logout,cmp,version;
	GridBagConstraints gbc;
	GridBagLayout gbl;
	JList<String> myList;
	JScrollPane jsp;
static	Color bg_color=new Color(0x2D313C);
static	Color text_color=new Color(0x6f7687);
	Rosfer rosfer;
	
	public Before_login_panel(Rosfer rosfer)
	{
		this.rosfer=rosfer;
		initialize();
		design1();
		this.setBackground(new Color(1f,1f,1f,0f));
	}
	

	
	private void design1()
	{
		setLayout(null);
		

		
		//top panel design
		bltop.setLayout(null);
		bltop.setBounds(0,0, 310, 20);
		add(bltop);
		
		top_left.setLayout(null);
		top_left.setBounds(0, 0, 160,20);
		bltop.add(top_left);
		
		top_left.setBackground(bg_color);
		rockittime.setBounds(5, 4, 100, 16);
		rockittime.setForeground(text_color);
		rockittime.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		top_left.add(rockittime);
		
		rockittime1.setBounds(5, 4, 100, 16);
		rockittime1.setForeground(text_color);
		rockittime1.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		top_left.add(rockittime1);
		rockittime1.setVisible(false);
		
		try{
		FileInputStream fin = new FileInputStream(new File("assets/version.txt"));
		byte[]  b = new byte[fin.available()];
		fin.read(b);
		String str = new String(b);
		str = str.substring(0,str.indexOf(";;"));
		version.setText(str);
		}catch(Exception e){}
		
		version.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		version.setForeground(text_color);
		version.setBounds(115, 4, 100, 15);
		top_left.add(version);
		
		
		bltop.add(top_right);
		top_right.setBackground(bg_color);
		top_right.setBounds(160,0,150,20);
		top_right.setLayout(null);
		min.setBounds(110, 0, 20, 20);
		min.setBorder(BorderFactory.createEmptyBorder());
		close.setBorder(BorderFactory.createEmptyBorder());
		close.setBounds(130,0, 20, 20);
		close.setBackground(bg_color);
	
		top_right.add(min);
		top_right.add(close);

		
		logout.setBounds(88, 0,20,20);
		logout.setBackground(bg_color);
		logout.setBorder(BorderFactory.createEmptyBorder());
		top_right.add(logout);
		
		name.setBounds(40, 0,45, 20);
		name.setForeground(Color.white);
		name.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,9));
		top_right.add(name);
		
		
		
		//mid pannel design
		blmid.setLayout(null);
		blmid.setBounds(0,43-23,310,45);
//
		blmid.setBackground(bg_color);

		blmid.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0x212530)), BorderFactory.createLineBorder(new Color(0x4b4f5a))));
		add(blmid);

		
		left.setBounds(17, 17, 8, 10);
		left.setBorder(BorderFactory.createEmptyBorder());
		left.setSize(8, 10);
		left.setBackground(bg_color);
		blmid.add(left);
		left.setEnabled(false);
		
		
		mid_mid.setBounds(40,2,220,41);
		mid_mid.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,1,0,1,new Color(0x212530)),BorderFactory.createMatteBorder(0,1,0,1,new Color(0x4b4f5a))));
		mid_mid.setLayout(null);
		mid_mid.setBackground(bg_color);
		blmid.add(mid_mid);
		
		select_project.setBounds(10,8,100,12);
		mid_mid.add(select_project);
		select_project.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		select_project.setForeground(text_color);
		


		
		 
		comment.setBounds(10,23,80,12);
		comment.setForeground(text_color);
		comment.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		mid_mid.add(comment);
		
		
		cmp.setBounds(190, 10, 15,15);
		cmp.setBackground(bg_color);
//		cmp.setForeground(text_color);
//		cmp.setBorder(BorderFactory.createEmptyBorder());
		mid_mid.add(cmp);
		cmp.setEnabled(false);
		
		time.setBounds(270, 15, 40, 12);
		time.setForeground(text_color);
		time.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,12));
				
		blmid.add(time);
		
//		shadowPanel=new JPanel();
//		shadowPanel.setBounds(-20,88,350,20);
//		shadowPanel.setBackground(new Color(1f,1f,1f,0f));
//		add(shadowPanel);
//		DropShadowBorder shadow = new DropShadowBorder();
//        shadow.setShadowColor(Color.black);
//        shadow.setShowLeftShadow(false);
//        shadow.setShowRightShadow(false);
//        shadow.setShowBottomShadow(true);
//        shadow.setShowTopShadow(false);
// 
//        shadow.setShadowSize(20);
//        shadowPanel.setBorder(shadow);
        

		
	}
	

	
	public void initialize()
	{
		version = new JLabel("");
		bltop=new JPanel();
		top_left=new JPanel();
		top_right=new JPanel();
		blmid=new JPanel();
		mid_left=new JPanel();
		mid_right=new JPanel();
		mid_mid=new JPanel();
		
		min=new JButton(new ImageIcon("assets/app_BUT_MIN.png"));
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.setToolTipText("Minimize");
		
		logout=new JLabel(new ImageIcon("assets/logout.png"),JLabel.CENTER);
		logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logout.setToolTipText("Logout");
		close=new JButton(new ImageIcon("assets/App_close.png"));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.setToolTipText("Close");
		cmp=new JLabel(new ImageIcon("assets/image4.png"),JLabel.CENTER);
		cmp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cmp.setToolTipText("Select Project");
		left=new JButton(new ImageIcon("assets/image5.png"));
		left.setCursor(new Cursor(Cursor.HAND_CURSOR));
		left.setToolTipText("Start Project");
		
		
		rockittime=new JLabel("RockeitTime",new ImageIcon("assets/image3.png"),JLabel.SOUTH_EAST);
		rockittime1=new JLabel("RockeitTime",new ImageIcon("assets/image6.png"),JLabel.SOUTH_EAST);
		time=new JLabel("00:00");
		select_project=new JLabel("Select Project");
		comment=new JLabel("Comment");
		name=new JLabel("",new ImageIcon("assets/image8.png"),JLabel.RIGHT);
		name.setIconTextGap(7);
		
	}
	
	public void run()
	{
	}

	}