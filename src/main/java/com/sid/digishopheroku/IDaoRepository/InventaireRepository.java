package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Inventaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InventaireRepository extends JpaRepository<Inventaire,Long> {
    List<Inventaire> findAllByBoutique(Boutique boutique);
    List<Inventaire> findAllByDateInventaireBefore(Date date);
    List<Inventaire> findAllByUser(AppUser user);

    List<Inventaire> findByBoutique(Boutique boutique);
    List<Inventaire> findByBoutiqueAndTypeInventaire(Boutique boutique, String typeInventaire);
}
