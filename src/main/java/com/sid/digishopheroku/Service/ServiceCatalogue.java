package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CatalogueRepository;
import com.sid.digishopheroku.IDaoRepository.CategorieProduitRepository;
import com.sid.digishopheroku.Metier.MetierCatalogue;
import com.sid.digishopheroku.Model.CatalogueProduit;
import com.sid.digishopheroku.Model.CategorieProduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceCatalogue implements MetierCatalogue{
    @Autowired
    private CatalogueRepository catalogueRepository;
    @Autowired
    private CategorieProduitRepository categorieProduitRepository;

    @Override
    public CatalogueProduit AddCatalogue(CatalogueProduit catalogueProduit, Long id_categorie) {
        CategorieProduit categorieProduit=categorieProduitRepository.getOne(id_categorie);
        catalogueProduit.setCategorieProduit(categorieProduit);
        return catalogueRepository.save(catalogueProduit);
    }

    @Override
    public List<CatalogueProduit> getallcatalogue() {
        return catalogueRepository.findAll();
    }
}
