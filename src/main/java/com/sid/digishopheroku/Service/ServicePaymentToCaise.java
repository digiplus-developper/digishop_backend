package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.BoutiqueRepository;
import com.sid.digishopheroku.IDaoRepository.CommandeRepository;
import com.sid.digishopheroku.IDaoRepository.PaymentToCaiseRepository;
import com.sid.digishopheroku.Metier.MetierCommande;
import com.sid.digishopheroku.Metier.MetierPayerLivraison;
import com.sid.digishopheroku.Metier.MetierPaymentToCaisse;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.Model.Payment;
import com.sid.digishopheroku.Model.PaymentToCaisse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class ServicePaymentToCaise implements MetierPaymentToCaisse {

    @Autowired
    private PaymentToCaiseRepository paymentToCaiseRepository;
    @Autowired
    private MetierPayerLivraison metierPayerLivraison;
    @Autowired
    public MetierCommande metierCommande;
    @Autowired
    BoutiqueRepository boutiqueRepository;
    @Autowired
    private CommandeRepository  commandeRepository;

    @Override
    public PaymentToCaisse savecashdeliver(double montant, Commande commande) {
        PaymentToCaisse paymentToCaisse=new PaymentToCaisse();
        paymentToCaisse.setMontant(montant);
        paymentToCaisse.setReference(UUID.randomUUID().toString().substring(15).toUpperCase());
        if (commande!=null){

            commande.setPayment(paymentToCaisse);
            Boutique  boutique=commande.getCartitemscomand().get(0).getProduit().getBoutiquesProduit();
            double solde=metierCommande.Calcul_montant_command(commande);//on recupere le montant de la commande
            solde+= (boutique.getCaisse().getSolde());
            boutique.getCaisse().setSolde(solde);
            boutiqueRepository.save(boutique);
            metierCommande.Activer_command(commande.getIdcommande());//on active la commande pour dire qu'elle a deja été payé
             paymentToCaisse= paymentToCaiseRepository.save(paymentToCaisse);
            metierPayerLivraison.sendSimpleEmail(commande.getUsercommande().getUsername(),commande.getUsercommande().getEmail());

        }
        return paymentToCaisse;

    }

    @Override
    public void delectecashdelivre(Long iduser, Long idcashdeliver) {

    }
}
