package com.sid.digishopheroku.AmazonMail;

public class AmazonSES {
    //this address must be verified with Amazon SES.
    final String FROM = "";

    //Le sujet pour l'email
    final String PASSWORD_RESET_SUBJECT = "Password reset request";
    //Le corps de l'email pour les users avec un non-HTML client email
    final String PASSWORD_RESET_HTMLBODY = "<h1> Une requete de reinitialisation de Mot de Passe</h1>" +
            "<p> Salut, $firstName!</p>" +
            "Nous avons recu une requete pour reinitialiser votre mot de passe sur DIGISHOP." +
            "Si ce n est pas vous, veuillez l'ignorer. " +
            "<a href = 'http://localhost:9003/password/resetRequest.html?token$tokenValue'>" +
            "Cliquez sur ce lien pour reinitialiser votre mot de passe." +
            "</a></br></br>" +
            "Thank you!"
            ;
    final String PASSWORD_RESET_TEXTLBODY = "Une requete de reinitialisation de Mot de Passe" +
            " Salut, $firstName!" +
            "Nous avons recu une requete pour reinitialiser votre mot de passe sur DIGISHOP." +
            "Si ce n est pas vous, veuillez l'ignorer. " +
            "Cliquez sur ce lien pour reinitialiser votre mot de passe." +
            "<a href = 'http://localhost:9003/password/resetRequest.html?token$tokenValue'>" +
            "Thank you!"
            ;



    public boolean sendPasswordResetRequest(String firstName, String email, String token) {
        boolean returnValue = false;
        return returnValue;
    }
}
