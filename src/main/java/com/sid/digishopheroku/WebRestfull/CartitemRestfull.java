package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierCart;
import com.sid.digishopheroku.Metier.MetierCartitem;
import com.sid.digishopheroku.Metier.MetierProduit;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.Cart;
import com.sid.digishopheroku.Model.CartItem;
import com.sid.digishopheroku.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cartitem")
public class CartitemRestfull {

    @Autowired
    private MetierCartitem metierCartitem;
    @Autowired
    private MetierCart metierCart;
    @Autowired
    MetierProduit metierProduit;



    @RequestMapping(value = "/save/{idcart}/{idproduit}",method = RequestMethod.POST)
     public CartItem Saveuser(@PathVariable Long idcart,@PathVariable Long idproduit,@RequestBody CartItem cartItem){
        Cart cart=metierCart.findCartByid(idcart);
       // cartItem.setCartcartitem(cart);
        Produit produit=metierProduit.findproduiById(idproduit);
       /* cartItem.setProduit(produit);
        cartItem.setSubtotal(produit.getPrixProduit());*/

        return metierCartitem.add_Product_to_CartItem(produit,cart.getUsercart(),1);
    }

    @RequestMapping(value = "/update/{idprodui}/{idcart}",method = RequestMethod.PUT)
     public CartItem updateuser(@PathVariable Long idprodui,@PathVariable Long idcart,@RequestBody CartItem cartItem){
        CartItem ctitem= metierCartitem.findcartitembyidproduit(idprodui,idcart);
        Cart cart=metierCart.findCartByid(idcart);
        cartItem.setCartcartitem(cart);
        cartItem.setId_Item(ctitem.getId_Item());
        return metierCartitem.updatecartitem(cartItem);

        //https://www.jmdoudoux.fr/java/dej/chap-spring_core.htm#spring_core-6
    }



    @GetMapping("/commande/{idcommande}")
    List<CartItem> findbyCommande(@PathVariable Long idcommande){
       return metierCartitem.findbyCommande(idcommande);
    }

    @RequestMapping(value = "/delectitem/{idproduit}/{idcart}",method = RequestMethod.DELETE)
    public void delectitem(@PathVariable Long idproduit,@PathVariable Long idcart){
        CartItem cartItem=metierCartitem.findcartitembyidproduit(idproduit,idcart);
        metierCartitem.delectitem(cartItem.getId_Item());
    }

    @PostMapping("/saveallitem/{idcart}")
    List<CartItem> saveallcart(@PathVariable Long idcart, @RequestBody List<CartItem> cartItemList){
        int i=0;
        Cart cart=metierCart.findCartByid(idcart);
        while (i<cartItemList.size()){
            cartItemList.get(i).setCartcartitem(cart);
            System.out.println(cartItemList.get(i).getProduit().getNomProduit());
            i++;
        }
        delectallitem(idcart);
        return metierCartitem.saveallcartitem(cartItemList);
    }

    @RequestMapping(value = "/delectallitem/{idcart}",method = RequestMethod.DELETE)
    public void delectallitem(@PathVariable Long idcart){
       List<CartItem> cartItems= metierCartitem.findCartitemByidcart(idcart);
        metierCartitem.delectallcartitem(cartItems);

    }
}
