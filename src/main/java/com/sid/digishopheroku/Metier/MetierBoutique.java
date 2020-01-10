package com.sid.digishopheroku.Metier;



import com.sid.digishopheroku.Model.Boutique;

import java.util.ArrayList;
import java.util.List;

public interface MetierBoutique {
     List<Boutique> findBoutiqueByMot(String motcle);
     ArrayList<String> findAllProduitBoutique();
     List<Boutique> findBoutiqueAssocie(String mc);
     List<?> findBoutiqueOrProduit(String mc);
     List<Boutique> findBoutiques();
     Boutique getBoutiqueById(Long id);
     Boutique findByNameShop(String name_shop);
     Boutique saveboutique(Boutique boutique);
     byte[] getphoto(Long id) throws Exception;

     Boutique findByNomBoutique(String nom_boutisue);
     Boutique findByTelephoneBoutique(String numero);


     Boutique addViewToBoutique(Long idBoutique);

}
