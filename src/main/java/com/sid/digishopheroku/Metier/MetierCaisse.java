package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Caisse;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;

public interface MetierCaisse {

    Caisse Add_caisse(Caisse caisse);

    Caisse getCaisse(Long id_shop);

    Caisse enterInitialAmount(CashTransactionForm cashTransactionForm);
    Caisse entrerMontantFermeture(CashTransactionForm cashTransactionForm);




}
