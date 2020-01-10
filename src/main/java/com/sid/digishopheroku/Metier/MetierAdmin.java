package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface MetierAdmin {

     Produit AddProduit(Produit p,String nom_boutisue);
     Produit Addproduct_Associer(Produit produit,Long id_produit_associer);
     Produit AddPromotion(Long id_product, double montant_promotion, Date date_debut, Date date_fin);

     boolean De_associerProduct(Long id_produp,Long id_produit_associer);
     Produit closePromotion(Produit produit);

     Boutique AddBoutique(Boutique b);

      AppUser AddUser(AppUser user,String name_shop,String status);


     Produit UpdateProduct(Produit produit);
     Boutique UpdateShop(Boutique boutique);
     AppUser updateUser(AppUser user);


     void delectProduit(Long idProduit, Long idBoutique);

     void delectUser(Long id_user);

     List<AppUser> findAllUser(String status);

     List<Produit> findProduitBoutiqueByMot(String motcle);


     void uploadboutique(MultipartFile file, Long idboutique) throws Exception;
     void uploadproduct(MultipartFile file, Long idproduct) throws Exception;


     boolean Activer_Produit(long id_product);
     boolean Activer_Reduct_Produit(long id_product);
     boolean Activer_User(Long id_user);
     boolean ActiverPrix(Long idproduit);

}
