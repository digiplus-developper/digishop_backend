package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CashSessionRepository;
import com.sid.digishopheroku.IDaoRepository.CashTransactionRepository;
import com.sid.digishopheroku.Metier.MetierCashSession;
import com.sid.digishopheroku.Metier.MetierCashTransaction;
import com.sid.digishopheroku.Model.Caisse;
import com.sid.digishopheroku.Model.CashSession;
import com.sid.digishopheroku.Model.CashStatus;
import com.sid.digishopheroku.Model.CashTransaction;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ServiceCashTransaction implements MetierCashTransaction {

    @Autowired
    private MetierCashSession metierCashSession;
    @Autowired
    CashSessionRepository cashSessionRepository;
    @Autowired
    CashTransactionRepository cashTransactionRepository;

    @Override
    public Caisse entrerMontantInitial(CashTransactionForm cashTransactionForm, Caisse caisse) {
        return null;
    }

    @Override
    public Caisse entrerMontantFermeture(CashTransactionForm cashTransactionForm) {
        return null;
    }

    @Override
    public CashSession entrerEnCaisse(CashSession cashSession,CashTransactionForm cashTransactionForm) {
        CashTransaction transaction = new CashTransaction();
        cashSession.setSolde(cashSession.getSolde()+cashTransactionForm.getMontant());
        transaction.setAmount(cashTransactionForm.getMontant());



        transaction.setCashSession(cashSession);
        transaction.setComment(cashTransactionForm.getCommentaire());
        transaction.setTransactionType(CashStatus.CASH_IN);
        transaction.setDateTransaction(new Date());
        transaction.setLastTransaction(cashSession.getSolde());

        cashTransactionRepository.save(transaction);
      cashSession.getTransactionList().add(transaction);
        return cashSessionRepository.save(cashSession);
    }

    @Override
    public CashSession sortieEnCaisse(CashSession cashSession,CashTransactionForm cashTransactionForm) {
        CashTransaction transaction = new CashTransaction();
        cashSession.setSolde(cashSession.getSolde() - cashTransactionForm.getMontant());
        transaction.setAmount(cashTransactionForm.getMontant());

        cashSessionRepository.save(cashSession);

        transaction.setCashSession(cashSession);
        transaction.setComment(cashTransactionForm.getCommentaire());
        transaction.setTransactionType(CashStatus.CASH_OUT);
        transaction.setDateTransaction(new Date());
        transaction.setLastTransaction(cashSession.getSolde());

        cashTransactionRepository.save(transaction);
        cashSession.getTransactionList().add(transaction);
        return cashSessionRepository.save(cashSession);
    }

    @Override
    public CashTransaction findById(Long id) {
        return cashTransactionRepository.getOne(id);
    }

    @Override
    public Caisse entrerCaisse(Caisse caisse) {
        return null;
    }

    @Override
    public List<CashTransaction> getcashtrans_session(Long id_cashssesion) {
        CashSession cashSession=cashSessionRepository.getOne(id_cashssesion);
        return cashTransactionRepository.findAllByCashSession(cashSession);
    }
}
