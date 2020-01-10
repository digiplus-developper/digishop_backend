package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.AppRole;
import com.sid.digishopheroku.Model.AppUser;

public interface MetierAccount {
    AppUser saveuser(AppUser userm);
    AppRole saverole(AppRole role);
    void  addRoleToUser(String username,String rolename);

    AppUser findUserByUsernameOrEmailOrPhone( String name);
    AppUser findUserByphone(String username);
    public AppUser resetPassword(Long iduser,String pwd);
}
