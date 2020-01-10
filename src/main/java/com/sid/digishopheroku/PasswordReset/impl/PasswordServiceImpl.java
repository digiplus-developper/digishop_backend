package com.sid.digishopheroku.PasswordReset.impl;

import com.sid.digishopheroku.AmazonMail.AmazonSES;
import com.sid.digishopheroku.IDaoRepository.AppUserRepository;
import com.sid.digishopheroku.Model.AppUser;
import com.sid.digishopheroku.PasswordReset.PasswordResetTokenEntity;
import com.sid.digishopheroku.PasswordReset.PasswordResetTokenRepository;
import com.sid.digishopheroku.PasswordReset.PasswordService;
import com.sid.digishopheroku.PasswordReset.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        // Retrouver un User à partir de son token
        AppUser user =userRepository.findAppUserByEmailVerificationToken(token);

        if (user!=null){
            //Verifier si le token est valide
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if (!hastokenExpired){
                user.setEmailVerificationToken(null);
                user.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(user);
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        boolean returnValue = false;

        AppUser user = userRepository.findByEmail(email);

        // Verifier que l User dont l email a ete entree existe
        if (user == null){
            return returnValue;
        }
                        // userId : Long --> String
        String token = new Utils().generatePasswordResetToken(user.getId_user().toString());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUser(user);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        returnValue = new AmazonSES().sendPasswordResetRequest(
                user.getFirstName(),
                user.getEmail(),
                token
        );

        return false;
    }
}
