package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Adresse;

public interface MetierAdress {

    Adresse add_adress(Adresse adresse);
    Adresse find_adress(Long id_adress);
}
