package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierCart;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserRestfull {
    @Autowired
    private MetierUser metierUser;


    @RequestMapping(value = "/allclient", method = RequestMethod.GET)
    public List<AppUser> getAllProduit() {
        return metierUser.getAlluser();
    }

    @RequestMapping(value = "/connection/{numero}", method = RequestMethod.GET)
    public AppUser connection(@PathVariable String numero){
        return metierUser.connection(numero);
    }

    @RequestMapping(value = "/vendeur/{shop_name}", method = RequestMethod.GET)
    public List<AppUser> getvendeur(@PathVariable String shop_name){
        return metierUser.findByBoutique(shop_name);
    }


    @RequestMapping(value = "/deconnect/{id}",method = RequestMethod.GET)
    public AppUser deconnect(@PathVariable Long id){
        System.out.println("deconnection");
        return metierUser.deconnection(id);
    }


    //https://www.jmdoudoux.fr/java/dej/chap-spring_core.htm#spring_core-6


}
