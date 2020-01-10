package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("select q from Cart q where q.id_cart= :x ")
    Cart findCartByid(@Param("x") Long Id);

    @Query("select q from Cart q where q.usercart.id_user= :x ")
    Cart findCartByiduser(@Param("x") Long Iduser);

    List<Cart> findByCartItemListNotNull();

}
