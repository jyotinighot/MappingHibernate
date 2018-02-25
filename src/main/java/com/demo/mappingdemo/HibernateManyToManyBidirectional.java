package com.demo.mappingdemo;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.GenericGenerator;

public class HibernateManyToManyBidirectional {
	public static void main(String[] args) {
		Student6 student1 = new Student6("Jyoti","Nighot");
        Student6 student2 = new Student6("Chitra", "Jadhav");
         
        Subject1 subject1 = new Subject1("Physics");
        Subject1 subject2 = new Subject1("Chemistry");
        Subject1 subject3 = new Subject1(" Biology");
 
        //Student1 have 3 subjects
        student1.getSubjects().add(subject1);
        student1.getSubjects().add(subject2);
        student1.getSubjects().add(subject3);
         
        //Student2 have 2 subjects
        student2.getSubjects().add(subject1);
        student2.getSubjects().add(subject2);

		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.persist(student1);
        session.persist(student2);

        List<Student6> student = (List<Student6>)session.createQuery("from Student6 ").list();
        for(Student6 s: student){
            System.out.println("Details : "+s+ "Subject Details " + s.getSubjects());
        }
        System.out.println("\n\n");

       
		session.flush();
		tr.commit();

	}

}

@Entity
@Table(name = "STUDENT")
 class Student6 {
 
    @Id
    @GeneratedValue
    @Column(name = "STUDENT_ID")
    private long id;
 
    @Column(name = "FIRST_NAME")
    private String firstName;
 
    @Column(name = "LAST_NAME")
    private String lastName;
 
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "STUDENT_SUBJECT", 
        joinColumns = { @JoinColumn(name = "STUDENT_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "SUBJECT_ID") })
    private List<Subject1> subjects = new ArrayList<Subject1>();
 
    public Student6() {
    }
 
    public Student6(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public List<Subject1> getSubjects() {
        return subjects;
    }
 
    public void setSubjects(List<Subject1> subjects) {
        this.subjects = subjects;
    }
 
     
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Student6))
            return false;
        Student6 other = (Student6) obj;
        if (id != other.id)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "\nStudent [id=" + id + ", firstName=" + firstName + ", lastName="
                + lastName + "]";
    }
 
}

@Entity
@Table(name = "SUBJECT")
class Subject1{
 
    @Id
    @GeneratedValue
    @Column(name = "SUBJECT_ID")
    private long id;
 
    @Column(name = "NAME")
    private String name;
     
     
    @ManyToMany(mappedBy="subjects")
    private List<Student6> students = new ArrayList<Student6>();
     
    public Subject1(){
         
    }
     
    public Subject1(String name){
        this.name = name;
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
 
    public List<Student6> getStudents() {
        return students;
    }
 
    public void setStudents(List<Student6> students) {
        this.students = students;
    }
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Subject1))
            return false;
        Subject1 other = (Subject1) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + "]";
    }
 
}