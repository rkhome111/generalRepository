package demo;

import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.sql.ResultSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class MyComponent1 extends JDialog implements KeyListener, FocusListener, MouseListener,ActionListener, ItemListener
{

	private static final long serialVersionUID = 1L;
	JPanel top,bottom;
	JScrollPane js;
	JList<String> list;
	DefaultListModel<String> dlm;
	String str[];
	String searchStr[];
	String project;
	JLabel elements[],done;
	JTextField tf;
	MyComponent1 mc;
	Rosfer rosfer;
	JLabel title,refresh;
	JComboBox<String> taskcmb, subtaskcmb;
	JPanel titlePanel;
	ResultSet taskrs, subtaskrs;
//	Timer maxtimer,maxtimer1;
	MyComponent1 mycmp1;
	int mHoveredJListIndex=-1;
	int maxcount=0,window=1;
	Element element;

	public void createNewWindow(String project,Element element) 
	{
		window=2;
		this.element=element;
		top.setOpaque(true);
		bottom.setOpaque(true);
		title.setText("  "+project);
		if(!top.isVisible())
			top.setVisible(true);
		
		taskcmb.removeAllItems();
		
		try {
			NodeList nodelist=element.getElementsByTagName("task");
			for(int i=0;i<nodelist.getLength();i++)
			{
				Element node=(Element)nodelist.item(i);
				taskcmb.addItem(node.getElementsByTagName("taskname").item(0).getTextContent());
				
				
				if(i==0)
				{
					subtaskcmb.removeAllItems();
					NodeList subtask=node.getElementsByTagName("subtask");
					for(int j=0;j<subtask.getLength();j++)
					{						
						Element subt=(Element)subtask.item(j);
						System.out.println(subt.getElementsByTagName("subtaskname").item(0).getTextContent());
						subtaskcmb.addItem(subt.getElementsByTagName("subtaskname").item(0).getTextContent());						
					}					
				}				
			}

			if(getHeight()<=120)
				maximize1();
			
		} catch (Exception e) {System.out.println(e);}
		

	}

	public void updateProject()
	{
	
		DefaultListModel<String> mydlm=(DefaultListModel<String>)list.getModel();
		mydlm.removeAllElements();
		NodeList nList = rosfer.document.getElementsByTagName("projectname");
		str=new String[nList.getLength()];
//		System.out.println(nList.getLength());
		for (int i = 0; i < nList.getLength(); i++) 
		{
			str[i]=nList.item(i).getTextContent();
			mydlm.addElement(nList.item(i).getTextContent());			
		}
		System.out.println("Project updated sucessfully "+nList.getLength());
	}
	
	public MyComponent1(Document doc, Rosfer rosfer)
	{
		super(rosfer);
		this.rosfer = rosfer;
		mycmp1=this;
		dlm = new DefaultListModel<String>();
		
		
		NodeList nList = doc.getElementsByTagName("projectname");
		str=new String[nList.getLength()];
		for (int i = 0; i < nList.getLength(); i++) 
		{
			str[i]=nList.item(i).getTextContent();
			dlm.addElement(nList.item(i).getTextContent());
		}
		
		
			
		
		list = new JList<String>(dlm);
		list.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN, 12));
		list.setFixedCellHeight(30);
//		list.setSelectionBackground(new Color(111, 118, 134));
//		list.setSelectionForeground(Color.white);
		list.addMouseMotionListener(new MouseAdapter() {@Override
			  public void mouseMoved(MouseEvent me) {
			    Point p = new Point(me.getX(),me.getY());
			    int index = list.locationToIndex(p);
			    if (index != mHoveredJListIndex) {
			      mHoveredJListIndex = index;
			      list.repaint();
			    }
			  }
			});
		
		initialize();
		top.setDoubleBuffered(true);
		bottom.setDoubleBuffered(true);

	}

	static int initial =30;
	int framex,framey,framewidth,frameheight,j;
	Rectangle r;
	public void minimize(int size) {
		
		setVisible(false);
		if(tf_focus)
		{
			updateProject();
			tf_focus=false;
		}
//		r = getBounds();
//		framex =getX();
//		framey =getY();
//		framewidth =getWidth();
//		frameheight =getHeight();
//		j=initial;;
//		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
//		if(ti1==null)
//		{
//			
//		ti1=new Timer(Rosfer.waitTime, new ActionListener() {
//			
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//			
//				setBounds(framex, framey+j, framewidth,frameheight-j);
//				top.setBounds(0, getHeight()-270,200,150);
//				bottom.setBounds(0,getHeight()-120,200,120);
////				setBackground(new Color(0,0,0,0));
////				System.out.println(bottom.getBounds());
//				j+=initial;
//				
//				if(j>frameheight)
//				{
//					ti1.stop();
//					j=initial;
//					setVisible(false);
//					rosfer.mycmpdialogvisible=false;
//					setBounds(r);
//				}
//			}
//		});
//		}
//		try{Thread.sleep(500);}catch(Exception e){}
		
//		ti1.start();
		
/*		for (int i = initial; i <= size; i += initial) {

			int framex =getX();
			int framey =getY() + initial;
			int framewidth =getSize().width;
			int frameheight =getSize().height - initial;
			this.setBackground(new Color(1, 1, 1, 0f));
			if(i+30==size)
				initial=1;
			setBounds(framex, framey, framewidth, frameheight);
			top.setBounds(0, getHeight()-270,200,150);
			bottom.setBounds(0,getHeight()-120,200,120);
			
			try {Thread.sleep(00);} catch (Exception ee) {System.out.println(ee);}

		}
		*/
		

	}

	public void maximize1()
	{
		i=120;
		maximize(270);
		
//		int framex = this.getX();
//		int framewidth = this.getSize().width;
//		int frameheight = this.getSize().height;
//		int framey = this.getY() + frameheight+initial;
//		setBounds(getX(),getY()+initial,getWidth(),getHeight());
//		for (int j = 120+initial; j <= rosfer.mycmp.top.getHeight()+120; j += initial) 
//		{
////			try {Thread.sleep(100);} catch (Exception eec) {System.out.println(eec);}
//			
//			this.setBackground(new Color(0,0,0,0));
//			setBounds(framex, framey - j, framewidth, j);
//			top.setBounds(0, getHeight()-270,200,150);
//			bottom.setBounds(0,getHeight()-120,200,120);
//
//			j+=initial;
//	}
//		setBounds(framex, framey-rosfer.mycmp.top.getHeight()-120-initial, framewidth, rosfer.mycmp.top.getHeight()+120);
//		top.setBounds(0, getHeight()-270,200,150);
//		bottom.setBounds(0,getHeight()-120,200,120);
	}
	int i=initial;
	Timer ti,ti1;
	int size;
	public void maximize(int size1)
	{

		mycmp1.setBounds(rosfer.getX()+50,rosfer.getY()-size1+55,200,size1);
		top.setBounds(0, getHeight()-270,200,150);
		bottom.setBounds(0,getHeight()-120,200,120);
		setVisible(true);


//		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
//		size=size1;
//		framex = getX();
//		framewidth = getSize().width;
//		frameheight =getSize().height;
//		framey = getY() + frameheight;
//		if(!isVisible()){
//		mycmp1.setBounds(framex, framey - 0, framewidth, 0);
//		mycmp1.setVisible(true);
//		mycmp1.top.setDoubleBuffered(true);
//		mycmp1.bottom.setDoubleBuffered(true);
//		}
//		if(ti==null)
//		{
//		ti=new Timer(Rosfer.waitTime, new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//			
//				mycmp1.setBounds(framex, framey - i, framewidth, i);
//				top.setBounds(0, getHeight()-270,200,150);
//				bottom.setBounds(0,getHeight()-120,200,120);
////				System.out.println(getBounds());
//				i+=initial;
//				if(i>size)
//				{
//					ti.stop();
//					i=initial;
//					rosfer.mycmpdialogvisible=true;
//				}
//			}
//		});
//		}
//		ti.start();
		
//		try{Thread.sleep(500);}catch(Exception e){}
//		for (int i = initial; i <= size; i += initial)
//		{
//			mycmp1.setBounds(framex, framey - i, framewidth, i);
//			top.setBounds(0, getHeight()-270,200,150);
//			bottom.setBounds(0,getHeight()-120,200,120);
//			i+=initial;
//			this.setBackground(new Color(0,0,0,0));
////			try{Thread.sleep(10);}catch(Exception e){}
//		}
//		setBounds(framex, framey-size-initial,framewidth, size);
//		top.setBounds(0, getHeight()-270,200,150);
//		bottom.setBounds(0,getHeight()-120,200,120);
	}
	
	public void initialize() 
	{
		
		setSize(200,270);
//		setLocation(695, 275);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		top = new JPanel();
		top.setLayout(null);
		top.setBounds(0,0,200,150);
		top.setBackground(new Color(111, 118, 134));
//		setComponentZOrder(top,0);
		top.setVisible(false);
		add(top);
//		top.setBackground(Color.red);
		
		
		titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 200, 30);
		titlePanel.setBackground(new Color(75, 83, 104));
		titlePanel.setLayout(null);

		title = new JLabel("", JLabel.LEFT);
		title.setText("   " + project);
		title.setForeground(new Color(0xffd846));
		title.setBounds(0, 4, 200, 24);
		title.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN, 13));
		titlePanel.add(title);
		top.add(titlePanel);
		
		taskcmb = new JComboBox<String>();
		taskcmb.setBounds(20, 40, 160, 25);
		taskcmb.setBackground(Color.white);
		taskcmb.setForeground(new Color(0x6f7686));
		taskcmb.addItemListener(this);
		taskcmb.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN, 14));
		top.add(taskcmb);
		
		subtaskcmb = new JComboBox<String>();
		subtaskcmb.setBounds(20, 70, 160, 25);
		subtaskcmb.setBackground(Color.white);
		subtaskcmb.setForeground(new Color(0x6f7686));
		subtaskcmb.setFont(rosfer.opensansregular.deriveFont(Font.PLAIN, 14));
		subtaskcmb.setMaximumRowCount(7);
		top.add(subtaskcmb);
		
		done = new JLabel(new ImageIcon("assets/done.png"));
		done.setBounds(75, 110, 44, 18);
		done.addMouseListener(this);
		done.addMouseListener(rosfer);
		top.add(done);
	
		
		
		System.out.println("Top Bounds Initial ===========>"+ top.getBounds());
		
		bottom=new JPanel();
		bottom.setLayout(null);
		bottom.setBounds(0,150,200,120);
		
		
		js = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		js.setBounds(0,0,200,90);
		bottom.add(js);
		bottom.setBackground(new Color(0xd0d3d8));
		
	
		tf = new JTextField("    -------Search Project-------",30);
		
		tf.setBorder(BorderFactory.createEmptyBorder());
		tf.setBounds(7,92,166,27);
		tf.addKeyListener(this);
		tf.addFocusListener(this);
		tf.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN, 13));
		bottom.add(tf);
		
		refresh =new JLabel(new ImageIcon("assets/refresh.png"),JLabel.LEFT);
		refresh.setBackground(new Color(0xd0d3d8));
		refresh.addMouseListener(this);
		refresh.setBounds(180, 90, 30, 30);
		bottom.add(refresh);
		
		add(bottom);
		add(new JPanel());
		
	}

	TreeSet<String> ts = new TreeSet<String>();
	Matcher matcher;

	@Override
	public void keyReleased(KeyEvent e) {
		if(!tf_focus)
			tf_focus=true;
		DefaultListModel<String> sss = (DefaultListModel<String>) list.getModel();
		
		if (tf.getText().equals("")) {
			sss.removeAllElements();
			for (String st : str)
				sss.addElement(st);
		} else {
			String pt = "(" + tf.getText().toLowerCase() + ")";

			Pattern pattern = Pattern.compile(pt);

			for (String s : str) {
				matcher = pattern.matcher(s.toLowerCase());

				if (!matcher.find()) {
					sss.removeElement(s);
				} else if (!sss.contains(s))
					sss.addElement(s);

			}

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if (tf.getText().equals("    -------Search Project-------"))
			tf.setText("");
	}

	boolean tf_focus=false;
	public void focusLost(FocusEvent e) {
		if (tf.getText().equals(""))
			tf.setText("    -------Search Project-------");
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getSource()==done)
		done.setIcon(new ImageIcon("assets/done_down.png"));
		
		else if(e.getSource()==refresh)
		{
			
			new Thread(){public void run(){
			
			refresh.setIcon(new ImageIcon("assets/refresh.GIF"));
			refresh.removeMouseListener(rosfer.mycmp);
//			refresh.update(refresh.getGraphics());
//			this.paintAll(this.getGraphics());
			try{
				Document doc=((Adder)Naming.lookup("rmi://"+rosfer.server_address+"/rkrmi")).login(rosfer.user, rosfer.pass);
						
				if(doc.getElementsByTagName("projectname").getLength()>0)
					rosfer.document=doc;

				updateProject();				
			}catch(Exception ex){System.out.println("unable to update project");}
			finally{refresh.setIcon(new ImageIcon("assets/refresh.png"));refresh.addMouseListener(rosfer.mycmp);}
			}}.start();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if(e.getSource()==done)
		done.setIcon(new ImageIcon("assets/done_up.png"));
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		if(e.getSource()==done)
		done.setIcon(new ImageIcon("assets/done.png"));
	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		System.out.println(e.getStateChange());
		if(e.getStateChange()==ItemEvent.SELECTED && e.getSource()==taskcmb)
		{
			System.out.println("Combobox Item State Changed");
			subtaskcmb.removeAllItems();
			String taskListString=(String)e.getItem();
			try{
				
				NodeList nodelist=element.getElementsByTagName("task");
				for(int i=0;i<nodelist.getLength();i++)
				{
					Element node=(Element)nodelist.item(i);
					
					if(node.getElementsByTagName("taskname").item(0).getTextContent().equalsIgnoreCase(taskListString))
					{
					NodeList subtask=node.getElementsByTagName("subtask");
					
					for(int j=0;j<subtask.getLength();j++)
						{
						Element subt=(Element)subtask.item(j);
						System.out.println(subt.getElementsByTagName("subtaskname").item(0).getTextContent());
						subtaskcmb.addItem(subt.getElementsByTagName("subtaskname").item(0).getTextContent());
						}
					break;
					}
				}
			}catch(Exception exc){System.out.println(exc);}
				
			
		}

	}
//	@Override
//	  public Component getListCellRendererComponent(JList aList, Object aValue, int aIndex, boolean aIsSelected, boolean aCellHasFocus)
//	  {
//	    Color backgroundColor = mHoveredJListIndex == aIndex ? Color.gray : Color.white;
//	    JLabel pane = new JLabel((String)aValue); // add contents here   
//	System.out.println("hello1");
//	    pane.setBackground(backgroundColor);
//	    return pane;
//	  }

}




