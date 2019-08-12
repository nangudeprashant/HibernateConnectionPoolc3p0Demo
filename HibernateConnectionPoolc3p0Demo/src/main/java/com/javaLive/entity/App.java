package com.javaLive.entity;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javaLive.databaseUtil.*;

public class App {
    public static void main(String[] args) {
    	final Logger logger = LoggerFactory.getLogger(App.class);
    	Student student1 = new Student(73,"Name73", "Address73");
        Student student2 = new Student(74,"Name74", "Address74");
        Transaction transaction = null;
        System.out.println("Table contents before starting any operation:");
        new App().getStudentList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            System.out.println("\n\n");
            // save the student objects
            session.save(student1);
            session.save(student2);
            // commit transaction
            transaction.commit();
            System.out.println("Displaying student list after inserting new entires");
            logger.info("Displaying student list after inserting new entires");
            new App().getStudentList();
            // update the student objects
            student1.setAddress("NewName73");
            student1.setName("NewName73");
            student2.setAddress("NewName74");
            student2.setName("NewName74");
            transaction = session.beginTransaction();
            System.out.println("\n\n");
            session.update(student1);
            session.update(student2);
            // commit transaction
            transaction.commit();
            System.out.println("Displaying student list after updating the entires");
            new App().getStudentList();
            // delete the student objects
            transaction = session.beginTransaction();
            System.out.println("\n\n");
            session.delete(student1);
            session.delete(student2);
            // commit transaction
            transaction.commit();
            System.out.println("Displaying student list after deleting the entires");
            new App().getStudentList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public void getStudentList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List < Student > students = session.createQuery("from Student", Student.class).list();
			for(Student s:students){
				System.out.println(s.toString());
			};
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}