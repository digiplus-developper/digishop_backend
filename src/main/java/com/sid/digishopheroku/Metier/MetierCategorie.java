package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.CatalogueProduit;
import com.sid.digishopheroku.Model.CategorieProduit;
import com.sid.digishopheroku.Model.Produit;

import java.util.List;

public interface MetierCategorie {
    /*C A T E G O R I E  DE  B O U T I Q U E S*/
 /*   //CRUD
    CategorieBoutique createCategorieBoutique(CategorieBoutique categorieBoutique);
    void deleteCategorieBoutique(Long id);
    CategorieBoutique getOneCategorieBoutique(Long id);
    //search
    List<Boutique> findShopsByNomCategory(String nomCategorieBoutique);
    List<Boutique> findShopsByCategory(CategorieBoutique categorieBoutique);

    CategorieBoutique updateCategorieBoutique(CategorieBoutique categorieBoutique);

    CategorieBoutique findCategorieBoutiqueByNom(String nomC);
    CategorieBoutique findCategorieBoutiqueById(Long id);
    List<CategorieBoutique> findCategoriesByTypeBoutique(String typeBoutique);
    int ViewsPerShopCategory( CategorieBoutique categorieBoutique);

*/


    /*C A T E G O R I E   DE   P R O D U I T S*/
    CategorieProduit createCategorieProduit(CategorieProduit categorieProduit);
    void deleteCategorieProduit(Long id);
    CategorieProduit getOneCategorieProduit(Long id);

    CategorieProduit updateCategorieProduit(CategorieProduit categorieProduit);


    CategorieProduit findCategorieProduitByNom(String nomC);
    CategorieProduit findCategorieProduitById(Long id);


    List<CategorieProduit> findCategoriesByClasse(String classe);
    List<CategorieProduit> findCategoriesByType(String type);
    List<CategorieProduit> findAllCategories();
    CategorieProduit addCatalogueToCategorie(CategorieProduit categorieProduit,CatalogueProduit catalogueProduit);


    int ViewsPerProductCategory(CategorieProduit categorieProduit);

    List<Produit> findProduitsByNomCategorie(String nomCategorieProduit);
    List<Produit> findProduitsByCategorie(CategorieProduit categorieProduit);
// 21 Services


}
