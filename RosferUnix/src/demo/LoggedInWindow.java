package demo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

public class LoggedInWindow extends JFrame implements FocusListener,MouseListener,MouseMotionListener,ActionListener,KeyListener
{

	private static final long serialVersionUID = 1L;
	
	JPanel top_right,bltop_left,bltop_right;
	JLabel blbottom,blmid,mid_mid,mid_left,mid_right,left,leftStop,logout;
	JButton  min,close;
	JLabel hour,time,frame_shadow,name,cmp;
	JTextField comment;
	JLabel select_project;
	JScrollPane jsp;
	AreYouSure rus;
	boolean rusvisible;
	static	Color bg_color=new Color(0x2D313C);
	static	Color text_color=new Color(0x1A1701);
	Rosfer rosfer;
	SystemTray systray;
	TrayIcon ti;
	PopupMenu traypopup;
	MenuItem exit;
	WindowListener winlis;
	public LoggedInWindow(Rosfer rosfer)
	{
		this.rosfer=rosfer;
	
		setUndecorated(true);
		initialize();
		design1();
		register();
		addFocusListener(rosfer);		

	}
	
	public void register()
	{
		
		rus=new AreYouSure(rosfer,this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		min.addActionListener(this);
		close.addActionListener(this);
		left.addMouseListener(this);
		cmp.addMouseListener(this);
		logout.addMouseListener(this);
		leftStop.addMouseListener(this);
		select_project.setFocusable(true);
		comment.addKeyListener(this);
		
		
		
		
		
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
	
	int height,width;
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(ti!=null && e.getSource()==ti && e.getButton()==MouseEvent.BUTTON1)
		{
			systray.remove(ti);	
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_DEICONIFIED));
			this.setVisible(true);
		
		}
		else if(ti!=null && e.getSource()==ti && e.getButton()==MouseEvent.BUTTON3)
			ti.setPopupMenu(traypopup);
		
		if(rosfer.mycmp.isVisible())
		{
//			rosfer.mycmp.setVisible(false);
//			rosfer.mycmpdialogvisible=false;
		}
		height=e.getY();
		width=e.getX();
		
	}
	boolean first=true;
	public void mouseReleased(MouseEvent e)
	{
//		if(rosfer.ls!=null && rosfer.ls.isVisible())
//			rosfer.ls.requestFocus();

		if(e.getSource()==left && !rosfer.trai.isVisible() )
		{
			cmp.removeMouseListener(this);
			cmp.setEnabled(false);
			if(rosfer.mycmp.isVisible())
				rosfer.mycmp.minimize();
			rus.setLocation(getX()+20,getY()-rus.getHeight()+35);
			rus.setVisible(true);
		}
		

		
		
	
		
		else if(e.getSource()==leftStop && !rosfer.trai.isVisible() )
		{
			
			rosfer.defaultTime=0;
			stop();
	
		}
		else if(e.getSource()==logout)
		{
			rosfer.windowName="first";
			
			rosfer.defaultThread.interrupt();
			if(leftStop.isVisible())
				stop();

//			MouseEvent me = new MouseEvent(blp.cmp, 0, 0, 0, 100, 100, 1, false);
			for(MouseListener ml: rosfer.blp.cmp.getMouseListeners()){
			    rosfer.blp.cmp.removeMouseListener(ml);
			    System.out.println("Mouse Listener Removed");
			}
		
			rosfer.lw.check.setText("Checking Server...");
			rosfer.lw.check.setVisible(false);
			rosfer.lw.mid.setVisible(true);

			

			rosfer.blp.rockittime1.setVisible(false);
			rosfer.blp.rockittime.setVisible(true);;
			rosfer.blp.name.setVisible(false);
			rosfer.blp.logout.setVisible(false);
			rosfer.blp.cmp.setEnabled(false);
			rosfer.setBounds(getX(),getY()+20,310,65);
			rosfer.blp.cmp.removeMouseListener(rosfer);
		
//			setBackground(new javax.swing.plaf.ColorUIResource(238,238,238));
			rosfer.setVisible(true);
			rosfer.maximize(190-23);
			rosfer.ls.setLocation(getX()-rosfer.ls.getWidth(), getY()+getHeight()-rosfer.ls.getHeight());
			

			this.setVisible(false);
			rosfer.blp.setVisible(true);
		}
		
		
		else if(e.getSource()==cmp  && cmp.isEnabled())
		{
		
			 int height=rosfer.mycmp.window==1?120:270;
			 
			rosfer.mycmp.maximize(height);
			
			rosfer.mycmp.setBounds(getX()+50,getY()-rosfer.mycmp.getHeight()+60,200,height);
//			rosfer.mycmp.setVisible(true);
		}
		
		else if(rosfer.mycmp.isVisible())
		{
			rosfer.mycmp.setVisible(false);
		}
		
		
	}
	
	
	 void stop()
		{
		 	rosfer.defaultTime=0;
		 	rosfer.work_status=3;
			leftStop.setVisible(false);
			left.setVisible(true);
			cmp.addMouseListener(this);
			cmp.setEnabled(true);				
			new Thread(rosfer.trai).start();
		}
		
		public void startProject()
		{
			rus.setVisible(false);
			rosfer.work_status=1;
			rosfer.checkbreak.time=0;
			if(rosfer.ptu==null)
			{
				rosfer.ptu=new ProjectTimeUpdate(rosfer);
				rosfer.ptu.setDaemon(true);
				rosfer.ptu.start();
			}
			else
			{
				rosfer.ptu.reset();
			}	
			 
			
			left.setVisible(false);
			leftStop.setVisible(true);
			
			if(rosfer.sc==null)
			{
			rosfer.sc=new SnapCounter(time.getText(),rosfer);
			rosfer.snapcounterthread=new Thread(rosfer.sc);
			rosfer.snapcounterthread.setDaemon(true);
			rosfer.snapcounterthread.start();
			}
		rosfer.sc.takeSnap();
			
			if(cmp.isEnabled())
			{
				cmp.removeMouseListener(this);	
				cmp.setEnabled(false);
			}
			
		}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	public void mouseDragged(MouseEvent e)
	{
		if(System.getProperty("os.name").equalsIgnoreCase("Linux"))
		{
		if(((getX()>170||getX()+width<e.getXOnScreen()) && (getX()<dim.width-getWidth() || getX()+width>e.getXOnScreen())) && ((getY()>255 ||getY()+height<e.getYOnScreen()) && (getY()<dim.height-getHeight()||getY()+height>e.getYOnScreen())))
		{
		setLocation(e.getXOnScreen()-width, e.getYOnScreen()-height);
		}
		else
		{
			if(getX()<170 && getY()>=255 && getY()<dim.height-getHeight())
				setLocation(169,Math.max(e.getYOnScreen()-height,256));

			else if(getY()<=255 && getX()>170 && getX()<=dim.width-getWidth())
				setLocation(Math.min(e.getXOnScreen()-width,dim.width-getWidth()-1), 255);

			else if(getX()+getWidth()>=dim.width && getY()>255  && getY()<=dim.height-getHeight())
				setLocation((Math.min(dim.width-getWidth(),getX()+getWidth())), e.getYOnScreen()-height);

			else if(getY()>=dim.height-getHeight() && getX()>=170 && getX()<=dim.width-getWidth())
				setLocation(Math.min(e.getXOnScreen()-width,dim.width-getWidth())-1, dim.height-getHeight());

		
			
			else
			{
				if(getX()<170 && getY()<255)
					setLocation(170, 255);
				
				else if(getX()>dim.width-getWidth()&& getY()<255)
					setLocation(dim.width-getWidth()-1,256);
				
				else if(getX()>dim.height-getHeight()&& getY()>dim.width-getWidth())
					setLocation(170,dim.width-getWidth());
				
				else if(getX()<dim.width-getWidth()&&getY()>=dim.height-getHeight())
					setLocation(169,Math.min(e.getYOnScreen()-height,dim.height-getHeight()));

			}
		}
		}
		else
			this.setLocation(e.getXOnScreen()-width, e.getYOnScreen()-height);
		
		adjust();
//		if(leftStop.isVisible())
//			rosfer.ls.setVisible(true);
	}
	
	public void adjust()
	{
		if(rosfer.mycmp.isVisible())
			rosfer.mycmp.setBounds(getX()+50,getY()-rosfer.mycmp.getHeight()+60,200,rosfer.mycmp.getHeight());
		if(rus.isVisible())
			rus.setLocation(getX()+20,getY()-rus.getHeight()+35);
		if(rosfer.ls.isVisible())
		{
			rosfer.ls.setLocation(rosfer.liw.getX()-rosfer.ls.getWidth(), rosfer.liw.getY()+rosfer.liw.getHeight()-rosfer.ls.getHeight());
//			rosfer.ls.toFront();
		}		
	}
	public void mouseMoved(MouseEvent e){}

public void actionPerformed(ActionEvent e)
{
	if(e.getSource()==min)
	{
		if(rus.isVisible())
		{
			rus.setVisible(false);
			cmp.setEnabled(true);
			cmp.addMouseListener(this);
		}
		if(rosfer.mycmp.isVisible())
			rosfer.mycmp.setVisible(false);
		
		this.setExtendedState(this.getExtendedState()|JFrame.ICONIFIED);

	}
	else if(e.getSource()==close)
	{
	//	this.setExtendedState(this.getExtendedState()|JFrame.ICONIFIED);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_ICONIFIED));
		
		if(SystemTray.isSupported())
		{
			try{
		systray=SystemTray.getSystemTray();
		
		ti=new TrayIcon(Toolkit.getDefaultToolkit().getImage("assets/16x16new.png"));
		systray.add(ti);
		ti.addMouseListener(this);
		
		traypopup=new PopupMenu();
		exit=new MenuItem("Exit");
		traypopup.add(exit);
		this.setVisible(false);
		exit.addActionListener(this);
			}catch(Exception eeee){System.out.println(eeee);}
		}
	
			
		
		
		
		
	}
	
	else if(exit!=null && e.getSource()==exit)
	{
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}


	public void propertyChange(PropertyChangeEvent e)
	{
		System.out.println(e.getNewValue());
		
	}


	private void design1()
	{
		setLayout(null);
		setBackground(new Color(0,0,0,0));
		this.setSize(310, 105);

		
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
		
		blbottom.setLayout(null);
		blbottom.setBounds(0,64, 310, 41);
		blbottom.setBackground(new Color(0xfee105));
		add(blbottom);
		time.setBounds(10, 5, 150, 30);
		time.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,31));
		time.setForeground(Color.black);
		blbottom.add(time);
		blbottom.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 1, 1, 1,new Color(0x293334)), BorderFactory.createMatteBorder(0, 1, 1, 1,new Color(0xd5b500))));

		

	}
	

	
	public void initialize()
	{
		top_right=new JPanel();
		blmid=new JLabel(new ImageIcon("assets/image7.png"));
		blbottom=new JLabel(new ImageIcon("assets/image14.png"));
		mid_left=new JLabel(new ImageIcon("assets/image7.png"));
		mid_right=new JLabel(new ImageIcon("assets/image7.png"));
		bltop_left=new JPanel();
		bltop_right=new JPanel();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("assets/48x48new.png"));
		
		
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
		time=new JLabel((rosfer.tu!=null)?rosfer.tu.getCurTime():"00:00:00");
		
			

		comment=new JTextField("Comment");
		select_project=new JLabel("Select Project");
		frame_shadow=new JLabel(new ImageIcon("assets/image19.png"));
		name=new JLabel("Roushan",new ImageIcon("assets/image8.png"),JLabel.RIGHT);
		name.setIconTextGap(7);

		rosfer.ls=new LeftSnap(rosfer,this);
		
		
		
		winlis=new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				if(rosfer.work_status==2)
				{
				rosfer.liw.setVisible(false);
					rosfer.ls.setVisible(false);
				Thread t=new Thread(){
					public void run()
					{
						if(rosfer.work_status!=3)
						{
							rosfer.work_status=3;
							rosfer.trai.takeScreenshot();
						}
					}
				};
				
				t.start();
				try{t.join();}catch(Exception exc){}
				}
				System.out.println("running window closing event");
				System.exit(0);
				
			}
			
		@Override	
		public void windowIconified(WindowEvent e)
		{
			System.out.println("iconified in liw");
			
			
/*				if(rus.isVisible())
					{rus.setVisible(false); rusvisible=true;}
				else
					rusvisible=false;
				
				if(rosfer.mycmp.isVisible())
					{rosfer.mycmp.setVisible(false); rosfer.mycmpdialogvisible=true;}
				else
					rosfer.mycmpdialogvisible=false;
*/				
//				if(rosfer.ls.isVisible())
//				{rosfer.ls.setVisible(false); rosfer.leftsnapvisible=true;}
//				else
//				rosfer.leftsnapvisible=false;

				System.out.println(rosfer.mycmpdialogvisible);
			
		}
		@Override
		public void windowDeiconified(WindowEvent e)
		{
			rosfer.liw.getContentPane().repaint();
			System.out.println("deiconified in liw");
			
				rosfer.ls.setVisible(rosfer.leftsnapvisible);
				
				System.out.println("Left snap visible istatus is "+rosfer.leftsnapvisible);
		
				

		}
		@Override
		public void windowStateChanged(WindowEvent e)
		{
			System.out.println("window state changed");
			if(rosfer.liw.getState()==JFrame.NORMAL)
			{
				System.out.println("window state is normal");
				if(rusvisible)
					rus.setVisible(true);
				if(rosfer.leftsnapvisible)
					rosfer.ls.setVisible(true);
				if(rosfer.mycmpdialogvisible)
				{
					rosfer.mycmp.setVisible(true);
//					rosfer.mycmp.requestFocus();
				}
				
			}
		}
		};
		
		
		this.addWindowListener(winlis);
		
		
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
	public void keyPressed(KeyEvent ke)
	{
		System.out.println(ke.getKeyCode());
		if(ke.getKeyCode()==10)
		{
			requestFocusInWindow();
		}
		
	}
	
	public void keyTyped(KeyEvent ke){}
	public void keyReleased(KeyEvent ke){}
	
}
	
