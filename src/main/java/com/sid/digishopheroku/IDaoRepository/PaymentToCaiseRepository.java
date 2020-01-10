package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.PaymentToCaisse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentToCaiseRepository extends JpaRepository<PaymentToCaisse,Long> {
}
