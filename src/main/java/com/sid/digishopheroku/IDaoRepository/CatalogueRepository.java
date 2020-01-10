package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.CatalogueProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogueRepository extends JpaRepository<CatalogueProduit,Long> {
}
