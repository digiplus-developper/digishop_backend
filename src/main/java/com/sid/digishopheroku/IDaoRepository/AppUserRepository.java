package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    @Query("select q from AppUser q where q.id_user= :x ")
    AppUser findUserById(@Param("x") Long Id);
    AppUser findByUsername(String username);
    List<AppUser> findByEnabledIsTrue();
    AppUser findByPhoneAndEnabledIsTrue(String phone);
    AppUser findAppUserByPhone(String phone);
    List<AppUser> findByActivervendeurIsTrue();


    /* Rechercher un utilisateur  par username, email ou phone selon le parametre entre
    *  Cette fonction est utile a la connexion */
    @Query("select u from AppUser u where u.username = :name or u.email = :name or u.phone =: name")
    AppUser findAppUserByUsernameOrEmailOrPhone(@Param("name") String name);

    @Query("select q from AppUser q where q.username like :x ")
    AppUser findClientByMot(@Param("x") String motcle);

    @Query("select q from AppUser q where q.phone= :x and q.password= :y ")
    AppUser connection(@Param("x") String numero, @Param("y") String password);

    @Query("delete from AppUser p where p.id_user= :x")
     void delectClient(@Param("x") Long id);

    List<AppUser> findByBoutique(Boutique boutique);

    List<AppUser> findByCartuserNotNull();

    AppUser findByEmail(String email);

    // password reset token
    AppUser findAppUserByEmailVerificationToken(String token);
}
