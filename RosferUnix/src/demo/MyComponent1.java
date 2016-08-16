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
	Timer maxtimer,maxtimer1;
	MyComponent1 mycmp1;
	int mHoveredJListIndex=-1;
	int maxcount=0,window=1;
	Element element;
	

	public void createNewWindow(String project,Element element) 
	{
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		window=2;
		this.element=element;
//		top.setOpaque(true);
//		bottom.setOpaque(true);
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
				maximize(270);
			
			Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
			
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
	
	public MyComponent1(Document doc, Rosfer rosfer,Frame parrent)
	{
		
		super(parrent);
		this.rosfer = rosfer;
		mycmp1=this;
		setUndecorated(true);
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
		
		list.addMouseMotionListener(new MouseAdapter() {@Override
			  public void mouseMoved(MouseEvent me) {
//			System.out.println("hello");
			    Point p = new Point(me.getX(),me.getY());
			    int index = list.locationToIndex(p);
			    if (index != mHoveredJListIndex) {
			      mHoveredJListIndex = index;
			      list.repaint();
			    }
			  }
			});
		
		initialize();
		
		list.addListSelectionListener(rosfer.mll);
		

	}

	int initial = 2;

	public void minimize() {

		rosfer.mycmpdialogvisible=false;
		Rectangle r = getBounds();
	
/*	
		System.out.println("minimized 11");
		for (int i = initial; i <= getHeight(); i += initial) {

			int framex =getX();
			int framey =getY() + initial;
			int framewidth =getSize().width;
			int frameheight =getSize().height - initial;
//			this.setBackground(new Color(1, 1, 1, 0f));

			setBounds(framex, framey, framewidth, frameheight);
			top.setBounds(0, getHeight()-270,200,150);
			bottom.setBounds(0,getHeight()-120,200,120);
			try {
				
				Thread.sleep(00);
			} catch (Exception ee) {System.out.println(ee);}

		}
		*/
		setVisible(false);
		setBounds(r);
		if(tf_focus)
		{
			updateProject();
			tf_focus=false;
		}

	}

	int j=120+initial;
	public void maximize11()
	{
		System.out.println("hello");
		final int framex = this.getX();
		final int framewidth = this.getSize().width;
		final int frameheight = this.getSize().height;
		final int framey = this.getY() + frameheight+initial;
		setBounds(getX(),getY()+initial,getWidth(),getHeight());
//		maxtimer1=new Timer(00, new ActionListener(){public void actionPerformed(ActionEvent e)
		for (int j = 120+initial; j <= rosfer.mycmp.top.getHeight()+120; j += initial) 
		{
			try {
				Thread.sleep(00);

			} catch (Exception eec) {System.out.println(eec);}
			
			setBounds(framex, framey - j, framewidth, j);
//			System.out.println(mycmp1.getBounds());
			top.setBounds(0, getHeight()-270,200,150);
			bottom.setBounds(0,getHeight()-120,200,120);
//			if(j>=rosfer.mycmp.top.getHeight()+120)
			{
//				maxtimer1.stop();
//				setBounds(getX(),getY()-initial,getWidth(),getHeight());	
			}
			j+=initial;

//		}});
//		maxtimer1.start();
//		setBounds(getX(),getY()-initial,getWidth(),getHeight());
	}
		setBounds(framex, framey-rosfer.mycmp.top.getHeight()-120-initial, framewidth, rosfer.mycmp.top.getHeight()+120);
		top.setBounds(0, getHeight()-270,200,150);
		bottom.setBounds(0,getHeight()-120,200,120);
	}
	int i=initial;
	public void maximize(int size)
	{
		System.out.println("maximize called");
		if(tf_focus)
		{
			updateProject();tf_focus=false;
			list.requestFocus(true);
		}
		tf.setText("    -------Search Project-------   ");
		if(rosfer.isVisible())
			rosfer.mycmp.setBounds(rosfer.getX()+50,rosfer.getY()-size+45,200,size);
		else
		{
			rosfer.mycmp.setBounds(rosfer.liw.getX()+50,rosfer.liw.getY()-size+60,200,size);
		}
		top.setBounds(0, getHeight()-270,200,150);
		bottom.setBounds(0,getHeight()-120,200,120);
		if(!isVisible())
			setVisible(true);
	}
	
	//this function is not working on linux
	public void maximize11(final int size)
	{
		rosfer.mycmpdialogvisible=true;
		list.requestFocus();
		final int framex = this.getX();
		final int framewidth = this.getSize().width;
		final int frameheight = this.getSize().height;
		final int framey = this.getY() + frameheight+initial;
	
		setBounds(framex,framey+initial-initial,framewidth,0);
		setVisible(true);
		
		
		maxtimer=new Timer(000, new ActionListener(){public void actionPerformed(ActionEvent e)
//		for (int i = initial; i <= size; i += initial)
		{
			mycmp1.setBounds(framex, framey - i, framewidth, i);
			top.setBounds(0, getHeight()-270,200,150);
			bottom.setBounds(0,getHeight()-120,200,120);
	
			i+=initial;
			if(i>size)
			{
				maxtimer.stop();
				setBounds(getX(),getY()-initial,getWidth(),getHeight());	
				i=initial;
			}
		}
		});
		
		
		maxtimer .start();

		
	
//		mycmpl.setBounds(framex, framey-size-initial,framewidth, size);
//		top.setBounds(0, getHeight()-270,200,150);
//		bottom.setBounds(0,getHeight()-120,200,120);
	}
	
	public void initialize() 
	{
		
		setSize(200,270);
//		setLocation(695, 275);
		this.setLocationRelativeTo(null);
//		this.setUndecorated(true);
		
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
		
	
		tf = new JTextField("    -------Search Project-------   ",30);
		
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
	public void focusGained(FocusEvent e) { System.out.println("Focus gained");
		if (tf.getText().equals("    -------Search Project-------   "))
			tf.setText("");
	}

	boolean tf_focus=false;
	public void focusLost(FocusEvent e) {
		if (tf.getText().equals(""))
			tf.setText("    -------Search Project-------   ");
		System.out.println("focus lost");
		
		
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




