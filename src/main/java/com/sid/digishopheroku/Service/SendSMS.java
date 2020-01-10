package com.sid.digishopheroku.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SendSMS {

    private final static String ACCOUNT_SID = "ACfd0e6673945a504d368ca6b18c67b0d1";
    private final static String AUTH_ID = "a5a9119f552a05bf7e1f4bb913bb8f06";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }


    boolean sendsmsuser(String to_number){

        Message.creator(new PhoneNumber(to_number), new PhoneNumber("+17069560319"),
                "nous vous remercions").create();
        return true;
    }

}
