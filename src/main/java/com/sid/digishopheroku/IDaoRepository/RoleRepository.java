package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole,Long> {

    AppRole findByRolename(String rolename);
}
