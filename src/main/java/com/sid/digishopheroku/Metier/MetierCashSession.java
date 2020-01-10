package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.CashSession;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;

public interface MetierCashSession {
    CashSession ouvertureCaisse(CashTransactionForm cashTransactionForm );
    CashSession fermetureCaisse(CashTransactionForm cashTransactionForm);
    CashSession entrerCaisse(CashTransactionForm cashTransactionForm);
    CashSession sortieCaisse(CashTransactionForm cashTransactionForm);

    CashSession findById(Long id_cashSession);
}
