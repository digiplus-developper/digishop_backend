package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.Model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository  extends JpaRepository<Favorites, Long> {
        Favorites findByAppUser(AppUser user);

}
