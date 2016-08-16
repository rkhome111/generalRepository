package demo;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DefaultAlertWindow extends JDialog implements MouseListener,Runnable
{
	
	private static final long serialVersionUID = 1L;
	Rosfer rosfer;
	JPanel top;
	JLabel title,message1,message2;
	JButton ok;
	
	DefaultAlertWindow(Rosfer rosfer)
	{

		this.rosfer=rosfer;
		setUndecorated(true);
		setLayout(null);
		setSize(200, 90);
		setAlwaysOnTop(true);
		top=new JPanel(null);
		top.setBackground(Before_login_panel.bg_color);
		title=new JLabel("RockeitTime",new ImageIcon("assets/image6.png"), JLabel.LEFT);
		title.setForeground(Before_login_panel.text_color);
		
		message1=new JLabel("Your Default project is running.");
		message2=new JLabel("Please change it.");
		if(rosfer!=null){
		title.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		message1.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		message2.setFont(rosfer.opensanslight.deriveFont(Font.PLAIN,12));
		}
		ok=new JButton(new ImageIcon("assets/ok.png"));
		ok.setBorder(BorderFactory.createEmptyBorder());
		top.setBounds(0, 0, 200, 20);
		title.setBounds(0, 0, 200, 20);
		top.add(title);
		add(top);
		message1.setBounds(10, 27, 190, 15);
		message2.setBounds(50, 45, 200, 15);
		add(message1);
		add(message2);
		
		ok.setBounds(75, 65, 50, 18);
		add(ok);
		ok.addMouseListener(this);
		
		setLocationRelativeTo(null);
		setVisible(false);
		
		
		setFocusableWindowState(false);
		
	}
	
	public void mousePressed(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		rosfer.defaultTime=0;
		setVisible(false);
		message1.setText("Your Default project is running.");
		message2.setText("Please change it.");
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	
	@Override
	public void run()
	{
		boolean isDefaultAvailable=false;
		String projectName="";
		
		NodeList nList = rosfer.document.getElementsByTagName("project");
		for (int i = 0; i < nList.getLength(); i++) 
		{
			Element element=(Element)nList.item(i);
			if(element.getElementsByTagName("projectid").item(0).getTextContent().equals("4703"))
			{
				isDefaultAvailable=true;
				projectName=element.getElementsByTagName("projectname").item(0).getTextContent();
				System.out.println(element.getElementsByTagName("projectid").item(0).getTextContent());
				break;
			}
		}
		
		while(isDefaultAvailable)
		{
			try{
				
				rosfer.defaultTime+=5;
				Thread.sleep(1000*5);

			if(rosfer.defaultTime>=900 && (rosfer.liw.logout.isVisible()|| rosfer.blp.logout.isVisible()))
			{
					if(rosfer.blp.isVisible())
					{
						rosfer.initializeMyComponent();
						rosfer.mycmp.list.setSelectedValue(projectName, true);
						rosfer.mll.changeValue();
						rosfer.startProject();
					}

				else if(rosfer.liw.left.isVisible() && !rosfer.ls.isVisible())
				{
					rosfer.mycmp.list.setSelectedValue(projectName, true);
					rosfer.mll.changeValue();
					rosfer.startProject();
				}
				
				else if(rosfer.projectId.equals("4703") && rosfer.liw.leftStop.isVisible())
					setVisible(true);

				rosfer.defaultTime=0;
			}
				
			}catch(Exception e){System.out.println("Default Alert window "+e);try{Thread.sleep(1000*60*60*24);}catch(Exception eee){}}
		}
		
	}
}
