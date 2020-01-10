package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoutiqueRepository extends JpaRepository<Boutique,Long> {

    @Query("select q from Boutique q where q.nomBoutique like :x ")
     List<Boutique> findNomBoutiqueByMot(@Param("x") String motcle);

    @Query("delete from Boutique p where p.idBoutique= :x")
    void delectBoutique(@Param("x") Long id);


    Boutique findByTelephoneBoutique(String phone);

    Boutique findByNomBoutique(String nom_boutisue);

    List<Boutique> findByFavorites(Favorites favorites);

    // Page<Boutique> findboutiqueByView();

    List<Boutique> findByNbreDeFavorisGreaterThanOrderByNbreDeFavoris(int x);

}
