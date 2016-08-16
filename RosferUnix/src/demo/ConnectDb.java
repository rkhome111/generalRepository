package demo;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class ConnectDb 
{

BufferedImage snapImg;
Rosfer rosfer;
Calendar cal;
long previousSnapTime;
Connection con1=null;
Statement stmt1=null;
PreparedStatement pstmt=null;
String user_id,project_id,projectTlId,projectTeamId,tl_id,teamId,taskText,subTaskText,comment,time,date,dateOnly,hour,min,sec,reference_id,formattedDate,timeSlot,insertQuery;
int task_id,task_list_id,work_seconds;
File f;
public ConnectDb(Rosfer rosfer)
{
		this.rosfer=rosfer;
		cal=Calendar.getInstance();
		f=new File("DB/localdb.db");
}

	public void insertScreenShort(int work_status,BufferedImage img)
	{
		snapImg=img;
		img.setRGB(20, 20, 20);
		try{
		user_id=rosfer.userId;
		project_id=rosfer.projectId;
		tl_id=rosfer.tlId;
		projectTeamId = rosfer.projectTeamId;
		projectTlId   = rosfer.projectTlId;
		teamId = rosfer.teamId;
		taskText=rosfer.task_text;
		subTaskText=rosfer.subtask_text;
		comment=rosfer.liw.comment.getText().equals("Comment")?"":rosfer.liw.comment.getText();
		task_id=rosfer.task_id;
		task_list_id=rosfer.task_list_id;
		time=rosfer.liw.time.getText();
		date=rosfer.document.getElementsByTagName("serverdate").item(0).getTextContent();
		cal.set(Integer.parseInt(date.substring(0,date.indexOf("-"))), Integer.parseInt(date.substring(date.indexOf("-")+1,date.lastIndexOf("-")))-1, Integer.parseInt(date.substring(date.lastIndexOf("-")+1))+rosfer.increaseDay, Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)),Integer.parseInt(time.substring(6,8)));
		cal.add(Calendar.SECOND, -rosfer.timegap);
		dateOnly=String.valueOf(cal.get(Calendar.YEAR))+"-"+((cal.get(Calendar.MONTH)+1)<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1))+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE));
		hour=cal.get(Calendar.HOUR_OF_DAY)<10?"0"+cal.get(Calendar.HOUR_OF_DAY):""+cal.get(Calendar.HOUR_OF_DAY);
		min=cal.get(Calendar.MINUTE)<10?"0"+cal.get(Calendar.MINUTE):""+cal.get(Calendar.MINUTE);
		sec=cal.get(Calendar.SECOND)<10?"0"+cal.get(Calendar.SECOND):""+cal.get(Calendar.SECOND);
		reference_id=hour+min+sec+" "+dateOnly+user_id+project_id+task_id+tl_id;
		formattedDate = String.valueOf(cal.get(Calendar.YEAR))+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
	
		int minute1=cal.get(Calendar.MINUTE);
		int second=cal.get(Calendar.SECOND);
		int totalSecond=(minute1%(rosfer.interval/60))*60+second;
		
		int remainTime=rosfer.interval-totalSecond;
		
			if(rosfer.work_status==1)
				work_seconds=remainTime;
			else if(rosfer.work_status==3)
				{
					if(rosfer.isgone)
						work_seconds=0-remainTime;
					else
						work_seconds=totalSecond;
				}
			else
				work_seconds=rosfer.interval;
			
			int minuteslot=Integer.parseInt(min)/(rosfer.interval/60);
				minuteslot=minuteslot*(rosfer.interval/60);
				
			String minute=minuteslot<10?"0"+minuteslot:""+minuteslot;
			timeSlot=hour+":"+minute+":"+"00";
		
         try{
        	 
 			synchronized (rosfer.localtoserver) 
 			{
		
 			con1=DriverManager.getConnection("jdbc:sqlite:"+f.getAbsolutePath());
 		
 			stmt1=con1.createStatement();
 			pstmt=con1.prepareStatement("insert into images (reference_id,image_name,folder_name,image_content) values(?,?,?,?)");

            insertQuery="insert into ac_tracker_screenshot_current(reference_id,user_id,project_id,tl_id,task_list_id,task_id,task_text,comment,time_slot,actual_created_on,created_by_id,created_on,work_date,record_status,status,work_status,description,updated_on,updated_by_id,verified_on,verified_by_id,work_seconds,team_id,project_user_tl_id,project_user_team_id) values "
            		+ "		('"+reference_id+"','"+user_id+"','"+project_id+"','"+tl_id+"','"+task_list_id+"','"+task_id+"','"+taskText+subTaskText+"','"+comment+"','"+timeSlot+"','"+formattedDate+"','"+user_id+"','"+formattedDate+"','"+dateOnly+"','1','"+rosfer.status+"','"+work_status+"','','0000-00-00 00:00:01','0','0000-00-00 00:00:01','0','"+work_seconds+"','"+teamId+"','"+projectTlId+"','"+projectTeamId+"')";

            insertQuery+=";insert into ac_tracker_screenshot_images_current(reference_id,image_name,actual_created_on,created_on) values('"+reference_id+"','scrn_"+reference_id+".jpg','"+formattedDate+"','"+formattedDate+"')";
           
//            System.out.println(insertQuery);
            
           
    			System.out.println(con1);
    			stmt1.executeUpdate(insertQuery);
    			System.out.println("query saved in local db");
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ImageIO.write(snapImg, "jpg", baos);
    			
    			
    			pstmt.setString(1, reference_id);
    			pstmt.setString(2, "scrn_"+reference_id+".jpg");
    			pstmt.setString(3, dateOnly);
    			pstmt.setBytes(4, baos.toByteArray());
    			pstmt.executeUpdate();
    			System.out.println("image saved in database");
    			
    			rosfer.localtoserver.doUpdate();
 			}
    			
    			}catch(Exception ee){System.out.println("unable to store data locally "+ee);}
            	finally{try{pstmt.close();stmt1.close();con1.close();}catch(Exception exc){System.out.println(exc);}}
            
        			
           
		}catch(Exception e){System.out.println(e);}
	}
}