package demo;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class PersonalDetails {
JFrame frame;
	JPanel top,mid,bottom;
	JTextField nametf,phonetf,emailtf;
	JTextArea addressta;
	JLabel headerlb,namelb,phonelb,emaillb,addresslb,citylb,qualificationlb,genderlb,languagelb,blanklb1,blanklb2;
	JRadioButton male,female;
	ButtonGroup buttongroup;
	JCheckBox hindi,english;
	JButton submit;
	JComboBox<String> qualificationcmb,citycmb;
	JScrollPane jsp;
	Pattern pattern;
	Matcher matcher;
	GridBagConstraints gbc;
	
	
	public PersonalDetails()
	{
		frame=new JFrame("Personal Details");
		top=new JPanel();
		mid=new JPanel();
		bottom=new JPanel();
			nametf=new JTextField(20);
			phonetf=new JTextField(20);
			emailtf=new JTextField(20);
		
			phonetf.addFocusListener(new FocusListener(){public void focusGained(FocusEvent e){validateName();} public void focusLost(FocusEvent e1){}});
			emailtf.addFocusListener(new FocusListener(){public void focusGained(FocusEvent e){validatePhone();} public void focusLost(FocusEvent e1){}});
			
			addressta=new JTextArea(4,10);
			addressta.setLineWrap(true);
			addressta.addFocusListener(new FocusListener(){public void focusGained(FocusEvent e){validateEmail();} public void focusLost(FocusEvent e1){}});
			addressta.addKeyListener(new KeyAdapter(){public void keyPressed(KeyEvent e){if(e.getKeyCode()==9){citycmb.requestFocus();	}}});
			jsp=new JScrollPane(addressta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//	jsp=new JScrollPane(addressta);
			
			
			namelb=new JLabel("Name");
			phonelb=new JLabel("Phone");
//			phonelb.setFocusable(true);
			emaillb=new JLabel("Email");
			headerlb=new JLabel("Personal Detail Form");
			addresslb=new JLabel("Address");
			citylb=new JLabel("City");
			qualificationlb=new JLabel("Qualification");
			genderlb=new JLabel("Gender");
			languagelb=new JLabel("Language");

			
		hindi=new JCheckBox("Hindi");
		english=new JCheckBox("English");
		
//		JButton b=new JButton();
		
			submit=new JButton("Submit");
//			submit.setMinimumSize(new Dimension(100,50));
			submit=new JButton(new ImageIcon("assets\\login.png"));
//			submit.setBackground(Color.yellow);
			submit.setMargin(new Insets(10,10,0,0));
			submit.setBorder(BorderFactory.createEmptyBorder());
			Graphics g=submit.getGraphics();
			System.out.println(g);
//			g.drawLine(0, 0, 10,20);
			submit.addMouseListener(new MouseAdapter(){
				public void mouseEntered(MouseEvent e){submit.setIcon(new ImageIcon("assets\\login_hover.png"));}
				public void mouseExited(MouseEvent e){submit.setIcon(new ImageIcon("assets\\login.png"));}
				public void mousePressed(MouseEvent e){submit.setIcon(new ImageIcon("assets\\down.png"));}
				});
		
		male=new JRadioButton("Male");
		male.setSelected(true);
		female=new JRadioButton("Female");
		buttongroup=new ButtonGroup();
		buttongroup.add(male);
		buttongroup.add(female);
		
		String qcmb[]={"BCA","MCA","BBA","MBA"};
		qualificationcmb=new JComboBox<String>(qcmb);
		citycmb=new JComboBox<String>(new String[] {"DELHI","MUMBAI","DEHRADUN","PATNA"});
		citycmb.addFocusListener(new FocusListener(){public void focusGained(FocusEvent e){validateAddress();} public void focusLost(FocusEvent e1){}});
		


		mid.setLayout(new GridBagLayout());
		gbc=new GridBagConstraints();
		gbc.insets=new Insets(10,10,0,0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		addComponent(headerlb,0,1,3);
		addComponent(namelb,1,0,1);
		addComponent(nametf,1,1,2);
		addComponent(phonelb,2,0,1);
		addComponent(phonetf,2,1,2);
		addComponent(emaillb,3,0,1);
		addComponent(emailtf,3,1,2);
		addComponent(addresslb,4,0,1);
		addComponent(jsp,4,1,2);
		addComponent(citylb,5,0,1);
		addComponent(citycmb,5,1,2);
		addComponent(qualificationlb,6,0,1);
		addComponent(qualificationcmb,6,1,2);
		addComponent(genderlb,7,0,1);
		addComponent(male,7,1,1);
		addComponent(female,7,2,1);
		addComponent(languagelb,8,0,1);
		addComponent(hindi,8,1,1);
		addComponent(english,8,2,1);
		gbc.fill=GridBagConstraints.NONE;

		addComponent(submit,9,0,3);
		

		
		submit.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
				
				
			//	validate();
				if(phonetf.getText().equals("")||emailtf.getText().equals("")||nametf.getText().equals("")||addressta.getText().equals(""))
					JOptionPane.showMessageDialog(frame,"Please fill all the fields");
				else if(!(hindi.isSelected()||english.isSelected()))
				{
					JOptionPane.showMessageDialog(frame,"Select your Language");
				}
				else
					JOptionPane.showMessageDialog(frame,"Every thing is ok");
			}
		});
		
		
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(350,500);
		frame.setMinimumSize(new Dimension(350,450));
		frame.setLocation(dimension.width/2-180,dimension.height/2-250);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mid,BorderLayout.PAGE_START);
		frame.add(top,BorderLayout.BEFORE_LINE_BEGINS);
	//	top.add(headerlb);
		frame.setVisible(true);
	//	frame.setResizable(false);
		submit.requestFocus();
	//	frame.pack();
		Dimension d=submit.getSize();
		System.out.println(d.width+ "  "+d.height);
	}
	

	
//	private void validate()
//	{
//		if(!(hindi.isSelected()||english.isSelected()))
//		{
//			hindi.setBorder(BorderFactory.createLineBorder(Color.red));
//			english.setBorder(BorderFactory.createLineBorder(Color.red));
//		}
//			
//	}

	private void validateName()
	{
		Pattern pattern=Pattern.compile("\\D+");
		String name=nametf.getText();
		Matcher matcher=pattern.matcher(name);

		if(matcher.find()&&matcher.start()==0&&matcher.end()==name.length()) 
		{
			nametf.setBorder(BorderFactory.createEtchedBorder());
			nametf.setToolTipText(null);
		
		}
			else 
			{
//				JOptionPane.showMessageDialog(frame,"Invalid Name");
//				nametf.requestFocus();
				nametf.setToolTipText("Invalid Name.");
				nametf.setBorder(BorderFactory.createLineBorder(Color.red));
				JOptionPane op=new JOptionPane();
				System.out.println(op.getClass());
				op.setFocusable(false);
				nametf.setText(JOptionPane.showInputDialog(null, "Enter a Valid Name","invalid Name",JOptionPane.OK_CANCEL_OPTION));
				nametf.setToolTipText("Invalide Name");
			
			}
	}
	
	private void validatePhone()
	{
		pattern=Pattern.compile("[0-9]*");
		String phone=phonetf.getText();
		matcher=pattern.matcher(phone);
		if((matcher.find()&&matcher.start()==0&&matcher.end()==phone.length()&&phone.length()==10) ||phone.length()==0)
		{
			phonetf.setBorder(BorderFactory.createEtchedBorder());
			phonetf.setToolTipText(null);
		}
		else 
		{
//			JOptionPane.showMessageDialog(frame,"Invalid Phone");
			phonetf.setToolTipText("Invalide Phone");
			phonetf.setBorder(BorderFactory.createLineBorder(Color.red));
			phonetf.setText(JOptionPane.showInputDialog(null, "Enter a valid Mobile No.","Invalid Mobile Number",JOptionPane.INFORMATION_MESSAGE));
			
		}
	}

	private void validateEmail()
	{
		pattern=Pattern.compile( "(\\w+@\\w+[.])(.{2,3})");
		String email=emailtf.getText();
		matcher=pattern.matcher(email);
		if((matcher.find()&&matcher.start()==0&&matcher.end()==email.length())||email.length()==0) 
		{
			emailtf.setBorder(BorderFactory.createEtchedBorder());
			emailtf.setToolTipText(null);
		}
		else 
		{
//			JOptionPane.showMessageDialog(frame,"Invalid Email");
			emailtf.setBorder(BorderFactory.createLineBorder(Color.red));
			emailtf.setToolTipText("Invalide Email");
			emailtf.setText(JOptionPane.showInputDialog(null, "Enter a valid Email ID.","Invalid Email",JOptionPane.INFORMATION_MESSAGE));
		}
	}
	
	private void validateAddress()
	{
		if((addressta.getText().length()==1)||(addressta.getText().length()==0)||(addressta.getText().length()<150&&addressta.getText().length()>20))
		{
			addressta.setBorder(BorderFactory.createEtchedBorder());
			addressta.setToolTipText(null);
		}
		else 
		{
		//	JOptionPane.showMessageDialog(frame,"Invalid Address");
			addressta.setBorder(BorderFactory.createLineBorder(Color.red));
		
			addressta.setToolTipText("Invalide Address");
			addressta.setText(JOptionPane.showInputDialog(null, "Enter a valid Address.","Invalid Address",JOptionPane.INFORMATION_MESSAGE));
			
		}
	
	}
	

	public void addComponent(Component c,int gridy,int gridx,int gridwidth)
	{
		gbc.gridy=gridy;
		gbc.gridx=gridx;
		gbc.gridwidth=gridwidth;
		mid.add(c,gbc);
		
	}
	
	
	public static void main(String args[])
	{//a a1=new a();
		new PersonalDetails();
		

	}
	
}


