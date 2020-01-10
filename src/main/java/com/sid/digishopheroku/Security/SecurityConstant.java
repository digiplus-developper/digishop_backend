package com.sid.digishopheroku.Security;

public class SecurityConstant  {

    public static final String SECRET="fokoutghislain@gmail.com";
    public static final long EXPIRATION_TIME=864_000_000;//10days
    public static final long PASSWORD_RESET_EXPIRATION_TIME=3600000;//1days

    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";

    public static final String SIGN_UP_URL = "/register";
    public static final String VERIFICATION_EMAIL_URL = "/password/emailVerification";

//Methode pour recuperer le SECRET dans application.properties
  /*  public static String getTokenSecret(){
        AppProperties  appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }*/
}
