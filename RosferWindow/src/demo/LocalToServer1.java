package demo;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.Timer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

public class LocalToServer1
{
	Rosfer rosfer;
	Timer timer;
	Connection con;
	Statement stmt;
	String dblocation=new File("DB/localdb").getAbsolutePath();
	ResultSet rs=null;
	ArrayList<Object> al=new ArrayList<Object>();
	Adder localstub;
	public LocalToServer1(Rosfer r)
	{
		rosfer=r;
		try{
		Class.forName("org.sqlite.JDBC");
		}catch(Exception e){System.out.println(e);}
		
		
	}
	
	
	String currentTime,date,time;
//	Calendar cal;
	public void doUpdate1()
	{
//		
//		try
//		{
//
//				localstub=(Adder)Naming.lookup("rmi://"+rosfer.server_address+"/rkrmi"); 
//
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
//String sql="";
//			while(rs.next())
//			{
//				System.out.println("sending data...");
//				String ref_id=rs.getString("reference_id");
//				sql+="insert into ac_tracker_screenshot(reference_id,user_id,project_id,tl_id,task_list_id,task_id,task_text,description,time_slot,actual_created_on,created_by_id,created_on,work_date,record_status,status,work_status,comment,updated_on,updated_by_id,verified_on,verified_by_id,work_seconds,sys_name) values "
//	            		+ "		('"+ref_id+"','"+rs.getString("user_id")+"','"+rs.getString("project_id")+"','"+rs.getString("tl_id")+"','"+rs.getString("task_list_id")+"','"+rs.getString("task_id")+"','"+rs.getString("task_text")+"','"+rs.getString("comment")+"','"+rs.getString("time_slot")+"','"+rs.getString("actual_created_on")+"','"+rs.getString("user_id")+"','"+currentTime+"','"+rs.getString("work_date")+"','1','"+rs.getString("status")+"','"+rs.getString("work_status")+"','','0000-00-00 00:00:01','0','0000-00-00 00:00:01','0','"+rs.getString("work_seconds")+"','"+rosfer.sys_name+"');";
//				
//				if(localstub.insert(sql,ref_id,"ac_tracker_screenshot_current"))
//				{
//					System.out.println("screenshot_current data send sucessfully...");
//					al.add(ref_id);
//				}
//				else
//					System.out.println("Unable to send data...");
//			}
//			
//			
//			for(String s:al)
//			{
//				stmt1.execute("delete from ac_tracker_screenshot_current where reference_id='"+s+"'");
//				System.out.println("deleted from local db screenshot current");
//			}
//			al.clear();
//			
//			rs1=stmt1.executeQuery("select * from ac_tracker_screenshot_images_current");
//			while(rs1.next())
//			{
//				String ref_id=rs1.getString("reference_id");
//				sql+="insert into ac_tracker_screenshot_images(reference_id,image_name,actual_created_on,created_on) values('"+rs1.getString("reference_id")+"','scrn_"+rs1.getString("reference_id")+".jpg','"+rs1.getString("actual_created_on")+"','"+currentTime+"');";
//
//				if(localstub.insert(sql,ref_id,"ac_tracker_screenshot_images_current"))
//				{
//					System.out.println("images_current data send sucessfully.....");
//					al.add(ref_id);
//				}
//				else
//					System.out.println("Unable to send data...");
//			}
//			FileOutputStream fout = new FileOutputStream(new File("C:\\Users\\sis227\\Desktop\\Rosferwin\\abc.txt"));
//			fout.write(sql.getBytes());
//			for(String s:al)
//			{
//				stmt1.execute("delete from ac_tracker_screenshot_images_current where reference_id='"+s+"'");
//				System.out.println("deleted from local db screenshot current");
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
//				{ System.out.println("Image inserted sucessfully...");
//					al.add(ref_id);
//				}
//				else
//					System.out.println("Unable to send image...");
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
	}
	
	Session hs ;
	public void doUpdate()
	{
		try
		{

				localstub=(Adder)Naming.lookup("rmi://"+rosfer.server_address+"/rkrmi"); 

		if(localstub!=null && localstub.check().length()>0)
		{
		synchronized(this)
		{
			hs= MyHibernateFactory.getSession();
			hs.beginTransaction();
			con=((SessionImpl)hs).connection();
			stmt=con.createStatement();
//			con1=DriverManager.getConnection("jdbc:sqlite:"+dblocation);
//			stmt1=con1.createStatement();	 	
//			con1.setAutoCommit(true);
//			rs=stmt1.executeQuery("select * from ac_tracker_screenshot_current");
			
			Criteria query = hs.createCriteria(Screenshot.class);
			List<Screenshot> list = query.list();
			for(Screenshot sh:list)
			{
				
//				Screenshot sh=new Screenshot();
//				sh.setId(rs.getInt("id"));
//				sh.setReference_id(rs.getString("reference_id"));
//				sh.setUser_id(rs.getInt("user_id"));
//				sh.setProject_id(rs.getInt("project_id"));
//				
//				sh.setTask_list_id(rs.getInt("task_list_id"));
//				sh.setTask_id(rs.getInt("task_id"));
//				sh.setTask_text(rs.getString("task_text"));
//				sh.setDescription(rs.getString("description"));
//				sh.setComment(rs.getString("comment"));
////				sh.setTask_text(" ");
////				sh.setDescription(" ");
////				sh.setComment(" ");
//				
//				sh.setTl_id(rs.getInt("tl_id"));
//				
//				sh.setTime_slot(rs.getString("time_slot"));
//				sh.setActual_created_on(rs.getString("actual_created_on"));
//				sh.setCreated_on(rs.getString("created_on"));
//				sh.setCreated_by_id(rs.getInt("created_by_id"));
//				
//				
//				sh.setUpdated_by_id(rs.getInt("updated_by_id"));
//				
//				
//				sh.setVerified_by_id(rs.getInt("verified_by_id"));
//				
//				sh.setWork_date(rs.getString("work_date"));
//				sh.setRecord_status(rs.getInt("record_status"));
//				
//				sh.setStatus(rs.getString("status"));
//				sh.setWork_status(rs.getInt("work_status"));
//				sh.setWork_seconds(rs.getInt("work_seconds"));
//				sh.setSys_name(rosfer.sys_name);
//				sh.setUpdated_on("0000-00-00 00:00:01");
//				sh.setVerified_on("0000-00-00 00:00:01");
////				System.out.println(localstub+"   "+sh);
				try{
				if(localstub.insertScreenshot(sh))
				{
					System.out.println("screenshot send sucessfully...");
					al.add(sh);
				}
					
				else 
					System.out.println("unable to send screenshot...");
				}catch(Exception e){System.out.println("Exception occures but continue......");}
			}
			
			for(Object s:al)
			{
				hs.delete(s);
				System.out.println("deleted from local db screenshot");
			}
			al.clear();
			
			
//			rs1=stmt1.executeQuery("select * from ac_tracker_screenshot_images_current");
			query = hs.createCriteria(ScreenshotImage.class);
			List<ScreenshotImage> list1 = query.list();
			for(ScreenshotImage si:list1)
			{
//
//				ScreenshotImage si=new ScreenshotImage();
//				
//				si.setReference_id(rs1.getString("reference_id"));
//				si.setActual_created_on(rs1.getString("actual_created_on"));
//				si.setCreated_on(rs.getString("created_on"));
//				si.setImage_name(rs1.getString("image_name"));
//			
//				
				
			try{
				if(localstub.insertScreenshotImage(si))
				{
					System.out.println("screenshot Images sent sucessfully ...");
					al.add(si);
				}
				else
					System.out.println("unable to send screenshot Images...");
			}catch(Exception e){System.out.println("Exception occures but continue......");}
			}
			
			for(Object s:al)
			{
				hs.delete(s);
				System.out.println("deleted from local db screenshot images");
			}
			al.clear();
			
			
			rs=stmt.executeQuery("select * from images");
			while(rs.next())
			{
				System.out.println("sending Images...");
				String ref_id=rs.getString("reference_id");
				try{
				if(localstub.insertImage(rs.getBytes("image_content"), ref_id, rs.getString("folder_name")))
					al.add(ref_id);
				}catch(Exception e){System.out.println("Exception in image but continue......");}
			}
			for(Object s:al)
			{
				stmt.execute("delete from images where reference_id='"+(String)s+"'");
				System.out.println("image deleted from database");
			}
			al.clear();
			hs.getTransaction().commit();
			
			notifyAll();
		}
		}
		}catch(Exception e){System.out.println(e);}
		finally{try{System.out.println("vaccuming database...");stmt.execute("vacuum");rs.close();hs.close();}catch(Exception e){}}
	
	}
	
	

}
