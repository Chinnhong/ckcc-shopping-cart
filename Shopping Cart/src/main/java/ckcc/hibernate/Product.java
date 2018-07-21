package ckcc.hibernate;
import javax.persistence.*;
@Entity
@Table(name="tblproduct")
public class Product {
	@Id
	@Column(name="pro_id")
	private String id;
	@Column(name="pro_name")
	private String name;
	@Column(name="pro_des")
	private String description;
	@Column(name="pro_price")
	private double price;
	@Column(name="pro_qty")
	private double qtyInStock;
	public Product(){}
	public Product(String id, String name, String description, double price, double qtyInStock) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.qtyInStock = qtyInStock;
	}
	public String getId(){return this.id;}

	public String getDes(){return this.description;}
	public double getPrice() {
		return this.price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getQtyInStock() {
		return this.qtyInStock;
	}
	
	public boolean isValidStock(double qty) {
		if(qty > this.qtyInStock) return false;
		return true;
	}
	
	public String toString() {
		return "[" + this.id + "\t'" + this.name + "'\t" + this.price + "\t" + this.qtyInStock + "\t'" + this.description + "']" + "\n"; 
	}
	
}
