package com.demo.mappingdemo;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateManyToOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		Aaddress a1= new Aaddress("SB Road", "Pune", 411047);
		Aaddress a2= new Aaddress("MG Road", "Pune", 411048);
		Aaddress a3= new Aaddress("BT Road", "Pune", 411049);
		Sstudent s1=new Sstudent("Jyoti", a1);
		Sstudent s2=new Sstudent("Jui", a1);
		Sstudent s3=new Sstudent("Rohini", a2);
		Sstudent s4=new Sstudent("Mohini", a2);
		Sstudent s5=new Sstudent("Gayatri", a3);
		
		
		session.persist(s1);
		session.persist(s2);
		session.persist(s4);
		session.persist(s5);
		session.persist(s3);
		session.flush();
		 List<Sstudent> students = (List<Sstudent>) session.createQuery(
	                "from Sstudent ").list();
	        for (Sstudent s : students) {
	            System.out.println("Student Details : " + s);
	            System.out.println("Student Address Details: "
	                    +s.getStudAddress());
	        }
		tr.commit();
	}

}

@Entity
@Table(name="Student")
class Sstudent{
	@Id
	@GeneratedValue
	@Column(name = "STUDENT_ID")
	private int studID;
	@Column(name = "STUDENT_NAME")
	private String studName;
	@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "STUDENT_ADDRESS")
	@JoinTable(
            name = "Student_Address",
            joinColumns = @JoinColumn(name = "StuId"),
            inverseJoinColumns = @JoinColumn(name = "AddId")
    )
	private Aaddress studAddress;
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
	public Aaddress getStudAddress() {
		return studAddress;
	}
	public void setStudAddress(Aaddress studAddress) {
		this.studAddress = studAddress;
	}
	@Override
	public String toString() {
		return "Sstudent [studID=" + studID + ", studName=" + studName
				+ ", studAddress=" + studAddress + "]";
	}
	public Sstudent( String studName, Aaddress studAddress) {
		super();
	//	this.studID = studID;
		this.studName = studName;
		this.studAddress = studAddress;
	}
	public Sstudent() {
		super();
		// TODO Auto-generated constructor stub
	}

}

@Entity
@Table(name="Address")
class Aaddress{
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
	public Aaddress( String street, String city, int pin) {
		super();
	//this.addressID = addressID;
		this.street = street;
		this.city = city;
		this.pin = pin;
	}
	Aaddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Aaddress [addressID=" + addressID + ", street=" + street
				+ ", city=" + city + ", pin=" + pin + "]";
	}
	
	
}