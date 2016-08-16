package demo;
import java.io.File;
import java.io.ObjectOutput;
import java.rmi.Naming;
import java.rmi.UnmarshalException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Timer;

public class LocalToServer
{
	Rosfer rosfer;
	Timer timer;
	Connection con1;
	Statement stmt1,stmt2;
	String dblocation=new File("DB/localdb.db").getAbsolutePath();
	ResultSet rs=null,rs1=null,rs2=null;
	ArrayList<String> al=new ArrayList<String>();
	Adder localstub;
	public LocalToServer(Rosfer r)
	{
		rosfer=r;
		try{
		Class.forName("org.sqlite.JDBC");
		}catch(Exception e){System.out.println(e);}
		
		
	}
	
	
	String currentTime,date,time;
	Calendar cal;
//	public void doUpdate1()
//	{
//		
//		try
//		{
//
//				localstub=(Adder)Naming.lookup("rmi://"+rosfer.server_address+"/rkrmi"); 
//				System.out.println(localstub);
//		if(localstub!=null && localstub.check().length()>0)
//		{
//		synchronized(this)
//		{
//			con1=DriverManager.getConnection("jdbc:sqlite:"+dblocation);
//			stmt1=con1.createStatement();	 	
//			con1.setAutoCommit(true);
//			rs=stmt1.executeQuery("select * from ac_tracker_screenshot_current");
//			cal=Calendar.getInstance();
//			time=rosfer.liw.time.getText();
//			date=rosfer.document.getElementsByTagName("serverdate").item(0).getTextContent();
//			cal.set(Integer.parseInt(date.substring(0,date.indexOf("-"))), Integer.parseInt(date.substring(date.indexOf("-")+1,date.lastIndexOf("-")))-1, Integer.parseInt(date.substring(date.lastIndexOf("-")+1))+rosfer.increaseDay, Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)),Integer.parseInt(time.substring(6,8)));
//			cal.add(Calendar.SECOND, -rosfer.timegap);
//			currentTime = String.valueOf(cal.get(Calendar.YEAR))+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
//
//			while(rs.next())
//			{
//				String ref_id=rs.getString("reference_id");
//				String sql="insert into ac_tracker_screenshot_current(reference_id,user_id,project_id,tl_id,task_list_id,task_id,task_text,description,time_slot,actual_created_on,created_by_id,created_on,work_date,record_status,status,work_status,comment,updated_on,updated_by_id,verified_on,verified_by_id,work_seconds,sys_name) values "
//	            		+ "		('"+ref_id+"','"+rs.getString("user_id")+"','"+rs.getString("project_id")+"','"+rs.getString("tl_id")+"','"+rs.getString("task_list_id")+"','"+rs.getString("task_id")+"','"+rs.getString("task_text")+"','"+rs.getString("comment")+"','"+rs.getString("time_slot")+"','"+rs.getString("actual_created_on")+"','"+rs.getString("user_id")+"','"+currentTime+"','"+rs.getString("work_date")+"','1','"+rs.getString("status")+"','"+rs.getString("work_status")+"','','0000-00-00 00:00:00','0','0000-00-00 00:00:00','0','"+rs.getString("work_seconds")+"','"+rosfer.sys_name+"')";
//				System.out.println(sql);
//				if(localstub.insert(sql,ref_id,"ac_tracker_screenshot_current"))
//					al.add(ref_id);
//			}
//			
//			for(String s:al)
//			{
//				stmt1.execute("delete from ac_tracker_screenshot_current where reference_id='"+s+"'");
//				System.out.println("deleted from local db screenshot");
//			}
//			al.clear();
//			
//			rs1=stmt1.executeQuery("select * from ac_tracker_screenshot_images_current");
//			while(rs1.next())
//			{
//				String ref_id=rs1.getString("reference_id");
//				String sql="insert into ac_tracker_screenshot_images_current(reference_id,image_name,actual_created_on,created_on) values('"+rs1.getString("reference_id")+"','scrn_"+rs1.getString("reference_id")+".jpg','"+rs1.getString("actual_created_on")+"','"+currentTime+"')";
//
//				if(localstub.insert(sql,ref_id,"ac_tracker_screenshot_images_current"))
//					al.add(ref_id);
//			}
//			
//			for(String s:al)
//			{
//				stmt1.execute("delete from ac_tracker_screenshot_images_current where reference_id='"+s+"'");
//				System.out.println("deleted from local db screenshot images current");
//			}
//			al.clear();
//			
//			
//			rs2=stmt1.executeQuery("select * from images");
//			
//			while(rs2.next())
//			{
//				String ref_id=rs2.getString("reference_id");
//				
//				if(localstub.insertImage(rs2.getBytes("image_content"), ref_id, rs2.getString("folder_name")))
//					al.add(ref_id);
//			}
//			for(String s:al)
//			{
//				stmt1.execute("delete from images where reference_id='"+s+"'");
//				System.out.println("image deleted from database");
//			}
//			al.clear();
//			
//			notifyAll();
//		}
//		}
//		}catch(Exception e){System.out.println("from local "+e);}
//		finally{try{stmt1.execute("vacuum");rs.close();rs1.close();rs2.close();stmt1.close();con1.close();}catch(Exception e){}}
//	
//	}
	

	public void doUpdate()
	{
		try
		{

				localstub=(Adder)Naming.lookup("rmi://"+rosfer.server_address+"/rkrmi"); 
				System.out.println("Localstub "+localstub);
		if(localstub!=null && localstub.check().length()>0)
		{
		synchronized(this)
		{
			con1=DriverManager.getConnection("jdbc:sqlite:"+dblocation);
			stmt1=con1.createStatement();	 	
			con1.setAutoCommit(true);
			rs=stmt1.executeQuery("select * from ac_tracker_screenshot_current");
			cal=Calendar.getInstance();
			time=rosfer.liw.time.getText();
			date=rosfer.document.getElementsByTagName("serverdate").item(0).getTextContent();
			cal.set(Integer.parseInt(date.substring(0,date.indexOf("-"))), Integer.parseInt(date.substring(date.indexOf("-")+1,date.lastIndexOf("-")))-1, Integer.parseInt(date.substring(date.lastIndexOf("-")+1))+rosfer.increaseDay, Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)),Integer.parseInt(time.substring(6,8)));
			cal.add(Calendar.SECOND, -rosfer.timegap);
			currentTime = String.valueOf(cal.get(Calendar.YEAR))+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE)+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
			
			while(rs.next())
			{
				Screenshot sh=new Screenshot();

				sh.setReference_id(rs.getString("reference_id"));
				sh.setUser_id(rs.getInt("user_id"));
				sh.setProject_id(rs.getInt("project_id"));
				
				sh.setTask_list_id(rs.getInt("task_list_id"));
				sh.setTask_id(rs.getInt("task_id"));
				sh.setTask_text(rs.getString("task_text"));
				sh.setDescription(rs.getString("description"));
				sh.setComment(rs.getString("comment"));
				sh.setTl_id(rs.getInt("tl_id"));
				
				sh.setTime_slot(rs.getString("time_slot"));
				System.out.println("time slot is "+rs.getString("time_slot"));
				sh.setActual_created_on(rs.getString("actual_created_on"));
				sh.setCreated_on(rs.getString("created_on"));
				sh.setCreated_by_id(rs.getInt("created_by_id"));
				
				sh.setUpdated_on(rs.getString("updated_on"));
				sh.setUpdated_by_id(rs.getInt("updated_by_id"));

				sh.setVerified_on(rs.getString("verified_on"));
				sh.setVerified_by_id(rs.getInt("verified_by_id"));
				
				sh.setWork_date(rs.getString("work_date"));
				sh.setRecord_status(rs.getInt("record_status"));
				
				sh.setTeamId(rs.getInt("team_id"));
				sh.setProjectTlId(rs.getInt("project_user_tl_id"));
				sh.setProjectTeamId(rs.getInt("project_user_team_id"));
				
				sh.setStatus(rs.getString("status"));
				sh.setWork_status(rs.getInt("work_status"));
				sh.setWork_seconds(rs.getInt("work_seconds"));
				sh.setSys_name(rosfer.sys_name);
				sh.setUpdated_on("0000-00-00 00:00:01");
				sh.setVerified_on("0000-00-00 00:00:01");
				sh.setVersion(rosfer.version);
				
				try{
				if(localstub.insertScreenshot(sh))
					al.add(sh.getReference_id());
				}
				catch(Exception e){System.out.print("Exception occured but continuing"+e);}
				
				
			}
			
			for(String s:al)
			{
				stmt1.execute("delete from ac_tracker_screenshot_current where reference_id='"+s+"'");
				System.out.println("deleted from local db screenshot");
			}
			al.clear();
			
			
			rs1=stmt1.executeQuery("select * from ac_tracker_screenshot_images_current");
			while(rs1.next())
			{
				ScreenshotImage si=new ScreenshotImage();
				
				si.setReference_id(rs1.getString("reference_id"));
				si.setActual_created_on(rs1.getString("actual_created_on"));
				si.setCreated_on(rs.getString("created_on"));
				si.setImage_name(rs1.getString("image_name"));
			
				
				try{
			
				if(localstub.insertScreenshotImage(si))
					al.add(si.getReference_id());
				}catch(Exception e){System.out.println(e);}
			}
			
			for(String s:al)
			{
				stmt1.execute("delete from ac_tracker_screenshot_images_current where reference_id='"+s+"'");
				System.out.println("deleted from local db screenshot images");
			}
			al.clear();
			
			
			rs2=stmt1.executeQuery("select * from images");
			
			while(rs2.next())
			{
				try{
				String ref_id=rs2.getString("reference_id");
				
				if(localstub.insertImage(rs2.getBytes("image_content"), ref_id, rs2.getString("folder_name")))
					al.add(ref_id);
				}catch(Exception e){System.out.println(e);}
				
			}
			
			for(String s:al)
			{
				
				stmt1.execute("delete from images where reference_id='"+s+"'");
				System.out.println("image deleted from database");
				
			}
			al.clear();
			
			notifyAll();
		}
		}
		}catch(Exception e){System.out.println("Connection Refused by the remote server "+e);}
		finally{try{stmt1.execute("vacuum");rs.close();rs1.close();rs2.close();stmt1.close();con1.close();}catch(Exception e){}}
	
	}
	
	

}
