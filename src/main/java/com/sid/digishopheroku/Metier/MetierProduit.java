package com.sid.digishopheroku.Metier;


import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Produit;

import java.util.List;
import java.util.Optional;

public interface MetierProduit {
     List<Produit> findProduit();
     List<Produit> findAllProduit();
     List<Produit> findProduitOrBoutique(String mot);
     byte[] getphoto(Long id) throws Exception;
     Optional<Produit> getproduiById(Long id);
     Produit findproduiById(Long id);
     Produit Add_QcodeToProduct(Long id_product,String qcode);
     Produit find_product_qcode(String qcode);
     Produit getproduitById(Long id);
     List<?> getBoutiqueAssocier(String mc);//qui retourne la liste de toutes les produits asso
     List<Produit> getProduitofBoutique(Long id);
     Produit saveproduit(Produit produit);
     List<Produit> products_of_shop(Long id_shop);
     List<Produit> products_of_shop_Activate(Long id_shop);
     List<Produit> findAllByBoutiquesProduitAndArrivageIsTrue(Long id_shop);

     /*30102019*/
     Produit AssociatedProduitsTo(Produit produit, Long id);
     List<Produit> getProduitsAssociesTo(Long id);
     List<Boutique> getBoutiqueAssocies(Long id);

     //14112019
     Produit findByNomProduit(String nomProduit);


     Produit addViewToProduit(Long idProduit);


}
