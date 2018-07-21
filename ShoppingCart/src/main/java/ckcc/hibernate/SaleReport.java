package ckcc.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblsale")
public class SaleReport {
    @Id
    @Column(name="order_id")
    private int orderID;
    @Column(name="cust_name")
    private String custName;
    @Column(name="cust_phone")
    private String custPhone;
    @Column(name="cust_address")
    private String custAddress;
    @Column(name="pro_id")
    private String proID;
    @Column(name="pro_name")
    private String proName;
    @Column(name="pro_price")
    private double proPrice;
    @Column(name="pro_qty")
    private double proQty;
    @Column(name="pro_dis")
    private double proDis;
    @Column(name="pro_totalprice")
    private double proTotalPrice;
    public SaleReport(){};
    public SaleReport( String custName, String custPhone, String custAddress, String proID, String proName, double proPrice, double proQty, double proDis, double proTotalPrice) {
     //   this.orderID = orderID;
        this.custName = custName;
        this.custPhone = custPhone;
        this.custAddress = custAddress;
        this.proID = proID;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proQty = proQty;
        this.proDis = proDis;
        this.proTotalPrice = proTotalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public String getProID() {
        return proID;
    }

    public String getProName() {
        return proName;
    }

    public double getProPrice() {
        return proPrice;
    }

    public double getProQty() {
        return proQty;
    }

    public double getProDis() {
        return proDis;
    }

    public double getProTotalPrice() {
        return proTotalPrice;
    }

    @Override
    public String toString() {
        return "SaleReport{" +
                "orderID='" + orderID + '\'' +
                ", custName='" + custName + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", proID='" + proID + '\'' +
                ", proName='" + proName + '\'' +
                ", proPrice=" + proPrice +
                ", proQty=" + proQty +
                ", proDis=" + proDis +
                ", proTotalPrice=" + proTotalPrice +
                '}';
    }
}
