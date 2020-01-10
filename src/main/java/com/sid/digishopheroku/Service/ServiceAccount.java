package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.AppUserRepository;
import com.sid.digishopheroku.IDaoRepository.RoleRepository;
import com.sid.digishopheroku.Metier.MetierAccount;
import com.sid.digishopheroku.Model.AppRole;
import com.sid.digishopheroku.Model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("serviceaccount")
@Transactional
public class ServiceAccount implements MetierAccount {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public AppUser saveuser(AppUser user ) {
        String hashpw=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashpw);
        return userRepository.save(user);
    }

    @Override
    public AppRole saverole(AppRole role) {
            return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String phone, String rolename) {
        AppRole role=roleRepository.findByRolename(rolename);
        System.out.println("le role est : "+role.getRolename());
        AppUser user=userRepository.findByPhoneAndEnabledIsTrue(phone);
        System.out.println("le user est : "+user.getId_user());
        user.getRoles().add(role);
    }

    @Override
    public AppUser findUserByphone(String phone) {
        return userRepository.findByPhoneAndEnabledIsTrue(phone);
    }

    // rechercher un user selon differents parametres


    @Override
    public AppUser findUserByUsernameOrEmailOrPhone(String name) {
        return userRepository.findAppUserByUsernameOrEmailOrPhone(name);
    }

    @Override
    public AppUser resetPassword(Long iduser,String pwd) {
        AppUser user = userRepository.findUserById(iduser);
        user.setPassword(pwd);
        saveuser(user);
        return user;
    }
}
