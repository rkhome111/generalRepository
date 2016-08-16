package demo;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
public class LeftSnap extends JDialog implements Runnable//,MouseListener,MouseMotionListener,FocusListener
{
	private static final long serialVersionUID = 1L;
	JPanel bottom;
	JLabel snap_img;
	BufferedImage snap;
	int height,width;
	Rosfer rosfer;
	
	
	public LeftSnap(Rosfer rosfer,JFrame parrent)
	{
		
		super(parrent);
		this.rosfer=rosfer;
		setUndecorated(true);
		height=85;
		width=105;
		setLayout(null);
		setBounds(0, 0,width,height);

		snap_img=new JLabel();
		snap_img.setBounds(0,0,width,height);
		snap_img.setBorder(BorderFactory.createLineBorder(Color.black));
		add(snap_img);
	}
	

	public void changeImage(BufferedImage snp)
	{
		
		Image img=snp.getScaledInstance(width,height, Image.SCALE_DEFAULT);
		snap_img.setIcon(new ImageIcon(img));
		
		rosfer.leftsnapvisible=false;
		

		if(rosfer.windowName.equals("second") && rosfer.work_status!=3)
		{
			System.out.println("Extended state is "+rosfer.liw.getExtendedState());
			System.out.println("iconified status is "+JFrame.ICONIFIED);
			System.out.println("LeftSnap is "+isVisible());
			rosfer.leftsnapvisible=true;
			
			if(!rosfer.liw.isVisible())
			{
				setLocation(rosfer.liw.getX()-this.getWidth(), rosfer.liw.getY()+rosfer.liw.getHeight()-this.getHeight());
				rosfer.leftsnapvisible=true;
			}
			
			if(rosfer.liw.isVisible() && rosfer.liw.getExtendedState()==1)
			{
				setLocation(rosfer.liw.getX()-this.getWidth(), rosfer.liw.getY()+rosfer.liw.getHeight()-this.getHeight());
					rosfer.leftsnapvisible=true;
			}
			
			else if( rosfer.liw.isVisible() && rosfer.liw.getExtendedState()==0 && !isVisible())
			{
				setLocation(rosfer.liw.getX()-this.getWidth(), rosfer.liw.getY()+rosfer.liw.getHeight()-this.getHeight());
				this.setVisible(true);
				rosfer.leftsnapvisible=true;
			}
			
		}
		else if(rosfer.windowName.equals("first") && !this.isVisible())
		{
			if(!rosfer.isVisible()|| rosfer.liw.getExtendedState()==JFrame.ICONIFIED)
				rosfer.leftsnapvisible=true;
			else if(!this.isVisible())
			{
				setLocation(rosfer.getX()-this.getWidth(), rosfer.getY()+rosfer.getHeight()-this.getHeight());
				this.setVisible(true);
//				rosfer.toFront();
			}
		}
		System.gc();
	}
	
		
	public void run()
	{
		try{
			Thread.sleep(1000*12);

			this.setVisible(false);
			rosfer.leftsnapvisible=false;

			}catch(Exception ex){}

	}
	
//	int width1,height1;
//	@Override
//	public void mousePressed(MouseEvent e)
//	{
//
//		height1=e.getY();
//		width1=e.getX();
//	}
//	public void mouseClicked(MouseEvent e){}
//	public void mouseEntered(MouseEvent e){}
//	public void mouseExited(MouseEvent e){}
//	public void mouseReleased(MouseEvent e){}
//	
//	public void mouseDragged(MouseEvent e)
//	{
//		if(rosfer.isVisible())
//		{
//		rosfer.setLocation(e.getXOnScreen()+getWidth()-width1, e.getYOnScreen()-height1);
//		rosfer.adjust();
//		}
//		else if(rosfer.liw.isVisible())
//		{
//			rosfer.liw.toFront();
//			rosfer.liw.setLocation(e.getXOnScreen()+getWidth()-width1, e.getYOnScreen()-height1-20);
//			rosfer.ls.setLocation(rosfer.liw.getX()-rosfer.ls.getWidth(), rosfer.liw.getY()+rosfer.liw.getHeight()-rosfer.ls.getHeight());
//			if(rosfer.mycmp.isVisible())
//				rosfer.mycmp.setBounds(rosfer.liw.getX()+50,rosfer.liw.getY()-rosfer.mycmp.getHeight()+60,200,rosfer.mycmp.getHeight());
//			if(rosfer.liw.rus.isVisible())
//				rosfer.liw.rus.setLocation(rosfer.liw.getX()+20,rosfer.liw.getY()-rosfer.liw.rus.getHeight()+35);
//		}
//	}
//	public void mouseMoved(MouseEvent e){}

}
