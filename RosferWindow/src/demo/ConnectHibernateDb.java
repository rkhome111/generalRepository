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

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

public class ConnectHibernateDb 
{

BufferedImage snapImg;
Rosfer rosfer;
Calendar cal;
long previousSnapTime;
Connection con1=null;
Statement stmt1=null;
PreparedStatement pstmt=null;
String taskText,subTaskText,comment,time,date,dateOnly,reference_id,hour,min,sec,formattedDate,timeSlot,insertQuery;
int user_id,project_id,tl_id;
int task_id,task_list_id,work_seconds;
File f;
Session hs;
public ConnectHibernateDb(Rosfer rosfer)
{
		this.rosfer=rosfer;
		cal=Calendar.getInstance();
//		f=new File("DB/localdb");
		hs = MyHibernateFactory.getSession();
}

	public void insertScreenShort(int work_status,BufferedImage img)
	{
		snapImg=img;
		img.setRGB(20, 20, 20);
		try{
		user_id=new Integer(rosfer.userId);
		project_id=new Integer(rosfer.projectId);
		tl_id=new Integer(rosfer.tlId);
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
 			ScreenshotImage si = new ScreenshotImage();
            si.setReference_id(reference_id);
			si.setActual_created_on(formattedDate);
			si.setCreated_on(formattedDate);
			si.setImage_name("scrn_"+reference_id+".jpg");
            
            
			Screenshot sh=new Screenshot();;
			sh.setReference_id(reference_id.toString());
			sh.setUser_id(user_id);
			sh.setProject_id(project_id);
			sh.setTask_list_id(task_list_id);
			sh.setTask_id(task_id);
			sh.setTask_text(taskText);
			sh.setDescription(comment);
			sh.setComment(comment);
			sh.setTl_id(tl_id);
			sh.setTime_slot(timeSlot);
			sh.setActual_created_on(formattedDate);
			sh.setCreated_on(formattedDate);
			sh.setCreated_by_id(user_id);
			sh.setUpdated_by_id(0);
			sh.setVerified_by_id(0);
			sh.setWork_date(dateOnly);
			sh.setRecord_status(1);
			sh.setStatus(rosfer.status);
			sh.setWork_status(work_status);
			sh.setWork_seconds(work_seconds);
			sh.setSys_name(rosfer.sys_name);
			sh.setUpdated_on("0000-00-00 00:00:01");
			sh.setVerified_on("0000-00-00 00:00:01");

			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(snapImg, "jpg", baos);
			Images image = new Images();
    		image.setReference_id(reference_id);
    		image.setImage_name("scrn_"+reference_id+".jpg");
    		image.setFolder_name(dateOnly);
    		image.setImage_content(baos.toByteArray());
    		
    		
			hs.beginTransaction();
			hs.save(sh);
			hs.save(si);
			hs.save(image);
			hs.getTransaction().commit();
			hs.close();
			
    			System.out.println("query saved in local db");
    			
    			rosfer.localtoserver.doUpdate();
 			}
    			
    			}catch(Exception ee){System.out.println("unable to store data locally "+ee);}
            	finally{try{if(pstmt!=null)pstmt.close();if(stmt1!=null)stmt1.close();if(con1!=null)con1.close();}catch(Exception exc){System.out.println(exc);}}
            
        			
           
		}catch(Exception e){System.out.println(e);}
	}
}