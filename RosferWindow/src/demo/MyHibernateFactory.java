package demo;


import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MyHibernateFactory 
{
	 static SessionFactory factory;
	
	
	public static Session getSession()
	{
		
		return factory.openSession();
	}
	
	
	
	static{
		try {
			Configuration configuration = new Configuration();
//			Properties properties = new Properties();
//			properties.setProperty("hibernate.dialect", "demo.SQLiteDialect");
//			properties.setProperty("hibernate.connection.url", "jdbc:sqlite:DB/localdb");
//			properties.setProperty("hibernate.connection.username", "");
//			properties.setProperty("hibernate.connection.password", "");
			
//			configuration.setProperties(properties);
			
			
			SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
			factory = new Configuration().configure().buildSessionFactory();
	
//			factory = new AnnotationConfiguration().configure().addAnnotatedClass(Student.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

}
