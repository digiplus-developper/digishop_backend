package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierCart;
import com.sid.digishopheroku.Metier.MetierFavorites;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Cart;
import com.sid.digishopheroku.Model.Favorites;
import com.sid.digishopheroku.PasswordReset.Utils;
import com.sid.digishopheroku.Service.ServiceAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@CrossOrigin("*")
@RestController
public class AccountRestController {

    @Autowired
    private ServiceAccount serviceAccount;
    @Autowired
    private MetierCart metierCart;
    @Autowired
    private MetierFavorites metierFavorites;
    @Autowired
    private MetierUser metierUser;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Utils utils;

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user){
        AppUser appUser=serviceAccount.findUserByphone(user.getUsername());

        if (appUser!=null) throw new RuntimeException("this user already exists");
        //****************************************************************************
        //generer le token de verification des emails
        String publicUserId = utils.generateUserId(30);
        appUser.setUserId(publicUserId);
        user.setEmailVerificationToken(utils.generateEmailVerificationToken(publicUserId));
        user.setEmailVerificationStatus(false);
        //******************************************************************************
        user=serviceAccount.saveuser(user);
        Cart cart=new Cart();
        Favorites favorites=new Favorites();
        cart.setUsercart(user);

        favorites.setAppUser(user);


        metierFavorites.addFavorites(favorites);
        metierCart.addcart(cart);
        System.out.println("user id "+user.getId_user());
        System.out.println("panier id "+cart.getId_cart());
        System.out.println("favorite id "+favorites.getId());
        serviceAccount.addRoleToUser(user.getPhone(),"USER");
        return user;
    }


    @GetMapping("/logout")
    public String page_deconnexion(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            AppUser user = (AppUser)auth.getPrincipal();
            metierUser.deconnection(user.getId_user());

            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/";

        //https://www.javatpoint.com/spring-security-login-logout
    }

    @GetMapping("/")
    public String index(){
        return "index";

    }

    @PostMapping("/admin/resetpassword")
    public  AppUser resetPassword(@RequestBody ResetPasswordForm resetPasswordForm, Principal principal){
        System.out.println(resetPasswordForm.getNewPassword());
        System.out.println(resetPasswordForm.getNewPassword2());
        System.out.println(resetPasswordForm.getOldPassword());
        AppUser user = serviceAccount.findUserByphone(principal.getName());
        System.out.println(principal.getName());
        // AppUser  user = metierUser.findById(resetPasswordForm.getId());
        String oldPwd = bCryptPasswordEncoder.encode(resetPasswordForm.getOldPassword());
        String newPwd = bCryptPasswordEncoder.encode(resetPasswordForm.getNewPassword());
        String newPwd2 = bCryptPasswordEncoder.encode(resetPasswordForm.getNewPassword2());
        System.out.println(newPwd);
        System.out.println(newPwd2);
        System.out.println(user.getPassword());
        System.out.println(oldPwd);
        if (!user.getPassword().equals(oldPwd))throw new RuntimeException(/*principal.getName() +*/ "You Most Enter your right password.");
        if (!newPwd.equals(newPwd2))throw new RuntimeException(" The Passwords you entered are differents.");

        return serviceAccount.resetPassword(user.getId_user(),newPwd);
    }
}
