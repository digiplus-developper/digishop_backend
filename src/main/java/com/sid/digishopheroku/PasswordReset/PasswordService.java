package com.sid.digishopheroku.PasswordReset;

public interface PasswordService {
    boolean requestPasswordReset(String email);

    boolean verifyEmailToken(String token);
}
