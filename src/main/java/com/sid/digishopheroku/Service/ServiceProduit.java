package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.*;
import com.sid.digishopheroku.Metier.MetierProduit;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "serviceproduit")
public class ServiceProduit implements MetierProduit {
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    @Autowired
    private ProduitRepository produitRepository;
    ArrayList<Produit> produ= new ArrayList<>();

    @Override
    public List<Produit> findProduit() {
        return produitRepository.findByActiveIsTrue();
    }

    @Override
    public List<Produit> findAllProduit() {
        return produitRepository.findAll();
    }

    @Override
    public List<Produit> findProduitOrBoutique(String mot) {
        return produitRepository.findProduitOrBoutique(mot);
    }
    String path = new File("").getAbsolutePath();

    @Override
    public byte[] getphoto(Long id) throws Exception {
        String current = System.getProperty("user.dir");
        System.out.println("Current working directory in Java : " + current+File.separator+"/ecom/product/");
        Produit p=produitRepository.findById(id).get();
       return Files.readAllBytes(Paths.get(current+"/ecom/product/"+p.getPhotoProduit()));

    }

    @Override
    public Optional<Produit> getproduiById(Long id) {
        return produitRepository.findById(id);
    }

    @Override
    public Produit findproduiById(Long id) {
        return produitRepository.findPrByid(id);
    }

    @Override
    public Produit Add_QcodeToProduct(Long id_product, String qcode) {
        Produit produit=produitRepository.getOne(id_product);
        produit.setQcode(qcode);
        return produitRepository.save(produit);
    }

    @Override
    public Produit find_product_qcode(String qcode) {
        return produitRepository.findByQcode(qcode);
    }

    @Override
    public Produit getproduitById(Long id) {
        return produitRepository.findProduitById(id);
    }

    @Override
    public List<?> getBoutiqueAssocier(String mc) {
        return produitRepository.findBoutiqueAssocier(mc);
    }

    @Override
    public List<Produit> getProduitofBoutique(Long id) {

       /* Date date =new Date();
       List<Produit> produit=  produitRepository.findArrivageProduit(id);
       ArrayList<Produit> produits =new ArrayList<>();
       int i=0;
        while (produit.size()-1>i){
            System.out.println("la valeur de i est :"+i+" fokou");
            if (((int)(date.getTime()- produit.get(i).getDateproduit().getTime())/3600)<48){
                System.out.println("le jour est :"+(int)(produit.get(i).getDateproduit().getTime())/3600);
                System.out.print(produit.get(i).getNomProduit());

                produits.add(produit.get(i));
                return produit;
            }
            System.out.println("la valeur de i est :"+i);
           i++;
        }
        System.out.print("aucun produit ne respecte");*/
        return produitRepository.findProduitofBoutique(id);
    }

    @Override
    public Produit saveproduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> products_of_shop(Long id_shop) {
        Boutique boutique=boutiqueRepository.getOne(id_shop);
        System.out.println("le nom de la boutique est :"+boutique.getNomBoutique());
        return produitRepository.findAllByBoutiquesProduit(boutique);
    }

    @Override
    public List<Produit> products_of_shop_Activate(Long id_shop) {
        Boutique boutique=boutiqueRepository.getOne(id_shop);
        System.out.println("le nom de la boutique est :"+boutique.getNomBoutique());
        List<Produit> produit=produitRepository.findAllByBoutiquesProduitAndActiveIsTrue(boutique);
        return produit;
    }

    @Override
    public List<Produit> findAllByBoutiquesProduitAndArrivageIsTrue(Long id_shop) {
        Boutique boutique=boutiqueRepository.getOne(id_shop);
        System.out.println("le nom de la boutique est :"+boutique.getNomBoutique());
        List<Produit> produit=produitRepository.findAllByBoutiquesProduit(boutique);
        return produit;
    }

    @Override
    public Produit AssociatedProduitsTo(Produit produit, Long id) {
        Produit p = produitRepository.getOne(id);
        if (produit.getBoutiquesProduit().getIdBoutique()!= p.getBoutiquesProduit().getIdBoutique()) throw new RuntimeException(p.getNomProduit() + "This product does not belong to the shop : "+ produit.getBoutiquesProduit().getNomBoutique()  );
        produit.getListproduitAssociers().add(p);
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> getProduitsAssociesTo(Long id) {
        return produitRepository.getOne(id).getListproduitAssociers();
            }

    @Override
    public List<Boutique> getBoutiqueAssocies(Long id) {
        Produit produit = produitRepository.getOne(id);
        List<Boutique> list = new ArrayList<>();
List<Boutique> boutiques = boutiqueRepository.findAll();
        for (Boutique boutique: boutiques
             ) {
            boolean exist= false;
            for (Produit p : boutique.getListProduitBoutique()){
                if (p.getCatalogueProduit() == produit.getCatalogueProduit()){
                    exist=true;
                }
            }
            if (exist==true){list.add(boutique);}

        }
        return list;
    }

    @Override
    public Produit addViewToProduit(Long idProduit) {
        Produit produit = produitRepository.getOne(idProduit);
        if (produit == null) throw new RuntimeException("LE PRODUIT N'EXISTE PAS!");
        produit.setNbredevues(produit.getNbredevues()+1);
        return produitRepository.save(produit);
    }

    @Override
    public Produit findByNomProduit(String nomProduit) {
        return produitRepository.findByNomProduit(nomProduit);
    }
}
