package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Adresse;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.CartItem;
import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.WebRestfull.Forms.PaymentDTO;

import java.util.Date;
import java.util.List;

public interface MetierCommande {


    Commande NouvelleCommandeEnLigne(Long idcart, Long id_customer);
    Commande CommandeEnCoursAdress(Long id_commande,Adresse adresse);

    Commande CommandeEncoursPayment(Long id_commande, PaymentDTO payment);

    Commande AddCommandeShop(Long id_vendeur, List<CartItem> cartItems);

    Commande Add_Commd_vendeur(Long id_cart, Long id_customer, String Nom_vendeur);

    Commande find_command(Long id_command);
    List<Commande> find_All_Command();
    List<Commande> findCommandByShop(Long id_shop);
    List<Commande> findCommandByShopActive(Long id_shop);
    List<Commande> find_commandBy_nom_vendeur(String nom_vendeur);
    List<Commande> find_commandBy_customer(Long id_customer);
    boolean Activer_command(Long id_command);//le vendeur active la commande
    boolean Desactiver_command(Long id_command);//le vender desactive la commande
    double Calcul_montant_command(Commande commande);

    boolean proposition_price(Long id_cart,double montant_propose);

    Commande Achat_Shop(List<CartItem> cartItems, Long id_vendeur);

    /* R E P O R T I N G */
    /* Trier les commandes par Produit */
    List<Commande> findAllByProduit(Long idProduit);
    /*Liste des commandes d'une boutique par periode*/
       List<Commande> findAllByBoutiqueAndDateCommandeBetween(Long idboutique, Date debut, Date fin);

    List<Commande> findByBoutiqueAndStatutContaining(Boutique boutique, String statutCommande);
    List<Commande> findByNomvendeurAndStatutContaining(Long idVendeur,String statutCommande);
}
