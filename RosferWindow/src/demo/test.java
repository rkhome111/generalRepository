package demo;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.stream.FileImageInputStream;

import org.hibernate.Session;
import org.hibernate.dialect.TeradataDialect;
import org.hibernate.service.spi.Stoppable;



public class test 
{
	 static {
	        try {
	            Field value = String.class.getDeclaredField("value");
	            value.setAccessible(true);
	            value.set("Hello World", value.get("G'Day Mate."));
	           
	        } catch (Exception e) {
	            throw new AssertionError(e);
	        }
	    }
	 
	public static void main(String args[]) throws Exception
	{
//	    System.out.println("Hello World");
		String s="Hello World";
		String s1=new String("Hello World");
		System.out.println(s);
	    System.out.println(s.hashCode()+"  "+s1.hashCode());	   
	    System.out.println(s==s1);
	    
	   
	    
	    
	    
//		Abc a = Abc.getInstance();
//		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream("abc.ser"));
//		oo.writeObject(a);
//		
//		oo.close();
//		a.changeVal(2);
//		a.show();
//		
//		Abc b =(Abc)new ObjectInputStream(new FileInputStream("abc.ser")).readObject();
//		b.show();
//		b.changeVal(5);
//		a.show();
//		b.show();
		
//		String s="abc";
//		String ss=null;
//		String s1=s.concat(ss);
//		System.out.println(s1);
//		Session s = MyHibernateFactory.getSession();
//		Screenshot sc = new Screenshot();
//		sc.setComment("checking");
//		sc.setSys_name("my System");
//		s.beginTransaction();
//		s.save(sc);
//		
//		s.getTransaction().commit();
//		System.out.println("sucessfully saved");
//		

		
		
//     Hashtable<String,test> hs = new Hashtable();
//     Set<Map.Entry<String,test>> me = hs.entrySet();
//     LinkedList<String> ls = new LinkedList<>();
//     Iterator<String> it = ls.iterator();
//    
//    System.out.println(hs.put("a", new test()));
//    System.out.println(hs.put("a", new test()));
//     HashMap<test,test > hm = new HashMap<>();
//     System.out.println(hm.put(null, new test()));
//     System.out.println(hm.put(null, new test()));
//     Collections.synchronizedMap(hm);
////     Collections.re
//     Properties prop = new Properties();
//     prop.put(null, null);
//     System.out.println("sucessfully run..."+hs.size());
//     
//     Generictest gt = new Generictest(10, 20);
//     System.out.println(gt.print());
//     System.out.println(gt.show(50));
//    
//     
//    
//     System.out.println();
	}
	
}
class Abc implements Externalizable
{
	private static Abc instance;
	private int val=10;
	static{
		instance=new Abc();
		
	}
	public Abc(){}
	public static Abc getInstance()
	{
		return instance;
	}
	public void show()
	{
		System.out.println("show method of Abc "+val);
	}
	public void changeVal(int x)
	{
		val=x;
	}
//	private Object readResolve() throws ObjectStreamException
//	{
//		return instance;
//	}
	@Override
	public void readExternal(ObjectInput oi) throws IOException,
			ClassNotFoundException {
		val=oi.readInt();
		
	}
	@Override
	public void writeExternal(ObjectOutput oo) throws IOException {
		oo.writeInt(val);
		
	}
}

class Generictest <T extends Number>
{
	T a;
	T b;
	
	public Generictest(T a, T b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public T print()
	{
		System.out.println("Value of a is :"+a);
		System.out.println("Value of b is :"+b);
		return b;
	}
	
	public T sum(T a,T b)
	{
		int x,y;
			y = a.intValue();
			x = b.intValue();
			return a;
			
	}
	
	public <Q> void print(Q[] a)
	{
		for(int i=0;i<a.length;i++)
			System.out.println(a[i]);
		
	}
	
	public <s extends Number> int show(s a)
	{
		return a.intValue();
	}
	
}
