package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.*;
import com.sid.digishopheroku.WebRestfull.Forms.InventaireForm;

import java.util.List;

public interface MetierInventaireItem {
    InventaireItem addProduitToInventaireItem(Inventaire inventaire,  InventaireForm inventaireForm);
    InventaireItem updateInventaireItem(InventaireItem item, int qte);
    InventaireItem saveInventaireItem(InventaireItem item);
    List<InventaireItem> saveAllInventaireItem(List<InventaireItem> items);
    void removeInventaireItem(Inventaire inventaire,InventaireItem item);
    void deleteInventaireItem(Long idItem);
    void deleteAllInventaireItems(List<InventaireItem> items);


    List<InventaireItem> findByInventaire(Inventaire inventaire);
    List<InventaireItem> findInventaireItemByProduit(Produit produit);
    InventaireItem findById(Long id);
}
