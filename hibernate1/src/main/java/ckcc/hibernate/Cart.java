package ckcc.hibernate;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	private List<Purchase> purchasedItems;
	private double discount;
	
	public Cart(double discount) {
		this.discount = discount;
		purchasedItems = new ArrayList<Purchase>();
	}
	
	public void addItem(Purchase item) {
		this.purchasedItems.add(item);
	}
	
	public void removeItem(Purchase item) {
		this.purchasedItems.remove(item);
	}
	
	public List<Purchase> getPurchasedItems(){
		return this.purchasedItems;
	}
	
	public double getDiscount() {
		return this.discount;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public double calculateSubTotal() {
		double subTotal = 0;
		for(Purchase pItem : this.getPurchasedItems()) {
			subTotal += pItem.getPrice();
		}
		return subTotal;
	}
	
	public double calculateTotal() {
		if(this.discount > 0) {
			return this.calculateSubTotal() - (this.calculateSubTotal() * this.discount);
		}
		return this.calculateSubTotal();
	}
	
	public String toString() {
		String itemList = "";
		for(Purchase pItem : this.getPurchasedItems()) {
			itemList += pItem.toString() + "\n";
		}
		return itemList;
	}

}
