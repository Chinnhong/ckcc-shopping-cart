package ckcc.hibernate.sample;

import java.util.ArrayList;

public class Employee {
	//data member of emp info
	
	private String empID;
	private String firstName;
	private String lastName;
	private boolean gender;
	private String email;
	private String dob;
	//data member of company info
	private String department;
	private String position;
	private double salary;
	private double benefit;
	//data member of family info
	private boolean hasSpouse;
	private int minorChild;
	public Employee() {}
	public Employee(String empID, String firstName, String lastName, boolean gender, String email, String dob,
			String department, String position, double salary, double benefit, boolean hasSpouse, int minorChild) {
		
		this.empID = empID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.dob = dob;
		this.department = department;
		this.position = position;
		this.salary = salary;
		this.benefit = benefit;
		this.hasSpouse = hasSpouse;
		this.minorChild = minorChild;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String [] toStringData() {
		String genderStr = gender? "Male":"Female";
		String hasSpouseStr = hasSpouse? "Yes":"No";
		String[] data = {empID, firstName, lastName,
				genderStr, email, dob, department, position, salary+"", benefit+"", hasSpouseStr, minorChild+""
		};
		return data;
	}
	private double exchangeSalary (double exchangeRate) {
		return this.salary*exchangeRate;
	}
	private double moneyForFamily() {
		if(this.hasSpouse) {
			return 150000*(this.minorChild+1);
		}
		else return 0;
	}
	private double getTaxRate(double salaryForTax) {
		if (salaryForTax>12500000)
			return 0.2;
		else if(salaryForTax>8500000)
			return 0.15;
		else if(salaryForTax>2000000)
			return 0.1;
		else if (salaryForTax>1200000)
			return 0.05;
		else return 0;
	}
	private double getBiasMoney(double salaryForTax) {
		if (salaryForTax>12500000)
			return 1210000;
		else if(salaryForTax>8500000)
			return 585000;
		else if(salaryForTax>2000000)
			return 160000;
		else if (salaryForTax>1200000)
			return 60000;
		else return 0;
	}
	private double taxBenefit (double exchangeRate) {
	return benefit*0.2*exchangeRate;
	}
	
	
	public double calculateTax(double exchangeRate) {
		//Exchange USD to Riel
			double rielSalary = this.exchangeSalary(exchangeRate);
		//Family tax
			double moneyFamily = this.moneyForFamily();
		//Find salary for tax
			double salaryForTax = rielSalary-moneyFamily;
		//Get percentage for tax based on tax rule
			double taxRate = this.getTaxRate(salaryForTax);
		// Get Bais Money based on tax rule
			double biasMoney = this.getBiasMoney(salaryForTax);
		// Get tax benefit
			double taxBenefit = this.taxBenefit(exchangeRate);
		//calculate Tax
			double finalTax = (salaryForTax*taxRate-biasMoney)+taxBenefit;
		//return tax
		
		return finalTax;
	}
}