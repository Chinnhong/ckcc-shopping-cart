package ckcc.hibernate.sample;

import ckcc.hibernate.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MainCls extends JFrame implements ActionListener{
	private JMenuBar menuBar;
	private JMenu mFile, mEmployee, mReport, mTax, mHelp;
	// For Menu File
	private JMenuItem mItemNew, mItemSave, mItemExit;
	// For Menu Employee Mgt
	private JMenuItem mItemNewEmp, mItemListEmp;
	// For Menu Report
	private JMenuItem mItemTaxReport;
	// For Menu Tax
	private JMenuItem mItemTaxRule, mItemTaxCalculator;
	// For Menu Help
	private JMenuItem mItemWelcome, mItemHelpContents, mItemCheckUpdate, mItemAbout;
	// JTree Employee Mgt
	private JTree jTreeEmp;
	private JTabbedPane jTab;
	ArrayList<Employee> listEmployee = new ArrayList<Employee>();

	//Emp information
	JTextField txtEmpID,txtFirstName,txtLastName, txtDOB,txtEmail,txtSearch,txtCalSalary,txtCalBenefit,txtCalExchange;
	JTextField txtid, txtname, txtprice, txtdes,txtmMinorChild,reportExchangeRate,txtqty,
				txtCalChildNumber ;
	JComboBox gender, cbFields;
	JRadioButton rbYes,rbNo,rbCalYes,rbCalNo;
	JButton btnSave,btnClear,btnCalClear,btnCalculate,btnSearch;
	JTable tbEmp,tbEmpTaxReport;
	JLabel resultTaxUSD,resultTaxRiel,resultNetUSD,resultNetRiel,empCount;
	DefaultTableModel tbModel,tbModelTaxReport;

	public MainCls() {

			/*
		 * Create Object Menu Items
		 * Create Object Menu
		 * Add items to Menu
		 */
		// Create Object Menu Item of File
		mItemNew = new JMenuItem("New");
		mItemSave = new JMenuItem("Save");
		mItemExit = new JMenuItem("Exit");
		// Create Object Menu File and add its items
		mFile = new JMenu("File");
		mFile.add(mItemNew);
		mFile.add(mItemSave);
		mFile.add(new JSeparator()); //Add line to Divide
		mFile.add(mItemExit);
		//=========================END MENU FILE ======================/
		// Create Object Menu Item of Employee Mgt
		mItemNewEmp = new JMenuItem("New Employee");
		mItemNewEmp.addActionListener(this);
		mItemListEmp = new JMenuItem("List Employee");
		mItemListEmp.addActionListener(this);
		// Create Object Menu Employee Mgt and add its items
		mEmployee = new JMenu("Employee Mgt");
		mEmployee.add(mItemNewEmp);
		mEmployee.add(mItemListEmp);
		//=========================END MENU EMPLOYEE =================/
		// Create Object Menu Item of Report
		mItemTaxReport = new JMenuItem("Employee Tax Report");
		mItemTaxReport.addActionListener(this);
		// Create Object Menu Report and add its items
		mReport = new JMenu("Reports");
		mReport.add(mItemTaxReport);
		//=========================END MENU REPORT ===================/
		// Create Object Menu Item of Tax Rule
		mItemTaxRule = new JMenuItem("Tax Rule 2018");
		mItemTaxRule.addActionListener(this);
		mItemTaxCalculator = new JMenuItem("Tax Calculator");
		mItemTaxCalculator.addActionListener(this);
		// Create Object Menu tax rule and add its items
		mTax = new JMenu("Tax Rule");
		mTax.add(mItemTaxRule);
		mTax.add(mItemTaxCalculator);
		//=========================END MENU TAX RULE ======================/
		// Create Object Menu Item of Help
		mItemWelcome = new JMenuItem("Welcome");
		mItemHelpContents = new JMenuItem("Help Contents");
		mItemCheckUpdate = new JMenuItem("Check Update");
		mItemAbout = new JMenuItem("About Employee Mgt");
		// Create Object Menu Help and add its items
		mHelp = new JMenu("Help");
		mHelp.add(mItemWelcome);
		mHelp.add(mItemHelpContents);
		mHelp.add(new JSeparator()); // Add Line to Divide
		mHelp.add(mItemCheckUpdate);
		mHelp.add(mItemAbout);
		//=========================END MENU HELP ======================/
		// Create Object Menu Bar and add its menus
		menuBar = new JMenuBar();
		menuBar.add(mFile); //Add File
		menuBar.add(mEmployee); //Add Employee Mgt
		menuBar.add(mReport); //Add Report
		menuBar.add(mTax); //Add Tax Rule
		menuBar.add(mHelp); //Add Help
		//========================END MENU BAR ======================/
		JTree leftJTree;
		//Add JTree into LeftPanel
		leftJTree = createJTree();
//		JPanel rightPanel = new JPanel();
//		rightPanel.add(new JLabel("Right"));
		jTab = new JTabbedPane();
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
										leftJTree, jTab);
		jsp.setDividerLocation(200);
		// Add Menu bar to Frame
		add(menuBar);
		add(jsp);
		setTitle("Tax Program v1.0");
		setJMenuBar(menuBar);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);                                      
	}
	private JPanel performOpenTaxCalculator() {
		JPanel taxPanel = new JPanel();
		// Create Group Box - Tax Calculator
		TitledBorder tBorderTaxCal = BorderFactory.createTitledBorder("EMPLOYEE TAX CALCULATOR");
		tBorderTaxCal.setTitleJustification(TitledBorder.CENTER);
		taxPanel.setBorder(tBorderTaxCal);

		//Block Calculator - Employee Info
		JPanel empInfo = new JPanel(new GridLayout(5,2,10,10));
		empInfo.add(new JLabel("Salary($) : "));
		empInfo.add(txtCalSalary = new JTextField(15));
		empInfo.add(new JLabel("Benefit($) : "));
		empInfo.add(txtCalBenefit = new JTextField(15));
		empInfo.add(new JLabel("Exchange Rate (1USD) : "));
		empInfo.add(txtCalExchange = new JTextField(15));
		//=================================================
		JPanel hasFamilyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hasFamilyPanel.add(rbCalYes = new JRadioButton("YES"));
		hasFamilyPanel.add(rbCalNo = new JRadioButton("NO", true));
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbCalYes);
		bg.add(rbCalNo);
		//=================================================
		empInfo.add(new JLabel("Has Spouse? : "));
		empInfo.add(hasFamilyPanel);
		empInfo.add(new JLabel("Minor Children : "));
		empInfo.add(txtCalChildNumber = new JTextField(15));
		
		//Create Block Calculator - Employee Info - FINAL
		JPanel empInfo_FINAL = new JPanel(new BorderLayout(10,10));
		empInfo_FINAL.add(new JLabel("Salary Tax Calculator"), BorderLayout.NORTH);
		empInfo_FINAL.add(new JSeparator(), BorderLayout.CENTER);
		empInfo_FINAL.add(empInfo, BorderLayout.SOUTH);
		
		//Create Panel Calculator Employee Info with Action Button
		JPanel empInfoButtonPanel = new JPanel(new BorderLayout(10,10));
		empInfoButtonPanel.add(empInfo_FINAL, BorderLayout.NORTH);
		empInfoButtonPanel.add(new JSeparator(), BorderLayout.CENTER);
		//=======================================================
		JPanel actionButtonPanel = new JPanel(new FlowLayout());
		actionButtonPanel.add(btnCalculate = new JButton("Calculate"));
		btnCalculate.addActionListener(this);
		actionButtonPanel.add(btnCalClear = new JButton("Clear"));
		//=======================================================
		empInfoButtonPanel.add(actionButtonPanel, BorderLayout.SOUTH);
		
		//Create Tax Calculator Result
		JPanel taxCalResultPanel = new JPanel(new GridLayout(4,2,10,10));
		taxCalResultPanel.add(new JLabel("Tax on Salary : ", SwingConstants.CENTER));
		taxCalResultPanel.add(resultTaxRiel = new JLabel("0.0 Riel", SwingConstants.CENTER));
		resultTaxRiel.setForeground(Color.RED);
		taxCalResultPanel.add(new JLabel(""));
		taxCalResultPanel.add(resultTaxUSD = new JLabel("0.0 USD", SwingConstants.CENTER));
		resultTaxUSD.setForeground(Color.RED);
		taxCalResultPanel.add(new JLabel("Net Salary : ", SwingConstants.CENTER));
		taxCalResultPanel.add(resultNetRiel = new JLabel("0.0 Riel", SwingConstants.CENTER));
		resultNetRiel.setForeground(Color.MAGENTA);
		taxCalResultPanel.add(new JLabel(""));
		taxCalResultPanel.add(resultNetUSD = new JLabel("0.0 USD", SwingConstants.CENTER));
		resultNetUSD.setForeground(Color.MAGENTA);
		
		//Panel Calculator Final
		JPanel calculatorFINAL = new JPanel(new BorderLayout(10,10));
		calculatorFINAL.add(empInfoButtonPanel, BorderLayout.NORTH);
		calculatorFINAL.add(taxCalResultPanel, BorderLayout.CENTER);
		
		taxPanel.add(calculatorFINAL);
		
		jTab.addTab("Tax Calculator", taxPanel);
		jTab.setSelectedComponent(taxPanel);
		return taxPanel;
	}
	private void performOpenTaxReport() {
		JPanel empTaxReport = new JPanel(new BorderLayout(10,10));
		JPanel condPanel = new JPanel(new BorderLayout(10,10));
		condPanel.add(new JSeparator(), BorderLayout.CENTER);
		JPanel empListPanel = new JPanel(new BorderLayout(15,15));
		empListPanel.add(condPanel, BorderLayout.NORTH);
		tbModelTaxReport = new DefaultTableModel();
		tbModelTaxReport.addColumn("ID");
		tbModelTaxReport.addColumn("Product Name");
		tbModelTaxReport.addColumn("Description");
		tbModelTaxReport.addColumn("Price");
		tbModelTaxReport.addColumn("Quantity");
		tbEmpTaxReport = new JTable(tbModelTaxReport);
	    empListPanel.add(new JScrollPane(tbEmpTaxReport), BorderLayout.CENTER);
	    empTaxReport.add(empListPanel);
		jTab.addTab("Employee Tax Report", empTaxReport);
		jTab.setSelectedComponent(empTaxReport);
	}
	private JPanel performOpenEmployee_ListEmployee(JPanel empList) {
		TitledBorder tBorderListEmp = BorderFactory.createTitledBorder("List Emp");
		tBorderListEmp.setTitleJustification(TitledBorder.CENTER);
		empList.setBorder(tBorderListEmp);
		//create search area and count emp
		JPanel searchAndCountPanel = new JPanel(new GridLayout(1, 2,10,10));
		//add search area panel

		JPanel searchPanel = new JPanel(new FlowLayout());
		searchPanel.add(new JLabel("Fields:"));
		String [] searchFieldText = {"ID","Name"};

		searchPanel.add(cbFields = new JComboBox(searchFieldText));
		searchPanel.add(new JLabel("Search"));
		searchPanel.add(txtSearch= new JTextField(20));
		btnSearch= new JButton("Search");
		btnSearch.addActionListener(this);
		searchPanel.add(btnSearch);

//		add search area
		searchAndCountPanel.add(searchPanel);
		// add count emp
		// searchAndCountPanel.add(empCount);
		
		//table Emp
		tbModel = new DefaultTableModel();
		tbModel.addColumn("ID");
		tbModel.addColumn("Name");
		tbModel.addColumn("Description");
		tbModel.addColumn("Price");
		tbModel.addColumn("Qty");

		
		tbEmp = new JTable(tbModel);
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Product.class)
				.buildSessionFactory();
		Session sessionObj = factory.getCurrentSession();
		 List<Product> tmp;
		try {
			sessionObj.beginTransaction();
			tmp =(ArrayList) sessionObj.createQuery("from Product").getResultList();
			sessionObj.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		for( Product p : tmp){
			tbModel.addRow(new Object[]{p.getId(),p.getName(),p.getDes(),p.getPrice(),p.getQtyInStock()});
		}
		JPanel searchAndListPanel = new JPanel(new BorderLayout(10,10));
		searchAndListPanel.add(searchAndCountPanel,BorderLayout.NORTH);
		searchAndListPanel.add(new JScrollPane(tbEmp),BorderLayout.CENTER);
		
		JPanel blockEmpList_Final = new JPanel(new BorderLayout(10,10));
	//	blockEmpList_Final.add(new JLabel("List of Your Emp"),BorderLayout.NORTH);
	//	blockEmpList_Final.add(new JSeparator(),BorderLayout.CENTER);
		blockEmpList_Final.add(new JScrollPane(searchAndListPanel), BorderLayout.SOUTH);
		
		
		return blockEmpList_Final;
	
	}
	private void performOpenEmp() {
		JPanel empPanel = new JPanel(new BorderLayout(10,10));
		JPanel empList = new JPanel(new BorderLayout(10,10));
		JPanel empNew = new JPanel(new BorderLayout(10,10));
		empNew.add(performOpenEmployee_NewEmployee(empNew),BorderLayout.NORTH);
		empList.add(performOpenEmployee_ListEmployee(empList),BorderLayout.NORTH);
		empPanel.add(new JScrollPane(empList),BorderLayout.CENTER);
		empPanel.add(new JScrollPane(empNew),BorderLayout.EAST);
		jTab.addTab("Emp List",empPanel);
		jTab.setSelectedComponent(empPanel);
	}
	private JPanel performOpenEmployee_SectionComInfo() {
		JPanel blockComInfo = new JPanel(new GridLayout(5, 2,10,10));
		blockComInfo.add(new JLabel("ProID: "));
		blockComInfo.add(txtid = new JTextField());
		
		blockComInfo.add(new JLabel("Name : "));
		blockComInfo.add(txtname = new JTextField());
		
		blockComInfo.add(new JLabel("Des : "));
		blockComInfo.add(txtdes = new JTextField());
		
		blockComInfo.add(new JLabel("price : "));
		blockComInfo.add(txtprice = new JTextField());

		blockComInfo.add(new JLabel("Qty : "));
		blockComInfo.add(txtqty= new JTextField());
		
		JPanel blockComInfo_Final = new JPanel(new BorderLayout(10,10));
		blockComInfo_Final.add(new JLabel("Company Information"),BorderLayout.NORTH);
		blockComInfo_Final.add(new JSeparator(),BorderLayout.CENTER);
		blockComInfo_Final.add(blockComInfo,BorderLayout.SOUTH);
		return blockComInfo_Final;
	}


	private JPanel performOpenEmployee_NewEmployee(JPanel empNew) {
		TitledBorder tBorderNewEmp = BorderFactory.createTitledBorder("Create New Customer");
		tBorderNewEmp.setTitleJustification(TitledBorder.CENTER);
		empNew.setBorder(tBorderNewEmp);
		
		JPanel blockAllInfo = new JPanel(new BorderLayout(10,10));

		blockAllInfo.add(performOpenEmployee_SectionComInfo(),BorderLayout.NORTH);

		
		JPanel blockAllInfo_Final = new JPanel(new BorderLayout(10,10));
		blockAllInfo_Final.add(blockAllInfo,BorderLayout.NORTH);
		blockAllInfo_Final.add(new JSeparator(),BorderLayout.CENTER);
		JPanel actionBtnPanel = new JPanel(new FlowLayout());
		actionBtnPanel.add(btnSave = new JButton("Save"));
		btnSave.addActionListener(this);
		actionBtnPanel.add(btnClear= new JButton("Clear"));
		btnClear.addActionListener(this);
		blockAllInfo_Final.add(actionBtnPanel,BorderLayout.SOUTH);
		
		return blockAllInfo_Final;
	}
	
	
	private void performOpenTaxRule2018() {
		JLabel lbl1 = new JLabel("",SwingConstants.CENTER);
		ImageIcon icon1 = new ImageIcon("image/tax.jpg");
		lbl1.setIcon(icon1);
		
		JLabel lbl2 = new JLabel("",SwingConstants.CENTER);
		ImageIcon icon2 = new ImageIcon("image/tax.jpg");
		lbl2.setIcon(icon2);
		
		JLabel lbl3 = new JLabel("",SwingConstants.CENTER);
		ImageIcon icon3 = new ImageIcon("image/tax.jpg");
		lbl3.setIcon(icon3);
		
		JLabel lbl4 = new JLabel("",SwingConstants.CENTER);
		ImageIcon icon4 = new ImageIcon("image/tax.jpg");
		lbl4.setIcon(icon4);
		
		
		JPanel jPanelIcon = new  JPanel(new BorderLayout(10,10));
		jPanelIcon.add(lbl1, BorderLayout.NORTH);
		jPanelIcon.add(lbl2, BorderLayout.CENTER);
		jPanelIcon.add(lbl3, BorderLayout.SOUTH);
		jPanelIcon.add(lbl4, BorderLayout.SOUTH);
		
		
		JScrollPane jspPicture = new JScrollPane(jPanelIcon);
		jspPicture.getVerticalScrollBar().setUnitIncrement(80);
		jTab.addTab("Emp Tax Rule", jspPicture);
		jTab.setSelectedComponent(jspPicture);
	}

	
	private JTree createJTree() {
		JTree leftJTree = new JTree();
		//Create Tree Root Node
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root Node");
		//Create Tree Node Employee Mgt
		DefaultMutableTreeNode nodeEmpMgt = new DefaultMutableTreeNode("Employee Mgt");
		//Create Tree Node Add Employee
		DefaultMutableTreeNode nodeAddEmp = new DefaultMutableTreeNode("Add Employee");
		//Create Tree Node List Employee
		DefaultMutableTreeNode nodeListEmp = new DefaultMutableTreeNode("List Employee");
		nodeEmpMgt.add(nodeAddEmp);
		nodeEmpMgt.add(nodeListEmp);
		//===========================END TREE NODE OF EMPLOYEE LIST ====================/
		
		//Create Tree Node Report
		DefaultMutableTreeNode nodeReport = new DefaultMutableTreeNode("Report");
		//Create Tree Node Employee Tax Report
		DefaultMutableTreeNode nodeEmpTaxReport = new DefaultMutableTreeNode("Employee Tax Report");
		nodeReport.add(nodeEmpTaxReport);
		//==========================END TREE NODE OF REPORT ============================/
		
		//Create Tree Node Tax Rule
		DefaultMutableTreeNode nodeTaxRule = new DefaultMutableTreeNode("Tax Rule");
		//Create Tree Node Tax Rule 2018
		DefaultMutableTreeNode nodeTaxRule2018 = new DefaultMutableTreeNode("Tax Rule 2018");
		//Create Tree Node Tax Calculator
		DefaultMutableTreeNode nodeTaxCalculator = new DefaultMutableTreeNode("Tax Calculator");
		nodeTaxRule.add(nodeTaxRule2018);
		nodeTaxRule.add(nodeTaxCalculator);
		//==========================END TREE NODE OF TAX RULE ============================/
		
		
		rootNode.add(nodeEmpMgt);
		rootNode.add(nodeReport);
		rootNode.add(nodeTaxRule);
		
		//Create Object of JTree Employee Mgt
		jTreeEmp = new JTree(rootNode);
		
		jTreeEmp.setRootVisible(false);
		jTreeEmp.setRowHeight(25);
		jTreeEmp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jTreeEmp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//Find selected node if tree 
				int selectedNode  = jTreeEmp.getRowForLocation(e.getX(), e.getY());
				//Condition when mouse clicked on a specific node
				if(selectedNode!=-1) {
					//when mouse click is double clicked
					if(e.getClickCount()==2) {
						//Get for whole tree path
						TreePath selectedTreePath = jTreeEmp.getPathForLocation(e.getX(), e.getY());
						//Get last selected path
						String path = selectedTreePath.getLastPathComponent().toString();
						if (path.equals("Add Employee")) { performOpenEmp();}
						else if (path.equals("List Employee")) {performOpenEmp();}
						else if (path.equals("Employee Tax Report")) { performOpenTaxReport();}
						else if (path.equals("Tax Rule 2018")) { performOpenTaxRule2018();}
						else if (path.equals("Tax Calculator")) {performOpenTaxCalculator();}
					}
				}
			}
		});
		//Expand all three nodes
		for(int i=0;i<jTreeEmp.getRowCount();i++) {
			jTreeEmp.expandRow(i);
		}
		
		//Add tree to panel
		//leftJTree.add(jTreeEmp);
		
		return jTreeEmp;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==mItemNewEmp) {
			performOpenEmp();

		}
		else if(e.getSource()==mItemListEmp)
		{
				performOpenEmp();

		}
		else if(e.getSource()==mItemTaxRule) {
			performOpenTaxRule2018();			
		}	
		else if (e.getSource()==mItemTaxReport)
			 performOpenTaxReport();	
		else if(e.getSource()==mItemTaxCalculator)	
			performOpenTaxCalculator();
		
		if(e.getSource()==btnSave) {

			String id = txtid.getText();
			String name = txtname.getText();
			String des = txtdes.getText();
			Double price = Double.parseDouble(txtprice.getText());
			Double qty = Double.parseDouble(txtqty.getText());

			SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Product.class)
					.buildSessionFactory();
			Session sessionObj = factory.getCurrentSession();

//			Employee empObj = new Employee(empID, firstName, lastName, gender1, email, dob, department, position, salary, benefit, hasSpouse, minorChild)
//;			//step 2: add data into JTable for emp list
//			String [] data= {empID, firstName, lastName,
//					gender1+"", email, dob, department, position, salary+"", benefit+"", hasSpouse+"", minorChild+""
//			};
			Product p = new Product(id,name,des,price,qty);
			String [] data ={id,name,des,price+"",qty+""};
			tbModel.addRow(new Object[]{id,name,des,price,qty});


			//tbModel.addRow(data);
			//step 3: add object into arraylist for reporting


			try {
				System.out.println("ok");
				sessionObj.beginTransaction();
				sessionObj.save(p);
				sessionObj.getTransaction().commit();
				System.out.println("Insert successfully!!!");
			}
			finally {
				factory.close();
			}
			txtid.setText("");
			txtname.setText("");
			txtdes.setText("");
			txtprice.setText("");
			txtqty.setText("");

		}
		if(e.getSource()==btnCalculate) {
			String empID= "0";
			String firstName = "0";
			String lastName = "0";
			boolean gender1 = false;
			String email = "0";
			String dob = "0";
			String department ="0";
			String position = "0";
//			Employee empObj = new Employee();
			Double salary = Double.parseDouble(txtCalSalary.getText());
			Double benefit = Double.parseDouble(txtCalBenefit.getText());
			boolean hasSpouse =rbCalYes.isSelected();
			int minorChild = hasSpouse? Integer.parseInt(txtCalChildNumber.getText()):0;
			//Exchange rate
			double exchangeRate = Double.parseDouble(txtCalExchange.getText());			
			Employee empObj1 = new Employee(empID, firstName, lastName, gender1, email, dob, department, position, salary, benefit, hasSpouse, minorChild);
		
;			double taxRiel = empObj1.calculateTax(exchangeRate);
			double taxUSD = taxRiel/exchangeRate;
			double netSalaryRiel = ((salary+benefit)*exchangeRate)-taxRiel;
			double netSalaryUSD = netSalaryRiel/exchangeRate  ;
			resultNetRiel.setText("Riel"+String.format("%.2f", netSalaryRiel));
			resultNetUSD.setText("USD"+String.format("%.2f", netSalaryUSD));
			resultTaxRiel.setText("Riel"+String.format("%.2f", taxRiel));
			resultTaxUSD.setText("USD"+String.format("%.2f", taxUSD));
			
			
		}
		if(e.getSource()==btnSearch){
			searchEmp();
		}
		
	}
	private void printEmpList (){
		for(Employee emp : listEmployee){
			tbModel.addRow(emp.toStringData());

		}
	}
	public void searchEmp(){
		String searchText= txtSearch.getText();
		String filter = cbFields.getSelectedItem().toString();
		System.out.println("total row ="+tbModel.getRowCount());
		for( int i=0;i<tbModel.getRowCount();i++){
			tbModel.removeRow(i);
		}
		System.out.println("row after delete: "+tbModel.getRowCount());
		if(filter=="ID")
			for(Employee emp : listEmployee ){
				if(emp.getEmpID().equals(searchText)) {
					tbModel.addRow(emp.toStringData());
				}
			}
			else System.out.println("filter not found");
	}

}
