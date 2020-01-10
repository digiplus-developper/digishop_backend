package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierPayerLivraison;
import com.sid.digishopheroku.Metier.MetierPaymentToCaisse;
import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.Model.PayerLivraison;
import com.sid.digishopheroku.Model.PaymentToCaisse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/payment")
public class PaymentRest {

    @Autowired
    private MetierPaymentToCaisse metierPaymentToCaisse;

    @Autowired
    private MetierPayerLivraison metierPayerLivraison;

    
    @PostMapping(value = "/save/caisse/{montant}")
    PaymentToCaisse savecashdeliver(@RequestBody Commande commande, @PathVariable double montant){

      /*  Message.creator(new PhoneNumber("+237"+appUser.getPhone()), new PhoneNumber("+17069560319"),
                "nous vous remercions").create();*/
        return metierPaymentToCaisse.savecashdeliver(montant,commande) ;
    }


    @PostMapping(value = "/save/livraison/{id_commad}")
    PayerLivraison savecashdeliver(@RequestBody PayerLivraison payerLivraison, @PathVariable Long id_commad){
        System.out.println("la commade a pour id  :" + id_commad);

      /*  Message.creator(new PhoneNumber("+237"+appUser.getPhone()), new PhoneNumber("+17069560319"),
                "nous vous remercions").create();*/
        return metierPayerLivraison.savecashdeliver(payerLivraison,id_commad) ;
    }
}
