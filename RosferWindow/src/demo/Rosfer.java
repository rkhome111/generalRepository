package demo;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdesktop.swingx.border.DropShadowBorder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
// new update on 20 july 2016
public class Rosfer extends JFrame implements MouseListener,MouseMotionListener,ActionListener,FocusListener
{
	private static final long serialVersionUID = 1L;
	int height,width;
	JPanel main_top,main_mid,bottom;
	Before_login_panel blp;
	LoginWindow lw;
	LoggedInWindow liw;
	AreYouSure rus;
	LeftSnap ls;
	MyComponent1 mycmp;
	ProjectTimeUpdate ptu;
	TimeUpdate tu;
	String sys_name,server_address,userId,projectId,projectTlId,projectTeamId,tlId,teamId,status,selectedProject,task_text="",subtask_text="",projectName;
	int task_id,task_list_id,work_status=1;
	Font opensanslight,opensansregular;
	boolean rusvisible,leftsnapvisible,mycmpdialogvisible,maxvisible,logoutvisible,intervalsnap,isgone;
	ConnectDb cdb;
	MyListListener mll;
	Document document;
	int interval,defaultTime,increaseDay=0,timegap;
//	Adder stub;
	String user,pass,projectNames[];
	Thread snapcounterthread;
	SnapCounter sc;
	public static Rosfer rosfer;
	public static int rou = 10;
	Timer minTimer,maxTimer,changeWindowTimer;
	private String osname;
	LocalToServer localtoserver;
//	LocalToServer1 localtoserver1;
	SystemTray systray;
	TrayIcon ti;
	PopupMenu traypopup;
	MenuItem exit;
	Thread defaultThread;
	DefaultAlertWindow daw;
	Trai trai;
	MaxDialog max;
	CheckBreak checkbreak;
	static int checklogin=0;
	Adder stub;
	public String version;





//public Rosfer(){}

	public Rosfer()
	{
		super("Rosfer");
		
		
//		try{
//		InputStream din1 =new URL("http://10.0.4.6/Roushan_Java/copy_paste/localdb.db").openStream();
//		String name = System.getProperty("os.name").toLowerCase().contains("win")?"localdb":"localdb.db";
//		
//		FileOutputStream fout1 = new FileOutputStream("DB/"+name);
//		byte [] b1 = new byte[din1.available()];
//		din1.read(b1);
//		fout1.write(b1);
//		din1.close();
//		fout1.close();
//		}catch(Exception e){e.printStackTrace();}
		
		
		
		
		JFrame.setDefaultLookAndFeelDecorated(false);
		setLayout(null);
		rosfer=this;
//		System.out.println(rosfer);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("assets/48x48new.png"));
		this.setType(Type.POPUP);
		trai=new Trai(this);
		sys_name=System.getProperty("user.name");
		System.out.println(sys_name);
		
		try{
			Class.forName("org.sqlite.JDBC");
			
			Date dt = new Date();
			PrintStream changeOutput = new PrintStream(new FileOutputStream(new File("assets/log"+dt.getDate()+"-"+(dt.getMonth()+1)+"-"+(dt.getYear()+1900)+".txt"),true));
			System.setOut(changeOutput);
			
			

			opensanslight = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/OpenSans-Light.ttf"));
			opensansregular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/OpenSans-Regular.ttf"));
			osname=System.getProperty("os.name");
		
			}catch(Exception e){System.out.println("Unable to load your font"+e);}
		
		cdb=new ConnectDb(this);
		lw=new LoginWindow(this);
		
		blp=new Before_login_panel(this);

	
		blp.name.setVisible(false);
		blp.logout.setVisible(false);
		
		
		main_top=new JPanel();
		main_mid=new JPanel();
		
		setSize(310, 85);
		lw.setSize(310, 190);
		blp.setSize(310,85);
//		blp.cmp.setEnabled(false);
		setUndecorated(true);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);

		setBackground(new Color(1f, 1f, 1f,0f));
		this.getContentPane().setBackground(new Color(1f, 1f, 1f,0f));
//		
		bottom=new JPanel();
		bottom.setBounds(-10,getHeight()-20, getWidth()+20, 20);
		bottom.setBackground(new Color(0,0,0,0));
		bottom.setBorder(rosfer.getShadowBorder(Color.black, false, false, false, true, 20));
		add(bottom);
		
		
		lw.setVisible(true);
		add(blp);
		
	

	
//		 getRootPane().setBorder(shadow);

		
		setLocationRelativeTo(null);
		setVisible(true);
		lw.setLocation(this.getX(),this.getY()-190);
		lw.setVisible(true);
		ls=new LeftSnap(this);
//		rus=new AreYouSure(this);
		max=new MaxDialog(this);
		
		
		UIManager.put("ToolTip.background",new Color(0xF4F4C4));
		UIManager.put("ToolTip.foreground",Color.black);
		UIManager.put("ToolTip.border",BorderFactory.createEmptyBorder());
		register();
		
//		lw.connect_tf.requestFocus();
		
		mll=new MyListListener(this);
		if(lw.login_auto.isSelected())
		{
			try{Thread.sleep(1000);}catch(Exception e){}
			MouseEvent me = new MouseEvent(lw.login, 0, 0, 0, 100, 100, 1, false);
			for(MouseListener ml: lw.login.getMouseListeners()){
			    ml.mouseReleased(me);
			}

		}
		
		changeWindowTimer=new Timer(0, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				rosfer.setSize(310, 125);
				rosfer.liw.setBounds(0, 0, 310, 105);
				rosfer.liw.name.setText(rosfer.blp.name.getText());
				rosfer.liw.setVisible(true);
				rosfer.blp.setVisible(false);
				rosfer.setLocation(rosfer.getX(),rosfer.getY()-20);
				rosfer.add(rosfer.liw);
				rosfer.bottom.setBounds(-10,rosfer.getHeight()-20, rosfer.getWidth()+20, 20);
				if(rosfer.ls!=null)
					rosfer.ls.setLocation(rosfer.getX()-rosfer.ls.getWidth(), rosfer.getY()+rosfer.getHeight()-rosfer.ls.getHeight());
				rosfer.getContentPane().repaint();
			}
		});
		
		 changeWindowTimer.setRepeats(false);

		 new UpdateGraphics(this);
		
	}
		
		
		

		
	
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource()==blp.close||(liw!=null && e.getSource()==liw.close))
		{
			this.setExtendedState(this.getExtendedState()|JFrame.ICONIFIED);
			
			if(SystemTray.isSupported())
			{	this.setVisible(false);
				try{
			systray=SystemTray.getSystemTray();
			ti=new TrayIcon(Toolkit.getDefaultToolkit().getImage("assets/16x16new.png"));
			systray.add(ti);
			ti.addMouseListener(this);
			
			traypopup=new PopupMenu();
			exit=new MenuItem("Exit");
			traypopup.add(exit);
			exit.addActionListener(this);
				}catch(Exception eeee){System.out.println(eeee);}
			}
			

		}
		else if(exit!=null && e.getSource()==exit)
		{
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if(e.getSource()==blp.min||(liw!=null && e.getSource()==liw.min))
		{
			this.setExtendedState(this.getExtendedState()|JFrame.ICONIFIED);
			//dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_ICONIFIED));
		
		}
		else if(e.getSource()==lw.min)
		{
			minimize(true);	
			max.setLocation(getX()+310-24, getY()-23);
		}

	}
	
	int i=0;
	Timer t;
	private void minimize(final boolean b)
	{

//		t=new Timer(waitTime, new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				
//			lw.setBounds(lw.getX(), lw.getY()+initial, 310, lw.getHeight()-initial);
//			lw.top.setBounds(0, lw.getHeight()-190, 310, 22);
//			lw.mid.setBounds(0, lw.getHeight()-170, 310, 170);
//
//			if(lw.getHeight()<=0)
//			{
//				lw.setVisible(false);
//				t.stop();
//				if(b)
					max.setVisible(b);
//			}
//		
//			}
//		});
//		t.start();
		
		lw.setVisible(false);
		if(blp.name.isVisible())
			max.setVisible(false);
	}
	
	private void maximize(int size)
	{
		
//		lw.setBounds(getX(), getY(), 0, 0);
//		lw.setVisible(true);
//		maxTimer=new Timer(waitTime, new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				lw.setBounds(getX(),lw.getY()-initial,310,lw.getHeight()+initial);
//				lw.top.setBounds(0, lw.getHeight()-190, 310, 22);
//				lw.mid.setBounds(0, lw.getHeight()-170, 310, 170);
//				if(lw.getHeight()>=190)
//				{
//					maxTimer.stop();
					lw.setBounds(getX(),getY()-190,310,190);
					lw.top.setBounds(0, lw.getHeight()-190, 310, 22);
					lw.mid.setBounds(0, lw.getHeight()-170, 310, 170);
					lw.setVisible(true);
//				}
//			}
//		});
//		maxTimer.start();
}
	
//	private void maximize(int size)
//	{
//		
//		lw.setBounds(getX(), getY(), 0, 0);
//		lw.setVisible(true);
//		while(lw.getHeight()<190)
//		{
//				lw.setBounds(getX(),lw.getY()-(initial*2),310,lw.getHeight()+(initial*2));
//				this.setBackground(new Color(0,0,0,0));
//				if(lw.getHeight()>=190)
//					lw.setBounds(getX(),getY()-190,310,190);
//		}
//
//}
	
	public Calendar getCurrentTime()
	{
		Calendar cal=null;
		try{
			URL oracle = new URL("http://www.worldtimezone.com/time/wtzresult.php?CiID=3958&forma=24h");
	        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
	        String inputLine;
	        StringBuffer ss=new StringBuffer();
	        while ((inputLine = in.readLine()) != null)
	            ss.append(inputLine);
	        in.close();
//	        System.out.println(ss.toString());
	      String date=ss.toString().substring(ss.toString().indexOf("><h5>")+5,ss.toString().indexOf("</td></tr>    <tr><td colspan=2 align=\"center\" bgcolor=\"#FFFFFF\"><b>Standard Time Zone:"));
//	      Date dt=DateFormat.getInstance().parse(date);
	      Date dt=new Date(Date.parse(date));
	      cal=Calendar.getInstance();
	      cal.setTime(dt);
	      
	      return cal;
			}catch(Exception e){return null;}

	}
	
	public static void main(String args[]) throws Exception
	{	
		try{
//			System.setProperty("java.rmi.server.hostname", "localhost");
			Registry registry=LocateRegistry.createRegistry(4444);
			Rosfer r=new Rosfer();
			shutdown sh=new shutdown(r);
			Runtime.getRuntime().addShutdownHook(sh);
		}catch(Exception e){
			
			DefaultAlertWindow daw = new DefaultAlertWindow(null);
			daw.message1.setText("Another instance is running.");
			daw.message2.setText(" Restart  system.");
			daw.ok.setVisible(false);
			daw.setVisible(true);
			Thread.sleep(3000);
			System.exit(1);	
		}	
	}

	public void closeYourself()
	{
		System.out.println("closeYourself called.");
		
		System.exit(1);
	}
	public void mousePressed(MouseEvent e)
	{
		
		if(ti!=null && e.getSource()==ti && e.getButton()==MouseEvent.BUTTON1)
		{
			systray.remove(ti);
			ti.removeMouseListener(this);
			this.setVisible(true);
			setExtendedState(JFrame.NORMAL);	
		}
		else if(ti!=null && e.getSource()==ti && e.getButton()==MouseEvent.BUTTON3)
			ti.setPopupMenu(traypopup);

		else if(ls!=null)
		{
			ls.requestFocus();
			if(e.getSource()==ls)
				this.requestFocus();
		}
		
		if(e.getSource()==lw.mid)
			height=e.getY()+10;
		else
			
		height=e.getY();
		width=e.getX();
		
		
		}

//	}
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	public void mouseDragged(MouseEvent e)
	{
		if(osname.equalsIgnoreCase("Linux"))
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
			setLocation(e.getXOnScreen()-width, e.getYOnScreen()-height);
		adjust();
	}
	
	protected void adjust()
	{
		if(rus!=null)
			rus.setLocation(getX()+20,getY()-rus.getHeight()+35);
			ls.setLocation(getX()-ls.getWidth(), getY()+getHeight()-ls.getHeight());
			lw.setLocation(getX(),getY()-190);
			max.setLocation(getX()+310-24, getY()-23);
		
		if(mycmp!=null)
			mycmp.setLocation(getX()+50,getY()-mycmp.getHeight()+55);
		
		
		
//		if(liw!=null && liw.top_right.isVisible())
//			rosfer.liw.top_right.setLocation(rosfer.getX()+310-rosfer.liw.top_right.getWidth(), rosfer.getY()-rosfer.liw.top_right.getHeight());
	}
	boolean select=false;
	public void mouseReleased(MouseEvent e)
	{
		if(e.getSource()==max.max_button)
		{
			maximize(lw.getHeight());
			max.setVisible(false);
		}
		if(liw!=null && e.getSource()==liw.left && !trai.isVisible())
		{
			liw.cmp.removeMouseListener(this);	
			liw.cmp.setEnabled(false);
			rus.setLocation(getX()+20,getY()-rus.getHeight()+35);
			rus.setVisible(true);
					
	
		}
		
		
		else if(mycmp!=null && e.getSource()==mycmp.done)
		{
			liw.select_project.setText(mycmp.title.getText().substring(2));
			storeId(true);
			
				mycmp.minimize(mycmp.getHeight());
				
				if(blp.isVisible())
				{
					changeWindowTimer.start();
//					setSize(310, 125);
//				liw.setBounds(0, 0, 310, 105);
//				liw.name.setText(blp.name.getText());
//				liw.setVisible(true);
//				blp.setVisible(false);
//				setLocation(getX(),getY()-20);
//				
//				rosfer.bottom.setBounds(-10,rosfer.getHeight()-20, rosfer.getWidth()+20, 20);
//				add(liw);
				}
		}
		
		
		

		
		
		
	
		else if(e.getSource()==lw.login)// && !ls.isVisible())
		{
			
			mycmp=null;	
			document=null;
			stub=null;
			defaultTime=0;
			increaseDay=0;
			lw.mid.setVisible(false);
			
			blp.bltop.paint(blp.bltop.getGraphics());
			blp.blmid.paint(blp.blmid.getGraphics());
					user=lw.username_tf.getText();
			pass=new String(lw.password_tf.getPassword());
			server_address=lw.connect_tf.getText();
			for(MouseListener ml: blp.cmp.getMouseListeners())
			    blp.cmp.removeMouseListener(ml);
																
			
			if(user.equals(""))
				lw.username_tf.setBorder(BorderFactory.createLineBorder(Color.red));
			else
				lw.username_tf.setBorder(BorderFactory.createEmptyBorder());
			
			if(pass.equals(""))
				lw.password_tf.setBorder(BorderFactory.createLineBorder(Color.red));
			else
				lw.password_tf.setBorder(BorderFactory.createEmptyBorder());
			
			if(server_address.equals(""))
				lw.connect_tf.setBorder(BorderFactory.createLineBorder(Color.red));
			else
				lw.connect_tf.setBorder(BorderFactory.createEmptyBorder());
				
		
			if(!((user.equals("")||pass.equals("") )))//||server_address.equals("")))
			{
				
					new Thread(){public void run(){
						try{
							doLogin();
						}catch(Exception exce){System.out.println(exce);}
					}}.start();
				
				
				
			}
			
	}
		
	else if(e.getSource()==blp.logout)
		{
//			setBackground(new Color(1f, 1f, 1f,0f));
			blp.name.setVisible(false);
			blp.logout.setVisible(false);
			blp.rockittime.setVisible(true);
			blp.rockittime1.setVisible(false);
			blp.cmp.removeMouseListener(this);
			lw.check.setText("Checking Connection from Server");
			lw.check.setVisible(false);
			lw.mid.setVisible(true);
	
			maximize(190-23);
			
			
		}
		
		else if(liw!=null && e.getSource()==liw.logout)
		{
			
			
			defaultThread.interrupt();
			if(liw.leftStop.isVisible())
				stop();
			
//			try{Thread.sleep(1000);}catch(Exception exc){}
//			MouseEvent me = new MouseEvent(blp.cmp, 0, 0, 0, 100, 100, 1, false);
			for(MouseListener ml: blp.cmp.getMouseListeners()){
			    blp.cmp.removeMouseListener(ml);
			    System.out.println("Mouse Listener Removed");
			}
			if(rus.isVisible())
				rus.setVisible(false);
		
			lw.check.setText("Checking Connection from Server");
			lw.check.setVisible(false);
			lw.mid.setVisible(true);

			liw.setVisible(false);
			blp.setVisible(true);

			blp.rockittime1.setVisible(false);
			blp.rockittime.setVisible(true);;
			blp.name.setVisible(false);
			blp.logout.setVisible(false);
			blp.cmp.setEnabled(false);
			setBounds(getX(),getY()+40,310,85);
			if(ls.isVisible())
				bottom.setBounds(-30,rosfer.getHeight()-20, rosfer.getWidth()+50,20);
			else
				bottom.setBounds(-10,rosfer.getHeight()-20, rosfer.getWidth()+30,20);
			blp.cmp.removeMouseListener(this);

			maximize(190-23);
			ls.setLocation(getX()-ls.getWidth(), getY()+getHeight()-ls.getHeight());
			
		}
		
		else if(liw!=null && e.getSource()==liw.leftStop && !trai.isVisible())
		{
			
			stop();
		}
			


		
		else if(e.getSource()==blp.cmp)
		{
			initializeMyComponent();
			
			 mycmp.maximize(120);
			 

		}
		
		else if(liw!=null && e.getSource()==liw.cmp && !mycmp.isVisible())
		{
			 mycmp.setLocation(getX()+50,getY()-mycmp.getHeight()+55);
			 int height=mycmp.window==1?120:270;
			 mycmp.tf.setText("    -------Search Project-------");
			 Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			 mycmp.maximize(height);

		}
		
		if(mycmp!=null && mycmp.isVisible()&&!(e.getSource()==blp.cmp||e.getSource()==liw.cmp))
			mycmp.minimize(mycmp.getHeight());

		
		}	
	

	@Override
	public void focusGained(FocusEvent e)
	{
		adjust();
	}
	public void focusLost(FocusEvent e){}
	public void mouseMoved(MouseEvent e)
	{
		if(select)
		{
			mouseDragged(e);
		}
	}
	public  void stop()
	{
		defaultTime=0;
		work_status=3;
		
		liw.leftStop.setVisible(false);
		
		liw.cmp.addMouseListener(this);
		liw.cmp.setEnabled(true);
		
		liw.left.setVisible(true);
		
		new Thread(rosfer.trai).start();
	}
	
	// on project start use takeScreenshot() and stop use only new Thread(rosfer.trai).start();
	public void startProject()
	{
		work_status=1;
		rus.setVisible(false);
		liw.left.setVisible(false);
		liw.leftStop.setVisible(true);
		rosfer.checkbreak.time=0;
		
		if(sc==null)
		{
			sc=new SnapCounter(liw.time.getText(),this);
			snapcounterthread=new Thread(sc);
			snapcounterthread.setDaemon(true);
			snapcounterthread.start();
		}
		
		
		
				if(ptu==null)
				{
					ptu=new ProjectTimeUpdate(this);
					ptu.setDaemon(true);
					ptu.start();
				}
				else
				{
					ptu.reset();
				}	

					if(liw.cmp.isEnabled())
					{
						liw.cmp.removeMouseListener(this);	
						liw.cmp.setEnabled(false);
					}
		
		sc.takeSnap();
	}
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	
	
	public void initializeMyComponent()
	{
		
		if(mycmp==null)
		{
		 mycmp= new MyComponent1(document,this);
		
		 mycmp.list.addListSelectionListener(mll);
		 mycmp.setLocation(getX()+50,getY()-mycmp.getHeight()+55);

		 	rus=new AreYouSure(this);
			liw.min.addActionListener(this);
			liw.close.addActionListener(this);
			liw.left.addMouseListener(this);
			liw.cmp.addMouseListener(this);
			liw.logout.addMouseListener(this);
			liw.leftStop.addMouseListener(this);
		 Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	public  void setSnap(LeftSnap l)
	{
		ls=l;
		ls.addMouseListener(this);
	}
	
	
	public Border getShadowBorder(Color color,boolean left,boolean right,boolean top,boolean bottom1,int size)
	{
		DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(color);
        shadow.setShowLeftShadow(left);
        shadow.setShowRightShadow(right);
        shadow.setShowTopShadow(top);
        shadow.setShowBottomShadow(bottom1);
        shadow.setShadowSize(size);
       
		return shadow;
	}
	
	
	public void storeId(boolean b)
	{
		liw.comment.setText("Comment");
		if(checkbreak==null)
			checkbreak=new CheckBreak(this);
		projectName=liw.select_project.getText();
		if(b)
		{
			task_text=(String)mycmp.taskcmb.getSelectedItem();
			subtask_text=((String)mycmp.subtaskcmb.getSelectedItem())!=null?(String)mycmp.subtaskcmb.getSelectedItem():"";
			
			try{
				NodeList nl=document.getElementsByTagName("project");
					for(int i=0;i<nl.getLength();i++)
					{
						Element node=(Element)nl.item(i);
						if(node.getElementsByTagName("projectname").item(0).getTextContent().equals(liw.select_project.getText()))
						{
							System.out.println("same project");
							projectId=node.getElementsByTagName("projectid").item(0).getTextContent();
							projectTlId=node.getElementsByTagName("projecttlid").item(0).getTextContent();
							projectTeamId=node.getElementsByTagName("projectteamid").item(0).getTextContent();
							
							
							NodeList tasknl=node.getElementsByTagName("task");
							for(int k=0;k<tasknl.getLength();k++)
							{
								Element taskel=(Element)tasknl.item(k);
								if(taskel.getElementsByTagName("taskname").item(0).getTextContent().equals(task_text))	
								{
									task_list_id=Integer.parseInt(taskel.getElementsByTagName("taskid").item(0).getTextContent());
									task_id=0;
									
									NodeList subnl=taskel.getElementsByTagName("subtask");
									for(int j=0;j<subnl.getLength();j++)
									{
										Element subelement=(Element)subnl.item(j);
										if(subelement.getElementsByTagName("subtaskname").item(0).getTextContent().equals(subtask_text))
										{
											task_id=Integer.parseInt(subelement.getElementsByTagName("subtaskid").item(0).getTextContent());
										}	
									}
								}
								
							}
						}	
					}
					
			}catch(Exception e){System.out.println(e);}
			task_text+=" ";
		}
		
		else
		{
			NodeList nl=document.getElementsByTagName("project");
			for(int i=0;i<nl.getLength();i++)
			{
				Element node=(Element)nl.item(i);
				if(node.getElementsByTagName("projectname").item(0).getTextContent().equals(liw.select_project.getText()))
				{
					projectId=node.getElementsByTagName("projectid").item(0).getTextContent();
					projectTlId=node.getElementsByTagName("projecttlid").item(0).getTextContent();
					projectTeamId=node.getElementsByTagName("projectteamid").item(0).getTextContent();
					break;
				}
			}
			task_id=0;
			task_list_id=0;
			task_text="";
			subtask_text="";
			
		}
		
		System.out.println("project id: "+projectId+"\nproject name:"+projectName+" \ntaskid:  "+task_id+"\n task_list id: "+task_list_id);
		System.out.println("task Text "+task_text+"\nsubtask_text: "+subtask_text);
		System.out.println("project TL Id :"+projectTlId+"\nproject Team Id :"+projectTeamId);
		
	}
	
	public void register()
	{
	lw.connect_tf.addMouseListener(this);
	addMouseListener(this);
	addMouseMotionListener(this);
	
	
	blp.close.addActionListener(this);
	blp.min.addActionListener(this);
	blp.logout.addMouseListener(this);
	lw.min.addActionListener(this);
	lw.login.addMouseListener(this);
	
	

	
		addWindowListener(new WindowAdapter(){				
		public void windowClosing(WindowEvent e)
		{
			if(rosfer.liw!=null && rosfer.work_status==2)
			{
				rosfer.setVisible(false);
				rosfer.ls.setVisible(false);
//			Thread t=new Thread(){ public void run() 
//							{
//								if(rosfer.work_status!=3)
//								{
									rosfer.work_status=3;
									rosfer.trai.takeScreenshot();;
//								}
//							}};
//					t.start();
//					try{t.join();}catch(Exception exc){System.out.println(exc);}
			}
				
			System.out.println("Window closing event is running");
			System.exit(1);
		}

		@Override
	public void windowIconified(WindowEvent e)
	{
			
			if(ls.isVisible())
				{ls.setVisible(false); leftsnapvisible=true;}
			else
				leftsnapvisible=false;
	

			if(rus!=null && rus.isVisible())
				{rus.setVisible(false); rusvisible=true;}
			else
				rusvisible=false;
	
			if(mycmp!=null && mycmp.isVisible())
				{mycmp.setVisible(false); mycmpdialogvisible=true;}
			else
				mycmpdialogvisible=false;
			
			
//			if(liw!=null && liw.top_right.isVisible())
//			{	liw.top_right.setVisible(false); logoutvisible=true;}
//			else
//				logoutvisible=false;
	}

		@Override
		public void windowDeiconified(WindowEvent e)
		{
		
				if(leftsnapvisible)
					ls.setVisible(true);
				
				
				if(mycmpdialogvisible)
					mycmp.setVisible(true);
				
//				if(logoutvisible)
//					liw.top_right.setVisible(true);
				
				if(rusvisible)
					rus.setVisible(true);
				
				if(blp.isVisible())
					blp.requestFocusInWindow();
				if(liw!=null && liw.isVisible())
					liw.requestFocusInWindow();
				rosfer.update(rosfer.getGraphics());
				if(ls.isVisible())
					ls.update(ls.getGraphics());
		}
	
	});
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void doLogin() throws Exception
	{
			lw.mid.setVisible(false);
			lw.check.setVisible(true);
			lw.paintAll(lw.getGraphics());
			
			
			FileInputStream f=new FileInputStream(new File("assets/version.txt"));
			byte vs[]=new byte[f.available()];
			f.read(vs);
			f.close();
			String readfile=(new String(vs).trim());
			server_address=readfile.substring(readfile.indexOf(";;")+2);
			version=readfile.substring(0,readfile.indexOf(";;"));
			System.out.println("Version is "+version);
			System.out.println("server address is "+server_address);
			System.out.println(user+"  "+pass);
			//login from server
			Thread t = new Thread(){public void run(){ 
			try{
					System.out.println("trying to login...");
					
					stub=(Adder)Naming.lookup("rmi://"+server_address+"/rkrmi");
					document=stub.login(user, pass);
					System.out.println(document);
					System.out.println("Logged in from server");

			}catch(Exception ex){ }

			}};
			t.setDaemon(true);
			t.start();
			t.join(15*1000);
			
			
			//login from local system
			if(document==null)
			{
				FileInputStream fin=new FileInputStream(new File("assets/user"));
				byte[] b=new byte[fin.available()];
				fin.read(b);
				fin.close();
				String ss = new String (b);
				System.out.println("value of ss is "+ss);
						
					if(ss!=null && ss.substring(ss.indexOf(",")+1, ss.lastIndexOf(",")).equals(user) && ss.substring(ss.lastIndexOf(",")+1).trim().equals(pass))
					{
							System.out.println("Login locally.......");	
//							System.out.println("user detail is "+ss);
							DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
							DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
							Document doc = dBuilder.parse(new File("assets/file.xml"));
							System.out.println(doc);
									
							try{
								Calendar calender=getCurrentTime();
								calender.add(Calendar.SECOND, Integer.valueOf(doc.getElementsByTagName("timegap").item(0).getTextContent()));
								System.out.println("calender"+calender);
								doc.getElementsByTagName("serverdate").item(0).getFirstChild().setNodeValue(calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+calender.get(Calendar.DAY_OF_MONTH));
								doc.getElementsByTagName("servertime").item(0).getFirstChild().setNodeValue(calender.get(Calendar.HOUR_OF_DAY)+":"+calender.get(Calendar.MINUTE)+":"+calender.get(Calendar.SECOND));
								document=doc;
								
								}catch(Exception exc){
									lw.check.setText("No Network connection found");
									lw.paintAll(lw.getGraphics());
									Thread.sleep(2000);lw.check.setText("Checking Connection from Server");
									lw.check.setVisible(false);
									lw.mid.setVisible(true);
									lw.paintAll(lw.getGraphics());
								}
							}
							else
								showInvalidMessage();

					
					
				}
				
			
			//document is not null
			if(document!=null && document.getElementsByTagName("userid").getLength()>0)
			{
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File("assets/file.xml"));
				transformer.transform(source, result);
				
				new Thread(){public void run(){
					try{
						if(stub!=null){
						byte[] b=stub.getUpdateFile("na");
						FileOutputStream fout=new FileOutputStream("update.jar");
						System.out.println("update file updated");
						fout.write(b);
						fout.close();
									}
					}catch(Exception e){System.out.println(e);}
				}}.start();
				
				
				lw.check.setText("Checking Connection from Server");
				lw.paintAll(lw.getGraphics());
				Thread.sleep(2000);
				minimize(false);
				blp.name.setText(user);
				blp.name.setVisible(true);
				if(blp.name.getText().length()>5)
					blp.name.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,7));
				else
					blp.name.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN,9));
				blp.logout.setVisible(true);
				blp.rockittime.setVisible(false);
				blp.rockittime1.setVisible(true);
				
				
				userId=document.getElementsByTagName("userid").item(0).getTextContent();
				tlId=document.getElementsByTagName("tlid").item(0).getTextContent();
				teamId = document.getElementsByTagName("teamid").item(0).getTextContent();
				timegap=Integer.valueOf(document.getElementsByTagName("timegap").item(0).getTextContent());
				status=document.getElementsByTagName("status").item(0).getTextContent();
				interval=Integer.parseInt(document.getElementsByTagName("interval").item(0).getTextContent());
				
				liw=new LoggedInWindow(rosfer);
				
				localtoserver=new LocalToServer(rosfer);
//				localtoserver1 = new LocalToServer1(rosfer);
				if(rosfer.tu==null)
					rosfer.tu=new TimeUpdate(rosfer);
				
					DataOutputStream dout=new DataOutputStream(new FileOutputStream("assets/user"));
					DataOutputStream dout1=new DataOutputStream(new FileOutputStream("assets/login"));
					
				if(lw.remember_cb.isSelected())
						dout.writeUTF(server_address+","+lw.username_tf.getText()+","+pass);

				else
					dout.writeUTF("");

					if(lw.login_auto.isSelected())
						dout1.writeUTF("Login automatically");
					else
						dout1.writeUTF("");
					
					dout.close();dout1.close();

				
				blp.cmp.addMouseListener(rosfer);
				daw=new DefaultAlertWindow(rosfer);
				defaultThread=new Thread(daw);
				defaultThread.setDaemon(true);
				defaultThread.start();
			}
			
			else
				showInvalidMessage();
			

	}
	
	
	private void showInvalidMessage() throws Exception
	{
		lw.check.setText("Invalid username or password");
		lw.paintAll(lw.getGraphics());
		Thread.sleep(2000);lw.check.setText("Checking Connection from Server");
		lw.check.setVisible(false);
		lw.mid.setVisible(true);
		lw.paintAll(lw.getGraphics());
	}
	
	
	
	
	
	
	
}


class MyListListener implements ListSelectionListener
{
	Rosfer rosfer;
	MyListListener(Rosfer rosfer)
	{
		this.rosfer=rosfer;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
			
			if(!e.getValueIsAdjusting()  && rosfer.mycmp.list.getSelectedValue()!=null)
//			if(rosfer.mycmp.list.getSelectedValue()!=null)

			{
//				try{
//					Thread.sleep(400);
//				}catch(Exception exc){System.out.println(exc.toString());}
//				
				changeValue();

		}
	}

	public void changeValue()
	{
		rosfer.selectedProject=rosfer.mycmp.list.getSelectedValue();
		rosfer.liw.select_project.setToolTipText(rosfer.mycmp.list.getSelectedValue());
		System.out.println("value changed to "+rosfer.mycmp.list.getSelectedValue());
		
		NodeList nList = rosfer.document.getElementsByTagName("project");
		for (int temp = 0; temp < nList.getLength(); temp++) 
		{
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE  && rosfer.mycmp.list.getSelectedValue()!=null) 
			{
				Element pg=(Element)nNode;
				if(pg.getElementsByTagName("projectname").item(0).getTextContent().equals(rosfer.selectedProject))
				{
					if(pg.getElementsByTagName("task").getLength()>0)
					{
						
						rosfer.mycmp.createNewWindow(rosfer.selectedProject,pg);
					}
					else
					{
						rosfer.mycmp.window=1;
						rosfer.liw.select_project.setText((String)rosfer.mycmp.list.getSelectedValue());
						rosfer.storeId(false);
						rosfer.mycmp.minimize(rosfer.mycmp.getHeight());
						rosfer.mycmp.minimize(rosfer.mycmp.getHeight());
						rosfer.mycmp.setSize(200, 120);
						if(rosfer.blp.isVisible())
						{
							rosfer.changeWindowTimer.start();
//							rosfer.setSize(310, 125);
//							rosfer.liw.setBounds(0, 0, 310, 105);
//							rosfer.liw.name.setText(rosfer.blp.name.getText());
//							rosfer.liw.setVisible(true);
//							rosfer.blp.setVisible(false);
//							rosfer.setLocation(rosfer.getX(),rosfer.getY()-20);
//							rosfer.add(rosfer.liw);
//							rosfer.bottom.setBounds(-10,rosfer.getHeight()-20, rosfer.getWidth()+20, 20);
//							rosfer.liw.select_project.setText((String)rosfer.mycmp.list.getSelectedValue());
//							if(rosfer.ls!=null)
//								rosfer.ls.setLocation(rosfer.getX()-rosfer.ls.getWidth(), rosfer.getY()+rosfer.getHeight()-rosfer.ls.getHeight());
						}
					}
					break;
				}
			}
		}
	
	}
}

class shutdown extends Thread
{
	Rosfer r;
	public shutdown(Rosfer r)
	{
		this.r=r;
	}

	public void run() 
	{
		
	Thread th=	new Thread(){public void run(){r.localtoserver.doUpdate();}};
	th.start();	
	try{
	th.join();
	}catch(Exception e){System.out.println(e);}
	
	System.out.println("shutdown hook");
	}
}

