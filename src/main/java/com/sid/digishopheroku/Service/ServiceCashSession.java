package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CashSessionRepository;
import com.sid.digishopheroku.IDaoRepository.CashTransactionRepository;
import com.sid.digishopheroku.Metier.MetierCaisse;
import com.sid.digishopheroku.Metier.MetierCashSession;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.*;
import com.sid.digishopheroku.WebRestfull.Forms.CashTransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Transactional
@Service
public class ServiceCashSession implements MetierCashSession {
@Autowired
    MetierUser metierUser;
@Autowired
    CashSessionRepository cashSessionRepository;
@Autowired
    CashTransactionRepository cashTransactionRepository;
@Autowired
    MetierCaisse metierCaisse;
 @Override
    public CashSession ouvertureCaisse(CashTransactionForm cashTransactionForm) {
        CashTransaction cashTransaction=new CashTransaction();
        cashTransaction.setAmount(cashTransactionForm.getMontant());
        cashTransaction.setComment(cashTransactionForm.getCommentaire());
        cashTransaction.setDateTransaction(new Date());
        cashTransaction.setTransactionType(CashStatus.OPEN);

        CashSession cashSession=new CashSession();
        cashSession.setCashStatus(CashStatus.OPEN);
        cashSession.setStartDate(new Date());
        cashSession.setInitialAmount(cashTransactionForm.getMontant());
        cashSession.setSolde(cashSession.getSolde()+cashTransactionForm.getMontant());
        AppUser user=metierUser.getuser(cashTransactionForm.getIdVendeur());
        if (user==null) throw new RuntimeException("no get user");
        cashSession.setUser(user);
        cashSession.setCaisse(user.getBoutique().getCaisse());
     Caisse caisse=cashSession.getCaisse();
     caisse.setSolde(caisse.getSolde()+cashTransactionForm.getMontant());
     metierCaisse.Add_caisse(caisse);


     cashSession=cashSessionRepository.save(cashSession);
        cashTransaction.setCashSession(cashSession);
        cashTransaction=cashTransactionRepository.save(cashTransaction);
        //cashSession.setSolde(cashSession.getSolde()+cashTransaction.getAmount());
        return cashSession;
    }

    @Override
    public CashSession fermetureCaisse(CashTransactionForm cashTransactionForm) {
    return transaction(cashTransactionForm,CashStatus.CLOSE);
    }


    @Override
    public CashSession entrerCaisse( CashTransactionForm cashTransactionForm) {
       return transaction(cashTransactionForm,CashStatus.CASH_IN);
    }

    @Override
    public CashSession sortieCaisse(CashTransactionForm cashTransactionForm) {

        return transaction(cashTransactionForm,CashStatus.CASH_OUT);
    }

    CashSession transaction(CashTransactionForm cashTransactionForm,String type){
        AppUser user=metierUser.getuser(cashTransactionForm.getIdVendeur());
        if (user==null) throw new RuntimeException("no get user");
        CashTransaction cashTransaction=new CashTransaction();
        cashTransaction.setAmount(cashTransactionForm.getMontant());
        cashTransaction.setComment(cashTransactionForm.getCommentaire());
        cashTransaction.setDateTransaction(new Date());
        cashTransaction.setTransactionType(type);
        CashSession cashSession=cashSessionRepository.getOne(cashTransactionForm.getId_cashsession());
        Caisse caisse=cashSession.getCaisse();
        if (type.equalsIgnoreCase(CashStatus.CASH_IN)){
            caisse.setSolde(caisse.getSolde()+cashTransactionForm.getMontant());
            cashSession.setSolde(cashSession.getSolde()+cashTransactionForm.getMontant());
            System.out.println(CashStatus.CASH_IN);
        }else  if (type.equalsIgnoreCase(CashStatus.CASH_OUT)){
            caisse.setSolde(caisse.getSolde()-cashTransactionForm.getMontant());
            cashSession.setSolde(cashSession.getSolde()-cashTransactionForm.getMontant());
            System.out.println(CashStatus.CASH_OUT);
        }else  if (type.equalsIgnoreCase(CashStatus.CLOSE)){
            double mnt_theo=cashSession.getSolde();
            double difference=mnt_theo-cashTransactionForm.getMontant();
            System.out.println("la difference es "+difference);
            caisse.setSolde(caisse.getSolde()+difference);
            cashSession.setClosurePhysicalAmount(cashTransactionForm.getMontant());
            System.out.println(CashStatus.CLOSE);
        }
        metierCaisse.Add_caisse(caisse);
        cashSession.setEndDate(new Date());
        cashSession.setCashStatus(type);
        cashSession.setClosureTheoricalAmount(cashTransactionForm.getMontant());
        cashTransaction.setCashSession(cashSession);
        //cashTransactionRepository.save(cashTransaction);
        //cashSession.setSolde(cashSession.getSolde()+cashTransaction.getAmount());
        //cashSession=cashSessionRepository.save(cashSession);

        return cashSession;
    }

    @Override
    public CashSession findById(Long id_cashSession) {
        return cashSessionRepository.getOne(id_cashSession);
    }
}