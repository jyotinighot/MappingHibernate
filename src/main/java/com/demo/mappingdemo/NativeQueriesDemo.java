package com.demo.mappingdemo;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
public class NativeQueriesDemo {

	@SuppressWarnings({ "unused", "unchecked", "deprecation" })
	public static void main(String[] args) {
		Stock s1 = new Stock(101,"Abc",3012);
		Stock s2 = new Stock(102,"PQRE",312);
		Stock s3 = new Stock(103,"SSSSS",1012);
		Stock s4 = new Stock(104,"ABCASAQA",4012);
		Stock s5 = new Stock(105,"ANASQOAK",6012);

		SessionFactory sFactory = HibernateUtils.getSessionFactory();
		Session session = sFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(s4);
		session.save(s5);
		session.flush();
		tr.commit();

		Query sqlQueryGetStockById = session.getNamedQuery("sqlfindStockWithGivenId");
		sqlQueryGetStockById.setInteger("sid",103);
		List<Stock> listOfObjectsSQLID  = sqlQueryGetStockById.list();
		System.out.println("SQL By ID -- "+listOfObjectsSQLID);


		Query sqlQueryGetStockByName = session.getNamedQuery("sqlfindStockWithGivenName");
		sqlQueryGetStockByName.setString("sname","SSSSS");

		List<Stock> listOfObjectsSQLName  = sqlQueryGetStockByName.list();
		System.out.println("SQL By Name -- "+listOfObjectsSQLName);

		Query hqlQueryGetStockById = session.getNamedQuery("hqlfindStockWithGivenId");
		hqlQueryGetStockById.setInteger("sid",105);
		List<Stock> listOfObjectsHQLId  = hqlQueryGetStockById.list();
		System.out.println("HQL By Id -- "+listOfObjectsHQLId);


		Query hqlQueryGetStockByName = session.getNamedQuery("@HQL_GET_ALL_STOCK");
		//hqlQueryGetStockByName.setString("sname","ANASQOAK");
		List<Stock> listOfObjectsHQLName  = hqlQueryGetStockByName.list();
		System.out.println("HQL By Name -- "+listOfObjectsHQLName);
	}


}

/*
 * @NamedNativeQueries  -- SQL Queries specific to DB -- refer to table and column names
 * 
 * @NamedQueries  -- HQL Queries -- Hibernate  -- refer to entity class and memeber fields
 * 
 */

@NamedNativeQueries(
	{
	@NamedNativeQuery(
	name = "sqlfindStockWithGivenId",
	query = "select * from stock_info  where stock_id = :sid",
        resultClass = Stock.class
	),
	@NamedNativeQuery(
			name = "sqlfindStockWithGivenName",
			query = "select * from stock_info  where stock_name = :sname",
		        resultClass = Stock.class
			)
})

@NamedQueries({ @NamedQuery(name = "hqlfindStockWithGivenId", 
query = "from Stock where stockId = :sid"),

@NamedQuery(name = "@HQL_GET_ALL_STOCK", 
query = "from Stock")
})

@Entity
@Table(name="stock_info")
class Stock{
	@Id
	//@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="stock_id")
	private int stockId;

	@Column(name="stock_name")
	private String stockName;

	@Column(name="stock_price")
	private int stockPrice;
	public Stock(int stockId, String stockName, int stockPrice) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.stockPrice = stockPrice;
	}
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public int getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(int stockPrice) {
		this.stockPrice = stockPrice;
	}

	@Override
	public String toString() {
		return "\nStock [stockId=" + stockId + ", stockName=" + stockName
				+ ", stockPrice=" + stockPrice + "]";
	}
}







