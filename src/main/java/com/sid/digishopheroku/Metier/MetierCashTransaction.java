package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Caisse;
import com.sid.digishopheroku.Model.CashSession;
import com.sid.digishopheroku.Model.CashTransaction;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;

import java.util.List;

public interface MetierCashTransaction {
    Caisse entrerMontantInitial(CashTransactionForm cashTransactionForm, Caisse caisse);
    Caisse entrerMontantFermeture(CashTransactionForm cashTransactionForm);

    CashSession entrerEnCaisse(CashSession cashSession,CashTransactionForm cashTransactionForm);
    CashSession sortieEnCaisse(CashSession cashSession,CashTransactionForm cashTransactionForm);

    CashTransaction findById(Long id);
    Caisse entrerCaisse(Caisse caisse);
    List<CashTransaction> getcashtrans_session(Long id_cashssesion);
}
