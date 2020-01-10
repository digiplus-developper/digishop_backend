package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierBoutique;
import com.sid.digishopheroku.Metier.MetierCaisse;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.Caisse;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin("*")
@RequestMapping("/caisse")
@RestController
public class CaisseRest {
    @Autowired
    private MetierCaisse metierCaisse;
    @Autowired
    MetierBoutique metierBoutique;
    @Autowired
    MetierUser metierUser;

    @GetMapping("/find/{id_shop}")
    Caisse find_one_caisse(@PathVariable Long id_shop){
        return metierCaisse.getCaisse(id_shop);
    }

    @PostMapping("montantInitial")
    Caisse entrerMontantInitial(@RequestBody CashTransactionForm cashTransactionForm){
        Caisse caisse = metierCaisse.getCaisse((metierUser.findByiduser(cashTransactionForm.getIdVendeur())).getBoutique().getIdBoutique());
        caisse.setSolde(caisse.getSolde()+ cashTransactionForm.getMontant());
        caisse.setDate(new Date());
        return caisse;
    }


}
