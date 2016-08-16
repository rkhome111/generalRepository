package demo;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.*;  
import java.rmi.server.*;  
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import demo.Adder;

@SuppressWarnings("serial")
public class AdderRemote extends UnicastRemoteObject implements Adder
{
//	ResultSet rs;
//	Connection con;
//	Statement stmt;
	DocumentBuilderFactory dbf;
	DocumentBuilder db;
//	String dbname,username,password;
	
	
@Override
	public boolean insertScreenshot(Screenshot sc) throws RemoteException 
{
		Session hs = null;
		Transaction tx = null;
		try{
		hs = MyHibernateFactory.getSession();
		Criteria criteria = hs.createCriteria(ScreenshotImage.class);
		criteria.add(Restrictions.eq("reference_id", sc.getReference_id()));
		if(criteria.list().size()>0)
		{
			System.out.println("Screenshot already exists "+sc.getReference_id());
			return true;
		}
		else{
				tx = hs.beginTransaction();
				sc.setCreated_on(getCurrentTime());
				hs.save(sc);
				hs.getTransaction().commit();
				System.out.println("Screenshot Inserted sucessfully "+sc.getReference_id()+"    "+sc.getActual_created_on());
				return true;
			}
		}catch(Exception e){if(tx!=null) tx.rollback();}
		finally{
			if(hs!=null && hs.isConnected()) hs.close();
		}
		return false;
	}
	@Override
	public boolean insertScreenshotImage(ScreenshotImage sc) throws RemoteException {
		
		
		Session hs = null;
		Transaction tx = null;
		try{
		hs = MyHibernateFactory.getSession();
		Criteria criteria = hs.createCriteria(ScreenshotImage.class);
		criteria.add(Restrictions.eq("reference_id", sc.getReference_id()));
		if(criteria.list().size()>0)
		{
			System.out.println("ScreenshotImage already exists "+sc.getReference_id());
			return true;
		}
		else{
				tx = hs.beginTransaction();
				sc.setCreated_on(getCurrentTime());
				hs.save(sc);
				hs.getTransaction().commit();
				System.out.println("ScreenshotImage Inserted sucessfully "+sc.getReference_id());
				return true;
			}
		}catch(Exception e){if(tx!=null) tx.rollback();}
		finally{
			if(hs!=null && hs.isConnected()) hs.close();
		}
		return false;
	}
	
	
    private static String getCurrentTime()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00"));
        return (new StringBuilder(String.valueOf(calendar.get(1)))).append("-").append(calendar.get(2) + 1).append("-").append(calendar.get(5)).append(" ").append(calendar.get(11)).append(":").append(calendar.get(12)).append(":").append(calendar.get(13)).toString();
    }
	
	@Override
	public byte[] getUpdateFile(String version) throws RemoteException {

		return getFileInByte("assets/update.jar");
	}
	@Override
	public byte[] getFile(String version,String os) throws RemoteException {
		
		if(getCurrentVersion().equalsIgnoreCase(version))
        {
            return null;
        }
		
		if(os.toLowerCase().contains("win"))
			return getFileInByte("assets/rosferwin.jar");
		else
			return getFileInByte("assets/rosferlinux.jar");
	}
	
	private byte[] getFileInByte(String fileName)
	{
		try{
			File file = new File(fileName);
			System.out.println(file.exists());
			FileInputStream fin = new FileInputStream(file);
			byte[] b = new byte[fin.available()]; 
			fin.read(b);
			fin.close();
		System.out.println(fileName+" file updated sucessfully");
		return b;
		
		}catch(Exception e){e.printStackTrace();}
		System.out.println("getUpdateFile called but unable to send it");
		return null;
	}
	
	
@Override
	public String getCurrentVersion() throws RemoteException {
	String ipAndPort="";
	try{
		ipAndPort = new String(Files.readAllBytes(Paths.get("assets/version.txt")));
	}catch(Exception e){}
	return ipAndPort.substring(0,ipAndPort.indexOf(";;"));
	}
@Override
public boolean insertLocalImage(byte[] b,String rid)
{
	Connection connection=null;
	Statement st=null;
	try{
	connection = MyHibernateFactory.getConnection();
	st = connection.createStatement();
	ResultSet rs=st.executeQuery("select work_date from ac_tracker_screenshot where reference_id="+rid);
	rs.next();
	Date date=rs.getDate(1);
	String foldername=String.valueOf(date.getYear()+1900)+"-"+((date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1))+"-"+(date.getDate()<10?"0"+date.getDate():date.getDate());
	System.out.println(foldername);
	return insertImage(b, rid, foldername);
	
	}catch(Exception e){}
	finally{
		try{
			if(st!=null) st.close();
			if(connection!=null)
				connection.close();
		}catch(Exception e){}
	}
	return false;
}
	AdderRemote()throws RemoteException,Exception
	{  
			 dbf = DocumentBuilderFactory.newInstance();
			 db = dbf.newDocumentBuilder();	
	}  


  @Override
public boolean insert(String sql)throws RemoteException
{
	  System.out.println("insert(String sql) called and returning false");
		  return false;
	
}

  public boolean insertImage(byte b[],String reference_id,String date)throws RemoteException
  {
	  try{

						  if(!(new File("screenshot/"+date+"/original").exists()))
					      {
							  	new File("screenshot/"+date).mkdir();
								new File("screenshot/"+date+"/original").mkdir();
								new File("screenshot/"+date+"/thumb").mkdir();
					          System.out.println(" New Date file created");
					      }
		
	  	File original = new File("screenshot/"+date+"/original/scrn_"+reference_id+".jpg");
	  	File thumb = new File("screenshot/"+date+"/thumb/scrn_"+reference_id+".jpg");
		DataOutputStream dout = new DataOutputStream(new FileOutputStream(original));
		dout.write(b);
		dout.close();
	
		BufferedImage im=ImageIO.read(original);
		Image img=im.getScaledInstance(100, 56, Image.SCALE_SMOOTH);
		BufferedImage buffered = new BufferedImage(100,56, Image.SCALE_SMOOTH);
		buffered.getGraphics().drawImage(img, 0, 0 , null);
		ImageIO.write(buffered,"jpg", new FileOutputStream(thumb));

		System.out.println("Image Inserted "+reference_id+"\n");
		return true;	
	  }catch(Exception e){System.out.println(e);}
	  return false;
  }

  @Override
  public Document getResult(String sql)throws RemoteException
  {
	  
	  System.out.println("getResult(String sql) and returning null");
	  return null;
//	  Document document=null ;
//	  try{
//		  
//		
//		  
//		  stmt=con.createStatement();
//		  rs = stmt.executeQuery(sql);
//		  while(rs.next())
//		  {
//			  
//			  document = db.newDocument();
//			  
//			  Element users = document.createElement("users");
//			  document.appendChild(users);
//				
//			  Element user=document.createElement("user");
//			  
//			  
//			  Element id=document.createElement("id");
//			  id.appendChild(document.createTextNode(rs.getString(1)));
//			  user.appendChild(id);
//			  
//			  Element name1=document.createElement("name");
//			  name1.appendChild(document.createTextNode(rs.getString(2)));
//			  user.appendChild(name1);
//			  
//			  users.appendChild(user);
//			  
//			  
//
//		  }
//		  
////		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
////			Transformer transformer = transformerFactory.newTransformer();
////			DOMSource source = new DOMSource(document);
////			StreamResult result = new StreamResult(new File("demo/file.xml"));
////			
////			transformer.transform(source, result);
////
////			System.out.println("File saved!");
//		
//			 
////	  File fXmlFile = new File("demo/file.xml");
////		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
////		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
////		doc = dBuilder.parse(fXmlFile);
////		doc.getDocumentElement().normalize();
////		
//	
//	  }catch(Exception e)
//	  {
//		  System.out.println(e);
//	  }
//	  return document;
  }
  
    public String check()

  {
    	Connection con=null;
    	Statement stmt=null;
	  try{
		  con = MyHibernateFactory.getConnection();
		  stmt=con.createStatement();
		  ResultSet rss=stmt.executeQuery("select count(*) from ac_users");
		  if(rss.next())
			{
				System.out.println("Connection with db is ok");
				return "ok";	
			}
	  }catch(Exception e)
	  {
		  System.out.println("Exception when creating connection with db");
	  }
	  finally {
		if(stmt!=null)
				try{stmt.close();}catch(Exception e){}
		if(con!=null)
			try{con.close();}catch(Exception e){}
	}
	  return " Not connected";
  }
  public static void main(String args[])
  {
//	  for(int i=0;i<1000000;i++)
	  {
		  try{
		  System.out.println(new AdderRemote().login("Roushan","123456"));
		  Thread.sleep(100);
		  }catch(Exception e){System.out.println(e);}
	  }
  }
  
  public synchronized Document login(String user,String pass)throws RemoteException
  {
	  Document doc=null;
      Connection con=null;
      PreparedStatement pstmt=null;
      Statement stmt = null;
      Statement stmt11 = null;
      
	  try{		
		
		  con = MyHibernateFactory.getConnection();

		  	pstmt=con.prepareStatement("select salt,token,id,parent_id,curtime() as time,curdate() as date,timezone,team_id from ac_users where username=?");
			pstmt.setString(1,user);
			ResultSet rs=pstmt.executeQuery();
			doc=db.newDocument();;
		 if(rs.next())
			{
				
				MessageDigest sha1 = MessageDigest.getInstance("SHA1");
				
				String str = rs.getString(1)+pass;
				byte[] b=sha1.digest(str.getBytes("UTF-8"));
				
				StringBuffer sb = new StringBuffer();
				for(byte a:b)
					sb.append(String.format("%02x",a & 0xff));
				
				if(rs.getString(2).equals(sb.toString()))
				{
					
					Element root = doc.createElement("user");
					doc.appendChild(root);
					
					Element time=doc.createElement("servertime");
						time.appendChild(doc.createTextNode(rs.getString("time")));
					
					Element timegap=doc.createElement("timegap");
						timegap.appendChild(doc.createTextNode(String.valueOf((int)(new Double(rs.getString("timezone"))*3600))));
						
					Element userid=doc.createElement("userid");
						userid.appendChild(doc.createTextNode(rs.getString("id")));
					Element tlid=doc.createElement("tlid");
						tlid.appendChild(doc.createTextNode(rs.getString("parent_id")));
						
					Element interval=doc.createElement("interval");
						
					Element date=doc.createElement("serverdate");
					date.appendChild(doc.createTextNode(rs.getString("date")));
					
					Element teamId=doc.createElement("teamid");
					teamId.appendChild(doc.createTextNode(rs.getString("team_id")));
					
					Element defaultproject=doc.createElement("defaultproject");
					defaultproject.appendChild(doc.createTextNode("4703"));
					
					Element status=doc.createElement("status");
						status.appendChild(doc.createTextNode("0"));
					
					stmt11=con.createStatement();
					String sql11="select user_interval from  ac_tracker_interval where user_id=(select id from ac_users where username='"+user+"')";
					
					ResultSet result=stmt11.executeQuery(sql11);
					if(result.next())
						interval.appendChild(doc.createTextNode(String.valueOf(result.getInt(1)*60)));
					else 
						interval.appendChild(doc.createTextNode("600"));
					
					root.appendChild(time);
					root.appendChild(timegap);
					root.appendChild(userid);
					root.appendChild(tlid);
					root.appendChild(teamId);
					root.appendChild(interval);
					root.appendChild(date);
					root.appendChild(defaultproject);
					root.appendChild(status);
					
					Statement currentStatusStmt = con.createStatement();
					ResultSet myrs = currentStatusStmt.executeQuery("select id from ac_project_status where display_in_rosfer='1'");
					String displayInRosfer="(";
					if(myrs.next())
					{
						displayInRosfer+=myrs.getString("id");
						while(myrs.next())
							displayInRosfer+=","+myrs.getString("id");						
					}
					displayInRosfer+=")";
//					System.out.println("display in rosfer is "+displayInRosfer);
						stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						ResultSet userRS=stmt.executeQuery("select project_id,auto_approve,tl_id,team_id from ac_project_users where user_id="+rs.getInt(3));
						StringBuffer projectSql=new StringBuffer();
						Map<String, String> map = new HashMap<>();
						if(userRS.next())
						{	
							
							
							
								projectSql.append("SELECT name,id FROM ac_projects WHERE ((id="+userRS.getString(1)+" and completed_on='0000-00-00 00:00:00')");
								map.put(userRS.getString(1), userRS.getString("tl_id")+":"+userRS.getString("team_id"));
								
								while(userRS.next())
								{
									projectSql.append(" or (id="+userRS.getString(1)+" and completed_on='0000-00-00 00:00:00')");								
									map.put(userRS.getString(1), userRS.getString("tl_id")+":"+userRS.getString("team_id"));
								}
								projectSql.append(")");
								if(displayInRosfer.length()>2)
									projectSql.append(" and current_status in "+displayInRosfer);
								System.out.println(projectSql);
	
							ResultSet projectRS=stmt.executeQuery(projectSql.toString());
							while(projectRS.next())
							{
								String projectId = projectRS.getString(2);

								Element project =doc.createElement("project");
								
								
								Element projectTlId=doc.createElement("projecttlid");
									projectTlId.appendChild(doc.createTextNode(map.get(projectId).substring(0, map.get(projectId).indexOf(":"))));
								Element projectTeamId=doc.createElement("projectteamid");
									projectTeamId.appendChild(doc.createTextNode(map.get(projectId).substring(map.get(projectId).indexOf(":")+1)));			
								
								Element prname=doc.createElement("projectname");
									prname.appendChild(doc.createTextNode(projectRS.getString(1)));
								Element prid=doc.createElement("projectid");
									prid.appendChild(doc.createTextNode(projectId));
									
								
									project.appendChild(prname);
									project.appendChild(prid);
									project.appendChild(projectTlId);
									project.appendChild(projectTeamId);
									root.appendChild(project);
							
									
								String sql="select name,id as task_id from ac_project_task_lists where project_id ='"+projectRS.getString(2)+"'";
								stmt=con.createStatement(ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_SCROLL_SENSITIVE);
								ResultSet tasklistrs=stmt.executeQuery(sql);
								
								while(tasklistrs.next())
								{
									Element task=doc.createElement("task");
									Element taskname=doc.createElement("taskname");
										taskname.appendChild(doc.createTextNode(tasklistrs.getString(1)));
									Element taskid=doc.createElement("taskid");
										taskid.appendChild(doc.createTextNode(tasklistrs.getString(2)));
										
										task.appendChild(taskname);
										task.appendChild(taskid);
									project.appendChild(task);
									
									
									Statement stmt1 = con.createStatement(ResultSet.CONCUR_UPDATABLE,ResultSet.TYPE_SCROLL_SENSITIVE);
									rs.first();
									ResultSet subtaskrs = stmt1.executeQuery("select text,id from ac_project_tasks where task_list_id="+ tasklistrs.getInt(2));
									while(subtaskrs.next())
									{
										Element subtask=doc.createElement("subtask");
											Element subtaskid=doc.createElement("subtaskid");
												subtaskid.appendChild(doc.createTextNode(subtaskrs.getString(2)));
											Element subtaskname=doc.createElement("subtaskname");
												subtaskname.appendChild(doc.createTextNode(subtaskrs.getString(1)));
											subtask.appendChild(subtaskname);
											subtask.appendChild(subtaskid);
										task.appendChild(subtask);
										
									}
									
								}
								
//								
								
							}
					
					
					
					
					
					
						}
					
				}
			
			}
//		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
//			Transformer transformer = transformerFactory.newTransformer();
//			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(new File("demo/file.xml"));
			
//			transformer.transform(source, result);

//			System.out.println("File saved!");
	  
	  }catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  finally {
		try{
			if(pstmt!=null)pstmt.close();
			if(stmt!=null)stmt.close();	
			if(stmt11!=null)stmt11.close();
			if(con!=null)con.close();
//			System.out.println("finally run in login module");
				
		}catch(Exception e){}
	}
	  notifyAll();
	return doc;
  }

  
}  