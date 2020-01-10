package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.CartItemRepository;
import com.sid.digishopheroku.IDaoRepository.CartRepository;
import com.sid.digishopheroku.IDaoRepository.CommandeRepository;
import com.sid.digishopheroku.Metier.MetierCart;
import com.sid.digishopheroku.Metier.MetierCartitem;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Cart;
import com.sid.digishopheroku.Model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "servicecart")
public class ServiceCart implements MetierCart {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ServiceCartitem serviceCartitem;
    @Autowired
    private MetierCartitem metierCartitem;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private MetierUser metierUser;

    @Override
    public Cart addcart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findCartByiduser(Long iduser) {
        return cartRepository.findCartByiduser(iduser);
    }

    @Override
    public Cart findCartByid(Long idcart) {
        return cartRepository.findCartByid(idcart);
    }




    @Override
    public Cart saveallcart(Cart cart) {
        double cartTotal = 0;
        List<CartItem> cartItemList = serviceCartitem.findByCartcartitem(cart);

        for (CartItem cartItem : cartItemList){
            if (cartItem.getQuantite()>0){
                serviceCartitem.updatecartitem(cartItem);

                cartTotal += cartItem.getSubtotal();
            }
        }
        cart.setGrandTotal(cartTotal);

        return cartRepository.save(cart);
    }



    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        double prix =cartItem.getProduit().getPrixProduit()*cartItem.getQuantite();

        cartItem.setSubtotal(prix);

        cartItemRepository.save(cartItem);

        return cartItem;

    }
//applique les nouveaux prix apres proposition
    @Override
    public boolean proposition_prix(Cart cart, double montant_proposition) {

        double pourcentage=calcul_pourcentage(cart.getGrandTotal(),montant_proposition);
        System.out.println("le pourcentage est: "+pourcentage);

        int i=0;
        while (i<cart.getCartItemList().size()){
            System.out.println("le vrais pourc est: "+pourcentage);
            if (cart.getCartItemList().get(i).getProduit().isAppliqueReduction()==true){
                cart.getCartItemList().get(i).setPrixvendu(pourcentage * cart.getCartItemList().get(i).getSubtotal());
                System.out.println("le le prix vendu  est: "+cart.getCartItemList().get(i).getPrixvendu());
                cartItemRepository.save(cart.getCartItemList().get(i));
            }
            i++;
        }
        if (montant_proposition!=0){
            cart.setGrandTotalVente(montant_proposition);
            cartRepository.save(cart);
        }

        return true;
    }
    //ramene e % la proposition de prix du vendeur
    @Override
    public double calcul_pourcentage(double prix_cart, double prix_propose){
        double pourcentage= prix_propose/prix_cart;
        //pourcentage.setScale(2,BigDecimal.ROUND_HALF_UP);
        return pourcentage;
    }




    @Override
    public void clearCart(Cart cart) {
        List<CartItem> cartItemList = metierCartitem.findByCartcartitem(cart);

        for (CartItem cartItem : cartItemList) {
            cartItem.setCartcartitem(null);
            metierCartitem.savecartitem(cartItem);
        }

        cart.setGrandTotal(0);

        cartRepository.save(cart);
    }


//    @Override
//    public Cart recuperePanierApresConnexion(AppUser user, Cart cart) {
//        if (user.getCartuser().getCartItemList().get(0).getProduit().getBoutiquesProduit().getIdBoutique() ==
//        cart.getCartItemList().get(0).getProduit().getBoutiquesProduit().getIdBoutique() )
//        return null;
//    }

/*LE client se connecte avec un panier , en meme temps il a un panier non cloturé en BD
* il decide de vider l ancien panier et de proceder*/
    @Override
    public Cart ViderEtMettreAjour(Long iduser, Cart cart) {
        AppUser user = metierUser.findByiduser(iduser);
        clearCart(user.getCartuser());
        user.setCartuser(cart);
        saveallcart(user.getCartuser());
        metierUser.Adduser(user);
        return cart;
    }


    public Cart re_new_cart(Long id_user, Cart cart) {
        AppUser user=metierUser.findByiduser(id_user);
        if (user==null) throw new RuntimeException("user is not here");
        Cart cart1=user.getCartuser();
        CartItem cartItem=new CartItem();
        System.out.println("la taille des l'élément du panier est "+cart.getCartItemList().size());
        cartItemRepository.deleteAll(cart1.getCartItemList());
        System.out.println("l'id du panier est "+cart1.getId_cart());
        for (CartItem item:cart.getCartItemList()
        ) {
            System.out.println("l'élément du panier est "+cart1.getCartItemList().size());
            cartItem=metierCartitem.add_Product_to_CartItem(item.getProduit(),user,item.getQuantite());
            cartItem.setCartcartitem(cart1);
            cartItemRepository.save(cartItem);
            cart1.getCartItemList().add(cartItem);

        }

        return cart1;
    }


    @Override
    public List<Cart> findAll() {
        return cartRepository.findByCartItemListNotNull();
    }
}
