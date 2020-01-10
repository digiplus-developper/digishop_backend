package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CartItemRepository;
import com.sid.digishopheroku.IDaoRepository.CommandeRepository;
import com.sid.digishopheroku.Metier.MetierCart;
import com.sid.digishopheroku.Metier.MetierCartitem;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "servicecartiem")
public class ServiceCartitem implements MetierCartitem {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private MetierCart metierCart;

    private double montanttotal=0;


    @Override
    public CartItem savecartitem(CartItem cartitem) {
        return cartItemRepository.save(cartitem);
    }

    @Override
    public List<CartItem> saveallcartitem(List<CartItem> cartItemList) {
        return cartItemRepository.saveAll(cartItemList);
    }

    @Override
    public List<CartItem> getallcartitem(Long idcart) {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem updatecartitem(CartItem cartitem) {
        /* on recupere l article en parametre, ensuite on recalcule le nouveau sous total */
        double bigDecimal = cartitem.getProduit().getPrixProduit()*cartitem.getQuantite();
        /* le sous total est arrondi au dixieme près*/
        cartitem.setSubtotal(bigDecimal);
        /* Mise a jour de l article */
       CartItem cartItems= cartItemRepository.save(cartitem);
       Cart cart=metierCart.findCartByid(cartItems.getCartcartitem().getId_cart());
       System.out.println("l'id du panier est une fois de plus: "+cart.getId_cart());
       List<CartItem> cartItemList=cart.getCartItemList();
       int i=0;
       while (i<cartItemList.size()){
           montanttotal +=cartItemList.get(i).getSubtotal();
           i++;
       }
       cart.setGrandTotal(montanttotal);


        return cartitem;
    }

    @Override
    public CartItem findcartitembyid(Long idcartitem) {
        return cartItemRepository.findCartitemByid(idcartitem);
    }

    @Override
    public CartItem findcartitembyidproduit(Long idproduit,Long idcart) {
         return cartItemRepository.findCartitemByidproduit(idproduit,idcart);
    }

    @Override
    public void   delectitem(Long idproduit) {
        cartItemRepository.deleteById(idproduit);
    }

    @Override
    public void delectallcartitem(List<CartItem> cartItemList) {
        cartItemRepository.deleteAll(cartItemList);
    }

    @Override
    public List<CartItem> findByCartcartitem(Cart cart) {
        return cartItemRepository.findByCartcartitem(cart);
    }

    @Override
    public List<CartItem> findCartitemByidcart(Long idcart) {
        return cartItemRepository.findCartitemByidcart(idcart);
    }

    @Override
    public List<CartItem> findbyCommande(Long idcommande) {
        Commande commande=commandeRepository.getOne(idcommande);
        return cartItemRepository.findByCommandeitem(commande);
    }

    @Override
    public CartItem add_Product_to_CartItem(Produit produit, AppUser user, int quantite) {
        List<CartItem> itemList = findByCartcartitem(user.getCartuser());
        //je recupere le panier d'user pour mettre ajour le prix du panier
        Cart cart1=metierCart.findCartByiduser(user.getId_user());

        for (CartItem cartItem : itemList){
            if (produit.getIdProduit() == cartItem.getProduit().getIdProduit()){
                // si le produit a ajouter correspond a un produit deja present dans le panier alors faire une mise a jour
                //1- ajouter a la quantite presente la nouvelle quantite
                cartItem.setQuantite(cartItem.getQuantite() + quantite);
                //2- mettre ajour le sous total proportionnellement a la nouvelle quantite
                cartItem.setSubtotal(produit.getPrixProduit()*quantite);
                //3-sauvegarde de l article mis a jour
                cartItemRepository.save(cartItem);
                return cartItem;

            }
        }
        // si le produit n est pas present dans le panier alors
        // 1- creer une nouvelle ligne dans le panier de l utilisateur
        CartItem cartItem = new CartItem();
        cartItem.setCartcartitem(user.getCartuser());
        //2- ajouter le produit a la nouvelle ligne du panier
        cartItem.setProduit(produit);

        //3- definir la quantite du produit ajouter au panier
        cartItem.setQuantite(quantite);
        //4- calculer le sous total par rapport au produit
        cartItem.setSubtotal(produit.getPrixProduit() * quantite);
        //ici on ajoute le prix final de vente
        cartItem.setPrixvendu(cartItem.getSubtotal());
        //5-sauvergarde du nouveau produit dans le panier
        cartItem = cartItemRepository.save(cartItem);
        Cart cart=metierCart.findCartByid(cartItem.getCartcartitem().getId_cart());
        cart.setGrandTotal(cart.getGrandTotal() + (cartItem.getSubtotal()));
        //cart.setGrandTotalVente(cart.getGrandTotal());
        System.out.println("l'id du panier est une fois de plus: "+cart.getId_cart());
        List<CartItem> cartItemList=cart.getCartItemList();
       /* int i=0;
        while (i<cartItemList.size()){
            montanttotal +=cartItemList.get(i).getSubtotal().doubleValue();
            i++;
        }
        cart.setGrandTotal(BigDecimal.valueOf(montanttotal));*/


        return cartItem;
    }

    @Override
    public void remove_cartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public List<CartItem> findByProduit(Produit produit) {
        return cartItemRepository.findAllByProduit(produit);
    }


}
