package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.InventaireRepository;
import com.sid.digishopheroku.Metier.MetierInventaire;
import com.sid.digishopheroku.Metier.MetierStock;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServiceInventaire implements MetierInventaire {
    @Autowired
    InventaireRepository inventaireRepository;
    @Autowired
    MetierStock metierStock;
    @Override
    public List<Inventaire> findAllByBoutique(Boutique boutique) {
        return inventaireRepository.findAllByBoutique(boutique);
    }

    @Override
    public List<Inventaire> findAllByDateInventaireBefore(Date date) {
        return inventaireRepository.findAllByDateInventaireBefore(date);
    }

    @Override
    public List<Inventaire> findAllByUser(AppUser user) {
        if (user==null)throw new RuntimeException("Utilisateur inconnu");
        return findAllByUser(user);
    }

    @Override
    public Inventaire saveInventaire(Inventaire inventaire) {
        return inventaireRepository.save(inventaire);
    }

    @Override
    public Inventaire findById(Long id) {
        return inventaireRepository.getOne(id);
    }

    @Override
    public void clearInventaire(Inventaire inventaire) {
        inventaire.getInventaireItemList().clear();
        inventaire.setDateInventaire(null);


    }

    @Override
    public void deleteInventaire(Inventaire inventaire) {
        inventaireRepository.delete(inventaire);
    }



    @Override
    public Inventaire addNewInventaireItem(Inventaire inventaire,List<InventaireItem> inventaireItems,AppUser user) {
inventaire.setDateInventaire(new Date());
inventaire.setBoutique(user.getBoutique());
inventaire.setUser(user);
inventaire.setInventaireItemList(inventaireItems);
        return inventaireRepository.save(inventaire);
    }

    @Override
    public void updateStockInventaire(Inventaire inventaire) {
        for (InventaireItem iItem : inventaire.getInventaireItemList()){
            // Sachant que : difference == quantitePhysique-quantiteTheorique

                iItem.getProduit().setStock(metierStock.updateStockInventaire(iItem.getProduit(),iItem.getDifference()));

        }
        //return inventaire.getInventaireItemList();
    }


    @Override
    public List<Inventaire> findByBoutiqueAndTypeInventaire(Boutique boutique, String typeInventaire) {
        return inventaireRepository.findByBoutiqueAndTypeInventaire(boutique, typeInventaire);
    }
}
