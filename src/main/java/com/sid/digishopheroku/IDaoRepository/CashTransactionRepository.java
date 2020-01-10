package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.CashSession;
import com.sid.digishopheroku.Model.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashTransactionRepository extends JpaRepository<CashTransaction,Long> {
    List<CashTransaction> findAllByCashSession(CashSession cashSession);
}
