package ckcc.hibernate.sample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Scanner;

public class Retrieve  extends JFrame{

    public static void main(String[] args) {
    Retrieve r = new Retrieve();
    Scanner sc = new Scanner(System.in);
    while (true) {
        System.out.println("1.Find By ID\n2.Find List\n3.Find LaseName\n4.Update Email\n5.Delete");
        switch (sc.nextInt()) {
            case 1:
                r.getStudentById();
                break;
            case 2:
                r.getStudentList();
                break;
            case 3:
                r.findStudent();
                break;
            case 4:
                r.updateStudent();
                break;
            case 5:
                r.deleteStudent();
                break;
            default:
                System.out.println("Invalid input!!!");
        }
    }
   // r.getStudentList();
  //  r.findStudent();
     //   r.updateStudent();

    }
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Student.class)
            .buildSessionFactory();
    Session sessionObj = factory.getCurrentSession();
    public  void getStudentById(){

        try {
            System.out.print("Enter Sutdent ID:");
            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            sessionObj.beginTransaction();
            Student stu = sessionObj.get(Student.class,id);
            System.out.println("ID: " +stu.getId());
            System.out.println("First Name: "+stu.getFirstName());
            System.out.println("Last Name: "+stu.getLastName()  );
            System.out.println("Email: "+stu.getEmail());
            sessionObj.getTransaction().commit();
            sc.close();
        }
        catch(Exception e){
            System.out.println("student not found");
        }
        finally {
            factory.close();
        }
    }
    public  void getStudentList(){
        try {
            sessionObj.beginTransaction();
            List<Student> students = sessionObj.createQuery("from Student").getResultList();
            for(Student stu : students){
            System.out.println("ID: " +stu.getId());
            System.out.println("First Name: "+stu.getFirstName());
            System.out.println("Last Name: "+stu.getLastName()  );
            System.out.println("Email: "+stu.getEmail());

            }

            sessionObj.getTransaction().commit();

        }
        finally {
            factory.close();
        }

    }
    public  void findStudent(){
        try {
            sessionObj.beginTransaction();
            Scanner sc = new Scanner(System.in);
            String find = sc.nextLine();
            List<Student> students = sessionObj.createQuery("from Student s where s.lastName ="+sc).getResultList();
            for(Student stu : students){
                System.out.println("ID: " +stu.getId());
                System.out.println("First Name: "+stu.getFirstName());
                System.out.println("Last Name: "+stu.getLastName()  );
                System.out.println("Email: "+stu.getEmail());
            }

            sessionObj.getTransaction().commit();

        }
        finally {
            factory.close();
        }
    }
    public  void updateStudent(){
        try {
            sessionObj.beginTransaction();
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            sessionObj.createQuery("update Student set email = 'chinnhong@gmail.com' where id = "+i).executeUpdate();


            sessionObj.getTransaction().commit();

        }
        finally {
            factory.close();
        }
    }
    public void deleteStudent(){
        try {
            sessionObj.beginTransaction();
            Scanner sc = new Scanner(System.in);
            int stuId = sc.nextInt();
            sc.close();
            Student myStudent = sessionObj.get(Student.class,stuId);
            sessionObj.delete(myStudent);
//            System.out.println(i);

            sessionObj.getTransaction().commit();

        }
        finally {
            factory.close();
        }
    }
}
