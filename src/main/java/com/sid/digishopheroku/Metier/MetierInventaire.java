package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Inventaire;
import com.sid.digishopheroku.Model.InventaireItem;

import java.util.Date;
import java.util.List;

public interface MetierInventaire {
    List<Inventaire> findAllByBoutique(Boutique boutique);
    List<Inventaire> findAllByDateInventaireBefore(Date date);
    List<Inventaire> findAllByUser(AppUser user);

    Inventaire saveInventaire(Inventaire inventaire);
    Inventaire addNewInventaireItem(Inventaire inventaire,List<InventaireItem> inventaireItems,AppUser user);
    void updateStockInventaire(Inventaire inventaire);

    Inventaire findById(Long id);
    void clearInventaire(Inventaire inventaire);
    void deleteInventaire(Inventaire inventaire);


//List<Inventaire> findInventairesByBoutique(Boutique boutique);
List<Inventaire> findByBoutiqueAndTypeInventaire(Boutique boutique, String typeInventaire);

}
