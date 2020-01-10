package com.sid.digishopheroku.Model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MTN_MONEY")
@PrimaryKeyJoinColumn(name = "id_Payment")
public class MtnMoney extends Payment {

    private String  ReceiverNumber;
    private String  StatusCode;
    private String  Amount;
    private String  TransactionID;
    private String  ProcessingNumber;
    private String  OpComment;
    private String  StatusDesc;
    private String  SenderNumber;
    private String  OperationType;


    public String getReceiverNumber() {
        return ReceiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        ReceiverNumber = receiverNumber;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public String getProcessingNumber() {
        return ProcessingNumber;
    }

    public void setProcessingNumber(String processingNumber) {
        ProcessingNumber = processingNumber;
    }

    public String getOpComment() {
        return OpComment;
    }

    public void setOpComment(String opComment) {
        OpComment = opComment;
    }

    public String getStatusDesc() {
        return StatusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        StatusDesc = statusDesc;
    }

    public String getSenderNumber() {
        return SenderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        SenderNumber = senderNumber;
    }

    public String getOperationType() {
        return OperationType;
    }

    public void setOperationType(String operationType) {
        OperationType = operationType;
    }
}
