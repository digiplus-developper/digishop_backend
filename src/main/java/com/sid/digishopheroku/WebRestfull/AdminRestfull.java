package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierAdmin;
import com.sid.digishopheroku.Metier.MetierProduit;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("/admin")
@RestController
public class AdminRestfull {

    @Autowired
    MetierUser metierUser;
    @Autowired
    MetierProduit metierProduit;
    @Autowired
    MetierAdmin metierAdmin; //pour le faible couplage
    /* Ajouter un nouveau produit */
    @RequestMapping(value = "/add/product/{nom_boutisue}", method = RequestMethod.POST)
    public Produit AddProduit(@RequestBody Produit produit,@PathVariable String nom_boutisue){
        System.out.println("debut de l'ajout du produit");
        System.out.println(produit.getCatalogueProduit());
        return metierAdmin.AddProduit(produit,nom_boutisue);
    }

    /* Activer ou  desactiver le prix d'un produit */
    @RequestMapping(value = "/afficherprix/{idproduit}", method = RequestMethod.GET)
    public boolean AfficherPrix(@PathVariable Long idproduit){
        return metierAdmin.ActiverPrix(idproduit);
    }

    /* Ajouter un nouveau produit */
    @PutMapping(value = "/add/product_associer/{id_produit_associer}")
    public Produit AddProduit_Associer(@RequestBody Produit produit,@PathVariable Long id_produit_associer){
        return metierAdmin.Addproduct_Associer(produit,id_produit_associer);
    }

    @PutMapping(value = "/rm/product_associer/{id_produit_associer}")
    public boolean De_associer(@RequestBody Long id_produp,@PathVariable Long id_produit_associer){
        return metierAdmin.De_associerProduct(id_produp,id_produit_associer);
    }

    @RequestMapping(value = "/find/user/{status}", method = RequestMethod.GET)
    public List<AppUser> findalluser(@PathVariable String status){
        return metierAdmin.findAllUser(status);
    }

    @RequestMapping(value = "/add/shop", method = RequestMethod.POST)
    public Boutique Addboutique(@RequestBody Boutique b){
        return metierAdmin.AddBoutique(b);
    }

    @PostMapping("/uploadboutique/{idboutique}")
    void uploadboutique(MultipartFile file, @PathVariable Long idboutique)throws Exception{
        metierAdmin.uploadboutique(file,idboutique);
    }

    @PostMapping("/uploadproduct/{idproduct}")
    void uploadproduct(MultipartFile file, @PathVariable Long idproduct)throws Exception{
        metierAdmin.uploadproduct(file,idproduct);
    }


    @RequestMapping(value = "/update/user", method = RequestMethod.PUT)
    public AppUser UpdateUser(@RequestBody AppUser user){
        return metierAdmin.updateUser(user);
    }


    /** creation d un vendeur */
    @RequestMapping(value = "/update/shop", method = RequestMethod.PUT)
    public Boutique UpdateShop(@RequestBody Boutique b){
        return metierAdmin.UpdateShop(b);
    }

    @RequestMapping(value = "/update/product", method = RequestMethod.PUT)
    public Produit UpdateProduct(@RequestBody Produit p){
        return metierAdmin.UpdateProduct(p);
    }



    /** creation d un vendeur */
    @RequestMapping(value = "/add/user/{name_shop}/{status}", method = RequestMethod.POST)
    public AppUser AddVendeur(@RequestBody AppUser user,@PathVariable String name_shop,@PathVariable String status){
        return metierAdmin.AddUser(user, name_shop,status);
    }



    @RequestMapping(value="/delete/produit/{idroduit}/boutique/{idBoutique}",method= RequestMethod.DELETE)
    public boolean delectProduit(@PathVariable Long idProduit,@PathVariable Long idBoutique){
        metierAdmin.delectProduit(idProduit,idBoutique);
        return true;
    }

    @RequestMapping(value="/delect/user/{id_user}",method= RequestMethod.DELETE)
    public boolean delectUser(@PathVariable Long id_user){
        metierAdmin.delectUser(id_user);
        return true;
    }

@PostMapping("/promotion/{id_product}/{montant}/{date_debut}")
public Produit createPromotion(@PathVariable Long id_product, @PathVariable double montant,@PathVariable String date_debut,@RequestBody String date_fin){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(date_debut+" et de fin est: "+date_fin);
    Date datefin=null,datedebut= null;
    try {
        datefin = simpleDateFormat.parse(date_fin);
        datedebut=simpleDateFormat.parse(date_debut);
        System.out.println(datedebut+" et de fin est: "+datefin);
    } catch (ParseException e) {
        e.printStackTrace();
    }

       return metierAdmin.AddPromotion(id_product,montant,datedebut,datefin);
}

    @GetMapping(value = "/produitboutique/{mot}")
    public List<Produit> getProduitByMot(@PathVariable String mot) {
        return metierAdmin.findProduitBoutiqueByMot(mot);
    }



    @GetMapping(value = "/qcode/{id_product}/{qcode}")
    public Produit AddQcode(@PathVariable long id_product, @PathVariable String qcode) {
        return metierProduit.Add_QcodeToProduct(id_product,qcode);
    }
    @GetMapping(value = "/product/activer/{id_product}")
    public boolean Active_product(@PathVariable long id_product) {
        return metierAdmin.Activer_Produit(id_product);
    }

    @GetMapping(value = "/product/reduction/{id_product}")
    public boolean Active_Reduct_product(@PathVariable long id_product) {
        return metierAdmin.Activer_Reduct_Produit(id_product);
    }

    @GetMapping(value = "/user/activer/{id_user}")
    public boolean Active_User(@PathVariable long id_user) {
        return metierAdmin.Activer_User(id_user);
    }

}
