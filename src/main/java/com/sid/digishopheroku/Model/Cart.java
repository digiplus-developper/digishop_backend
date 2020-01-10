package com.sid.digishopheroku.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_cart;
    private double GrandTotal=0;
    private double GrandTotalVente=0;
    //@JsonIgnore
    @OneToMany(mappedBy = "cartcartitem")
    private List<CartItem> cartItemList;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id_user")
    private AppUser usercart;



    public Cart() {
    }

    public Long getId_cart() {
        return id_cart;
    }

    public void setId_cart(Long id_cart) {
        this.id_cart = id_cart;
    }

    public double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        GrandTotal = grandTotal;
    }


    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public AppUser getUsercart() {
        return usercart;
    }

    public void setUsercart(AppUser usercart) {
        this.usercart = usercart;
    }

    public double getGrandTotalVente() {
        return GrandTotalVente;
    }

    public void setGrandTotalVente(double grandTotalVente) {
        GrandTotalVente = grandTotalVente;
    }
}
