package ckcc.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		Session sessionObj = factory.getCurrentSession();
		Scanner sc = new Scanner(System.in);
		HashMap<String, Product> productList = new HashMap<String, Product>();
		Cart myCart = new Cart(0);

		String menuNumber = "";
		do {
			// Main menus of program
			System.out.println("---- Program Menus ----");
			System.out.println("| 1. New Product      |");
			System.out.println("| 2. List of Products |");
			System.out.println("| 3. Go to Shopping   |");
			System.out.println("| 4. My Shopping Cart |");
			System.out.println("| 5. Check Out        |");
			System.out.println("-----------------------");
			int mainSelectedMenu = sc.nextInt();
			sc.nextLine();
			switch(mainSelectedMenu) {
				// New Product
				case 1 :
					String answer = "";
					do {
						System.out.println("----------------- Create New Product --------------------\n");
						System.out.println("Enter your product ID: ");
						String productID = sc.nextLine();
						System.out.println("Enter your product name: ");
						String productName = sc.nextLine();
						System.out.println("Enter your product description: ");
						String productDes = sc.nextLine();
						System.out.println("Enter your product price: ");
						double productPrice = sc.nextDouble();
						sc.nextLine();
						System.out.println("Enter your product qty: ");
						double productQty = sc.nextDouble();
						sc.nextLine();
						// Create object of product
						Product p = new Product(productID, productName, productDes, productPrice,productQty);
						productList.put(productID, p);
						//database

						try {
							sessionObj.beginTransaction();
							sessionObj.save(p);
							sessionObj.getTransaction().commit();
							System.out.println("Insert successfully!!!");
						}
						finally {
							factory.close();
						}
						//database
						System.out.println("---------------------------------------------------");
						System.out.println("Do you want to add more product? (Y/N)");
						answer = sc.nextLine();
					}while(answer.equalsIgnoreCase("Y"));
					break;
				// List of Products
				case 2 :
					System.out.println("----------------- Product List --------------------\n");
					System.out.println("[ID\tName\t\tPrice\tQty\tDescription]");
					System.out.println("---------------------------------------------------");
					for(Product pro : productList.values()){
						System.out.println(pro.toString());
					}
					System.out.println("---------------------------------------------------");
					break;
				// Go to Shopping
				case 3 :
					String continueShopping = "";
					int orderNo = 1;
					System.out.println("----------------- Let's go shopping products you want --------------------\n");
					do {
						System.out.println("Enter product's code you want to buy: ");
						String productCode = sc.nextLine();
						try {
							sessionObj.beginTransaction();
							Product pro = sessionObj.get(Product.class,productCode);
							sessionObj.getTransaction().commit();

						// Check product code is exist in collection
						if(true) {
							Product prod = productList.get(productCode);
							System.out.println("Enter product's QTY you want to buy: ");
							double productQty = sc.nextDouble();
							sc.nextLine();
							if(prod.isValidStock(productQty)) {
								System.out.println("Enter percentage discount for this product if you have? ");
								double discount = sc.nextDouble();
								sc.nextLine();
								// Create object of purchase item
								Purchase purchaseObj = new Purchase(orderNo + "", prod, productQty, discount);
								myCart.addItem(purchaseObj);
								orderNo++;
							}else {
								System.out.println("Not enough stock!! product with code " + productCode + " has only " + prod.getQtyInStock() + " in stock.");
							}
						}else {
							System.out.println("Product with code " + productCode + " is not found in list to purchase.\n");
						}
						}
						finally {
							factory.close();
						}
						System.out.println("---------------------------------------------------");
						System.out.println("Do you want to shop more product? (Y/N)");
						continueShopping = sc.nextLine();						
					}while(continueShopping.equalsIgnoreCase("Y"));
					break;
				// My Shopping Cart
				case 4 :
					System.out.println("----------------- My Shopping Cart --------------------\n");
					System.out.println("[No\tName\t\tQty\tUnitPrice\tDiscount\tPrice]");
					System.out.println("---------------------------------------------------------");
					System.out.println(myCart.toString());
					System.out.println("---------------------------------------------------------");
					break;
				// Check Out
				case 5 :
					System.out.println("--------- Before Check out, Give me your information ---------\n");
					System.out.println("Enter your identification number: ");
					String id = sc.nextLine();
					System.out.println("Enter your name: ");
					String name = sc.nextLine();
					System.out.println("Enter your email: ");
					String email = sc.nextLine();
					System.out.println("Enter your shipping address: ");
					String shipAddress = sc.nextLine();
					System.out.println("Enter your billing address: ");
					String billAddress = sc.nextLine();
					System.out.println("--------- Do you have discount card ---------\n");
					System.out.println("Enter percentage on your discount card: ");
					double cardDiscount = sc.nextDouble();
					sc.nextLine();
					// Set discount on card
					myCart.setDiscount(cardDiscount);
					// Create customer object
					Customer newCustomer = new Customer(id, name, email, shipAddress, billAddress);
					// Place order to customer
					newCustomer.placeOrder(myCart);
					System.out.println("\n\n");
					System.out.println(newCustomer.checkOut());
					break;
			}
			System.out.println("Do you want to continue to Main Menu? (Y/N)");
			menuNumber = sc.next();
		}while(menuNumber.equalsIgnoreCase("Y"));
	}

}
