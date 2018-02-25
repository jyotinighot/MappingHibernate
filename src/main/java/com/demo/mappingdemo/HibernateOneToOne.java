package com.demo.mappingdemo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.demo.mappingdemo.HibernateUtils;

public class HibernateOneToOne {

	public static void main(String[] args) {

		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		Studen student = new Studen();
		student.setName("Jyoti");
		session.persist(student);


		Department dept = new Department();
		dept.setName("Computer");
		student.setDepartment(dept);
		
		Studen student1 = new Studen();
		student1.setName("Jui");
		session.persist(student1);

		Department dept1 = new Department();
		dept1.setName("Civil");
		student1.setDepartment(dept1);
		
		Studen student2 = new Studen();
		student2.setName("Rashmi");
		session.persist(student2);
		student2.setDepartment(dept1);

		 List<Studen> students = (List<Studen>) session.createQuery(
	                "from Studen ").list();
	        for (Studen s : students) {
	            System.out.println("Student Details : " + s);
	            System.out.println("Student Address Details: "
	                    +s.getDepartment());
	        }
	    
	    
		session.flush();
		tr.commit();

	}

}


@Entity
class Studen {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;

	@OneToOne (cascade=CascadeType.ALL)
	//@JoinColumn(name="DEPT_ID", unique= true, nullable=true, insertable=true, updatable=true)
	@JoinTable(
            name = "Student_Department",
            joinColumns = @JoinColumn(name = "StudID"),
            inverseJoinColumns = @JoinColumn(name = "DeptId")
    )
	private Department department;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Studen [id=" + id + ", name=" + name + ", department="
				+ department + "]";
	}
	
}


@Entity
@Table(name="Dept")
class Department {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String deptName) {
		this.name = deptName;
	}

	public String toString() {
		return "Department id: " + getId() + 
				", name: " + getName();
	}
}

