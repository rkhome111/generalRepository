package demo;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
public class LeftSnap extends JDialog implements Runnable,MouseListener,MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	JPanel bottom;
	JLabel snap_img;
	BufferedImage snap;
	int height,width;
	Rosfer rosfer;
	
	
	public LeftSnap(Rosfer rosfer)
	{
		super(rosfer);
		this.rosfer=rosfer;
		setUndecorated(true);
		setFocusableWindowState(false);
		this.setBackground(new Color(0,0,0,0));
		this.getContentPane().setBackground(new Color(1f, 1f, 1f,0f));
//		setLayout(null);

		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		height=105;
		width=105;
		setLayout(null);
		setBounds(0, 0,width,height);
//		setOpacity(0.99f);
//		Image img=snap.getScaledInstance(width,height, Image.SCALE_DEFAULT);
		snap_img=new JLabel();
		snap_img.setBounds(0,0,width,height-20);
		snap_img.setBorder(BorderFactory.createLineBorder(Color.black));
		add(snap_img);
		
		
		JPanel bottom=new JPanel();
		bottom.setBounds(-20,height-20, width+50, 20);
		bottom.setBackground(new Color(0,0,0,0));
		bottom.setBorder(rosfer.getShadowBorder(Color.black, false, false, false, true, 20));
		add(bottom);
		
		
		

	}
	
	public void changeImage(BufferedImage snp)
	{
		
		Image img=snp.getScaledInstance(width,height-20, Image.SCALE_DEFAULT);
		snap_img.setIcon(new ImageIcon(img));
		if(rosfer.ls.isVisible())
			rosfer.ls.update(rosfer.ls.getGraphics());
		
		else if(rosfer.getState()==JFrame.ICONIFIED)
		{
			rosfer.leftsnapvisible=true;
			rosfer.bottom.setBounds(-30,rosfer.getHeight()-20, rosfer.getWidth()+50,20);
		}
		else if(rosfer.getState()==JFrame.NORMAL && !rosfer.ls.isVisible())
		{
			setLocation(rosfer.getX()-this.getWidth(), rosfer.getY()+rosfer.getHeight()-this.getHeight());
			rosfer.ls.setVisible(true);
			rosfer.bottom.setBounds(-30,rosfer.getHeight()-20, rosfer.getWidth()+50,20);
			rosfer.ls.update(rosfer.ls.getGraphics());
		}
	
		System.gc();
	}
	
	
		
	public void run()
	{
		Rectangle r=this.getBounds();
		try{
			Thread.sleep(1000*11);
			
			for(int i=0;i<=width;i+=5)
			{
				this.setBounds(rosfer.getX()-width+i,rosfer.getY()+rosfer.getHeight()-height,width-i,height);
				Thread.sleep(5);
			}
	
				setVisible(false);
				rosfer.leftsnapvisible=false;
				
			this.setBounds(r);
			
			
			}catch(Exception ex){System.out.println(ex);}
	
		rosfer.bottom.setBounds(-10,rosfer.getHeight()-20, rosfer.getWidth()+20,20);
		if(rosfer.isVisible())
			rosfer.repaint();
		System.out.println(" left snap is "+this.isVisible()+rosfer.leftsnapvisible);
		
	}
	
	int width1,height1;
	@Override
	public void mousePressed(MouseEvent e)
	{

		height1=e.getY();
		width1=e.getX();
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	
	public void mouseDragged(MouseEvent e)
	{
		rosfer.setLocation(e.getXOnScreen()+getWidth()-width1, e.getYOnScreen()-height1-20);
		rosfer.adjust();
	}
	public void mouseMoved(MouseEvent e){}

}
