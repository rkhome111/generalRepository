package demo;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionImpl;

public class MyHibernateFactory 
{
	static SessionFactory factory;
	static Session hs;
	static
	{
		Configuration cfg=new Configuration().configure("hibernate.cfg.xml");

		factory = cfg.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build());
	
	}
	
	public static Session getSession()
	{
		return factory.openSession();
	}
	
	public static Connection getConnection()
	{
		return ((SessionImpl)getSession()).connection();
	}
	

}
