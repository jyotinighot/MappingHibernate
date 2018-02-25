package com.demo.mappingdemo;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.GenericGenerator;

public class HibernateOneToOneBidirectional {
	public static void main(String[] args) {

		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		Studen2 s1=new Studen2("Jyoti");
		Department2 d1= new Department2("Computer");
		d1.setStudent(s1);
		s1.setDepartment(d1);
		session.persist(s1);
		Studen2 s2=new Studen2("Jui");
		Department2 d2= new Department2("Civil");
		d2.setStudent(s2);
		s2.setDepartment(d2);
		session.persist(s2);
		/*
		List<Department2> departments = (List<Department2>)session.createQuery("from Department2 ").list();
        for(Department2 s: departments){
            System.out.println("Details : "+s);
        }*/


		session.flush();
		tr.commit();

	}

}


@Entity
class Studen2 {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;

	@OneToOne (mappedBy="student",cascade=CascadeType.ALL)
	//@JoinColumn(name="DEPT_ID", unique= true, nullable=true, insertable=true, updatable=true)
	/*@JoinTable(
			name = "Student_Department",
			joinColumns = @JoinColumn(name = "StudID"),
			inverseJoinColumns = @JoinColumn(name = "DeptId")
			)*/
	@JoinColumn(name="Stud_Dept")
	private Department2 department;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department2 getDepartment() {
		return department;
	}

	public void setDepartment(Department2 department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Studen [id=" + id + ", name=" + name + ", department="
				+ department + "]";
	}

	Studen2( String name) {
		super();
		this.name = name;
	}

	Studen2() {
		super();
		// TODO Auto-generated constructor stub
	}

}


@Entity
@Table(name="Dept")
class Department2 {
	@Id
    @Column(name="ADDRESS_ID")
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign",parameters=@Parameter(name="property", value="student"))
	private int id;
	private String name;
	@OneToOne
    @PrimaryKeyJoinColumn
	//@JoinColumn(name="S_D")
    private Studen2 student;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Studen2 getStudent() {
		return student;
	}
	public void setStudent(Studen2 student) {
		this.student = student;
	}
	@Override
	public String toString() {
		return "Department2 [id=" + id + ", name=" + name + ", student="
				+ student + "]";
	}
	Department2(String name) {
		super();
			this.name = name;
	}
	Department2() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

