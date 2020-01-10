package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {

    @Query("select q from Produit q where q.nomProduit like :x ")
     List<Produit> findProduitByNomProduit(@Param("x") String motcle);

    @Query("select q from Produit q where q.idProduit= :x ")
    Produit findPrByid(@Param("x") Long idprodui);

    @Query("select q from Produit q where q.produit.idProduit= :x ")
     Produit findProduitById(@Param("x") Long id);

    @Query("select q from Produit q where q.boutiquesProduit.nomBoutique like :x or q.nomProduit like :x")
     List<Produit> findProduitOrBoutique(@Param("x") String motcle);

    @Query("select q.boutiquesProduit from Produit q where q.nomProduit like :x ")
     List<?> findBoutiqueAssocier(@Param("x") String motcle);

    @Query("select q from Produit q where q.boutiquesProduit.idBoutique= :x ")
     List<Produit> findProduitofBoutique(@Param("x") Long id);

    @Query("delete from Produit p where p.idProduit= :x")
     boolean delectProduit(@Param("x") Long id);
    @Query("select v from Produit as v order by v.nbredevues desc")
    List<Produit> findProduitsByNbredevuesOrderByDesc();

    List<Produit> findByNbreDeFavorisGreaterThanOrderByNbreDeFavorisDesc(int occucrence);
    Produit findByQcode(String qcode);

    // @Query("select q from Produit q where q.boutiquesProduit.idBoutique= :x ")
    List<Produit> findAllByBoutiquesProduitAndArrivageIsTrue(Boutique boutique);
    List<Produit> findByActiveIsTrue();

    List<Produit> findAllByBoutiquesProduitAndActiveIsTrue(Boutique boutique);
    List<Produit> findAllByBoutiquesProduit(Boutique boutique);
    List<Produit> findAllByProduit(Produit produit);

    Produit findByNomProduit(String nomProduit);

  //  List<Produit> findByBoutiqu(Boutique boutique);


    List<Produit> findByBoutiquesProduit(Boutique boutique);

}
