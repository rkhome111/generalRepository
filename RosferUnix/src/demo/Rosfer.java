package demo;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.AbstractLayoutCache.NodeDimensions;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Rosfer extends JFrame implements MouseListener,MouseMotionListener,ActionListener,FocusListener
{

	private static final long serialVersionUID = 1L;
	int height,width;
	JPanel main_top,main_mid;
	Before_login_panel blp;
	LoginWindow lw;
	LoggedInWindow liw;
	LeftSnap ls;
	MyComponent1 mycmp;
	ProjectTimeUpdate ptu;
	TimeUpdate tu;
	String sys_name,server_address,userId,projectId,projectTlId,projectTeamId,tlId,teamId,status,selectedProject,task_text="",subtask_text="";
	int task_id,task_list_id,work_status=1,timegap;
	Font opensanslight,opensansregular;
	boolean leftsnapvisible,mycmpdialogvisible,maxvisible,logoutvisible,isgone;
	ConnectDb cdb;
	MyListListener mll;
	Document document;
	int interval,defaultTime;
	String user,pass,projectNames[],windowName="first";
	Thread snapcounterthread;
	SnapCounter sc;
	Rosfer rosfer;
	Timer minTimer,maxTimer;
	MaxDialog max;
	private String osname;
	LocalToServer localtoserver;	
	SystemTray systray;
	TrayIcon ti;
	PopupMenu traypopup;
	MenuItem exit;
	Thread defaultThread;
	DefaultAlertWindow daw;
	int increaseDay=0,checklogin=0;
	Trai trai;
	CheckBreak checkbreak;
	Adder stub;
	String version;
	public Rosfer()
	{
		super("Rosfer");
//		try{
//		InputStream din1 =new URL("http://10.0.4.6/Roushan_Java/copy_paste/localdb.db").openStream();
//		String name = System.getProperty("os.name").toLowerCase().contains("win")?"localdb":"localdb.db";
		
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
		
//		setIconImage(Toolkit.getDefaultToolkit().getImage("assets/48x48new.png"));
//		this.setType(Type.POPUP);
		
		sys_name=System.getProperty("user.name");
		try{
			setIconImage(ImageIO.read(new File("assets/16x16new.png")));
			
			Date dt = new Date();
			PrintStream fout = new PrintStream(new FileOutputStream(new File("assets/log"+dt.getDate()+"-"+(dt.getMonth()+1)+"-"+(dt.getYear()+1900)+".txt"),true));
			System.setOut(fout);
			
			
			opensanslight = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/OpenSans-Light.ttf"));
			opensansregular = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/OpenSans-Regular.ttf"));
		    osname=System.getProperty("os.name");
		
			}catch(Exception e){System.out.println("Unable to load your font"+e); e.printStackTrace();}
		
		mll=new MyListListener(this);
		max=new MaxDialog(this);
		cdb=new ConnectDb(this);
		lw=new LoginWindow(this);
		
		blp=new Before_login_panel(this);

		
		trai=new Trai(this);
		
		blp.name.setVisible(false);
		blp.logout.setVisible(false);
		
		
		main_top=new JPanel();
		main_mid=new JPanel();
		
		setSize(310, 65);
		lw.setSize(310, 190);
		blp.setSize(310,65);
//		blp.cmp.setEnabled(false);
		setUndecorated(true);
//		this.getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);

//		setBackground(new Color(0,0,0,0));
//		this.getContentPane().setBackground(new Color(0,0,0,0));
		
		lw.setVisible(true);
		add(blp);
		
	

	
//		 getRootPane().setBorder(shadow);

		
		setLocationRelativeTo(null);
		setVisible(true);
		lw.setLocation(this.getX(),this.getY()-190);
		lw.setVisible(true);
	


		UIManager.put("ToolTip.background",new Color(0xF4F4C4));
		UIManager.put("ToolTip.foreground",Color.black);
		UIManager.put("ToolTip.border",BorderFactory.createEmptyBorder());
		register();
		
//		lw.connect_tf.requestFocus();
		
		
		if(lw.login_auto.isSelected())
		{
			try{Thread.sleep(1000);}catch(Exception e){}
			MouseEvent me = new MouseEvent(lw.login, 0, 0, 0, 100, 100, 1, false);
			for(MouseListener ml: lw.login.getMouseListeners()){
			    ml.mouseReleased(me);
			}
			
		}
		
		
		
		 
	}
	
	
	
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource()==blp.close)
		{
//			System.exit(0);
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
			exit.addActionListener(this);
				}catch(Exception eeee){System.out.println(eeee);}
			}
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_ICONIFIED));
			this.setVisible(false);

		}
		else if(exit!=null && e.getSource()==exit)
		{
			System.exit(0);
		}
		else if(e.getSource()==blp.min)//||(liw!=null && e.getSource()==liw.min))
		{
			if(rosfer.mycmp.isVisible())
				rosfer.mycmp.setVisible(false);
			
			this.setExtendedState(this.getExtendedState()|JFrame.ICONIFIED);

			
		}
		else if(e.getSource()==lw.min)
		{
			minimize(true);	
			lw.mid.setEnabled(false);
			
//			cr.mouseDragged(e);
		}

	}
	
	int initial=1;
	int i=0;
	Timer mintimer;
	private void minimize(boolean b)
	{
/*		mintimer=new Timer(00, new ActionListener(){public void actionPerformed(ActionEvent e)
//		while(lw.getHeight()>0)
		{
			lw.setBounds(lw.getX(), lw.getY()+initial, 310, lw.getHeight()-initial);
//			try{Thread.sleep(0);}catch(Exception e){}
			if(lw.getHeight()<=0)
				mintimer.stop();
		}
		});
		mintimer.start();
*/		lw.setVisible(false);
		if(b)
		{
			max.setLocation(getX()+310-24,getY()-23);
			max.setVisible(true);
			maxvisible=true;
		}
		else
		{
			max.setVisible(false);
			maxvisible=false;
		}
		if(blp.name.isVisible())
			max.setVisible(false);
		
	}
	
	 void maximize(int size)
	{
		
/*		lw.setBounds(getX(), getY(), 0, 0);
		lw.setVisible(true);
		maxTimer=new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lw.setBounds(getX(),lw.getY()-(initial*5),310,lw.getHeight()+(initial*5));
//				lw.top.setBounds(0, lw.getHeight()-190, 310, 22);
//				lw.mid.setBounds(0, lw.getHeight()-170, 310, 170);
//				System.out.println(lw.getBounds());
				if(lw.getHeight()>=190)
				{
					maxTimer.stop();
					lw.setBounds(getX(),getY()-190,310,190);
				}
			}
		});
		maxTimer.start();
*/
		 lw.setLocation(getX(),getY()-190);
		 lw.setVisible(true);
		
}
	


	public static void main(String args[]) throws Exception
	{				
		
//		System.out.println("TRANSLUCENT supported:          " + AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.TRANSLUCENT));
//        System.out.println("PERPIXEL_TRANSPARENT supported: " + AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.PERPIXEL_TRANSPARENT));
//        System.out.println("PERPIXEL_TRANSLUCENT supported: " + AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.PERPIXEL_TRANSLUCENT));
        try{
    		LocateRegistry.createRegistry(4444);
    		Rosfer r=new Rosfer();
    		shutdown sh=new shutdown(r);
    		Runtime.getRuntime().addShutdownHook(sh);
    		}catch(Exception e){
    			DefaultAlertWindow daw = new DefaultAlertWindow(null);
    			daw.message1.setText("  running in background.");
    			daw.message2.setText("restart system.");
    			daw.ok.setVisible(false);
    			daw.setVisible(true);
    			Thread.sleep(5000);
    			System.exit(1);
    		}
	}

	public void mousePressed(MouseEvent e)
	{
		
		if(ti!=null && e.getSource()==ti && e.getButton()==MouseEvent.BUTTON1)
		{
			systray.remove(ti);
			
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_DEICONIFIED));
			this.setVisible(true);
			
			if(ls!=null)
				ls.setVisible(leftsnapvisible);
		
			
		}
		else if(ti!=null && e.getSource()==ti && e.getButton()==MouseEvent.BUTTON3)
			ti.setPopupMenu(traypopup);

		
		if(e.getSource()==lw.mid)
			height=e.getY()+10;
		else
		height=e.getY();
		width=e.getX();
		
		if(ls!=null)
		{
			ls.requestFocus();
			if(e.getSource()==ls)
				this.requestFocus();
		}
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
			if(ls!=null)
			ls.setLocation(getX()-ls.getWidth(), getY()+getHeight()-ls.getHeight());
			lw.setLocation(getX(),getY()-190);
		
		if(mycmp!=null && blp.isVisible())
			mycmp.setLocation(getX()+50,getY()-mycmp.getHeight()+45);
		
		if(max.isVisible())
			max.setLocation(getX()+310-24,getY()-23);
		
		if(ls!=null && ls.isVisible())
			ls.toFront();

	}
	
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
	      String date=ss.toString().substring(ss.toString().indexOf("><h5>")+5,ss.toString().indexOf("</td></tr>    <tr><td colspan=2 align=\"center\" bgcolor=\"#FFFFFF\"><b>Standard Time Zone:"));
	      Date dt=new Date(Date.parse(date));
	      cal=Calendar.getInstance();
	      cal.setTime(dt);
	      return cal;
			}catch(Exception e){return null;}
	}
	boolean select=false;
	public void mouseReleased(MouseEvent e)
	{
		System.out.println(e.getSource());

		
		if(mycmp!=null && e.getSource()==mycmp.done)
		{
			liw.select_project.setText(mycmp.title.getText().substring(2));
			storeId(true);
			
				mycmp.minimize();
				
				if(!rosfer.liw.isVisible())
				{
					rosfer.windowName="second";
					liw.name.setText(blp.name.getText());
					this.setVisible(false);
					liw.setLocation(getX(),getY()-20);
					liw.setVisible(true);
					rosfer.mycmp=new MyComponent1(rosfer.document, rosfer,rosfer.liw);
				}

		}
		

		
		else if(e.getSource()==max.max_button)
		{
			maximize(190-23);
			max.setVisible(false);
			maxvisible=false;
			
		}
	

		
		
		
	
		else if(e.getSource()==lw.login )//&& (ls==null || !ls.isVisible()))
		{
			new Thread(){public void run(){
			defaultTime=0;
			increaseDay=0;
			mycmp=null;
			document=null;
			stub=null;
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
				
		
			if(!(user.equals("")||pass.equals("")))//||server_address.equals("")))
			{
				try{
					doLogin();
				}catch(Exception exce){System.out.println(exce);}
				
			}
			}}.start();
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
	
			if(mycmp!=null && mycmp.isVisible())
				mycmp.minimize();
			maximize(190-23);
			
			
		}
		
		
		
		
			


		
		else if(e.getSource()==blp.cmp)
		{
			if(mycmp==null)
				mycmp=new MyComponent1(document, rosfer, this);
			if(!mycmp.isVisible())
				mycmp.maximize(120);

		}
		
		else
		{
			if(mycmp!=null && mycmp.isVisible())
			{
				mycmp.minimize();
			}
		}

		
		
	}


	public void mouseMoved(MouseEvent e)
	{
		if(select)
		{
			mouseDragged(e);
		}
	}
	
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	

		 


	
	public void changeWindow()
	{
		
	}
	

	
	public void storeId(boolean b)
	{
		liw.comment.setText("Comment");
		if(checkbreak==null)
			checkbreak=new CheckBreak(this);
		
		
		if(b)
		{
			task_text=(String)mycmp.taskcmb.getSelectedItem();
			subtask_text=(String)mycmp.subtaskcmb.getSelectedItem();
			rosfer.mycmp.window=2;
			
			try{
				NodeList nl=document.getElementsByTagName("project");
					for(int i=0;i<nl.getLength();i++)
					{
						Element node=(Element)nl.item(i);
						if(node.getElementsByTagName("projectname").item(0).getTextContent().equals(liw.select_project.getText()))
						{
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
			rosfer.mycmp.window=1;
			
		}
		
		System.out.println("project id: "+projectId+" \ntaskid:  "+task_id+"\n task_list id: "+task_list_id);
		System.out.println("task Text "+task_text+"\nsubtask_text: "+subtask_text);
		System.out.println("project TL Id :"+projectTlId+"\nproject Team Id :"+projectTeamId);
		
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		System.out.println("focus Gained");
		liw.adjust();
	}
	public void focusLost(FocusEvent e){System.out.println("focus lost");}
	
	public void register()
	{
	lw.connect_tf.addMouseListener(this);
	addMouseListener(this);
	addMouseMotionListener(this);
	

	
	blp.close.addActionListener(this);
	blp.min.addActionListener(this);
	max.max_button.addMouseListener(this);
	blp.logout.addMouseListener(this);
	lw.min.addActionListener(this);
	lw.login.addMouseListener(this);
	

	
		addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e)
		{System.out.println("running window closing event");System.exit(0);}
		
		
		@Override
		public void windowIconified(WindowEvent e)
		{
			System.out.println("Iconified in rosfer");
				if(ls.isVisible())
					{ls.setVisible(false); leftsnapvisible=true;}
				else
					leftsnapvisible=false;
				if(mycmp!=null && mycmp.isVisible())
				{
					System.out.println("my component is visible");
					mycmp.setVisible(false); mycmpdialogvisible=true;
					
				}
				else
					mycmpdialogvisible=false;
		}
			
			@Override
			public void windowDeiconified(WindowEvent e)
			{
				
				System.out.println(mycmp.isVisible());
				System.out.println("Deiconified from rosfer "+mycmpdialogvisible);
					if(leftsnapvisible)
						ls.setVisible(true);
					
					
					if(mycmpdialogvisible)
					{
						mycmp.setVisible(true);
						mycmp.requestFocusInWindow();
					}
					
					
					if(blp.isVisible())
						blp.requestFocusInWindow();
					
			

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
			version=readfile.substring(0, readfile.indexOf(";;"));
			System.out.println("server address is "+server_address);
			//login from server
			Thread t = new Thread(){public void run(){ 
			try{
				
					System.out.println("trying to login...");
					
					stub=(Adder)Naming.lookup("rmi://"+server_address+"/rkrmi");
					document=stub.login(user, pass);
					
					System.out.println("Logged in from server");

			}catch(Exception ex){ }

			}};
			t.setDaemon(true);
			t.start();
			t.join(15*1000);
			
			
			//login from local system
			if(document==null)
			{
				/*FileInputStream fin=new FileInputStream(new File("assets/user"));
				byte[] b=new byte[fin.available()];
				fin.read(b);
				fin.close();
				String ss = new String(b);*/
				String ss = new String (Files.readAllBytes(Paths.get("assets/user")));
				System.out.println(ss.length()+ "value of ss is "+ss+"  "+ss.substring(ss.indexOf(",")+1, ss.lastIndexOf(",")));
				System.out.println("user name is "+user+ " Password is "+pass);
					if(ss!=null && ss.substring(ss.indexOf(",")+1, ss.lastIndexOf(",")).equals(user) && ss.substring(ss.lastIndexOf(",")+1).trim().equals(pass))
					{
							System.out.println("Login locally.......");	
							System.out.println("user detail is "+ss);
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
					}catch(Exception e){System.out.println(e);e.printStackTrace();}
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
		Thread.sleep(3000);lw.check.setText("Checking Connection from Server");
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
				
				if(!e.getValueIsAdjusting() && rosfer.mycmp.list.getSelectedValue()!=null)
//				if(rosfer.mycmp.list.getSelectedValue()!=null)

				{
//					try{
//						Thread.sleep(400);
//					}catch(Exception exc){System.out.println(exc.toString());}
					
					changeValue();

			}

		}

		
		public void changeValue()
		{
			rosfer.selectedProject=rosfer.mycmp.list.getSelectedValue();
			rosfer.liw.select_project.setToolTipText(rosfer.mycmp.list.getSelectedValue());
			System.out.println("value changed to "+rosfer.mycmp.list.getSelectedValue());
			
			NodeList nList = rosfer.document.getElementsByTagName("project");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE && rosfer.mycmp.list.getSelectedValue()!=null) 
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
							
							
							rosfer.liw.select_project.setText((String)rosfer.mycmp.list.getSelectedValue());
							
							rosfer.storeId(false);
							rosfer.mycmp.minimize();
	//						rosfer.mycmp.setVisible(false);
						
							if(rosfer.isVisible())
							{
								rosfer.windowName="second";
								rosfer.setVisible(false);
								rosfer.liw.setLocation(rosfer.getX(),rosfer.getY()-20);
								rosfer.liw.name.setText(rosfer.blp.name.getText());
								rosfer.liw.setVisible(true);
								rosfer.mycmp=new MyComponent1(rosfer.document, rosfer,rosfer.liw);
								if(rosfer.ls!=null)
									rosfer.ls.setLocation(rosfer.getX()-rosfer.ls.getWidth(), rosfer.getY()+rosfer.getHeight()-rosfer.ls.getHeight());
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

