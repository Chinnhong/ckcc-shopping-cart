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
    private DefaultTableModel tbModel;
    private JTable tbAddNewProducts;
    private JComboBox cbFields;
    //Purchase
    private JButton btnAdd,btnClearID,btnRemove,btnPay;
    private JTextField txtPurhcaseProid,txtPurhcaseQTY,txtPurhcaseDiscount,txtTotalPrie,txtDiscount;
    private JTable tbPurchasedItems ;

    double totaldiscount;
    double totalprice;
    int orderID=1;
    List<Purchase> purchasedItems = new ArrayList<Purchase>() ;
        //Payment

    private JTextField txtCustName, txtCustPhone,txtCustAddress,txtCustDiscount;
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
        DefaultMutableTreeNode nodeEmpMgt = new DefaultMutableTreeNode("Transactions");
        //Create Tree Node Add Employee
        DefaultMutableTreeNode nodeAddNewProduct = new DefaultMutableTreeNode("Add New Product");
        //Create Tree Node List Employee
        DefaultMutableTreeNode nodeGoShopping = new DefaultMutableTreeNode("Go Shopping");

        DefaultMutableTreeNode nodeReport = new DefaultMutableTreeNode("Report");
        nodeEmpMgt.add(nodeAddNewProduct);
        nodeEmpMgt.add(nodeGoShopping);
        nodeEmpMgt.add(nodeReport);



        rootNode.add(nodeEmpMgt);


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
        jTab.addTab("New Items",productPanel);
        jTab.setSelectedComponent(productPanel);
    }
    private JPanel performOpenNewProductsList(JPanel empList) {
        TitledBorder tBorderListNewProducts = BorderFactory.createTitledBorder("Added Items");
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
        blockAddNewProduct.add(txtqty= new JTextField(8));

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

    //////////////////////////////////////////////////////////
    //Purchasing items
    private void performAddNewPurchasedItem() {
        JPanel productPanel = new JPanel(new BorderLayout(10,10));
        JPanel productList = new JPanel(new BorderLayout(10,10));
        JPanel productNew = new JPanel(new BorderLayout(10,10));
        productNew.add(performAddNewPurchasedProduct(productNew),BorderLayout.NORTH);
        productList.add(performOpenPurchasedList(productList),BorderLayout.NORTH);
        productPanel.add(new JScrollPane(productList),BorderLayout.CENTER);
        productPanel.add(new JScrollPane(productNew),BorderLayout.EAST);
        jTab.addTab("New Items",productPanel);
        jTab.setSelectedComponent(productPanel);
    }
    private JPanel performOpenPurchasedList(JPanel empList) {
        TitledBorder tBorderListNewProducts = BorderFactory.createTitledBorder("Added Items");
        tBorderListNewProducts.setTitleJustification(TitledBorder.CENTER);
        empList.setBorder(tBorderListNewProducts);

        tbModel = new DefaultTableModel();
        tbModel.addColumn("Order No.");
        tbModel.addColumn("Code");
        tbModel.addColumn("Name");
        tbModel.addColumn("Unit Price");
        tbModel.addColumn("QTY");
        tbModel.addColumn("Discount");
        tbModel.addColumn("Total Price");
        tbPurchasedItems = new JTable(tbModel);

        JPanel listProductsPanel = new JPanel(new BorderLayout(10,10));
        listProductsPanel.add(new JScrollPane(tbPurchasedItems),BorderLayout.CENTER);
        JPanel blockProductsListFinal = new JPanel(new BorderLayout(10,10));
        blockProductsListFinal.add(new JScrollPane(listProductsPanel), BorderLayout.SOUTH);
        return blockProductsListFinal;

    }
    private JPanel addNewPurchasedProduct() {
        JPanel blockPurchaseProduct = new JPanel(new GridLayout(5, 2,10,10));
        blockPurchaseProduct.add(new JLabel("Enter Product ID/OrderID: "));
        blockPurchaseProduct.add(txtPurhcaseProid = new JTextField());
        blockPurchaseProduct.add(new JLabel("Enter Product QTY: "));
        blockPurchaseProduct.add(txtPurhcaseQTY = new JTextField());
        blockPurchaseProduct.add(new JLabel("Enter Product Discount: "));
        blockPurchaseProduct.add(txtPurhcaseDiscount = new JTextField());

        blockPurchaseProduct.add(new JLabel("Discount:"));
        blockPurchaseProduct.add(txtDiscount = new JTextField());
        txtDiscount.setEditable(false);
        blockPurchaseProduct.add(new JLabel("Grand Total:"));

        blockPurchaseProduct.add(txtTotalPrie= new JTextField());
        txtTotalPrie.setEditable(false);

        JPanel blockAddNewProductFinal = new JPanel(new BorderLayout(10,10));
        blockAddNewProductFinal.add(new JSeparator(),BorderLayout.NORTH);
        blockAddNewProductFinal.add(blockPurchaseProduct,BorderLayout.CENTER);
        return blockAddNewProductFinal;
    }
    private JPanel performAddNewPurchasedProduct(JPanel newProduct) {
        TitledBorder tBorderNewProduct = BorderFactory.createTitledBorder("Enter Product ID");
        tBorderNewProduct.setTitleJustification(TitledBorder.CENTER);
        newProduct.setBorder(tBorderNewProduct);

        JPanel blockAddNew = new JPanel(new BorderLayout(10,10));

        blockAddNew.add(addNewPurchasedProduct(),BorderLayout.NORTH);

        JPanel blockAddNewFinal = new JPanel(new BorderLayout(10,10));
        blockAddNewFinal.add(blockAddNew,BorderLayout.NORTH);
        blockAddNewFinal.add(new JSeparator(),BorderLayout.CENTER);
        JPanel actionBtnPanel = new JPanel(new FlowLayout());
        actionBtnPanel.add(btnAdd = new JButton("Add"));
        btnAdd.addActionListener(this);
        actionBtnPanel.add(btnClearID= new JButton("Clear"));
        btnClearID.addActionListener(this);
        actionBtnPanel.add(btnRemove= new JButton("Remove"));
        btnRemove.addActionListener(this);
        actionBtnPanel.add(btnPay= new JButton("Pay"));
        btnPay.addActionListener(this);


        blockAddNewFinal.add(actionBtnPanel,BorderLayout.SOUTH);

        return blockAddNewFinal;
    }
    public void clearPurchaseTxt(){
        txtPurhcaseDiscount.setText("");
        txtPurhcaseQTY.setText("");
        txtPurhcaseProid.setText("");
    }
    public void clearAddTxt(){
        txtid.setText("");
        txtdes.setText("");
        txtname.setText("");
        txtprice.setText("");
        txtqty.setText("");
    }
    public void totalPay(){
        totalprice=0;
        totaldiscount=0;
        for(Purchase p : purchasedItems) {
            tbModel.addRow(new Object[]{p.getOrderNo(),p.getProduct().getId(), p.getProduct().getName(),p.getProduct().getPrice(), p.getQty(), p.getDiscount() + " ("+p.getDiscountRate()+"%)", p.getPrice()});
            totaldiscount+=p.getDiscount();
            totalprice+=p.getPrice();
            txtDiscount.setText(String.format("%.2f",totaldiscount));
            txtTotalPrie.setText(String.format("%.2f",totalprice));
        }
    }
        ///////////////////////////////////////////////////////////////
    //Display sale Report
        public void performOpenSaleReport() {
            JPanel productList = new JPanel(new BorderLayout(10,10));
            JPanel productListPanel = new JPanel(new BorderLayout(15,15));
            tbProduct = new DefaultTableModel();
            tbProduct.addColumn("Order ID");
            tbProduct.addColumn("Customer Name");
            tbProduct.addColumn("Customer Phone");
            tbProduct.addColumn("Customer Add");
            tbProduct.addColumn("Pro ID");
            tbProduct.addColumn("Pro Name");
            tbProduct.addColumn("Price");
            tbProduct.addColumn("Qty");
            tbProduct.addColumn("Discount");
            tbProduct.addColumn("Total");
            tbProducts = new JTable(tbProduct);


            for( SaleReport s : getSaleReport()){
                tbProduct.addRow(new Object[]{s.getOrderID(),s.getCustName(),s.getCustPhone(),s.getCustAddress(),s.getProID(),s.getProName(),
                s.getProPrice(),s.getProQty(),s.getProDis(),s.getProTotalPrice()});
            }

            productListPanel.add(new JScrollPane(tbProducts), BorderLayout.CENTER);
            productList.add(productListPanel);
            jTab.addTab("Sale Report", productList);
            jTab.setSelectedComponent(productList);
        }
    private List<SaleReport> getSaleReport(){
        List<SaleReport> tmp;
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(SaleReport.class)
                .buildSessionFactory();
        Session sessionObj = factory.getCurrentSession();
        try {
            sessionObj.beginTransaction();
            tmp =(ArrayList) sessionObj.createQuery("from SaleReport").getResultList();
            sessionObj.getTransaction().commit();
        }
        finally {
            factory.close();
        }
        return tmp;

    }
    //End Sale Report





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
            totaldiscount=0;
            totalprice=0;
                performAddNewPurchasedItem();
            System.out.println(purchasedItems);
            totalPay();

        }
        else if(e.getSource()==mSaleReport){
            performOpenSaleReport();
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

            clearAddTxt();
        }
        if(e.getSource()==btnClear){
            clearAddTxt();
        }
        if(e.getSource()==btnSearch){

            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
            Session sessionObj = factory.getCurrentSession();
            String search = txtSearch.getText();
            String field = cbFields.getSelectedItem().toString().toLowerCase();
            int rowCount=tbProduct.getRowCount();
            System.out.println(tbProduct.getRowCount());
            while (tbProduct.getRowCount()>0){
                tbProduct.removeRow(rowCount-1);
                rowCount--;
            }
            List<Product> product ;
            try {
                sessionObj.beginTransaction();
                product = sessionObj.createQuery("from Product s where s."+field+" like '%"+search+"%'").getResultList();
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
        if(e.getSource()==btnAdd){
            String id = txtPurhcaseProid.getText();
            double qty =Double.parseDouble( txtPurhcaseQTY.getText());
            double discount = Double.parseDouble(txtPurhcaseDiscount.getText());

            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
            Session sessionObj = factory.getCurrentSession();
            Product product;
            Purchase purchase;
            try {
                sessionObj.beginTransaction();

                 product = sessionObj.get(Product.class,id);

                purchase = new Purchase(orderID+"",product,qty,discount);
                purchasedItems.add(purchase);
                sessionObj.getTransaction().commit();
                clearPurchaseTxt();
            }
            finally {
                factory.close();
            }
            double stockQty=product.getQtyInStock()-qty;
            SessionFactory factory1 = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
            Session sessionObj1 = factory1.getCurrentSession();
            try {
                sessionObj1.beginTransaction();
                sessionObj1.createQuery("update Product set qtyInStock = "+stockQty+"where id = "+id).executeUpdate();
                sessionObj1.getTransaction().commit();
            }
            finally {
                factory1.close();
            }


            tbModel.addRow(new Object[]{purchase.getOrderNo(),purchase.getProduct().getId(), purchase.getProduct().getName(),purchase.getProduct().getPrice(),
                    purchase.getQty(), purchase.getDiscount()+" ("+purchase.getDiscountRate()+"%)", purchase.getPrice()});

            totaldiscount+=purchase.getDiscount();
            totalprice+=purchase.getPrice();

            txtDiscount.setText(String.format("%.2f",totaldiscount));
            txtTotalPrie.setText(String.format("%.2f",totalprice));
            orderID++;
        }
        if(e.getSource()==btnClearID){
            clearPurchaseTxt();
        }
        if(e.getSource()==btnRemove){
            int index =0;
            String orderNo= txtPurhcaseProid.getText();
            for(int i=0;i<tbModel.getRowCount();i++){
                if(tbModel.getValueAt(i,0).equals(orderNo)){
                     index = i;
                }
            }
            System.out.println("index"+index);

            String proID= tbModel.getValueAt(index,1).toString();

            double discount = purchasedItems.get(index).getDiscount();


            Product product;
            SessionFactory factory1 = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
            Session sessionObj1 = factory1.getCurrentSession();
            System.out.println(purchasedItems.get(index).getQty()+"..."+purchasedItems.get(index).getProduct().getQtyInStock());
            try {
                sessionObj1.beginTransaction();
                product = sessionObj1.get(Product.class,proID);
                sessionObj1.getTransaction().commit();
            }
            finally {
                factory1.close();
            }

            double qty = product.getQtyInStock()+ purchasedItems.get(index).getQty();


            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Product.class)
                    .buildSessionFactory();
            Session sessionObj = factory.getCurrentSession();
            System.out.println(purchasedItems.get(index).getQty()+"..."+purchasedItems.get(index).getProduct().getQtyInStock());
            try {
                sessionObj.beginTransaction();
                sessionObj.createQuery("update Product set qtyInStock = "+qty+"where id = "+proID).executeUpdate();
                sessionObj.getTransaction().commit();
            }
            finally {
                factory.close();
            }
            tbModel.removeRow(index);
            purchasedItems.remove(index);
            int row = tbModel.getRowCount();
            while (row>0){
                tbModel.removeRow(row-1);
                row--;
            }
            totalPay();
            txtDiscount.setText(String.format("%.2f",totaldiscount));
            txtTotalPrie.setText(String.format("%.2f",totalprice));
            txtPurhcaseProid.setText("");

        }
        if(e.getSource()==btnPay){
          JPanel f =  new JPanel(new GridLayout(5, 2,10,10));
            f.add(new JLabel("Customer Name"));
            f.add(txtCustName= new JTextField());
            f.add(new JLabel("Customer Phone"));
            f.add(txtCustPhone =new JTextField());
            f.add(new JLabel("Customer Address"));
            f.add( txtCustAddress= new JTextField());
            f.add(new JLabel("Customer Discount"));
            f.add( txtCustDiscount= new JTextField());

            //JOptionPane.showConfirmDialog(null,f);
            int option = JOptionPane.showConfirmDialog(null, f, "Customer Info", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                String custName = txtCustName.getText();
                String custPhone = txtCustPhone.getText();
                String custAddress = txtCustAddress.getText();
                double dis = Double.parseDouble(txtCustDiscount.getText());
                System.out.println(custName+"---"+custPhone+"---"+custAddress+"---"+dis);

                //add purchased Product to sale report

                for(Purchase p: purchasedItems){

                    SaleReport saleReport = new SaleReport(p.getOrderNo(),custName,custPhone,custAddress,p.getProduct().getId(),p.getProduct().getName(),
                            p.getProduct().getPrice(),p.getQty(),p.getDiscount(),p.getPrice());
                    SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(SaleReport.class)
                            .buildSessionFactory();
                    Session sessionObj = factory.getCurrentSession();


                    try {
                        System.out.println("ok");
                        sessionObj.beginTransaction();
                        sessionObj.save(saleReport);
                        sessionObj.getTransaction().commit();
                        System.out.println("Insert successfully!!!");
                    }
                    finally {
                        factory.close();
                    }
                }
                JPanel payPanel = new JPanel(new GridLayout(5, 2,10,10));
                payPanel.add(new JLabel("Customer Name "));
                payPanel.add(new JLabel(":  "+custName));
                payPanel.add(new JLabel("Customer Address") );
                payPanel.add(new JLabel(":  "+custAddress));
                payPanel.add(new JLabel("Customer Phone"));
                payPanel.add(new JLabel(":  "+custPhone));
                payPanel.add(new JLabel("Total Discount"));
                payPanel.add(new JLabel(":  $"+totaldiscount));
                payPanel.add(new JLabel("Total Price"));
                payPanel.add(new JLabel(":  $"+totalprice));
                JOptionPane.showMessageDialog(null,payPanel,"Invoice",JOptionPane.OK_OPTION);
                int index = tbModel.getRowCount();
                while (index>0){
                    tbModel.removeRow(index-1);
                    purchasedItems.remove(index-1);
                    index--;

                }

            }

        }


    }
}