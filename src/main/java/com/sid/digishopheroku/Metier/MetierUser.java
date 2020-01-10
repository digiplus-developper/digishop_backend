package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.*;

import java.util.ArrayList;
import java.util.List;

public interface MetierUser {
   AppUser Adduser(AppUser appUser);
   ArrayList<AppUser> getAlluser();
   AppUser getuser(Long iduser);
   AppUser connection(String numero);
   AppUser deconnection(Long iduser);
   AppUser findByUsername( String username);
   AppUser findByiduser( Long iduser);
   void delectuser(Long id_user);
   List<AppUser> findByBoutique(String shop_name);


   /* liste des utilisateurs avec un panier en cours dans une boutique */
   List<AppUser> UsersWithCartNotEmpty(Boutique boutique);
}
