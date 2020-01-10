package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayementRepository extends JpaRepository<Payment,Long> {
}
