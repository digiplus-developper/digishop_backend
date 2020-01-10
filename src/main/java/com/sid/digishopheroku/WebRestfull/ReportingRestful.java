package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.*;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/reporting")
public class ReportingRestful {
    @Autowired
    MetierCommande metierCommande;
    @Autowired
    MetierTransaction metierTransaction;
    @Autowired
    MetierInventaire metierInventaire;
    @Autowired
    MetierBoutique metierBoutique;
    @Autowired
    MetierProduit metierProduit;
    @Autowired
    MetierStock metierStock;
    @Autowired
    MetierFavorites metierFavorites;
    @Autowired
    MetierUser metierUser;

    /* 1. E T A T   DES  V E N T E S   EN   L I G N E */
   // Tri des commandes existantes par produit




    /* a) Liste des commandes d'une boutique par periode */
    @GetMapping("findcommandesByBoutique/{idboutique}/datedebut/{debut}/datefin/{fin}")
    List<Commande> findAllByBoutiqueAndDateCommandeBetween(@PathVariable Long idboutique, @PathVariable Date datedebut, @PathVariable Date datefin){
        return metierCommande.findAllByBoutiqueAndDateCommandeBetween(idboutique,datedebut,datefin);
    }
    // b) liste des commandes en cours
    @GetMapping("/findcommandebyboutique/{idboutique}")
    List<Commande> findCommandesByBoutique(@PathVariable Long idboutique) {
        return metierCommande.findCommandByShop(idboutique);
    }
     // c)  Cette methode retourne l'ensemble des commandes contenant un  produit donné
    @GetMapping("/findcommandebyproduit/{id_produit}")
    List<Commande> findAllByProduit(@PathVariable Long id_produit) {
        return metierCommande.findAllByProduit(id_produit);
    }

    /*2. E T A T  DES  V E N T E S  EN  B O U T I Q U E*/
    // a) lister les ventes par session

    // b) lister les ventes par categorie
    @GetMapping("/boutique/ventes/{idBoutique}")
    List<Commande> venteEnBoutique(@PathVariable Long idBoutique) {
        return metierCommande.findByBoutiqueAndStatutContaining(metierBoutique.getBoutiqueById(idBoutique),STATUTCMD.ENBOUTIQUE);
    }
    // c) lister  toutes les ventes de la boutique par date

   /*3. L E S    P R O D U I T S    R E T O U R N E S   A V E C    C O M M E N T A I R E S*/
   // a. Lister les produits retournés (avec la raison du retour) ;
    //b. Lister les clients qui ont retournés un produit (avec le nombre des produits retournés)
   // c. Lister les livreurs des produits retournés.

    /* 4. ETAT DU STOCK A UN MOMENT DONNE*/
    //a. Lister les produits du stock par catégorie et par nombre ;
    @GetMapping("/stock/bycategorie/{idBoutique}")
    List<Produit> findProduitByCategorie(@PathVariable Long idBoutique) {
        return metierProduit.getProduitofBoutique(idBoutique);
    }
   // b. Lister les sorties du stock par catégorie et par nombre ;

  // @GetMapping("/stock/sorties/{idBoutique}")
  // List<Transaction> findAllSortieStock(@PathVariable Long idBoutique) {
  //     return metierTransaction.findTransactionByTypeTransaction(TYPETRANSACTION.SORTIE_STOCK,idBoutique);
  // }

//   @GetMapping("/stock/sorties/{idBoutique}")
//   List<Transaction> findAllSortieStock(@PathVariable Long idBoutique) {
//       return metierTransaction.findTransactionByTypeTransaction(TYPETRANSACTION.SORTIE_STOCK,idBoutique);
//   }

    //c. Lister les entrées du stock par catégorie et par nombre.
    @GetMapping("/stock/entree/{idBoutique}")
    List<Transaction> findAllEntreeStock(@PathVariable Long idBoutique) {
        return metierTransaction.findTransactionByTypeTransaction(TYPETRANSACTION.AJOUT_STOCK,idBoutique);
    }
    /* 5. i N V E N T A I R E S  DE  LA  B O U T I Q U E */
   // a. Lister tous les inventaires de la boutique par date ;
    @GetMapping("/inventaire/byshop/{idBoutique}")
    List<Inventaire> findAllInventaireByBoutique(@PathVariable Long idBoutique) {
        return metierInventaire.findAllByBoutique(metierBoutique.getBoutiqueById(idBoutique));
    }
   // b. Lister les inventaires totaux ;
   @GetMapping("/inventaire/totalbyshop/{idBoutique}")
   List<Inventaire> findAllInventaireTotalByBoutique(@PathVariable Long idBoutique) {
       return metierInventaire.findByBoutiqueAndTypeInventaire(metierBoutique.getBoutiqueById(idBoutique), TYPEINVENTAIRE.GLOBAL);
   }
   // c. Lister les inventaires partiels.
   @GetMapping("/inventaire/partialbyshop/{idBoutique}")
   List<Inventaire> findAllInventairePartielByBoutique(@PathVariable Long idBoutique) {
       return metierInventaire.findByBoutiqueAndTypeInventaire(metierBoutique.getBoutiqueById(idBoutique), TYPEINVENTAIRE.PARTIEL);
   }
    /*
     *6. Rapports des inventaires de la boutique
     *
7. L’état de la caisse (Afficher une page qui donne l’état de la caisse à un moment donné)
8. Les sessions de caisse
a. Lister toutes les sessions de la caisse ;
b. Lister les vendeurs qui ont ouvert une session de caisse.9. L’état d’une session de caisse à un moment donné (Afficher une page qui donne l’état
de la session à un moment donné)
10. Les produits réservés de la boutique
a. Lister les produits réservés par catégorie et par date ;
*/
@GetMapping("/produit/reserves/{idBoutique}")
   List<Produit> findAllProduitReservesByBoutique(@PathVariable Long idBoutique) {
List<Stock> stockList = metierStock.findByReservedStockGreaterThan(0);
    List<Produit> produits = new ArrayList<>();
for (Stock stock : stockList){
    if (stock.getProduit().getBoutiquesProduit().getIdBoutique()==idBoutique){
        produits.add(stock.getProduit());
    }
}

    return produits;
}
//b. Lister les clients qui ont réservés un produit de la boutique avec les produits réservés.
@GetMapping("/stock/sorties/{idBoutique}")
List<Transaction> findAllReservedStock(@PathVariable Long idBoutique) {
    return metierTransaction.findTransactionByTypeTransaction(TYPETRANSACTION.RESERVATION_STOCK,idBoutique);
}
//11. Nombre des utilisateurs ayant consulter la boutique (Afficher le nombre des utilisateurs ayant consulter la bouter la boutique)
// V O I R  BoutiqueRestfull - ProduitRestfull

///12. Nombre des utilisateurs ayant initier un panier dans la boutique (Afficher la liste des utilisateurs ayant initier un panier de la boutique)
    @GetMapping("/user/withcartin/{idBoutique}")
    List<AppUser> UserWithCartIn(@PathVariable Long idBoutique) {
        return  metierUser.UsersWithCartNotEmpty(metierBoutique.getBoutiqueById(idBoutique));
}

//13. Les produits favoris des clients
//a. Lister les produits favoris des clients par catégorie ;
@GetMapping("/produits/favoris/{idBoutique}")
List<Produit> findAllProduitsFavoris(@PathVariable Long idBoutique) {
    List<Produit> produitsFavoris = new ArrayList<>();
    for (Produit produit:metierFavorites.produuits_les_plus_favoris()
         ) {
        if (produit.getBoutiquesProduit().getIdBoutique() == idBoutique){
            produitsFavoris.add(produit);
        }
    }
    return produitsFavoris;
}
//b. Lister les produits favoris des clients par ordre décroissant.
    // Voir 13.a
//14. L’état des paiements
//a. Lister les paiements par mode de paiement ;

//b. Lister les modes de paiement utiliser par les clients par ordre décroissant.



    /*liste des commandes des vendeurs en foction de l etat */


    @GetMapping("/findcommandetermineebyvendeur/{id_vendeur}")
    List<Commande> findAllByVendeur(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.TERMINEE);
    }
    @GetMapping("/findcommandeenboutiquebyvendeur/{id_vendeur}")
    List<Commande> findAllEnBoutiqueByVendeur(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.ENBOUTIQUE);
    }
    @GetMapping("/findcommandetoadressebyvendeur/{id_vendeur}")
    List<Commande> findAllByVendeurAdresse(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.ADRESSE);
    }

    @GetMapping("/findcommandeinitieebyvendeur/{id_vendeur}")
    List<Commande> findAllinitieeByVendeur(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.INITIEE);
    }
    @GetMapping("/findcommandeannuleebyvendeur/{id_vendeur}")
    List<Commande> findAllAnnuleeByVendeurAdresse(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.ANNULEE);
    }
    @GetMapping("/findcommandeenboutiquesanspanierbyvendeur/{id_vendeur}")
    List<Commande> findAllSansPanierByVendeurAdresse(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.ENBOUTIQUESANSPANIER);
    }
    @GetMapping("/findcommandepayeebyvendeur/{id_vendeur}")
    List<Commande> findAllPayeeByVendeurAdresse(@PathVariable Long id_vendeur) {
        return metierCommande.findByNomvendeurAndStatutContaining(id_vendeur,STATUTCMD.PAYEMENT);
    }


}
