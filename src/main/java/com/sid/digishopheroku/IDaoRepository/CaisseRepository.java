package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Caisse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaisseRepository extends JpaRepository<Caisse,Long> {
    Caisse findByBoutique(Boutique boutique);
}
