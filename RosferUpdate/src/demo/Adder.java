package demo;

import java.rmi.*;  

public interface Adder extends Remote{  

public byte[] getFile(String version,String os)throws RemoteException;
public byte[] getUpdateFile(String version)throws RemoteException;
public String getCurrentVersion()throws RemoteException;

}  