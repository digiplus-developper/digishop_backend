package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Cart;
import com.sid.digishopheroku.Model.CartItem;
import com.sid.digishopheroku.Model.Commande;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("select q from CartItem q where q.id_Item= :x ")
    CartItem findCartitemByid(@Param("x") Long Id);

    @Query("select q from CartItem q where q.produit.idProduit= :x and q.cartcartitem.id_cart= :y ")
    CartItem findCartitemByidproduit(@Param("x") Long Idproduit,@Param("y") Long idcart);

    List<CartItem> findByCartcartitem(Cart cart);

    @Query("select q from CartItem q where q.cartcartitem.id_cart= :x ")
    List<CartItem> findCartitemByidcart(@Param("x") Long Idcart);

    List<CartItem> findByCommandeitem(Commande commande);

    List<CartItem> findAllByProduit(Produit produit);
}
