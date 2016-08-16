package demo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Trai extends JDialog implements Runnable
{
	private static final long serialVersionUID = 1L;
	JDialog frame;
	JPanel mainPanel;
	JLabel min,main,logo,snap_img,name,comment,oval,time;
	static Color content_color=new Color(0xd0d3d8);
	BufferedImage snap;
	Rosfer rosfer;
	boolean visibility;

 	public Trai(Rosfer rosfer)
 	{
 		this.rosfer=rosfer;
 		
 		initialize();
		design();	
		frame=this;
		this.setFocusableWindowState(false);
	
		
 	}
	public void run()
	{
		
		name.setText(rosfer.user);
		comment.setText(rosfer.liw.comment.getText());
		rosfer.liw.left.removeMouseListener(rosfer);
		rosfer.liw.leftStop.removeMouseListener(rosfer);
		rosfer.liw.left.setToolTipText("Please wait...");
		rosfer.liw.leftStop.setToolTipText("Please wait...");
		rosfer.liw.left.setEnabled(false);
		rosfer.liw.leftStop.setEnabled(false);
		
	
		//Storing Snap in Database
		takeScreenshot();
		
		
		if(rosfer.work_status==1)
		{
			rosfer.work_status=2;
//			rosfer.isgone=true;
		}
		
		if(rosfer.work_status==3)
		{
			new Thread(rosfer.ls).start();
//			rosfer.isgone=true;
		}
		try{
			setOpacity(0.0f);
			setVisible(true);
			
			for(float f=0.0f;f<=1.00;f+=0.05)
			{
				Thread.sleep(50);
				this.setOpacity(f);
			}

			for(int i=4;i>=0;i--)
			{
				time.setText(String.valueOf(i));
				Thread.sleep(1000);
			}
		
			for(float f=1;f>=0.0;f-=0.05)
			{
				Thread.sleep(50);
				this.setOpacity(f);
			}
		
			
			this.setVisible(false);
			time.setText("5");
			rosfer.liw.left.setToolTipText("Start Project");
			rosfer.liw.leftStop.setToolTipText("Stop Project");
			rosfer.liw.left.setEnabled(true);
			rosfer.liw.leftStop.setEnabled(true);
			
	rosfer.ls.changeImage(snap);
	
	
	rosfer.liw.left.addMouseListener(rosfer);
	rosfer.liw.leftStop.addMouseListener(rosfer);
	
		}catch(Exception e){System.out.println(e+" here");}
		
	}
	
	
	void takeScreenshot()
	{
		try{
			
			snap = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			Image img=snap.getScaledInstance(117, 88, Image.SCALE_DEFAULT);
			snap_img.setIcon(new ImageIcon(img));
			
			new Thread(){public void run(){new ConnectDb(rosfer).insertScreenShort(rosfer.work_status,snap);}}.start();
			Thread.sleep(2000);
			}catch(Exception e){System.out.println("Unable to Capture Screen Short");}
	}
	
	public void design()
	{	
		setSize(157, 168);
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		Point location=new Point(d.width-180,d.height-230);
		setLocation(location);
		this.getContentPane().setBackground(new Color(0,0,0,0));
		this.setBackground(new Color(0,0,0,0));
		
		Border shadow = rosfer.getShadowBorder(Color.black, true, true, true, true, 5);
       
 
        mainPanel.setBounds(0, 0, getWidth(), getHeight());
        mainPanel.setBackground(new Color(0,0,0,0));
        mainPanel .setLayout(null);
        this.add(mainPanel);
        mainPanel.setBorder(shadow);
        add(mainPanel);
		
        main.setBounds(5, 5,147,158);
		mainPanel.add(main);
		min.setBounds(130, 5, 12, 12);
		main.add(min);
		logo.setBounds(8,35, 20, 15);
		main.add(logo);

		name.setBounds(28, 35,50, 15);
		name.setFont(new Font(name.getName(), Font.PLAIN, 11));
		name.setForeground(Color.white);
		main.add(name);
		
		comment.setBounds(90, 35, 50, 15);
		comment.setFont(new Font(name.getName(), Font.PLAIN, 11));
		comment.setForeground(new Color(0x707383));
		main.add(comment);
		

		
		oval.setBounds(100, 105, 40, 40);
		main.add(oval);
		main.add(snap_img);
		
		time.setBounds(15, 11, 20, 20);
		time.setFont(new Font(time.getName(), Font.PLAIN, 20));
		oval.add(time);
		
		
		
		
		snap_img.setBounds(15,55,117,87);
		
		min.addMouseListener(new MouseListener()
		{		
			public void mouseClicked(MouseEvent e){frame.setVisible(false);}
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			
		});

	}
	
	public void initialize()
	{
		setUndecorated(true);
		min=new JLabel(new ImageIcon("assets/image2.png"));
		main=new JLabel(new ImageIcon("assets/Trai_bg.png"));
		logo=new JLabel(new ImageIcon("assets/image8.png"));
		snap_img=new JLabel();
		comment=new JLabel("Comments");
		oval=new JLabel(new ImageIcon("assets/image18.png"));
		time=new JLabel("5");
		name=new JLabel(rosfer.user);
		mainPanel=new JPanel();
		this.setAlwaysOnTop(true);
	}

}


