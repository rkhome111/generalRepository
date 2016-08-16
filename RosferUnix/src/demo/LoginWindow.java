package demo;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.*;

public class LoginWindow extends JWindow implements MouseListener,KeyListener,MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	JPanel top,mid;
	JButton min;
	JTextField connect_tf,username_tf;
	JPasswordField password_tf;
	JLabel connect_lb,username_lb,password_lb,rockeit_lb,check,login;
	JCheckBox remember_cb,login_auto;
	static Color content_color;
	Rosfer rosfer;
	public LoginWindow(Rosfer rosfer)
	{
		super(rosfer);
		this.rosfer=rosfer;
		initialize();
		design();
//		setUndecorated(true);
//		setFocusable(false);
		
	}
	
	public void design()
	{
		setLayout(null);
		setBounds(0, 0, 310, 190);
		//top pannel design
		
		check =new JLabel("Checking Connection from Server");
		check.setBounds(60,110,200,15);
		check.setBackground(Color.white);
		check.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,13));
		add(check);
		check.setVisible(false);
		
		top.setBounds(0, 0, 310, 22);
		top.setBackground(new Color(0x2d313c));
		top.setLayout(null);
		add(top);
		
		rockeit_lb.setBounds(5, 2, 200, 20);
		rockeit_lb.setForeground(new Color(0xfae24a));
		rockeit_lb.setFont(new Font(rockeit_lb.getName(), Font.PLAIN, 12));
		top.add(rockeit_lb);
		
		min.setBounds(290,5,13,10);
		min.setBorder(BorderFactory.createEmptyBorder());
		min.setBackground(new Color(0x2d313c));
		top.add(min);
		
		
		
		
		
		
		mid.setBounds(0, 20, 310, 170);
		mid.setBackground(new Color(0xd0d3d8));
		mid.setLayout(null);
		add(mid);
		
		
		connect_lb.setBounds(15,14,100,15);
		connect_lb.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,16));
		connect_lb.setForeground(Color.black);
		mid.add(connect_lb);
		
		connect_tf.setBounds(120, 12,175, 25);
		connect_tf.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		connect_tf.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		connect_tf.setForeground(Color.black);
		mid.add(connect_tf);
		
		
		username_lb.setBounds(15,48,100,15);
		username_lb.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,16));
		username_lb.setForeground(Color.black);
		mid.add(username_lb);
		
		username_tf.setBounds(120, 45,175, 25);
		username_tf.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		username_tf.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		username_tf.setForeground(Color.black);
		mid.add(username_tf);
		
		
		password_lb.setBounds(15,82,100,15);
		password_lb.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,16));
		password_lb.setForeground(Color.black);
		mid.add(password_lb);
		
		password_tf.setBounds(120, 78,175, 25);
		password_tf.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		password_tf.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,14));
		password_tf.setForeground(Color.black);
		password_tf.setEchoChar('*');
		mid.add(password_tf);
		
		
		remember_cb.setBounds(15,113,160,15);
		remember_cb.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		remember_cb.setBackground(new Color(0xd0d3d8));
		remember_cb.setForeground(Color.black);
		remember_cb.setBorder(BorderFactory.createEmptyBorder());
		mid.add(remember_cb);
		
		login_auto.setBounds(180,113,170,15);
		login_auto.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		login_auto.setBackground(new Color(0xd0d3d8));
		login_auto.setForeground(Color.black);
		login_auto.setBorder(BorderFactory.createEmptyBorder());
		mid.add(login_auto);
		
		login.setBounds(120, 135, 75, 30);
		login.setBackground(new Color(0xd0d3d8));
		login.setBorder(BorderFactory.createEmptyBorder());
		mid.add(login);
		login.addMouseListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
	}
	
//	public static void main(String args[])
//	{
//		JFrame f=new JFrame();
//		f.setSize(400, 400);
//		f.add(new LoginWindow());
//		f.setVisible(true);
//	}
	
	public void initialize()
	{
		top=new JPanel();
		mid=new JPanel();
		min=new JButton(new ImageIcon("assets/image2.png"));
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.setToolTipText("Minimize Login Window");
		login=new JLabel(new ImageIcon("assets/login.png"),JLabel.CENTER);
		login.setCursor(new Cursor(Cursor.HAND_CURSOR));
		login.setToolTipText("Login");
		connect_tf=new JTextField();
		connect_tf.addKeyListener(this);
		username_tf=new JTextField();
		username_tf.addKeyListener(this);
		password_tf=new JPasswordField();
		password_tf.addKeyListener(this);
		connect_lb=new JLabel("Connect");
		username_lb=new JLabel("Username");
		password_lb=new JLabel("Password");
		remember_cb=new JCheckBox("Remember My Password");
		login_auto=new JCheckBox("Login Automatically");
//		connect_tf,username_tf;
//		JPasswordField password_tf;
		try{
			DataInputStream din=new DataInputStream(new FileInputStream("assets/user"));
				String str=din.readUTF();
				if(str.length()>2)
				{
				connect_tf.setText(str.substring(0, str.indexOf(",")));
				username_tf.setText(str.substring(str.indexOf(",")+1,str.lastIndexOf(",")));
				password_tf.setText(str.substring(str.lastIndexOf(",")+1));
				remember_cb.setSelected(true);
				
				}
				DataInputStream din1=new DataInputStream(new FileInputStream("assets/login"));
				String str1=din1.readUTF();
				if(str1.equals("Login automatically"))
					login_auto.setSelected(true);
				din.close();din1.close();
			}catch(Exception exception){System.out.println(exception);}
		
		rockeit_lb=new JLabel("RockeitTime Authorization",new ImageIcon("assets/rockit_yellow.png"),JLabel.LEFT);
		rockeit_lb.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,25));
		
		
	}
	
	int height,width;
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getSource()==login)
		login.setIcon(new ImageIcon("assets/down.png"));
		else
		{
			height=e.getY();
			width=e.getX();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		login.setIcon(new ImageIcon("assets/login_hover.png"));
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		login.setIcon(new ImageIcon("assets/login.png"));
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	
	@Override
	 public void keyPressed(KeyEvent e)
	{
		System.out.println(e.getKeyCode());
		if(e.getKeyCode()==10)
		{
			MouseEvent me = new MouseEvent(rosfer.lw.login, 0, 0, 0, 100, 100, 1, false);
			for(MouseListener ml:rosfer.lw.login.getMouseListeners())
			{
				ml.mouseClicked(me);
			}
		}
	}
	 public void keyTyped(KeyEvent e){}
	 public void keyReleased(KeyEvent e){}
	 
	 public void mouseDragged(MouseEvent e)
		{
		 	rosfer.setLocation(e.getXOnScreen()-width, e.getYOnScreen()-height+getHeight());
			rosfer.adjust();
		}
		public void mouseMoved(MouseEvent e){}
}
