package demo;


import java.rmi.*;  


import org.w3c.dom.Document;
public interface Adder extends Remote{  
  
public Document getResult(String sql)throws RemoteException;  
public boolean insert(String sql)throws RemoteException;  
public String check()throws RemoteException;  
public boolean insertImage(byte b[],String r,String date)throws RemoteException;  
public Document login(String user,String pass)throws RemoteException;
public boolean insertLocalImage(byte b[],String rid) throws RemoteException;
	
	

//	public String check()throws RemoteException;  
//	public boolean insertImage(byte b[],String r,String date)throws RemoteException; 
//	public boolean insert(String sql,String ref_id,String tablename)throws RemoteException;
//	public boolean insertLocalImage(byte b[],String rid) throws RemoteException;
//	public Document login(String user,String pass)throws RemoteException;

	public boolean insertScreenshot(Screenshot sc)throws RemoteException;  
	public boolean insertScreenshotImage(ScreenshotImage sc)throws RemoteException;
	public byte[] getUpdateFile(String version)throws RemoteException;
	public byte[] getFile(String version,String osName)throws RemoteException;
	public String getCurrentVersion()throws RemoteException;

}  