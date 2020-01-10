package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Produit;
import com.sid.digishopheroku.Model.Stock;
import com.sid.digishopheroku.WebRestfull.Forms.NewStockForm;

import java.util.List;

public interface MetierStock {
    Stock reserveStock(Produit produit, int quantite) ;
    Stock repartirNouveauStock(NewStockForm stockForm);
    Stock addNewStock(Produit produit,Stock stock);

    Long calcul_product_validity(Produit produit);
    Long calcul_stock_duration(Produit produit);
    boolean stock_limit_checked(Produit produit);


   Stock sortieStock(Produit produit,int qty);
    Stock retourProduit(Produit produit, int qte);
    Stock updateStockInventaire(Produit produit, int difference);

    List<Stock> findByReservedStockGreaterThan(int zero);


    Stock addNewStock(Produit produit,NewStockForm stockForm);
}
