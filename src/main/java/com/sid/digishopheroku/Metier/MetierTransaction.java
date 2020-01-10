package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.*;

import java.util.List;

public interface MetierTransaction {
    Transaction newStockTransaction(Stock stock, Long id_user);
    Transaction reservationStock(Stock stock, int quantity, Long id_user);

    Transaction reservationStock(Stock stock, int quantity, Long id_user,String commentaire);

    Transaction sortieStock(Stock stock, int qty, Long id_user);
    Transaction sortieStock(Stock stock, int qty, Long id_user,String commentaire);

    Transaction retourProduit(Produit produit, int qte, Long id_user);
    Transaction retourProduit(Produit produit, int qte, Long id_user,String commentaire);

    List<Transaction> getalltransactionbyuser(Long user);

/* G E S T I O N    D E    L A    C A I S S E */

    /* G E S T I O N  DE  L  I N V E N T A I R E */
    List<Transaction> updateStockAfterInventory(Inventaire inventaire);
    List<Transaction> updateStockAfterInventory(Inventaire inventaire,String commentaire);


    /* R E P O R T I N G */
    List<Transaction> findTransactionByTypeTransaction(String typeTransaction,Long idBoutique);
}
