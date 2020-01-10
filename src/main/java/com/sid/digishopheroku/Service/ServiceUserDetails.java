package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.Model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service("serviceuserdetails")
public class ServiceUserDetails implements UserDetailsService {

    @Autowired
    private ServiceAccount serviceAccount;


    @Override
    public UserDetails loadUserByUsername(String usernameorphoneoremail) throws UsernameNotFoundException {

        //AppUser user=serviceAccount.findUserByUsername(username);
        AppUser user = serviceAccount.findUserByUsernameOrEmailOrPhone(usernameorphoneoremail);

        if (user==null || user.isActivervendeur()==false)throw new UsernameNotFoundException(usernameorphoneoremail);
        Collection<GrantedAuthority> authorities=new ArrayList<>();//on a une collection de role
        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRolename()));
        });
        return new User(user.getPhone(),user.getPassword(),authorities);
//   retour en verifiant si l user à valider le token envoyer par email
//    return new User(String username, String password,  user.getEmailVerificationStatus(), true, true , true , authorities);

    }
}
