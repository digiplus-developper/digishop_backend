package com.sid.digishopheroku.WebRestfull.Forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class NewStockForm {

    private String stockName;

    private int totalStock;
    private int quotaReservedStock;
    private int quotaShoppingStock ;

    private int stockAlarmLimit;
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateManufacture;
    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateExpiration;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getQuotaReservedStock() {
        return quotaReservedStock;
    }

    public void setQuotaReservedStock(int quotaReservedStock) {
        this.quotaReservedStock = quotaReservedStock;
    }

    public int getQuotaShoppingStock() {
        return 100-quotaReservedStock;
    }

    public void setQuotaShoppingStock(int quotaShoppingStock) {
        this.quotaShoppingStock = quotaShoppingStock;
    }

    public int getStockAlarmLimit() {
        return stockAlarmLimit;
    }

    public void setStockAlarmLimit(int stockAlarmLimit) {
        this.stockAlarmLimit = stockAlarmLimit;
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

}
