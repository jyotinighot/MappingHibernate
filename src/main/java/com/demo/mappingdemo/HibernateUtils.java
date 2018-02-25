package com.demo.mappingdemo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	static SessionFactory sessionFactory = null;
	
	public static SessionFactory getSessionFactory(){
		if(null == sessionFactory){
			sessionFactory= new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}

}
