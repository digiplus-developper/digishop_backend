package com.sid.digishopheroku.PasswordReset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/password")
public class PasswordResetRestful {
    @Autowired PasswordService passwordService;
    @GetMapping(path = "/emailVerification}",  //path = "/emailVerification/{token}}"
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token){ //@RequestParam(value = "token")
       // public OperationStatusModel verifyEmailToken(@PathVariable(value = "token") String token){ //@RequestParam(value = "token")
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified =  passwordService.verifyEmailToken(token);

        if (isVerified){
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }



    @PostMapping(path = "/resetRequest",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetRequest(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        OperationStatusModel returnValue = new OperationStatusModel();


        boolean operationResult = passwordService.requestPasswordReset(passwordResetRequestModel.getEmail()) ;
        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult){
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        return returnValue;
}
}

