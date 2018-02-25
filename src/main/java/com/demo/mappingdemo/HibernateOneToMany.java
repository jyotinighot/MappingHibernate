package com.demo.mappingdemo;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.demo.mappingdemo.HibernateUtils;

public class HibernateOneToMany {

	public static void main(String[] args) {
		Emp e1 = new Emp(1,"Emp1",60000,null);
		Emp e2 = new Emp(2,"Emp2",70000,null);
		Emp e3 = new Emp(3,"Emp3",80000,null);
		Emp e4 = new Emp(4,"Emp4",90000,null);
		Emp e5 = new Emp(5,"Emp5",95000,null);
		
		Employer c1 = new Employer(100,"Xyz","Pune",null);
		Employer c2 = new Employer(200,"PQR","Pune",null);
		
		Set<Emp> setOfEmps1 = new HashSet<Emp>();
		setOfEmps1.add(e1);
		setOfEmps1.add(e2);
		setOfEmps1.add(e3);
		
		c1.setEmployees(setOfEmps1);
		e1.setEmployer(c1);
		e2.setEmployer(c1);
		e3.setEmployer(c1);
		
		
		Set<Emp> setOfEmps2 = new HashSet<Emp>();
		setOfEmps2.add(e4);
		setOfEmps2.add(e5);
		
		c2.setEmployees(setOfEmps2);
		e4.setEmployer(c2);
		e5.setEmployer(c2);
		
		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.persist(c1);
		session.save(c2);
		session.flush();
		tr.commit();
		
	}
	
}

@Entity(name="CompanyInfo1")
class Employer {
	@Id
	private int registrationId;
	private String companyName;
	private String companyAddress;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(
	            name = "Company_Employee",
	            joinColumns = @JoinColumn(name = "registrationId"),
	            inverseJoinColumns = @JoinColumn(name = "empId")
	    )
	private Set<Emp> employees;
	public Employer() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "\nEmployer [registrationId=" + registrationId + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress
				+ ", employees=" + employees + "]";
	}
	
	public Employer(int registrationId, String companyName,
			String companyAddress, Set<Emp> employees) {
		super();
		this.registrationId = registrationId;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.employees = employees;
	}

	public int getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(int registrationId) {
		if(registrationId<=0)
			 this.registrationId=ThreadLocalRandom.current().nextInt(100,500);
		else	
			this.registrationId = registrationId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Set<Emp> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Emp> employees) {
		this.employees = employees;
	}
}

@Entity(name="employeeInfo1")
class Emp{
	@Id
	private int empId;
	private String empName;
	private int empSalary;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Employer employer;
	
	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Emp(int empId, String empName, int empSalary, Employer employer) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empSalary = empSalary;
		this.employer = employer;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(int empSalary) {
		this.empSalary = empSalary;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	@Override
	public String toString() {
		return "\nEmp [empId=" + empId + ", empName=" + empName + ", empSalary="
				+ empSalary + ", employer=" + employer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empName == null) ? 0 : empName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emp other = (Emp) obj;
		if (empName == null) {
			if (other.empName != null)
				return false;
		} else if (!empName.equals(other.empName))
			return false;

		return true;
	}
	
}






