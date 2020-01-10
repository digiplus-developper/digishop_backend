package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.PayerLivraisonRepository;
import com.sid.digishopheroku.Metier.MetierPayerLivraison;
import com.sid.digishopheroku.Model.PayerLivraison;
import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.Model.Payment;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(value = "servicecashdeliver")
public class ServicePayerLivraison implements MetierPayerLivraison {

    @Autowired
    private PayerLivraisonRepository payerLivraisonRepository;
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public ServiceCommande serviceCommande;


    private final static String ACCOUNT_SID = "ACfd0e6673945a504d368ca6b18c67b0d1";
    private final static String AUTH_ID = "a5a9119f552a05bf7e1f4bb913bb8f06";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }


    @Override
    public String sendSimpleEmail(String namedesti,String adressdesti) {

        // Create a Simple MailMessage.   https://o7planning.org/fr/11145/tutoriel-spring-email
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(adressdesti);
        message.setSubject("Nos Salutation Distinguées");
        message.setText("nous vous remercions M "+namedesti+"  d'avoir fait vos achat chez nous ");

        // Send Message!
        //this.emailSender.send(message);

        return "Email Sent!";
    }


    @Override
    public PayerLivraison savecashdeliver(PayerLivraison payerLivraison, Long id_command) {
        Commande commande=serviceCommande.find_command(id_command);
        commande.setPayment(payerLivraison);
        commande.getUsercommande().setCode_confirmation(UUID.randomUUID().toString().substring(6).toUpperCase());
         payerLivraison= payerLivraisonRepository.save(payerLivraison);
       // sendSimpleEmail(commande.getUsercommande().getUsername(),commande.getUsercommande().getEmail());
        return payerLivraison;
    }


    @Override
    public void delectecashdelivre(Long iduser, Long idcashdeliver) {

        payerLivraisonRepository.deleteById(idcashdeliver);

    }
}
