package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.TransactionRepository;
import com.sid.digishopheroku.Metier.MetierTransaction;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class ServiceTransaction implements MetierTransaction {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    MetierUser metierUser;
    @Override
    public Transaction newStockTransaction(Stock stock, Long id_user) {
        Transaction transaction = new Transaction();
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setMotif(stock.getStockName());
        transaction.setStock(stock);
        transaction.setDateOperation(new Date());
        transaction.setQuantity(stock.getDernierAjout());
        transaction.setTypeTransaction(TYPETRANSACTION.AJOUT_STOCK);
        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public Transaction reservationStock(Stock stock, int quantity, Long id_user) {
        Transaction transaction = new Transaction();
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setMotif("Reservation - Directe - de - Stock - de " + quantity +" de "+ stock.getProduit().getNomProduit());
        transaction.setDateOperation(new Date());
        transaction.setTypeTransaction(TYPETRANSACTION.RESERVATION_STOCK);
        transaction.setQuantity(quantity);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction reservationStock(Stock stock, int quantity, Long id_user, String commentaire) {
        Transaction transaction = new Transaction();
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setMotif("Reservation - Directe - de - Stock - de " + quantity +" de "+ stock.getProduit().getNomProduit());

        transaction.setCommentaire(commentaire);
        transaction.setDateOperation(new Date());
        transaction.setTypeTransaction(TYPETRANSACTION.RESERVATION_STOCK);
        transaction.setQuantity(quantity);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction sortieStock(Stock stock, int qty, Long id_user) {
        Transaction transaction = new Transaction("SORTIE DE STOCK DE "+stock.getProduit().getNomProduit(),new Date(),stock);
        transaction.setQuantity(qty);
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setTypeTransaction(TYPETRANSACTION.SORTIE_STOCK);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction sortieStock(Stock stock, int qty, Long id_user, String commentaire) {
        Transaction transaction = new Transaction("SORTIE DE STOCK DE "+stock.getProduit().getNomProduit(),new Date(),stock);
        transaction.setQuantity(qty);
        transaction.setCommentaire(commentaire);
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setTypeTransaction(TYPETRANSACTION.SORTIE_STOCK);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction retourProduit(Produit produit, int qte, Long id_user) {
        Transaction transaction = new Transaction("RETOUR DE STOCK "+produit.getNomProduit(),new Date(),produit.getStock());
        transaction.setQuantity(qte);
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setTypeTransaction(TYPETRANSACTION.RETOUR_PRODUIT);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public Transaction retourProduit(Produit produit, int qte, Long id_user, String commentaire) {
        Transaction transaction = new Transaction("RETOUR DE STOCK "+produit.getNomProduit(),new Date(),produit.getStock());
        transaction.setQuantity(qte);

        transaction.setCommentaire(commentaire);
        AppUser user=metierUser.findByiduser(id_user);
        transaction.setUser(user);
        transaction.setTypeTransaction(TYPETRANSACTION.RETOUR_PRODUIT);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getalltransactionbyuser(Long id_user) {
        AppUser user=metierUser.findByiduser(id_user);
        return transactionRepository.findAllByUser(user);
    }


    /* gestion de la caisse */

//    @Override
//    public Caisse entrerMontantInitial(CashTransactionForm cashTransactionForm, Caisse caisse) {
//        return null;
//    }
//
//    @Override
//    public Caisse entrerMontantFermeture(CashTransactionForm cashTransactionForm) {
//        return null;
//    }


    @Override
    public List<Transaction> updateStockAfterInventory(Inventaire inventaire) {
        List<Transaction> transactions = new ArrayList<>();
        for (InventaireItem inventaireItem : inventaire.getInventaireItemList()){
            Transaction transaction = new Transaction();
            transaction.setTypeTransaction(TYPETRANSACTION.UPDATE_FROM_INVENTORY);
            transaction.setDateOperation(new Date());
            transaction.setStock(inventaireItem.getProduit().getStock());
            transaction.setMotif("DIFFERENCE DE "+ inventaireItem.getDifference() +" APRES L'INVENTAIRE " + inventaire.getId());
            transaction.setQuantity(inventaireItem.getDifference());
            transaction.setCommentaire("");

            transactions.add(transaction);
        }
       return transactionRepository.saveAll(transactions);

    }

    @Override
    public List<Transaction> updateStockAfterInventory(Inventaire inventaire, String commentaire) {
        List<Transaction> transactions = new ArrayList<>();
        for (InventaireItem inventaireItem : inventaire.getInventaireItemList()){
            Transaction transaction = new Transaction();
            transaction.setTypeTransaction(TYPETRANSACTION.UPDATE_FROM_INVENTORY);
            transaction.setDateOperation(new Date());
            transaction.setStock(inventaireItem.getProduit().getStock());
            transaction.setMotif("DIFFERENCE DE "+ inventaireItem.getDifference() +" APRES L'INVENTAIRE " + inventaire.getId());
            transaction.setQuantity(inventaireItem.getDifference());
            transaction.setCommentaire(commentaire);

            transactions.add(transaction);
        }
        return transactionRepository.saveAll(transactions);

    }
    /* reporting */

    @Override
    public List<Transaction> findTransactionByTypeTransaction(String typeTransaction,Long idBoutique) {
       List<Transaction> transactions = transactionRepository.findAllByTypeTransaction(typeTransaction);

       List<Transaction> transactionList = new ArrayList<>();
    for (Transaction transaction : transactions){
            if (transaction.getStock().getProduit().getBoutiquesProduit().getIdBoutique() == idBoutique){
                transactionList.add(transaction);
            }
        }
    return transactionList;
    }
}
