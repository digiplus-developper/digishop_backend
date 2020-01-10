package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.*;
import com.sid.digishopheroku.Model.*;
import com.sid.digishopheroku.WebRestfull.Forms.NewStockForm;
import com.sid.digishopheroku.WebRestfull.Forms.TransactionStockForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/product")
public class ProduitResfull {
    @Autowired
    MetierProduit metierProduit;
    @Autowired
    MetierCartitem metierCartitem;
    @Autowired
    MetierStock metierStock;
    @Autowired
    MetierTransaction metierTransaction;
    @Autowired MetierCategorie metierCategorie;

/*cette methode retourne les produits activés*/
    @RequestMapping(value = "/produits", method = RequestMethod.GET)
    public List<Produit> getAllProduit() {
        return metierProduit.findProduit();
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<Produit> findAllProduit() {
        return metierProduit.findAllProduit();
    }

    @GetMapping(path = "/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getphoto(@PathVariable Long id)throws Exception{
       return metierProduit.getphoto(id);
    }



    @PostMapping(path ="/upload/{idproduit}")
    public void uploadphotoboutique( MultipartFile file, @PathVariable Long idproduit) throws Exception{
        Produit p=metierProduit.findproduiById(idproduit);
        p.setPhotoProduit(idproduit+".png");
        String current = System.getProperty("user.dir");
        Files.write(Paths.get(current+"/ecom/product/"+p.getPhotoProduit()),file.getBytes());
        System.out.println("fichier enregistrer");
        metierProduit.saveproduit(p);

    }


    @GetMapping(value = "/{mot}")
    public List<Produit> getProduitOrBoutique(@PathVariable String mot) {
        return metierProduit.findProduitOrBoutique("%"+mot+"%");
    }
//retourne les produits d'une boutiques
    @GetMapping(value = "/products_of_shop_active/{id_shop}")
    public List<Produit> products_of_shop_Activite(@PathVariable Long id_shop) {
        return metierProduit.products_of_shop(id_shop);
    }
    //retourne les produits d'une boutiques
    @GetMapping(value = "/products_of_shop/{id_shop}")
    public List<Produit> products_of_shop(@PathVariable Long id_shop) {
        return metierProduit.products_of_shop(id_shop);
    }
//cette methode retoure les nouvelles arrivage
    @GetMapping(value = "/arriver_of_shop/{id_shop}")
    public List<Produit> arriver_of_shop(@PathVariable Long id_shop) {
        return metierProduit.findAllByBoutiquesProduitAndArrivageIsTrue(id_shop);
    }

    @GetMapping(value = "/qcode/{qcode}")
    public Produit FindProductQcode(@PathVariable String qcode) {
        return metierProduit.find_product_qcode(qcode);
    }

    @GetMapping(value = "/getproduitbyid/{id}")
    public Optional<Produit> getProduitOrBoutique(@PathVariable Long id) {
        return metierProduit.getproduiById(id);
    }

    @GetMapping(value = "/getproduitassocier/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return metierProduit.getproduitById(id);
    }

    @GetMapping(value = "/getboutiqueassocier/{mc}")//pour retourner la liste des boutiques associers a un produit
    public List<?> getBoutiqueAssocier(@PathVariable String mc) {
        return metierProduit.getBoutiqueAssocier("%"+mc+"%");
    }

    @GetMapping(value = "/getproduitofboutique/{id}")
    public List<Produit> getArrivageProduit(@PathVariable Long id) {
        return metierProduit.getProduitofBoutique(id);
    }
/* G E S T I O N   DES   S T O C K S */
// 1 ajout ou mise à jour de stock
    @PostMapping(value = "/addNewStock/{id_produit}/{id_user}")
    public Stock addNewStock(@RequestBody NewStockForm stockForm, @PathVariable Long id_produit, @PathVariable Long id_user){

        Produit produit = metierProduit.findproduiById(id_produit);
       // Stock stock = metierStock.repartirNouveauStock(stockForm);
       // Stock stock = metierStock.addNewStock(produit,stock);

        Stock stock = metierStock.addNewStock(produit,stockForm);

        metierTransaction.newStockTransaction(stock,id_user);

        return stock;
    }
    // 2- reserver un stock -
//    @GetMapping(value = "reserveStock/{id_produit}/quantity/{quantity}/{id_user}")
//    public Stock reserveStock(@PathVariable Long id_produit,@PathVariable int quantity,@PathVariable Long id_user){
//        Produit produit = metierProduit.findproduiById(id_produit);
//        Stock stock = metierStock.reserveStock(produit,quantity);
//
//metierTransaction.reservationStock(stock,quantity,id_user);
//        return stock;
//    }
    @PostMapping(value = "reserveStock")
    public Stock reserveStock(@RequestBody TransactionStockForm transactionStockForm){
        if (!(transactionStockForm.getTypeTransaction().equalsIgnoreCase(TYPETRANSACTION.RESERVATION_STOCK)))throw new RuntimeException("Le type de l'operation est different de : "+TYPETRANSACTION.RESERVATION_STOCK);
        Produit produit = metierProduit.findproduiById(transactionStockForm.getIdProduit());
        Stock stock = metierStock.reserveStock(produit, transactionStockForm.getQuantity());

        metierTransaction.reservationStock(stock, transactionStockForm.getQuantity(), transactionStockForm.getIdUser(), transactionStockForm.getCommentaire());
        return stock;
    }
//3- sortie de stock // suppression d'une quantite
//    @GetMapping(value = "sortieStock/{id_produit}/quantity/{qty}/{id_user}")
//    public Stock sortieStock(@PathVariable int qty, @PathVariable Long id_produit,@PathVariable Long id_user){
//    Produit produit = metierProduit.findproduiById(id_produit);
//    Stock stock = metierStock.sortieStock(produit,qty);
//
//    metierTransaction.sortieStock(stock,qty,id_user);oui
//    }
@PostMapping(value = "sortieStock")
public Stock sortieStock(@RequestBody TransactionStockForm transactionStockForm){
    if (!(transactionStockForm.getTypeTransaction().equalsIgnoreCase(TYPETRANSACTION.SORTIE_STOCK)))throw new RuntimeException("Le type de l'operation est different de : "+TYPETRANSACTION.SORTIE_STOCK);

    Produit produit = metierProduit.findproduiById(transactionStockForm.getIdProduit());
    Stock stock = metierStock.sortieStock(produit,transactionStockForm.getQuantity());

    metierTransaction.sortieStock(stock,transactionStockForm.getQuantity(),transactionStockForm.getIdUser(),transactionStockForm.getCommentaire());
    return stock;
}
//4- retour d'un produit en boutique
    @PostMapping(value = "retourProduit")
       public Stock retourProduit(@RequestBody TransactionStockForm transactionStockForm ){
        if (!(transactionStockForm.getTypeTransaction().equalsIgnoreCase(TYPETRANSACTION.RETOUR_PRODUIT)))throw new RuntimeException("Le type de l'operation est different de : "+TYPETRANSACTION.RETOUR_PRODUIT);

        Produit produit = metierProduit.findproduiById(transactionStockForm.getIdProduit());
        Stock stock = metierStock.retourProduit(produit,transactionStockForm.getQuantity());
        metierTransaction.retourProduit(produit,transactionStockForm.getQuantity(),transactionStockForm.getIdUser(),transactionStockForm.getCommentaire());
        return stock;
    }

/* resultat en Millisecondes - duree avant qu un stock n'atteigne sa date d'expiration*/
@GetMapping(value = "getProductValidity/{id_produit}")
    public Long produit_validity(@PathVariable Long id_produit){
        Produit produit = metierProduit.findproduiById(id_produit);
        return metierStock.calcul_product_validity(produit);
}/* resultat en Millisecondes -- temps mis par un produit en stock*/
    @GetMapping(value = "/getProductDuration/{id_produit}")
    public Long produit_duration(@PathVariable Long id_produit){
        Produit produit = metierProduit.findproduiById(id_produit);
        return metierStock.calcul_stock_duration(produit);
    }
/* resultat TRUE OR FALSE --  selon si le produit a atteint sa quantite critique */
    @GetMapping(value = "/stockLimitNotification/{id_produit}")
    public Boolean getLimitNotification(@PathVariable Long id_produit){
        return metierStock.stock_limit_checked(metierProduit.findproduiById(id_produit));
    }

/* Retourne toutes les transactions */
@GetMapping("/all_transaction/{id_user}")
    List<Transaction> getalltran(@PathVariable Long id_user){
    return metierTransaction.getalltransactionbyuser(id_user);
}

    /* -reserver un stock

    * -sortir un stock :
    *        sortie directe
    *        sortie pour une commande en ligne
    *
    *  -rechercher des articles par noms
    *  -filtrer les articles par categorie, statut*/


    /* P R O D U I T S   ET   B O U T I Q U E   A S S O C I E S*/
    @GetMapping(value = "boutiquesAssociesTo/produit/{id}")
    public List<Boutique> getBoutiqueAssociesTo(@PathVariable Long id){
        return metierProduit.getBoutiqueAssocies(id);
    }
    @PostMapping(value = "associateProduitTo/{id}/produit/{id_pdtAssocie}")
    Produit associerProduitTo(@PathVariable Long id , @PathVariable Long id_pdtAssocie){
        return metierProduit.AssociatedProduitsTo(metierProduit.findproduiById(id),id_pdtAssocie);
    }
    @GetMapping(value = "produitsAssociesTo/{id}")
    List<Produit> getProduitsAssociesTo(@PathVariable Long id){
        return metierProduit.getProduitsAssociesTo(id);
    }


    /*C A T E G O R I E   ET   C A T A L O G U E   DE   P R O D U I T S*/

    @PostMapping("/category/addcategory")
        // creer une nouvelle categorie
    CategorieProduit addNewCategory(@RequestBody CategorieProduit categorieProduit){

        return metierCategorie.createCategorieProduit(categorieProduit);
    }
    //retrouver une categorie existante à partir de son ID
    @GetMapping("/category/getcategorybyid/{id}")
    CategorieProduit getCategoryById(@PathVariable Long id){
        return metierCategorie.findCategorieProduitById(id);
    }
    //Mettre a jour une categorie existante
    @PutMapping("/category/updatecategory/{id}")
    CategorieProduit updateCategory(@PathVariable Long id,@RequestBody CategorieProduit categorie){
        categorie.setId(id);
        return metierCategorie.updateCategorieProduit(categorie);
    }
    // supprimer une categorie
    @DeleteMapping("/category/deletecategory/{id}")
    void deleteategoryById(@PathVariable Long id){
        metierCategorie.deleteCategorieProduit(id);
    }
    //liste de toutes les categories existantes
    @GetMapping("/category/getallcategories")
    List<CategorieProduit> getAllCategories(){
        return metierCategorie.findAllCategories();
    }
    //liste des categories  par classe
    @GetMapping("/category/findbyclasse/{classe}")
    List<CategorieProduit> getCategoryByClasse(@PathVariable String classe){
        return metierCategorie.findCategoriesByClasse(classe);
    }
    //liste des catgories par nom
    @GetMapping("/category/getcategorybynom/{nom}")
    CategorieProduit getCategoryByNom(@PathVariable String nom){
        return metierCategorie.findCategorieProduitByNom(nom);
    }
    //liste des categories par type
    @GetMapping("/category/getcategorybytype/{type}")
    List<CategorieProduit> getCategoryById(@PathVariable String type){
        return metierCategorie.findCategoriesByType(type);
    }
    //Ajouter un catalogue à une Categorie
    @GetMapping("/category/addcataloguetocategory/{id_category}")
    CategorieProduit gaddCatalogueToCategorie(@PathVariable Long id_category,@RequestBody CatalogueProduit catalogue){
        return metierCategorie.addCatalogueToCategorie(metierCategorie.findCategorieProduitById(id_category),catalogue);
    }
    //popularite d une categorie -- nombre de vues  ********** NOT READY
    @GetMapping("/category/getviewsof/{id_category}")
    int ViewsPerProductCategory(@PathVariable Long id_category){
        return metierCategorie.ViewsPerProductCategory(metierCategorie.findCategorieProduitById(id_category));
    }
    // ************** NOT READY
    @GetMapping("/category/getallproductsof/{id_category}")
    List<Produit> findProduitsByCategorie(@PathVariable Long id_category){
        return metierCategorie.findProduitsByCategorie(metierCategorie.findCategorieProduitById(id_category));
    }

    // Reporting 11 - ajouter une vue à un produit
    @PostMapping("/addviewTo/{idProduit}")
    Produit addOneView(@PathVariable Long idProduit){
        return metierProduit.addViewToProduit(idProduit);
    }
}
