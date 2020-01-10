package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.InventaireItemRepository;
import com.sid.digishopheroku.Metier.MetierInventaireItem;
import com.sid.digishopheroku.Metier.MetierProduit;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Inventaire;
import com.sid.digishopheroku.Model.InventaireItem;
import com.sid.digishopheroku.Model.Produit;
import com.sid.digishopheroku.WebRestfull.Forms.InventaireForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceInventaireItem implements MetierInventaireItem {
    @Autowired
    InventaireItemRepository itemRepository;
    @Autowired
    MetierProduit  metierProduit;
    @Autowired
    MetierUser metierUser;
    @Override
    public InventaireItem addProduitToInventaireItem( Inventaire inventaire,  InventaireForm inventaireForm) {
      //  for (InventaireItem item : inventaire.getInventaireItemList()){
     //       if (produit.getIdProduit()==item.getProduit().getIdProduit()){
       //         System.err.println(produit.getNomProduit() + " existe deja dans l'inventaire.");
        //        return item;
      //      }
    //    }
        Produit produit = metierProduit.findproduiById(inventaireForm.getIdProduit());
      // int qtePhysique = inventaireForm.getQuantityOnHand();
        AppUser user = metierUser.getuser(inventaireForm.getIdVendeur());


            InventaireItem inventaireItem = new InventaireItem();


            inventaireItem.setProduit(produit);
            inventaireItem.setQuantitePhysique(inventaireForm.getQuantityOnHand());
            System.out.println("la quantite sous la main : "+inventaireForm.getQuantityOnHand());
            inventaireItem.setQuantiteTheorique(produit.getQuantiteEnStock());
            inventaireItem.setInventaire(inventaire);
            inventaireItem.setDifference(inventaireItem.getQuantitePhysique()-inventaireItem.getQuantiteTheorique());

            return itemRepository.save(inventaireItem);


    }

    @Override
    public InventaireItem updateInventaireItem(InventaireItem item, int qte) {
        item.setQuantitePhysique(qte);
        return itemRepository.save(item);
    }

    @Override
    public InventaireItem saveInventaireItem(InventaireItem item) {
        return itemRepository.save(item);
    }

    @Override
    public List<InventaireItem> saveAllInventaireItem(List<InventaireItem> items) {
        return itemRepository.saveAll(items);
    }

    @Override
    public void removeInventaireItem(Inventaire inventaire,InventaireItem item) {
        inventaire.getInventaireItemList().remove(item);
        itemRepository.delete(item);

    }

    @Override
    public void deleteInventaireItem(Long idItem) {
        itemRepository.delete(itemRepository.getOne(idItem));
    }

    @Override
    public void deleteAllInventaireItems(List<InventaireItem> items) {
        itemRepository.deleteAll(items);

    }



    @Override
    public List<InventaireItem> findByInventaire(Inventaire inventaire) {
        return itemRepository.findByInventaire(inventaire);
    }

    @Override
    public List<InventaireItem> findInventaireItemByProduit(Produit produit) {
        return itemRepository.findByProduit(produit);
    }

    @Override
    public InventaireItem findById(Long id) {
        return itemRepository.getOne(id);
    }
}
