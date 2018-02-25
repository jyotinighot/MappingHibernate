package com.demo.mappingdemo;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateManyToOneBidirectional {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		Aaddress1 a1= new Aaddress1("SB Road", "Pune", 411047,null);
		Aaddress1 a2= new Aaddress1("MG Road", "Pune", 411048,null);
		Aaddress1 a3= new Aaddress1("BT Road", "Pune", 411049,null);
		Sstudent1 s1=new Sstudent1("Jyoti", a1);
		Sstudent1 s2=new Sstudent1("Jui", a1);
		Sstudent1 s3=new Sstudent1("Rohini", a2);
		Sstudent1 s4=new Sstudent1("Mohini", a2);
		Sstudent1 s5=new Sstudent1("Gayatri", a3);
		Set<Sstudent1> studList1=new HashSet<Sstudent1>();
		studList1.add(s1);
		studList1.add(s2);
		a1.setStudList(studList1);
		Set<Sstudent1> studList2=new HashSet<Sstudent1>();
		studList2.add(s3);
		studList2.add(s4);
		a2.setStudList(studList2);
		Set<Sstudent1> studList3=new HashSet<Sstudent1>();
		studList3.add(s5);
		a3.setStudList(studList3);
		
		/*session.persist(a1);
		session.persist(a2);
		session.persist(a3);*/
		session.persist(s1);
		session.persist(s2);
		session.persist(s4);
		session.persist(s5);
		session.persist(s3);
		session.flush();
		/************ Navigation Using Student Class  *****************/
		System.out.println("*********** Navigation Using Student Class  *****************");
		 List<Sstudent1> students = (List<Sstudent1>) session.createQuery(
	                "from Sstudent1 ").list();
	        for (Sstudent1 s : students) {
	            System.out.println("Student Details : " + s);
	            System.out.println("Student Address Details: "
	                    +s.getStudAddress());
	        }
	        
	        /************ Navigation Using Address  Class  *****************/
	        System.out.println("*********** Navigation Using Address  Class  ***********");
		List<Aaddress1> address=(List<Aaddress1>) session.createQuery("from Aaddress1 ").list();
		for (Aaddress1 aaddress1 : address) {
			 System.out.println("\n\nAddress Details : " + aaddress1);
	            System.out.println("Student  Details: "
	                    +aaddress1.getStudList());
		}
		tr.commit();
	}

}

@Entity
@Table(name="Student")
class Sstudent1{
	@Id
	@GeneratedValue
	@Column(name = "STUDENT_ID")
	private int studID;
	@Column(name = "STUDENT_NAME")
	private String studName;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "STUDENT_ADDRESS")
/*	@JoinTable(
            name = "Student_Address",
            joinColumns = @JoinColumn(name = "StuId"),
            inverseJoinColumns = @JoinColumn(name = "AddId")
    )*/
	private Aaddress1 studAddress;
	public int getStudID() {
		return studID;
	}
	public void setStudID(int studID) {
		this.studID = studID;
	}
	public String getStudName() {
		return studName;
	}
	public void setStudName(String studName) {
		this.studName = studName;
	}
	public Aaddress1 getStudAddress() {
		return studAddress;
	}
	public void setStudAddress(Aaddress1 studAddress) {
		this.studAddress = studAddress;
	}
	@Override
	public String toString() {
		return "\nSstudent1 [studID=" + studID + ", studName=" + studName
				+ ", studAddress=" + studAddress + "]";
	}
	Sstudent1(String studName, Aaddress1 studAddress) {
		super();
		this.studName = studName;
		this.studAddress = studAddress;
	}
	Sstudent1() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((studName == null) ? 0 : studName.hashCode());
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
		Sstudent1 other = (Sstudent1) obj;
		if (studName == null) {
			if (other.studName != null)
				return false;
		} else if (!studName.equals(other.studName))
			return false;
		return true;
	}
	
	
}

@Entity
@Table(name="Address")
class Aaddress1{
	@Id
	@GeneratedValue
	@Column(name="Address_ID")
	private int addressID;
	@Column(name="Address_STREET")
	private String street;
	@Column(name="Address_City")
	private String city;
	@Column(name="Address_PIN")
	private int pin;
	@OneToMany(mappedBy = "studAddress", cascade = CascadeType.ALL)
	private Set<Sstudent1> studList;
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public Set<Sstudent1> getStudList() {
		return studList;
	}
	public void setStudList(Set<Sstudent1> studList) {
		this.studList = studList;
	}
	Aaddress1(String street, String city, int pin, Set<Sstudent1> studList) {
		super();
		this.street = street;
		this.city = city;
		this.pin = pin;
		this.studList = studList;
	}
	Aaddress1() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		final int maxLen = 10;
		return "\nAaddress1 [addressID=" + addressID + ", street=" + street
				+ ", city=" + city + ", pin=" + pin  + "]";
	}
	
	
	
}