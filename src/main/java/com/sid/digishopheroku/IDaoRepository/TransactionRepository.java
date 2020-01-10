package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByUser(AppUser user);
    List<Transaction> findAllByTypeTransaction(String typeTransaction);
}
