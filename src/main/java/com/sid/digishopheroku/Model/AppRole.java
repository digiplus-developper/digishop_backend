package com.sid.digishopheroku.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class AppRole implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String rolename;


    public AppRole(String rolename) {
        this.rolename = rolename;
    }

    public AppRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
