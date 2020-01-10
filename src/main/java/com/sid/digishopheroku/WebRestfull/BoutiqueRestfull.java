package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.*;
import com.sid.digishopheroku.Model.*;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;
import com.sid.digishopheroku.WebRestfull.Forms.InventaireForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@CrossOrigin("*")
@RequestMapping("/shop")
@RestController
public class BoutiqueRestfull {
@Autowired
private MetierBoutique metierBoutique;
@Autowired
    MetierInventaire metierInventaire;
@Autowired
    MetierUser metierUser;
@Autowired
    MetierProduit metierProduit;
@Autowired
    MetierInventaireItem metierInventaireItem;
@Autowired
MetierTransaction metierTransaction;
@Autowired
MetierCashTransaction metierCashTransaction;
@Autowired
MetierCashSession metierCashSession;
@Autowired
MetierCategorie metierCategorie;

    @GetMapping(path = "/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getphoto(@PathVariable Long id)throws Exception{
        return metierBoutique.getphoto(id);
    }


   @RequestMapping(value = "/findallmot", method = RequestMethod.GET)//ici on recupere la liste de tous les noms de boutiques et produits
    public List<String> getAllProduitBoutique() {
        return metierBoutique.findAllProduitBoutique();
    }

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    public List<Boutique> getAllBoutique() {
        return metierBoutique.findBoutiques();
    }

    @RequestMapping(value = "/find/{mc}", method = RequestMethod.GET)
    public List<Boutique> getBoutiqueAssocier(@PathVariable String mc) {
        return metierBoutique.findBoutiqueAssocie(mc);
    }

    @RequestMapping(value = "/name/{name_shop}", method = RequestMethod.GET)
    public Boutique getshopbyname(@PathVariable String name_shop) {
        return metierBoutique.findByNomBoutique(name_shop);
    }

    @RequestMapping(value = "/getbout/{id}", method = RequestMethod.GET)
    public Boutique getBoutiqueById(@PathVariable Long id) {
        return metierBoutique.getBoutiqueById(id);
    }


    @RequestMapping(value = "/findprodorbou/{mc}", method = RequestMethod.GET)
    public List<?> getBoutiqueOrProduit(@PathVariable String mc) {
        return metierBoutique.findBoutiqueOrProduit("%"+mc+"%");
    }

    @RequestMapping(value = "/produit/{mot}", method = RequestMethod.GET)
    public List<Boutique> getBoutiqueByMot(@RequestParam(name="mot", defaultValue="") String mot) {
        return metierBoutique.findBoutiqueByMot("%"+mot+"%");
    }

    @PostMapping(path ="/uploadphotoboutique/{idboutiq}")
    public void uploadphotoboutique(MultipartFile file,@PathVariable Long idboutiq) throws Exception{
       Boutique boutique=metierBoutique.getBoutiqueById(idboutiq);
       boutique.setPhotoboutique(idboutiq+".png");

            Files.write(Paths.get(System.getProperty("user.home")+"/ecom/shop/"+boutique.getPhotoboutique()),file.getBytes());
            metierBoutique.saveboutique(boutique);

    }

    /* G E S T I O N   DES   I N V E N T A I R E S*/
    @GetMapping(value = "/findInventaireByBoutique/{id_boutique}")
    public List<Inventaire> findByBoutique(@PathVariable Long id_boutique){
        return metierInventaire.findAllByBoutique(metierBoutique.getBoutiqueById(id_boutique));
    }

    @GetMapping(value = "/findInventaireBySalesman/{id_user}")
    public List<Inventaire> findByUser(@PathVariable Long id_user){
        return metierInventaire.findAllByUser(metierUser.getuser(id_user));
    }

    @GetMapping(value = "/findInventairesByDate/{date}")
    public List<Inventaire> findByDate(@PathVariable Date date){
        return metierInventaire.findAllByDateInventaireBefore(date);
    }
/* creer un nouvel inventaire */
    @PostMapping(value = "/newInventaire")
    public Inventaire newInventaire(@RequestBody List<InventaireForm> inventaireFormList){
        List<InventaireItem> inventaireItemList = new ArrayList<>();

        Inventaire inventaire = metierInventaire.saveInventaire(new Inventaire());
        System.out.println("la shop est"+inventaireFormList.size());

        AppUser appUser=metierUser.getuser(inventaireFormList.get(0).getIdVendeur());
        System.out.println("l' user est"+appUser.getUsername());
        Boutique boutique=appUser.getBoutique();
        inventaire.setBoutique(boutique);
/*Determination du type d'inventaire */
        if (boutique.getListProduitBoutique().size() == inventaireFormList.size()){
            inventaire.setTypeInventaire(TYPEINVENTAIRE.GLOBAL);
        }else {
            inventaire.setTypeInventaire(TYPEINVENTAIRE.PARTIEL);
        }
        for (InventaireForm inventaireForm : inventaireFormList){
            InventaireItem item = metierInventaireItem.addProduitToInventaireItem(inventaire,inventaireForm);
            inventaireItemList.add(item);
        }
        metierInventaireItem.saveAllInventaireItem(inventaireItemList);
        AppUser user =  metierUser.findByiduser(inventaireFormList.get(0).getIdVendeur());

        inventaire = metierInventaire.addNewInventaireItem(inventaire,inventaireItemList,user);

        return metierInventaire.saveInventaire(inventaire);
    }
// Mise a jour du stock apres avoir fait un Inventaire
    @GetMapping("/updateStock/inventaire/{id_inventaire}/commentaire/{commentaire}")
    public void updateStockAfterInventaire(@PathVariable Long id_inventaire,@PathVariable String commentaire){
        Inventaire inventaire = metierInventaire.findById(id_inventaire);
        metierInventaire.updateStockInventaire(inventaire);
        metierTransaction.updateStockAfterInventory(inventaire,commentaire);

    }


    /* G E S T I O N   DES   C A I S S E S*/
    @PostMapping(value = "/cashopen")
    public CashSession ouverturecaisse(@RequestBody CashTransactionForm cashTransactionForm){
        return metierCashSession.ouvertureCaisse(cashTransactionForm);
    }

    @PostMapping(value = "/cashclose")
    public CashSession fermeturerecaisse(@RequestBody CashTransactionForm cashTransactionForm){
        return metierCashSession.fermetureCaisse(cashTransactionForm);
    }

    /* G E S T I O N  DE  LA  C A I S S E*/
    // cash in
    @PostMapping("/cashin/")
    public CashSession entreeEnCaisse(@RequestBody CashTransactionForm transactionForm){

        return metierCashTransaction.entrerEnCaisse( metierCashSession.entrerCaisse(transactionForm),transactionForm);
    }
    //cash out
    @PostMapping("/cashout/")
    public CashSession SortieEnCaisse(@RequestBody CashTransactionForm transactionForm){

        return metierCashTransaction.sortieEnCaisse( metierCashSession.sortieCaisse(transactionForm),transactionForm);
    }
    /* LISTE DES TRANSACTIONS DE LA CAISSE*/
    @GetMapping("/all_transact_session/{id_cashsession}")
    List<CashTransaction> getcashtrans_session(@PathVariable Long id_cashsession){
        return metierCashTransaction.getcashtrans_session(id_cashsession);
    }


/*Repoting 11  : AJOUTER DES VUES A UNE BOUTIQUE */
    @PostMapping("/addviewTo/{idBoutique}")
    Boutique addOneView(@PathVariable Long idBoutique){
        return metierBoutique.addViewToBoutique(idBoutique);
    }

}
