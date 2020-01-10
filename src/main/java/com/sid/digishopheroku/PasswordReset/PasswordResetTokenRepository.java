package com.sid.digishopheroku.PasswordReset;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity,Long> {
}
