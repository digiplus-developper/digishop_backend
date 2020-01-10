package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.AppUserRepository;
import com.sid.digishopheroku.Metier.MetierBoutique;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "serviceuser")
public class ServiceUser implements MetierUser {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    private MetierBoutique metierBoutique;
    @Override
    public AppUser Adduser(AppUser appUser) {

        return appUserRepository.save(appUser);
    }

    @Override
    public ArrayList<AppUser> getAlluser() {
        return (ArrayList<AppUser>) appUserRepository.findByEnabledIsTrue();
    }

    @Override
    public AppUser getuser(Long iduser) {
        return appUserRepository.findUserById(iduser);
    }

    @Override
    public AppUser connection(String numero)
    {
        return appUserRepository.findAppUserByUsernameOrEmailOrPhone(numero);
    }

    @Override
    public AppUser deconnection(Long iduser) {
        AppUser appUser = appUserRepository.findUserById(iduser);
        appUser.setEnabled(false);
        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public AppUser findByiduser(Long iduser) {
        return appUserRepository.findUserById(iduser);
    }

    @Override
    public void delectuser(Long id_user) {
        appUserRepository.deleteById(id_user);
    }

    @Override
    public List<AppUser> findByBoutique(String shop_name) {
        Boutique boutique=metierBoutique.findByNomBoutique(shop_name);
        List<AppUser> appUsers=appUserRepository.findByBoutique(boutique);
        System.out.println("l'utilisateur est: "+appUsers.get(0).getUsername());
        List<AppUser> appUsers1=new ArrayList<>();
        /*  int i=0;
        while (i<appUsers.size()){
            List<AppRole> roles= (List<AppRole>) appUsers.get(i).getRoles();
            System.out.println("le role est: "+roles.get(i).getRolename());
            int j=0;
            while (j<roles.size()){
                if (roles.get(j).getRolename().equalsIgnoreCase("VENDEUR")){
                    appUsers1.add(appUsers.get(i));
                    System.out.println("le role est: "+appUsers1.get(j).getUsername());
                    j++;
                }
            }
            i++;
        }*/
        return appUsers;
    }

    @Override
    public List<AppUser> UsersWithCartNotEmpty(Boutique boutique) {
        List<AppUser> appUsers = appUserRepository.findAll();
        for (AppUser user : appUsers){
            /* Condition 1 : si La liste des Articles dans le Panier de l'USER est vide
            * Condition 2 : si La liste des Articles dans le Panier de l'USER est null
            * Condition 1 : Si un La Boutique d'origine d'un produit dans le panier ne correspond à la Boutique consideree */
            if (user.getCartuser().getCartItemList().isEmpty()
                    || user.getCartuser().getCartItemList()==null
                    ||user.getCartuser().getCartItemList().get(0).getProduit().getBoutiquesProduit().getIdBoutique()!=boutique.getIdBoutique()){
                appUsers.remove(user);
            }
        }
        return appUsers;
    }
}
