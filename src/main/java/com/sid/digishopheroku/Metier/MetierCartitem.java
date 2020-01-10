package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.*;

import java.util.List;

public interface MetierCartitem {

    CartItem savecartitem(CartItem cartitem);
    List<CartItem> saveallcartitem(List<CartItem> cartItemList);

    List<CartItem> getallcartitem(Long idcart);
    List<CartItem> findbyCommande(Long idcommande);
    CartItem findcartitembyid(Long idcartitem);
    CartItem findcartitembyidproduit(Long idproduit,Long idcart);

    void delectitem(Long idproduit);
    void delectallcartitem(List<CartItem> cartItemList);

    List<CartItem> findByCartcartitem(Cart cart);
    List<CartItem> findCartitemByidcart(Long Idcart);

    CartItem add_Product_to_CartItem(Produit produit, AppUser user,int quantite);
    CartItem updatecartitem(CartItem cartitem);
    void remove_cartItem(CartItem cartItem);

    List<CartItem> findByProduit( Produit produit);
}
