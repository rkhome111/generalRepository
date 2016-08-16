package demo;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
  
public class MyServer {  
public static void main(String[] args){  
try{  
	
	
	String ipAndPort = new String(Files.readAllBytes(Paths.get("assets/version.txt")));
	StringBuilder sb = new StringBuilder(ipAndPort);
//	sb.reverse();
//	System.out.println(sb);
//	StringBuffer sb1 = new StringBuffer(ipAndPort);
//	sb1.reverse();
//	System.out.println(sb1);
	ipAndPort = ipAndPort.trim();
	String myip = ipAndPort.substring(ipAndPort.indexOf(";;")+2,ipAndPort.indexOf(":"));
	int myport = new Integer(ipAndPort.substring(ipAndPort.indexOf(":")+1)).intValue();
	System.out.println("starting server at IP "+ myip +"and port "+myport);
	

	RMISocketFactory.setSocketFactory(new RMISocketFactory() {
		
		@Override
		public Socket createSocket(String host, int port) throws IOException {
			return new Socket(myip, myport);
		}
		
		@Override
		public ServerSocket createServerSocket(int port) throws IOException {
			return new ServerSocket(myport);
		}
	});
		
	System.setProperty("java.rmi.server.hostname", myip);
	Adder stub=new AdderRemote();  
	Registry registry=LocateRegistry.createRegistry(myport);
	
	registry.rebind("rkrmi",stub);
//	Naming.rebind("rmi://10.0.1.251:1115/rkrmi",stub);
	MyHibernateFactory.getConnection().close();
  System.out.println("Server Started at IP "+ myip +"and port "+myport);
	PrintStream fout = new PrintStream(new File("serverlog.txt"));
	System.setOut(fout);
	System.setErr(fout);
}catch(Exception e){System.out.println(e);}  
}  
}   
