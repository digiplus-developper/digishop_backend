package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.PayerLivraison;

public interface MetierPayerLivraison {

    PayerLivraison savecashdeliver(PayerLivraison payerLivraison, Long id_command);
    String sendSimpleEmail(String namedeliver,String adressdestinat);
    void delectecashdelivre(Long iduser,Long idcashdeliver);
}
