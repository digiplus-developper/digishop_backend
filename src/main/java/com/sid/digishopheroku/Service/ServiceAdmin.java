package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.FavoritesRepository;
import com.sid.digishopheroku.IDaoRepository.ProduitRepository;
import com.sid.digishopheroku.IDaoRepository.StockRepository;
import com.sid.digishopheroku.Metier.*;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Transactional
@Service(value = "serviceadmin")
public class ServiceAdmin  implements MetierAdmin{
   @Autowired
   ProduitRepository produitRepository;
    @Autowired
    MetierBoutique   metierBoutique;
    @Autowired
    MetierUser metierUser;
    @Autowired
    MetierAccount metierAccount;
    @Autowired
    MetierCart metierCart;
    @Autowired
    MetierCaisse metierCaisse;
    @Autowired
    FavoritesRepository favoritesRepository;
    @Autowired
    StockRepository stockRepository;

    @Override
    public Produit AddProduit(Produit p,String nom_boutisue) {
        Boutique boutique=metierBoutique.findByNomBoutique(nom_boutisue);
        //Verifier s il ya un produit avec le meme Nom
        Produit test = produitRepository.findByNomProduit(p.getNomProduit());
        //si le prroduit est dans la meme boutique
        if (test.getBoutiquesProduit().getIdBoutique()==p.getBoutiquesProduit().getIdBoutique())throw new RuntimeException("UN PRODUIT AVEC LE MEME NOM EXISTE DEJA!");
        if (boutique!=null && test.getBoutiquesProduit().getIdBoutique()!=p.getBoutiquesProduit()
        .getIdBoutique()){
            p.setReferenceProduit(boutique.getNomBoutique()+p.getNomProduit()+ UUID.randomUUID().toString().substring(10).toUpperCase());

            Stock stock = stockRepository.save(new Stock(p));
           // stock.setProduit(p);


            p.setStock(stock);
            p.setBoutiquesProduit(boutique);
            Produit produit=produitRepository.save(p);
            //System.out.println("la categorie est "+produit.getCategorieProduct().getNomcategorie());
            //System.out.println("fin de l'ajout du produit"+produit.getNomProduit());
            return produit;
        }
        return  null;
    }

    @Override
    public Produit Addproduct_Associer(Produit produit, Long id_produit_associer) {
        Produit produit_associer=produitRepository.getOne(id_produit_associer);
        System.out.println("le produit "+produit.getNomProduit()+" est associer a "+produit_associer.getNomProduit());
        produit.setProduit(produit_associer);
        produit_associer.setProduit(produit);
        return produitRepository.save(produit);
    }

    @Override
    public Produit AddPromotion(Long id_product, double montant_promotion, Date date_debut, Date date_fin) {
        Produit produit=produitRepository.getOne(id_product);
        produit.setPourcentagepromotion(metierCart.calcul_pourcentage(produit.getPrixProduit(),montant_promotion));
        if (produit.getPourcentagepromotion()!=(0)){
            produit.setPrixpromotionnel(produit.getPrixProduit()*(produit.getPourcentagepromotion()));
            produit.setDateDebutPromotion(date_debut);
            produit.setDateFinPromotion(date_fin);
            produit.setPromoAcitve(Boolean.TRUE);
            produitRepository.save(produit);

        }
        return produit;
    }

    @Override
    public boolean De_associerProduct(Long id_produp, Long id_produit_associer) {
        Produit produit_associer=produitRepository.getOne(id_produit_associer);
        Produit produit=produitRepository.getOne(id_produp);

        System.out.println("les histoires  ci "+produit_associer.getProduit().getNomProduit());
        System.out.println("les histoires  ci 111  "+produit.getProduit().getNomProduit());
        produit.setProduit(null);
        produit_associer.setProduit(null);
       return true;
    }

    @Override
    public Produit closePromotion(Produit produit) {
        produit.setPromoAcitve(Boolean.FALSE);
        produitRepository.save(produit);
        return produit;
    }
    @Override
    public Boutique AddBoutique(Boutique b) {
        if (b==null){
            return b;
        }else {
            Caisse caisse=new Caisse();
            caisse= metierCaisse.Add_caisse(caisse);
            b.setCaisse(caisse);
          //  Produit produit = new Produit();

           // b.getListProduitBoutique().add(produit);

            Boutique boutique=metierBoutique.saveboutique(b);
             addGenericProduct(boutique.getNomBoutique());
            System.out.println("l'id   de la caisse est "+boutique.getCaisse().getId_caise());
         return  boutique;
        }
    }
public Produit addGenericProduct(String shop){
        Produit produit = new Produit();
        produit.setNomCategorie("Generique");
        produit.setNomProduit("Generique");

        return AddProduit(produit,shop);
}


    @Override
    public Produit UpdateProduct(Produit p) {
       Produit produit1=produitRepository.getOne(p.getIdProduit());
       System.out.println("le qcode est"+ p.getQcode());
        if (p.getPrixProduit()!=0){
            produit1.setPrixProduit(p.getPrixProduit());
        }
        if (p.getQuantity()!=0){
            produit1.setQuantity(p.quantity);
            produit1.setQuantiteEnStock(produit1.getQuantiteEnStock()-p.getQuantiteEnStock());
        }
        if (!p.getNomCategorie().equalsIgnoreCase("")){
            produit1.setNomCategorie(p.getNomCategorie());
        }
        if (!p.getQcode().equalsIgnoreCase("") || !p.getQcode().equalsIgnoreCase(null)){
            produit1.setQcode(p.getQcode());
        }
        if (p.getQuantiteEnStock()!=0){
            int quantestock=produit1.getQuantiteEnStock();
            quantestock +=p.getQuantiteEnStock();
            produit1.setQuantiteEnStock(quantestock);

        }
        return produit1;
    }

    @Override
    public Boutique UpdateShop(Boutique b) {
        Boutique boutique=metierBoutique.findByNomBoutique(b.getNomBoutique());
        if (!b.getAdressBoutique().equalsIgnoreCase("")){
            boutique.setAdressBoutique(b.getAdressBoutique());
        }
        if (!b.getTelephoneBoutique().equalsIgnoreCase("")){
            boutique.setTelephoneBoutique(b.getTelephoneBoutique());
        }

        return metierBoutique.saveboutique(boutique);
    }

    @Override
    public AppUser updateUser(AppUser u) {
        AppUser user=metierUser.getuser(u.getId_user());
        if (!u.getPhone().equalsIgnoreCase("")){
            user.setPhone(u.getPhone());
        }
        if (!u.getPassword().equalsIgnoreCase("")){
            user.setPassword(u.getPassword());
        }
        if (!u.getEmail().equalsIgnoreCase("")){
            user.setEmail(u.getEmail());
        }

        return metierAccount.saveuser(user);
    }



    @Override
    public void delectProduit(Long idProduit, Long idBoutique) {
       Boutique boutique =  metierBoutique.getBoutiqueById(idBoutique);

        if (VerifierProduit_dansCommandeEtPanier(produitRepository.getOne(idProduit))==Boolean.TRUE) throw new  RuntimeException("IMPOSSIBLE CAR CE PRODUIT EXISTE DANS UN PANIER OU A DEJA ETE COMMANDE!");
       boutique.getListProduitBoutique().remove(produitRepository.findPrByid(idProduit));
       metierBoutique.saveboutique(boutique);
       produitRepository.delete(produitRepository.getOne(idProduit));

    }
public boolean VerifierProduit_dansCommandeEtPanier(Produit produit){

    List<Cart> carts = metierCart.findAll();

    boolean productUsed = false;

        for (Cart cart : carts){

            for (CartItem item : cart.getCartItemList()){
                if (item.getProduit().getIdProduit()==produit.getIdProduit()){
                    productUsed = true;
                    break;
                }
            }
            break;
        }

       return productUsed;
}

    @Override
    public void delectUser(Long id_user) {
        metierUser.delectuser(id_user);
    }


    /* cette methode permet de retourner un utilisateur en fonction de son status*/
    @Override
    public List<AppUser> findAllUser(String status) {
        List<AppUser> users=metierUser.getAlluser();//on recupere la liste des utilisateur
        List<AppUser> u=new ArrayList<>();
        int i=0;
        while (i<users.size()){ //on parcour la  liste pour verifier le status
            System.out.println("l'utilsateur  "+users.get(i).getUsername());
            ListIterator<AppRole> listIterator=users.get(i).getRoles().listIterator();//Iterator est utilisé pour parcourir une collection
            while (listIterator.hasNext()) {
                if (listIterator.next().getRolename().equalsIgnoreCase(status)){
                    System.out.println(status);
                    u.add(users.get(i));
                }
            }

            i++;
        }

        return u;
    }

    @Override
    public List<Produit> findProduitBoutiqueByMot(String motcle) {
        return null;
    }


    @Override
    public void uploadboutique(MultipartFile file,Long idboutique) throws Exception {
        Boutique b =metierBoutique.getBoutiqueById(idboutique);
        b.setPhotoboutique(idboutique+".png");
        String current = System.getProperty("user.dir");
        Files.write(Paths.get(current+"/ecom/shop/"+b.getPhotoboutique()),file.getBytes());
        System.out.println("fichier enregistrer service"+current);
        metierBoutique.saveboutique(b);
        System.out.println("l'id   du boutique apres  upload est "+b.getIdBoutique());

    }

    @Override
    public void uploadproduct(MultipartFile file, Long idproduct) throws Exception {
        Produit p =produitRepository.findPrByid(idproduct);
        p.setPhotoProduit(idproduct+".png");
        String current = System.getProperty("user.dir");
        Files.write(Paths.get(current+"/ecom/product/"+p.getPhotoProduit()),file.getBytes());
        System.out.println("fichier enregistrer service"+current);
        produitRepository.save(p);
    }

    @Override
    public boolean Activer_Produit(long id_product) {
        Produit produit=produitRepository.getOne(id_product);
        if(produit.isActive()){
            produit.setActive(false);
            return false;
        }else {
            produit.setActive(true);
            return true;
        }
    }

    @Override
    public boolean Activer_Reduct_Produit(long id_product) {
        Produit produit=produitRepository.getOne(id_product);
        if(produit.isAppliqueReduction()){
            produit.setAppliqueReduction(false);
            return false;
        }else {
            produit.setAppliqueReduction(true);
            return true;
        }
    }

    @Override
    public boolean Activer_User(Long id_user) {
        AppUser user=metierUser.getuser(id_user);
        if(user.isActivervendeur()){
            user.setActivervendeur(false);
            return false;
        }else {
            user.setActivervendeur(true);
            return true;
        }
    }

    @Override
    public boolean ActiverPrix(Long idproduit) {
        Produit produit=produitRepository.findPrByid(idproduit);
        if(produit.isAfficherprix()){
            produit.setAfficherprix(false);
            produit=produitRepository.save(produit);
            return produit.isAfficherprix();
        }else {
            produit.setAfficherprix(true);
            produit= produitRepository.save(produit);
            return produit.isAfficherprix();
        }
    }


    @Override
    public AppUser AddUser(AppUser user,String name_shop,String status) {
        AppUser appUser= metierUser.Adduser(user);
        Boutique boutique=metierBoutique.findByNomBoutique(name_shop);
        if (boutique==null) throw new RuntimeException("this Shop not exist");
        appUser.setBoutique(boutique);
        String stat=status.toUpperCase();
        if (status.equalsIgnoreCase("proprietaire")){

            metierAccount.addRoleToUser(appUser.getPhone(),"VENDEUR");
            metierAccount.addRoleToUser(appUser.getPhone(),"USER");
            metierAccount.addRoleToUser(appUser.getPhone(),stat);
        }
        if (status.equalsIgnoreCase("vendeur")){
            metierAccount.addRoleToUser(appUser.getPhone(),"USER");
            metierAccount.addRoleToUser(appUser.getPhone(),stat);
        }

        if (status.equalsIgnoreCase("user")){
            metierAccount.addRoleToUser(appUser.getPhone(),stat);
        }
        Favorites favorites=new Favorites();
        favorites.setAppUser(appUser);
        favoritesRepository.save(favorites);
        Cart cart= new Cart();
        cart =metierCart.addcart(cart);
        System.out.println("l'id du panier est: "+cart.getId_cart());
        cart.setUsercart(appUser);

        return metierAccount.saveuser(appUser);
    }
/*
 public Produit AddProduit(Produit p,String nom_boutisue) {
        System.out.println("pendant l'ajout du produit");
        Produit produit=new Produit();
        Boutique boutique=metierBoutique.findByNomBoutique(nom_boutisue);
        if (boutique.getIdBoutique()!=0){
            List<Produit> produitlist=boutique.getListProduitBoutique();

            if (produitlist.size()==0){

                p.setReferenceProduit(boutique.getNomBoutique()+p.getNomProduit()+ UUID.randomUUID().toString().substring(10).toUpperCase());
                if (p.getQuantiteEnStock()>0){
                    p.setQuantiteEnStock(p.getQuantiteEnStock()-p.getQuantity());
                }
                p.setBoutiquesProduit(boutique);
                produit=produitRepository.save(p);
                System.out.println("fin de l'ajout du produit"+produit.getNomProduit());

            }else {

                int i=0;
                int pr=3;
                while (i<produitlist.size()){


                    if(produitlist.get(i).getNomProduit().equalsIgnoreCase(p.getNomProduit())){
                        pr=0;
                    }

                    i++;
                }

                if (pr==0){
                    p.setReferenceProduit(boutique.getNomBoutique()+p.getNomProduit()+ UUID.randomUUID().toString().substring(10).toUpperCase());
                    if (p.getQuantiteEnStock()>0){
                        p.setQuantiteEnStock(p.getQuantiteEnStock()-p.getQuantity());
                    }
                    p.setBoutiquesProduit(boutique);
                    produit=produitRepository.save(p);
                    System.out.println("fin de l'ajout du produit"+produit.getNomProduit());
                }


            }



        }
        return  produit;
    }
 */



}
