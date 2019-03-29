package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HConnection {
	
private static SessionFactory sessionFactory = null;
	
	private HConnection(){
		
	}
	public static SessionFactory getSessionFactory(){
		if(sessionFactory==null){
			sessionFactory=new AnnotationConfiguration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}

	
}
