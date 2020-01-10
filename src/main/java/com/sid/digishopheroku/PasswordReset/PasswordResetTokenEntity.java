package com.sid.digishopheroku.PasswordReset;

import com.sid.digishopheroku.Model.AppUser;

import javax.persistence.*;
import java.io.Serializable;
@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "users_id")
    private AppUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
