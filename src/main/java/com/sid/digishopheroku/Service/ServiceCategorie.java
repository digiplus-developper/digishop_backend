package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.BoutiqueRepository;
import com.sid.digishopheroku.IDaoRepository.CategorieProduitRepository;
import com.sid.digishopheroku.IDaoRepository.ProduitRepository;
import com.sid.digishopheroku.Metier.MetierCategorie;
import com.sid.digishopheroku.Model.CatalogueProduit;
import com.sid.digishopheroku.Model.CategorieProduit;
import com.sid.digishopheroku.Model.Produit;
import com.sid.digishopheroku.RuntimeExceptionMsg.CategorieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "serviceCategorie")
public class ServiceCategorie implements MetierCategorie {

    @Autowired
    CategorieProduitRepository categoriePdtRepository;

    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    BoutiqueRepository boutiqueRepository;

    /* ****************service categorie de boutiques */
//    @Override
//    public CategorieBoutique createCategorieBoutique(CategorieBoutique categorieBoutique) {
//        if (categorieBoutique == null) throw new RuntimeException(CategorieException.NULL_CATEGORY);
//        return categorieBRepository.save(categorieBoutique);
//    }
//
//    @Override
//    public void deleteCategorieBoutique(Long id) {
//        if (categorieBRepository.getOne(id) == null) throw  new RuntimeException(CategorieException.NULL_CATEGORY);
//         categorieBRepository.deleteById(id);
//    }
//
//    @Override
//    public CategorieBoutique getOneCategorieBoutique(Long id) {
//        return categorieBRepository.getOne(id);
//    }
//
//    @Override
//    public CategorieBoutique updateCategorieBoutique(CategorieBoutique categorieBoutique) {
//        if (categorieBoutique.getId()==null) throw new RuntimeException(categorieBoutique.getNomCategorie() + " doesnt exist. Operation Aborted!");
//        CategorieBoutique  categorie = categorieBRepository.getOne(categorieBoutique.getId());
//        categorie.setDescription(categorieBoutique.getDescription());
//        categorie.setImage(categorieBoutique.getImage());
//        categorie.setNomCategorie(categorieBoutique.getNomCategorie());
//        categorie.setTypeBoutique(categorieBoutique.getTypeBoutique());
//
//        return categorieBRepository.save(categorie);
//    }
//
//
//    @Override
//    public CategorieBoutique findCategorieBoutiqueByNom(String nomC) {
//        return categorieBRepository.findByNomCategorieContaining(nomC);
//    }
//
//
//    @Override
//    public CategorieBoutique findCategorieBoutiqueById(Long id) {
//        return categorieBRepository.getOne(id);
//    }
//
//    @Override
//    public List<Boutique> findShopsByNomCategory(String nomCategorieBoutique) {
//        CategorieBoutique categorieBoutique = categorieBRepository.findByNomCategorieContaining(nomCategorieBoutique);
//        return boutiqueRepository.findAllByCategorieBoutiqueListIsContaining(categorieBoutique);
//    }
//
//    @Override
//    public List<Boutique> findShopsByCategory(CategorieBoutique categorieBoutique) {
//        return boutiqueRepository.findAllByCategorieBoutiqueListIsContaining(categorieBoutique);
//    }
//
//    @Override
//    public List<CategorieBoutique> findCategoriesByTypeBoutique(String typeBoutique) {
//
//        return categorieBRepository.findAllByTypeBoutiqueContaining(typeBoutique);
//    }
//
//    @Override
//    public int ViewsPerShopCategory(CategorieBoutique categorieBoutique) {
//
//        return 0;
//    }
    /* *************************service categorie de produits */

    @Override
    public CategorieProduit createCategorieProduit(CategorieProduit categorieProduit) {
        if (categorieProduit == null) throw new RuntimeException(CategorieException.NULL_CATEGORY);
        return categoriePdtRepository.save(categorieProduit);    }

    @Override
    public void deleteCategorieProduit(Long id) {
        if (categoriePdtRepository.getOne(id) == null) throw  new RuntimeException(CategorieException.NULL_CATEGORY);
        categoriePdtRepository.deleteById(id);
    }

    @Override
    public CategorieProduit getOneCategorieProduit(Long id) {
        return null;
    }


    @Override
    public CategorieProduit updateCategorieProduit(CategorieProduit categorieProduit) {
        if (categorieProduit.getId()==null) throw new RuntimeException(categorieProduit.getNomCategorie() + " doesnt exist. Operation Aborted!");
        CategorieProduit cpdt = categoriePdtRepository.getOne(categorieProduit.getId());
        cpdt.setClasse(categorieProduit.getClasse());

        cpdt.setType(categorieProduit.getType());

        return categoriePdtRepository.save(cpdt);
    }



    @Override
    public CategorieProduit findCategorieProduitByNom(String nomC) {
        return null;
    }
    @Override
    public CategorieProduit findCategorieProduitById(Long id) {
        return categoriePdtRepository.getOne(id);
    }


//TODO
    @Override
    public int ViewsPerProductCategory(CategorieProduit categorieProduit) {
        int views = 0;
//        for (Produit p : categorieProduit.getProduitList()
//             ) {
//            views += p.getNbredevues();
//        }
        return views;
    }



    @Override
    public List<Produit> findProduitsByCategorie(CategorieProduit categorieProduit) {
        //return produitRepository.findAllByCategorieProduit(categorieProduit);
        return null;
    }
    @Override
    public List<Produit> findProduitsByNomCategorie(String nomCategorieProduit) {
        CategorieProduit categorieProduit = categoriePdtRepository.findByNomCategorie(nomCategorieProduit);
       return null;
       // return produitRepository.findAllByCategorieProduit(categorieProduit);
    }


    @Override
    public List<CategorieProduit> findCategoriesByClasse(String classe) {
        return categoriePdtRepository.findByClasseContaining(classe);
    }

    @Override
    public List<CategorieProduit> findCategoriesByType(String type) {
        return categoriePdtRepository.findByTypeContaining(type);
    }

    @Override
    public List<CategorieProduit> findAllCategories() {
        return categoriePdtRepository.findAll();
    }

    @Override
    public CategorieProduit addCatalogueToCategorie(CategorieProduit categorieProduit,CatalogueProduit catalogueProduit) {
        if (!(categorieProduit.getCatalogueProduits().contains(catalogueProduit))) {
            categorieProduit.getCatalogueProduits().add(catalogueProduit);
        }
        return categoriePdtRepository.save(categorieProduit);
    }
}
