package com.sid.digishopheroku.WebRestfull;


import com.sid.digishopheroku.Metier.MetierCart;
import com.sid.digishopheroku.Metier.MetierCartitem;
import com.sid.digishopheroku.Metier.MetierProduit;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Cart;
import com.sid.digishopheroku.Model.CartItem;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin("*")
@RestController
@RequestMapping("/cart")
public class CartRestfull {

    @Autowired
   private MetierCart metierCart;
    @Autowired
    private MetierUser metierUser;
    @Autowired
    private MetierProduit metierProduit;
    @Autowired
    private MetierCartitem metierCartitem;

    @RequestMapping(value = "/save/{iduser}",method = RequestMethod.POST)
    public Cart Savecart(@PathVariable Long iduser,@RequestBody Cart cart){
        AppUser u=metierUser.getuser(iduser);
        System.out.println("la valeur de la cart est : "+cart.getId_cart());
        System.out.println("la valeur de l'utilisteur es : "+u.getUsername());
        cart.setUsercart(u);
        return metierCart.addcart(cart);
    }

    @PostMapping("/update/")
    public Cart updatecart(Principal principal, @RequestBody Cart cart){
        AppUser user = metierUser.findByUsername(principal.getName());
        //cart = metierCart.saveallcart()
        cart.setUsercart(user);
        user.setCartuser(cart); // association bilaterale user-cart
        metierUser.Adduser(user);
        return metierCart.saveallcart(cart);
    }

    /*LUNDI 18 NOVEMBRE 2019 L user se connecte avec un nouveau panier et souhaite effacer l ancien en Base de Donnees */
    @PostMapping("/clearandupdate/{iduser}")
    public Cart updatecart(Long iduser, @RequestBody Cart cart){
        return metierCart.ViderEtMettreAjour(iduser,cart);
    }


    @GetMapping("/getcart/{iduser}")
    public Cart findcartbyiduser(@PathVariable Long iduser){
        return metierCart.findCartByiduser(iduser);
    }
/**-------------------------------Mapping pour la version finale--------------------------------*/

    /* recuperer un panier
@GetMapping("/get")
public Cart get_cart(Principal principal){
    AppUser user = metierUser.findByUsername(principal.getName());
    Cart cart = user.getCartuser();

    metierCart.updateCart(cart);

    return cart;
} */


/* ajout d un article au panier */
    @RequestMapping("/addCartItem")
    public CartItem addItem(@RequestBody Produit  produit, @RequestBody String qte, Principal principal, Model model){

        AppUser user = metierUser.findByUsername(principal.getName());
        produit = metierProduit.findproduiById(produit.getIdProduit());
        CartItem cartItem = new CartItem();
        if (Integer.parseInt(qte) <= produit.getQuantiteEnStock()){

            cartItem = metierCartitem.add_Product_to_CartItem(produit,user,Integer.parseInt(qte));
            model.addAttribute("addBookSuccess", true);
            System.out.println("Article ajouté au panier avec succès!");
        }else {  model.addAttribute("notEnoughStock",true);
            System.out.println("Il n y a pas assez de " + produit.getNomProduit() + " en stock.");}

        return cartItem;
    }

/* update cartItem --- mise a jour d un article dans le panier*/
    @RequestMapping("/updateCartItem")
    public CartItem updateCart(@RequestBody Long idcartitem, @RequestBody int qte){
        /* rechercher l article du panier a partir de l id envoyé dans la requete */
        CartItem cartItem = metierCartitem.findcartitembyid(idcartitem);
        cartItem.setQuantite(qte);
        metierCartitem.updatecartitem(cartItem);

        return cartItem;
    }
/* retirer un article du panier */
    @RequestMapping("/removeItem/{idItem}")
    public void remove_cartItem(@PathVariable("idItem") Long idItem){
        metierCartitem.remove_cartItem(metierCartitem.findcartitembyid(idItem));

    }

    /* effacer tout le contenu d un  panier*/
    @RequestMapping("/clearCart")
    public void vider_panier(Principal principal){
        AppUser user = metierUser.findByUsername(principal.getName());

        Cart cart = user.getCartuser();
        metierCart.clearCart(cart);
        System.out.println("Le panier a été vidé.");
    }

    @GetMapping("/proposition/{id_cart}/{montant_propose}")
    boolean Add_MontantPropose(@PathVariable double montant_propose,@PathVariable Long id_cart){
        Cart cart=metierCart.findCartByid(id_cart);
        System.out.println(cart.getGrandTotal()+" et le montant"+montant_propose);
        metierCart.proposition_prix(cart,montant_propose);
        return true;
    }

}
