package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CaisseRepository;
import com.sid.digishopheroku.Metier.MetierBoutique;
import com.sid.digishopheroku.Metier.MetierCaisse;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Caisse;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service("servicecaisse")
public class ServiceCaisse implements MetierCaisse {
    @Autowired
    private CaisseRepository caisseRepository;
    @Autowired
    private MetierBoutique metierBoutique;
    @Autowired
    MetierUser metierUser;


    @Override
    public Caisse Add_caisse(Caisse caisse) {
        return caisseRepository.save(caisse);
    }

    @Override
    public Caisse getCaisse(Long id_shop) {
        Boutique boutique=metierBoutique.getBoutiqueById(id_shop);
        System.out.println("l'id de la caisse est "+boutique.getCaisse().getId_caise());
        Caisse caisse=caisseRepository.findByBoutique(boutique);
        System.out.println("l'id de la caisse est "+caisse.getId_caise());
        return caisse;
    }

    @Override
    public Caisse enterInitialAmount(CashTransactionForm cashTransactionForm) {
        //Caisse caisse = metierCaisse.getCaisse((metierUser.findByiduser(cashTransactionForm.getIdVendeur())).getBoutique().getIdBoutique());

       Caisse caisse = caisseRepository.getOne((metierUser.findByiduser(cashTransactionForm.getIdVendeur())).getBoutique().getIdBoutique());
       caisse.setSolde(caisse.getSolde()+ cashTransactionForm.getMontant());
        caisse.setDate(new Date());
        caisseRepository.save(caisse);
        return caisse;


    }

    @Override
    public Caisse entrerMontantFermeture(CashTransactionForm cashTransactionForm) {
        Caisse caisse;
        return null;
    }
}
