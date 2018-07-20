package ckcc.hibernate.sample;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class Save {
	public static void main(String[] args) {
	
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();
		Session sessionObj = factory.getCurrentSession();
		try {
			Student tempStudent = new Student("Chinnhong","Seng","com");
			sessionObj.beginTransaction();
			sessionObj.save(tempStudent);
			sessionObj.getTransaction().commit();
			System.out.println("Insert successfully!!!");
		}
			finally {
				factory.close();
			}
	}
}
