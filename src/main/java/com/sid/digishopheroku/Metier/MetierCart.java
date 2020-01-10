package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Cart;
import com.sid.digishopheroku.Model.CartItem;

import java.util.List;

public interface MetierCart {
    Cart addcart(Cart cart);
    Cart findCartByiduser(Long iduser);
    Cart findCartByid(Long idcart);
    Cart saveallcart(Cart cart);

    CartItem updateCartItem(CartItem cartItem);
    boolean proposition_prix(Cart cart, double montant_proposition);

    //ramene e % la proposition de prix du vendeur
    double calcul_pourcentage(double prix_cart, double prix_propose);


    void clearCart(Cart cart);
//12. Nombre des utilisateurs ayant initier un panier dans la boutique


  //  Cart recuperePanierApresConnexion(AppUser user,Cart cart);
    Cart ViderEtMettreAjour(Long iduser, Cart cart);

    List<Cart> findAll();
}
