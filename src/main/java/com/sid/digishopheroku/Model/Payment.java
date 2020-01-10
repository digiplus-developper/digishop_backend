package com.sid.digishopheroku.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id_Payment;
    @JsonIgnore
    @OneToMany(mappedBy = "payment")
    private List<Commande> commandeList;

    public Long getId_Payment() {
        return id_Payment;
    }

    public void setId_Payment(Long id_Payment) {
        this.id_Payment = id_Payment;
    }

    public List<Commande> getCommandeList() {
        return commandeList;
    }

    public void setCommandeList(List<Commande> commandeList) {
        this.commandeList = commandeList;
    }
}
