package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.CashSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashSessionRepository extends JpaRepository<CashSession,Long> {
    CashSession findCashSessionByUser(AppUser user);
}
