package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    List<Commande> findByUsercommande(AppUser user);
    List<Commande> findByNomvendeur(String nom_vendeur);
    List<Commande> findByBoutique(Boutique boutique);

    List<Commande> findAllByBoutiqueAndDateCommandeBetween(Boutique boutique, Date debut , Date fin);
    List<Commande> findCommandesByBoutiqueOrderByDateCommandeDesc(Boutique boutique);
    List<Commande> findByBoutiqueAndStatutContaining(Boutique boutique,String statutCommande);

 List<Commande> findByNomvendeurAndStatutContaining(String nomVendeur,String statutCommande);
}
