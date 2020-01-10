package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.CategorieProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieProduitRepository extends JpaRepository<CategorieProduit,Long> {

    CategorieProduit findByNomCategorie(String nomCategorie);

    List<CategorieProduit> findByClasseContaining(String classe);

    List<CategorieProduit> findByTypeContaining(String type);

}
