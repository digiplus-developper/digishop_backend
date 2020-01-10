package com.sid.digishopheroku.Service;


import com.sid.digishopheroku.IDaoRepository.BoutiqueRepository;
import com.sid.digishopheroku.IDaoRepository.ProduitRepository;
import com.sid.digishopheroku.Metier.MetierBoutique;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service(value = "serviceboutique")
public class ServiceBoutique implements MetierBoutique {
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Override
    public List<Boutique> findBoutiqueByMot(String motcle) {
        return boutiqueRepository.findNomBoutiqueByMot("%"+motcle+"%");//methode qui find une boutique par mot cle
    }

    @Override
    public ArrayList<String> findAllProduitBoutique() {
        ArrayList<String> listProduiBoutiq=new ArrayList<>();
        ArrayList<Produit> lo= (ArrayList<Produit>) produitRepository.findByActiveIsTrue();
        ArrayList<Boutique> bo= (ArrayList<Boutique>) boutiqueRepository.findAll();

        int i=0,j=0;
        if (produitRepository.findByActiveIsTrue().size()>=0){
            while (i<produitRepository.findByActiveIsTrue().size()) {
                String pro=lo.get(i).getNomProduit();
                listProduiBoutiq.add(pro);
                i++;
            }
        }
        if (boutiqueRepository.findAll().size()>=0) {
            while (j < boutiqueRepository.findAll().size()) {
                String pro = bo.get(j).getNomBoutique();
                listProduiBoutiq.add(pro);
                j++;
            }
        }
        return listProduiBoutiq;
    }

    @Override
    public List<Boutique> findBoutiqueAssocie(String mc) {

        List<Boutique> boutiqueList= new ArrayList<>();
        List<Boutique> Listboutique=boutiqueRepository.findNomBoutiqueByMot(mc);
        ArrayList<Produit> produits= (ArrayList<Produit>) produitRepository.findProduitByNomProduit(mc);
        int i=0;
        int j=0;
        while (i<=Listboutique.size()){
            Boutique bou=Listboutique.get(i);
            Produit pro=produits.get(i);
            Boutique b=new Boutique();
            if (mc.equalsIgnoreCase(bou.getNomBoutique())){
                boutiqueList.add(bou);
            }
            if (pro.getNomProduit().equalsIgnoreCase(mc)){
                Long id=pro.getIdProduit();

            }
            i++;
        }
        return boutiqueList;
    }

    @Override
    public List<?> findBoutiqueOrProduit(String mc) {
       if ((findBoutiqueByMot(mc).size()!=0)&&(produitRepository.findProduitByNomProduit(mc).size()!=0) ){

           List<?> prodandbou=findBoutiqueByMot(mc);
           prodandbou.containsAll(produitRepository.findProduitByNomProduit(mc));

           return prodandbou;
       }else if (produitRepository.findProduitByNomProduit(mc).size()!=0){
           return produitRepository.findProduitByNomProduit(mc);
       }else {
           return findBoutiqueByMot(mc);
       }
    }

    @Override
    public List<Boutique> findBoutiques(){
        return boutiqueRepository.findAll();
    }

    @Override
    public Boutique getBoutiqueById(Long id) {
        return boutiqueRepository.getOne(id);
    }

    @Override
    public Boutique findByNameShop(String name_shop) {
        return boutiqueRepository.findByNomBoutique(name_shop);
    }

    @Override
    public Boutique saveboutique(Boutique boutique) {
        return boutiqueRepository.save(boutique);
    }

    @Override
    public byte[] getphoto(Long id) throws Exception {
        String current = System.getProperty("user.dir");
        System.out.println("Current working directory in Java : " + current+ File.separator+"/ecom/shop/");
        Boutique b=boutiqueRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(current+"/ecom/shop/"+b.getPhotoboutique()));
    }

    @Override
    public Boutique findByNomBoutique(String nom_boutisue) {
        return boutiqueRepository.findByNomBoutique(nom_boutisue);
    }

    @Override
    public Boutique findByTelephoneBoutique(String numero) {
        return boutiqueRepository.findByTelephoneBoutique(numero);
    }


    @Override
    public Boutique addViewToBoutique(Long idBoutique) {
        Boutique boutique = boutiqueRepository.getOne(idBoutique);
        if (boutique==null) throw  new RuntimeException("LA BOUTIQUE N'EXISTE PAS!");
            boutique.setNbredevues(boutique.getNbredevues() + 1);
            return boutiqueRepository.save(boutique);

    }
}
