package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.CatalogueProduit;

import java.util.List;

public interface MetierCatalogue {
    CatalogueProduit AddCatalogue(CatalogueProduit catalogueProduit, Long id_categorie);
    List<CatalogueProduit> getallcatalogue();

}
