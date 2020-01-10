package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.*;
import com.sid.digishopheroku.Metier.*;
import com.sid.digishopheroku.Model.*;
import com.sid.digishopheroku.WebRestfull.Forms.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Service(value = "servicecommande")
public class ServiceCommande implements MetierCommande {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    MetierCartitem metierCartitem;
    @Autowired
    MetierUser metierUser;
    @Autowired
    MetierAdress metierAdress;
    @Autowired
    MetierBoutique metierBoutique;
    @Autowired
    MetierCart metierCart;
    @Autowired
    PayementRepository payementRepository;
    @Autowired
    PayerLivraisonRepository payerLivraisonRepository;
    @Autowired
    PaymentToCaiseRepository paymentToCaiseRepository;
    @Autowired
    MetierStock metierStock;
    @Autowired
    MetierTransaction metierTransaction;
@Autowired
    MetierProduit metierProduit;
    @Override
    public Commande NouvelleCommandeEnLigne(Long idcart, Long id_customer) {
        Cart cart=metierCart.findCartByid(idcart);
        AppUser user=metierUser.findByiduser(id_customer);
        Boutique boutique=cart.getCartItemList().get(0).getProduit().getBoutiquesProduit();
       if ((!cartRepository.existsById(idcart) || cart.getCartItemList()==null || boutique==null)&& user==null) throw new RuntimeException("le panier n'existe pas");

       Commande commande=new Commande();
       commande.setGrandTotal(cart.getGrandTotal());
       commande.getCartitemscomand().addAll(cart.getCartItemList());
       commande.setBoutique(boutique);
       commande.setNomvendeur(boutique.getNomBoutique());
       commande.setUsercommande(user);
       commande.setReferencecommande(UUID.randomUUID().toString().substring(5));
       commande.setStatut(STATUTCMD.INITIEE);

       //sauvegarde de la commande
        commandeRepository.save(commande);
       List<CartItem> cartItems=cartItemRepository.findCartitemByidcart(cart.getId_cart());
        for (CartItem item: cartItems) {
       //     item.setCartcartitem(null);
            item.setCommandeitem(commande);
            cartItemRepository.save(item);

        }

        metierCart.clearCart(cart);
       return commande;
    }

    @Override
    public Commande CommandeEnCoursAdress(Long id_commande, Adresse adresse) {
        Commande commande=commandeRepository.getOne(id_commande);
        if (!commandeRepository.existsById(id_commande))throw new RuntimeException("pas de commande");
        adresse.setUser(commande.getUsercommande() );
        adresse=metierAdress.add_adress(adresse);
        commande.setAdresse(adresse);
        commande.setStatut(STATUTCMD.ADRESSE);
        return calculFraisLivraison(commande,adresse);
    }
 Commande calculFraisLivraison(Commande  commande, Adresse adresse){
        double fraislivraison=0;
     if (adresse.getTypeAdress().equalsIgnoreCase(TYPEADRESSE.POINT_SPECIFIQUE)){

         for (CartItem cartItem : commande.getCartitemscomand()){
             fraislivraison+=cartItem.getProduit().getFraisLivraisonPointSpecifique();
                    commande.setGrandTotal(commande.getGrandTotal() + cartItem.getProduit().getFraisLivraisonPointSpecifique());

               }
            }else if (adresse.getTypeAdress().equalsIgnoreCase(TYPEADRESSE.POINT_RELAIS)){

         for (CartItem cartItem : commande.getCartitemscomand()){
             fraislivraison+=cartItem.getProduit().getFraisLivraisonPointRelais();
             commande.setGrandTotal(commande.getGrandTotal() + cartItem.getProduit().getFraisLivraisonPointRelais());
         }
     }else {
         // TAXE
     }
     commande.setFraislivraison(fraislivraison);
     return commande;
 }
    @Override
    public Commande CommandeEncoursPayment(Long id_commande, PaymentDTO payment) {
        Commande commande=commandeRepository.getOne(id_commande);
        for (CartItem item:commande.getCartitemscomand()) {
            metierStock.sortieStock(item.getProduit(),item.getQuantite());
        }
        if (!commandeRepository.existsById(id_commande))throw new RuntimeException("pas de commande");
        System.out.println("le type de payment est "+payment.getTypepayment());
        if (TYPEPAYMENT.CASHDELIVER.equalsIgnoreCase(payment.getTypepayment())){
            System.out.println("le dexieume type de payment est "+TYPEPAYMENT.CASHDELIVER);
           PayerLivraison payerLivraison=new PayerLivraison();
           payerLivraison.setMontantCashdeli(payment.getMontantPayment());
           payerLivraison= payerLivraisonRepository.save(payerLivraison);
           System.out.println("l'id du payment est " +payerLivraison.getId_Payment());
           commande.setPayment(payerLivraison);
        }else if (payment.getTypepayment()==TYPEPAYMENT.CASH){
            PaymentToCaisse paymentToCaisse=new PaymentToCaisse();
            paymentToCaisse.setMontant(payment.getMontantPayment());
            paymentToCaisse= paymentToCaiseRepository.save(paymentToCaisse);
            commande.setPayment(paymentToCaisse);
        }
        commande.setStatut(STATUTCMD.PAYEMENT);
        return commande;
    }

    @Override //cette methode permet d'ajouter les produits pris en boutique
    public Commande AddCommandeShop(Long id_vendeur, List<CartItem> cartItems) {
        AppUser user =metierUser.findByiduser(id_vendeur);
        Commande commande=new Commande();
        cartItems=metierCartitem.saveallcartitem(cartItems);
        for (CartItem item:cartItems) {
            commande.setGrandTotal(commande.getGrandTotal()+item.getSubtotal());
        }
        commande.getCartitemscomand().addAll(cartItems);
        for (CartItem item:commande.getCartitemscomand()) {
            metierStock.sortieStock(item.getProduit(),item.getQuantite());
        }
        commande.setStatut(STATUTCMD.ENBOUTIQUESANSPANIER);

        return commandeRepository.save(commande);
    }

    @Override
    public Commande Add_Commd_vendeur(Long id_cart, Long id_customer, String Nom_vendeur) {
        Commande commande=NouvelleCommandeEnLigne(id_cart,id_customer);
        commande.setStatut(STATUTCMD.ENBOUTIQUE);
        commande.setNomvendeur(Nom_vendeur);
        for (CartItem item:commande.getCartitemscomand()) {
            metierStock.sortieStock(item.getProduit(),item.getQuantite());
        }
        return commandeRepository.save(commande);

    }

    @Override
    public Commande find_command(Long id_command) {
        return commandeRepository.getOne(id_command);
    }

    @Override
    public List<Commande> find_All_Command() {
        return commandeRepository.findAll();
    }

    @Override
    public List<Commande> findCommandByShop(Long id_shop) {
        Boutique boutique=metierBoutique.getBoutiqueById(id_shop);
        return commandeRepository.findByBoutique(boutique);
    }

    @Override
    public List<Commande> findCommandByShopActive(Long id_shop) {

        List<Commande> commandeList=findCommandByShop(id_shop);
        List<Commande> commandes=new ArrayList<>();


        return commandes;
    }

    @Override
    public List<Commande> find_commandBy_nom_vendeur(String nom_vendeur) {
        List<Commande> listcommande=commandeRepository.findByNomvendeur(nom_vendeur);
        System.out.println("la comm: "+listcommande.size());
        return listcommande;
    }

    @Override
    public List<Commande> find_commandBy_customer(Long id_customer) {
        AppUser user=metierUser.getuser(id_customer);

        return commandeRepository.findByUsercommande(user);
    }

    @Override
    public boolean Activer_command(Long id_command) {
        Commande commande=find_command(id_command);

            return true;


       // commandeRepository.save(commande);
    }

    @Override
    public boolean Desactiver_command(Long id_command) {
        Commande commande=find_command(id_command);

            return true;

    }

    @Override
    public double Calcul_montant_command(Commande commande) {
        double montant=0;
        int i=0;
        int lengt_command=commande.getCartitemscomand().size();
        while (i<lengt_command){
            montant+=commande.getCartitemscomand().get(i).getSubtotal();
            System.out.println(montant);
            i++;
        }
        return montant;
    }



    @Override
    public boolean proposition_price(Long id_cart, double montant_propose) {
        Cart cart=cartRepository.findCartByid(id_cart);



        return false;
    }


    @Override
    public Commande Achat_Shop(List<CartItem> cartItems, Long id_vendeur ) {
        Commande commande=new Commande();
        AppUser user=metierUser.findByiduser(id_vendeur);
        Boutique boutique=user.getBoutique();
        System.out.println("la shop est"+boutique.getNomBoutique());
        System.out.println("la taille est"+cartItems.size());
        if (boutique==null) throw new RuntimeException("this cart not exist");
        commande.setCartitemscomand(cartItems);
        commande.setBoutique(user.getBoutique());
        commande.setNomvendeur(user.getUsername());

        Cart cart=new Cart();
        cart=user.getCartuser();
        System.out.println("la shop est"+cart.getId_cart());
        PaymentToCaisse payment=new PaymentToCaisse();
        payment.setMontant(user.getCartuser().getGrandTotal());
        if (cart==null) throw new RuntimeException("this cart not exist");
        payment=payementRepository.save(payment);
        if (payment==null) throw new RuntimeException("this payment not exist");
        commande.setPayment(payment);
        commande=commandeRepository.save(commande);
        if (commande==null) throw new RuntimeException("this command not exist");

        return commande;
    }



    @Override
    public List<Commande> findAllByProduit(Long  idProduit) {
        Produit p = metierProduit.findproduiById(idProduit);
        List<CartItem> cartItems  = metierCartitem.findByProduit(p);
        List<Commande> commandeList = new ArrayList<>();

        for (CartItem item : cartItems) {
            if (item.getCommandeitem() != null) {
                commandeList.add(item.getCommandeitem());
            }
        }
        return commandeList;
    }

    @Override
    public List<Commande> findAllByBoutiqueAndDateCommandeBetween(Long idboutique, Date debut, Date  fin) {
        Boutique boutique = metierBoutique.getBoutiqueById(idboutique);


        return commandeRepository.findAllByBoutiqueAndDateCommandeBetween(boutique,debut,fin);
    }

    @Override
    public List<Commande> findByBoutiqueAndStatutContaining(Boutique boutique, String statutCommande) {

        return commandeRepository.findByBoutiqueAndStatutContaining(boutique,statutCommande);
    }

    @Override
    public List<Commande> findByNomvendeurAndStatutContaining(Long idVendeur, String statutCommande) {
        AppUser vendeur = metierUser.findByiduser(idVendeur);

        return commandeRepository.findByNomvendeurAndStatutContaining(vendeur.getUsername(),statutCommande);
    }
}
