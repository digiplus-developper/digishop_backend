package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierCommande;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.Adresse;
import com.sid.digishopheroku.Model.CartItem;
import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.WebRestfull.Forms.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/commande")
public class CommandeRestfull {

@Autowired
    MetierCommande metierCommande;
@Autowired
MetierUser metierUser;

    /*1 Creer une nouvelle Commande a partir d un panier qui sera supprimer par la suite*/
    @GetMapping("/ligne/{idcart}/{id_customer}")
    public Commande nouvelle_commande(@PathVariable Long idcart,@PathVariable Long id_customer){

        return metierCommande.NouvelleCommandeEnLigne(idcart,id_customer);
    }
//*1
    @PostMapping("/ligne/adresse/{idcommande}/")
    public Commande CommandeEnCoursAdress(@PathVariable Long idcommande,@RequestBody Adresse adresse){
        return metierCommande.CommandeEnCoursAdress(idcommande,adresse);

    }

    @PostMapping("/ligne/payment/{idcommande}/")
    public Commande CommandeEncoursPayment(@PathVariable Long idcommande,@RequestBody PaymentDTO paymentDTO){
        return metierCommande.CommandeEncoursPayment(idcommande,paymentDTO);

    }
    @PostMapping("/shop/{id_vendeur}/")
    public Commande AddCommandeShop(@PathVariable Long id_vendeur,@RequestBody List<CartItem> cartItems){
        return metierCommande.AddCommandeShop(id_vendeur,cartItems);

    }

//2 cette methode permet d'enregistrer une commande
    @PostMapping("/{idcart}/{id_customer}/{Nom_vendeur}")
    public Commande Add_Commd_vendeur(@PathVariable Long idcart, @PathVariable Long id_customer,@PathVariable String Nom_vendeur){
        return metierCommande.Add_Commd_vendeur(idcart,id_customer,Nom_vendeur);
    }
//Methode permettant qu'un vendeur valide une commande ie elle est utiliser pour approuver la commande
    @GetMapping("/Activer/{id_command}")
    public boolean Activer_commad(@PathVariable Long id_command){
       return metierCommande.Activer_command(id_command);
    }
    @GetMapping("/Desactiver/{id_command}")
    public boolean Desactiver_commad(@PathVariable Long id_command){
        return metierCommande.Desactiver_command(id_command);
    }

    @GetMapping("/all")
    List<Commande> find_All_Command(){
        return metierCommande.find_All_Command();
    }


/* Liste de toutes les commandes effectuees par un vendeur */
    @GetMapping("/all/shop/{nom_vendeur}")
    List<Commande> find_All_CommandBy_User(@PathVariable String nom_vendeur){
        System.out.println("le nom du vendeur est : "+nom_vendeur);
        return metierCommande.find_commandBy_nom_vendeur(nom_vendeur);
    }


    //on a les commandes d'une boutique
    @GetMapping("/shop/{id_shop}")
    List<Commande> findCommandByShop(@PathVariable Long id_shop){
        return metierCommande.findCommandByShop(id_shop);
    }

    //on a les commandes d'une boutique
    @GetMapping("/customer/{id_customer}")
    List<Commande> findCommandCustomer(@PathVariable Long id_customer){
        return metierCommande.find_commandBy_customer(id_customer);
    }

//une commande est activée lorsque le vendeur a donné le ok que la commande peut etre livré
    //on a les commandes actives d'une boutique
    @GetMapping("/shop_active/{id_shop}")
    List<Commande> findCommandByShopActive(@PathVariable Long id_shop){
        return metierCommande.findCommandByShopActive(id_shop);
    }




    /* C O M M A N D E   EN   B O U T I Q U E */
    @PostMapping("commandeEnBoutique")
    Commande commandeEnBoutique(){
        return null;
    }
    @PostMapping("/achat_command/{id_vendeur}")
    Commande Achat_Shop(@RequestBody List<CartItem> cartItems,@PathVariable Long id_vendeur ){
        return metierCommande.Achat_Shop(cartItems, id_vendeur);
    }



}


