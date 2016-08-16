package demo;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.rmi.Naming;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UpdateFile  implements MouseListener
{

	AlertWindow al;
	Adder stub;
	String version,rmiserver;
	File file;
	
	public static void main(String args[])  throws Exception
	{
		new UpdateFile();
	} 
	
	
	
	public UpdateFile() throws Exception
	{
		
		file=new File("assets/version.txt");
		FileInputStream f=new FileInputStream(file);
		byte vs[]=new byte[f.available()];
		f.read(vs);
		f.close();
		
		String readfile=(new String(vs).trim());
		version=readfile.substring(0,readfile.indexOf(";;")).trim();
		rmiserver=readfile.substring(readfile.indexOf(";;")+2);
		rmiserver = rmiserver.trim();
		System.out.println("version is "+version+" server path is"+rmiserver);
		System.out.println("rmi://"+rmiserver+"/rkrmi");
		stub=(Adder)Naming.lookup("rmi://"+rmiserver+"/rkrmi"); 
		if(version.equals(stub.getCurrentVersion()))
		{
			System.out.println("You have already updated version");
		}
		
		
		else
		{
			al=new AlertWindow(this);
			al.setVisible(true);

		}
		 	
		
	}
	
	 void update()
		{
		 
		 try{
			 al.ok.setVisible(false);
			
			byte b[]=stub.getFile(version,System.getProperty("os.name"));
			System.out.println("Updating file");
			OutputStream fout=new FileOutputStream("rosfer.jar");
			fout.write(b);
			fout.close();
			
			FileOutputStream fileout=new FileOutputStream(file);
			fileout.write((stub.getCurrentVersion()+";;"+rmiserver).getBytes());
			fileout.close();
			
			System.out.println("File updated sucessfully");
			al.message1.setText("Rosfer Updated Sucessfully...");
			al.message2.setText("Click OK to continue...");
			al.sucess.setVisible(true);
			
//			al.setVisible(false);
		 }catch(Exception e){System.out.println(e);}
		 
		}
	
	public void mousePressed(MouseEvent e)
	{
		if(e.getSource()==al.ok)
		{
			al.message1.setText("While updating Rosfer...");
			al.message2.setText("Please Wait...");
			
			new Thread(){public void run(){update();}}.start();
			
		}
		
		else if(e.getSource()==al.sucess)
		{
			System.exit(1);
		}
		
		
	}
	public void mouseClicked(MouseEvent e){}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	

	}





	class AlertWindow extends JDialog
	{
		
		private static final long serialVersionUID = 1L;
		
		JPanel top;
		JLabel title,message1,message2;
		JButton ok,sucess;
		AlertWindow(UpdateFile uf)
		{

		
			setUndecorated(true);
			setLayout(null);
			setSize(200, 90);
			setAlwaysOnTop(true);
			top=new JPanel(null);
			top.setBackground(new Color(0x2D313C));
			title=new JLabel("RockeitTime",new ImageIcon("assets/image6.png"), JLabel.LEFT);
			title.setForeground(new Color(0x6f7687));
//			title.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
			message1=new JLabel("New Updates are available...",JLabel.CENTER);
			message2=new JLabel("Update it now..",JLabel.CENTER);
//			message1.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
//			message2.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
			ok=new JButton(new ImageIcon("assets/ok.png"));
			sucess=new JButton(new ImageIcon("assets/ok.png"));
			sucess.setVisible(false);
			ok.setBorder(BorderFactory.createEmptyBorder());
			top.setBounds(0, 0, 200, 20);
			title.setBounds(0, 0, 200, 20);
			top.add(title);
			add(top);
			message1.setBounds(0, 27, 190, 15);
			message2.setBounds(0, 45, 200, 15);
			add(message1);
			add(message2);
			
			ok.setBounds(75, 65, 50, 18);
			sucess.setBounds(75,65,50,18);
			add(ok);
			add(sucess);
			ok.addMouseListener(uf);
			sucess.addMouseListener(uf);
			
			setLocationRelativeTo(null);
			setFocusableWindowState(false);
			
		}
		
	}		
	


