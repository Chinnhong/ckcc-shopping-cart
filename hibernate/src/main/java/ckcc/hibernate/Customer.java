package ckcc.hibernate;

public class Customer {
	
	private String id;
	private String name;
	private String email;
	private String shippingAddress;
	private String billingAddress;
	private Cart shoppingCart;
	
	public Customer(String id, String name, String email, String shippingAddress, String billingAddress) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.shoppingCart = null;
	}
	
	public void placeOrder(Cart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public void cancelOrder() {
		this.shoppingCart = null;
	}
	
	public String toString() {
		String customerInfo = "";
		customerInfo += "[ ID		: " + this.id + " ]" + "\n";
		customerInfo += "[ Name		: " + this.name + " ]" + "\n";
		customerInfo += "[ Email	: " + this.email + " ]" + "\n";
		customerInfo += "[ Ship To	: " + this.shippingAddress + " ]" + "\n";
		customerInfo += "[ Bill To	: " + this.billingAddress + " ]";
		return customerInfo;
	}
	
	public String checkOut() {
		String shoppingCartOutput = "";
		shoppingCartOutput += "--------------- Your Purchase Invoice-------------" + "\n";
		shoppingCartOutput += this.toString() + "\n";
		shoppingCartOutput += "--------------------------------------------------" + "\n";
		if(this.shoppingCart == null) {
			return "Your shopping cart is empty!!!";
		}
		shoppingCartOutput += "[No\tName\t\tQty\tUnitPrice\tDiscount\tPrice]" + "\n";
		shoppingCartOutput += "---------------------------------------------------------" + "\n";
		shoppingCartOutput += this.shoppingCart.toString() + "\n";
		shoppingCartOutput += "---------------------------------------------------------" + "\n";
		shoppingCartOutput += "Sub Total : " + this.shoppingCart.calculateSubTotal() + "\n";
		shoppingCartOutput += "Discount : " + (this.shoppingCart.getDiscount() * 100) + "%" + "\n";
		shoppingCartOutput += "Total : " + String.format("%.2f",this.shoppingCart.calculateTotal()) + "\n";
		shoppingCartOutput += "---------------------------------------------------------" + "\n";
		return shoppingCartOutput;
	}

}
