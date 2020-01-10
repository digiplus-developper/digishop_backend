package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Inventaire;
import com.sid.digishopheroku.Model.InventaireItem;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventaireItemRepository extends JpaRepository<InventaireItem,Long> {
    List<InventaireItem> findByInventaire(Inventaire inventaire);
    List<InventaireItem> findByProduit(Produit produit);
}
