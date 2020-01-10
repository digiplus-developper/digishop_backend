package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.Model.PaymentToCaisse;

public interface MetierPaymentToCaisse {
    PaymentToCaisse savecashdeliver(double montant, Commande commande);
    void delectecashdelivre(Long iduser,Long idcashdeliver);
}
