package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.AdressRepository;
import com.sid.digishopheroku.Metier.MetierAdress;
import com.sid.digishopheroku.Model.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "serviceadress")
public class ServiceAdress implements MetierAdress {

    @Autowired
    AdressRepository adressRepository;

    @Override
    public Adresse add_adress(Adresse adresse) {
        return adressRepository.save(adresse);
    }

    @Override
    public Adresse find_adress(Long id_adress) {
        return adressRepository.getOne(id_adress);
    }
}
