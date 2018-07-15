package ckcc.hibernate;

import ckcc.hibernate.sample.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PurchaseUI extends JFrame implements ActionListener{
    public static void main(String[] args) {
        new PurchaseUI();

    }

    private JMenuBar menuBar;
    private JMenu mStore, mPurchase, mReport;
    // For Menu File
    private JMenuItem mAddNewProduct, mSearchProduct, mProductList,mExit;
    // For Menu Employee Mgt
    private JMenuItem mGoShopping;
    // For Menu Report
    private JMenuItem mSaleReport;

    private DefaultTableModel tbProduct;

    private JTabbedPane jTab;

    private JTable tbProducts;
    private JTree jTreeEmp;

    //Add new Product
    private JTextField txtid, txtname,txtdes,txtprice,txtqty,txtSearch;
    private JButton btnSearch,btnSave,btnClear;
    private DefaultTableModel tbModel ;
    private JTable tbAddNewProducts;
    private JComboBox cbFields;

    public PurchaseUI() {

        // Create Object Menu Item of File
        mAddNewProduct = new JMenuItem("Add New Product");
        mAddNewProduct.addActionListener(this);
        mSearchProduct = new JMenuItem("Search Product");
        mSearchProduct.addActionListener(this);
        mProductList = new JMenuItem("Products List");
        mProductList.addActionListener(this);
        mExit = new JMenuItem("Exit");
        mExit.addActionListener(this);
        // Create Object Menu File and add its items
        mStore = new JMenu("Store");
        mStore.add(mAddNewProduct);
        mStore.add(mSearchProduct);
        mStore.add(mProductList);
        mStore.add(new JSeparator()); //Add line to Divide
        mStore.add(mExit);
        //=========================END MENU FILE ======================/
        // Create Object Menu Item of Employee Mgt
        mGoShopping = new JMenuItem("Go Shopping");
        mGoShopping.addActionListener(this);
        mPurchase = new JMenu("Purchase");
        mPurchase.add(mGoShopping);
        //=========================END MENU EMPLOYEE =================/
        // Create Object Menu Item of Report
        mSaleReport = new JMenuItem("Sale Reports");
        mSaleReport.addActionListener(this);

//        mSaleReport.addActionListener(this);
        // Create Object Menu Report and add its items
        mReport = new JMenu("Reports");
        mReport.add(mSaleReport);
        //=========================END MENU REPORT ===================/

        // Create Object Menu Bar and add its menus
        menuBar = new JMenuBar();
        menuBar.add(mStore); //Add File
        menuBar.add(mPurchase); //Add Employee Mgt
        menuBar.add(mReport); //Add Report
        //========================END MENU BAR ======================/

        add(menuBar);
        JTree leftJTree;
        //Add JTree into LeftPanel
        leftJTree = createJTree();

        jTab = new JTabbedPane();
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
                leftJTree, jTab);
        jsp.setDividerLocation(200);
        // Add Menu bar to Frame
        add(menuBar);
        add(jsp);

        setTitle("Shopping Cart v1.0");
        setJMenuBar(menuBar);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
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

        //Expand all three nodes
        for(int i=0;i<jTreeEmp.getRowCount();i++) {
            jTreeEmp.expandRow(i);
        }

        return jTreeEmp;
    }
    //Product List
    public void performOpenProductList() {
        JPanel productList = new JPanel(new BorderLayout(10,10));
        JPanel productListPanel = new JPanel(new BorderLayout(15,15));
        tbProduct = new DefaultTableModel();
        tbProduct.addColumn("ID");
        tbProduct.addColumn("Product Name");
        tbProduct.addColumn("Description");
        tbProduct.addColumn("Price");
        tbProduct.addColumn("Quantity");
        tbProducts = new JTable(tbProduct);


        for( Product p : getProducts()){
            tbProduct.addRow(new Object[]{p.getId(),p.getName(),p.getDes(),p.getPrice(),p.getQtyInStock()});
        }

        productListPanel.add(new JScrollPane(tbProducts), BorderLayout.CENTER);
        productList.add(productListPanel);
        jTab.addTab("Product List", productList);
        jTab.setSelectedComponent(productList);
    }
    private List<Product> getProducts(){
        List<Product> tmp;
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
        Session sessionObj = factory.getCurrentSession();
        try {
            sessionObj.beginTransaction();
             tmp =(ArrayList) sessionObj.createQuery("from Product").getResultList();
            sessionObj.getTransaction().commit();
        }
        finally {
            factory.close();
        }
        return tmp;

    }
    //End Product List

    //Add New Product
    private void performAddNewItem() {
        JPanel productPanel = new JPanel(new BorderLayout(10,10));
        JPanel productList = new JPanel(new BorderLayout(10,10));
        JPanel productNew = new JPanel(new BorderLayout(10,10));
        productNew.add(performAddNewProduct(productNew),BorderLayout.NORTH);
        productList.add(performOpenNewProductsList(productList),BorderLayout.NORTH);
        productPanel.add(new JScrollPane(productList),BorderLayout.CENTER);
        productPanel.add(new JScrollPane(productNew),BorderLayout.EAST);
        jTab.addTab("Emp List",productPanel);
        jTab.setSelectedComponent(productPanel);
    }
    private JPanel performOpenNewProductsList(JPanel empList) {
        TitledBorder tBorderListNewProducts = BorderFactory.createTitledBorder("List Emp");
        tBorderListNewProducts.setTitleJustification(TitledBorder.CENTER);
        empList.setBorder(tBorderListNewProducts);

        tbModel = new DefaultTableModel();
        tbModel.addColumn("ID");
        tbModel.addColumn("Name");
        tbModel.addColumn("Description");
        tbModel.addColumn("Price");
        tbModel.addColumn("Qty");
        tbAddNewProducts = new JTable(tbModel);

        JPanel listProductsPanel = new JPanel(new BorderLayout(10,10));
        listProductsPanel.add(new JScrollPane(tbAddNewProducts),BorderLayout.CENTER);
        JPanel blockProductsListFinal = new JPanel(new BorderLayout(10,10));
        blockProductsListFinal.add(new JScrollPane(listProductsPanel), BorderLayout.SOUTH);
        return blockProductsListFinal;

    }
    private JPanel addNewProduct() {
        JPanel blockAddNewProduct = new JPanel(new GridLayout(5, 2,10,10));
        blockAddNewProduct.add(new JLabel("ProID: "));
        blockAddNewProduct.add(txtid = new JTextField());

        blockAddNewProduct.add(new JLabel("Name : "));
        blockAddNewProduct.add(txtname = new JTextField());

        blockAddNewProduct.add(new JLabel("Des : "));
        blockAddNewProduct.add(txtdes = new JTextField());

        blockAddNewProduct.add(new JLabel("price : "));
        blockAddNewProduct.add(txtprice = new JTextField());

        blockAddNewProduct.add(new JLabel("Qty : "));
        blockAddNewProduct.add(txtqty= new JTextField());

        JPanel blockAddNewProductFinal = new JPanel(new BorderLayout(10,10));
        blockAddNewProductFinal.add(new JSeparator(),BorderLayout.NORTH);
        blockAddNewProductFinal.add(blockAddNewProduct,BorderLayout.CENTER);
        return blockAddNewProductFinal;
    }
    private JPanel performAddNewProduct(JPanel newProduct) {
        TitledBorder tBorderNewProduct = BorderFactory.createTitledBorder("Create New Product");
        tBorderNewProduct.setTitleJustification(TitledBorder.CENTER);
        newProduct.setBorder(tBorderNewProduct);

        JPanel blockAddNew = new JPanel(new BorderLayout(10,10));

        blockAddNew.add(addNewProduct(),BorderLayout.NORTH);

        JPanel blockAddNewFinal = new JPanel(new BorderLayout(10,10));
        blockAddNewFinal.add(blockAddNew,BorderLayout.NORTH);
        blockAddNewFinal.add(new JSeparator(),BorderLayout.CENTER);
        JPanel actionBtnPanel = new JPanel(new FlowLayout());
        actionBtnPanel.add(btnSave = new JButton("Save"));
        btnSave.addActionListener(this);
        actionBtnPanel.add(btnClear= new JButton("Clear"));
        btnClear.addActionListener(this);
        blockAddNewFinal.add(actionBtnPanel,BorderLayout.SOUTH);

        return blockAddNewFinal;
    }
    //End Add New Product
    //Search Product
    private void performSearchProduct(){
        JPanel searchAndCountPanel = new JPanel(new GridLayout(1, 2,10,10));
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



        ////////////////////////////////////////////////////////////
        JPanel empTaxReport = new JPanel(new BorderLayout(10,10));
        JPanel condPanel = new JPanel(new BorderLayout(10,10));
        condPanel.add(new JSeparator(), BorderLayout.CENTER);
        JPanel empListPanel = new JPanel(new BorderLayout(15,15));
        empListPanel.add(condPanel, BorderLayout.NORTH);
        tbProduct = new DefaultTableModel();
        tbProduct.addColumn("ID");
        tbProduct.addColumn("Product Name");
        tbProduct.addColumn("Description");
        tbProduct.addColumn("Price");
        tbProduct.addColumn("Quantity");
        tbProducts = new JTable(tbProduct);



        empListPanel.add(searchAndCountPanel,BorderLayout.NORTH);
        empListPanel.add(new JScrollPane(tbProducts), BorderLayout.CENTER);

        empTaxReport.add(empListPanel);
        jTab.addTab("Search Product", empTaxReport);
        jTab.setSelectedComponent(empTaxReport);
    }
    //End Search Product
    //Go Shopping
    public void performGOShopping(){

    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== mProductList) {

            performOpenProductList();
        }
        else if(e.getSource()==mAddNewProduct){
            performAddNewItem();
        }
        else if(e.getSource()==mSearchProduct){
         performSearchProduct();
        }
        else if(e.getSource()==mGoShopping){

        }
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
            Product p = new Product(id,name,des,price,qty);
            String [] data ={id,name,des,price+"",qty+""};
            tbModel.addRow(new Object[]{id,name,des,price,qty});

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
        if(e.getSource()==btnClear){
            txtid.setText("");
            txtname.setText("");
            txtdes.setText("");
            txtprice.setText("");
            txtqty.setText("");
        }
        if(e.getSource()==btnSearch){

            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
            Session sessionObj = factory.getCurrentSession();
            String search = txtSearch.getText();
            String field = cbFields.getSelectedItem().toString();
            List<Product> product ;
            try {
                sessionObj.beginTransaction();
                if(field=="ID"){
               product = sessionObj.createQuery("from Product s where s."+"id like '%"+search+"%'").getResultList();}
                else{
                    product = sessionObj.createQuery("from Product s where s."+"name like '%"+search+"%'").getResultList();}
                System.out.println(product);
                sessionObj.getTransaction().commit();
            }
            finally {
                factory.close();
            }
            for( Product p : product){
                tbProduct.addRow(new Object[]{p.getId(),p.getName(),p.getDes(),p.getPrice(),p.getQtyInStock()});
            }
        }
    }
}