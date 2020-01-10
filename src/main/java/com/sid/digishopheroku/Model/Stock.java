package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stockName;

    private int totalStock;

    private int reservedStock;
    private int shoppingStock;
    private int stockAlarmLimit;

    private Date dateManufacture;
    private Date dateExpiration;

    private int dernierAjout;

    @OneToOne
    private Produit produit;
    @JsonIgnore
    @OneToMany(mappedBy = "stock")
    private List<Transaction> transactionList;

    public Stock(String stockName, int actualStock, int reservedStock, int shoppingStock, int stockAlarmLimit, Produit produit) {
        this.stockName = stockName;
        this.totalStock = actualStock;
        this.reservedStock = reservedStock;
        this.shoppingStock = shoppingStock;
        this.stockAlarmLimit = stockAlarmLimit;
        this.produit = produit;
    }

    public Stock() {
    }
    public Stock(Produit produit){
        this.produit = produit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int actualStock) {
        this.totalStock = actualStock;
    }

    public int getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(int reservedStock) {
        this.reservedStock = reservedStock;
    }

    public int getShoppingStock() {
        return shoppingStock;
    }

    public void setShoppingStock(int shoppingStock) {
        this.shoppingStock = shoppingStock;
    }

    public int getStockAlarmLimit() {
        return stockAlarmLimit;
    }

    public void setStockAlarmLimit(int stockAlarmLimit) {
        this.stockAlarmLimit = stockAlarmLimit;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Date getDateManufacture() {
        return dateManufacture;
    }

    public void setDateManufacture(Date dateManufacture) {
        this.dateManufacture = dateManufacture;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public int getDernierAjout() {
        return dernierAjout;
    }

    public void setDernierAjout(int dernierAjout) {
        this.dernierAjout = dernierAjout;
    }
}
