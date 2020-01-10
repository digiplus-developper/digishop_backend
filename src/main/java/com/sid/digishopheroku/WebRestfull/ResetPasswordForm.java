package com.sid.digishopheroku.WebRestfull;

public class ResetPasswordForm {
    private  String oldPassword;
    private String newPassword;
    private String newPassword2;
    // private Long id;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
/*
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    */
}
