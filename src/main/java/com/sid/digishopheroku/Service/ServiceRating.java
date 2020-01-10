package com.sid.digishopheroku.Service;

import com.sid.digishopheroku.IDaoRepository.RatingRepository;
import com.sid.digishopheroku.Metier.MetierBoutique;
import com.sid.digishopheroku.Metier.MetierRating;
import com.sid.digishopheroku.Metier.MetierUser;
import com.sid.digishopheroku.Model.Boutique;
import com.sid.digishopheroku.Model.Rating;
import com.sid.digishopheroku.WebRestfull.Forms.RatingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRating implements MetierRating {
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    MetierBoutique metierBoutique;
    @Autowired
    MetierUser metierUser;

    @Override
    public Rating addRating(RatingForm ratingForm) {
        Rating rating =  new Rating();
        rating.setBoutique(metierBoutique.getBoutiqueById(ratingForm.getIdBoutique()));
        rating.setUser(metierUser.findByiduser(ratingForm.getIdUser()));
        rating.setRating(ratingForm.getRating());
        rating.setCommentaire(ratingForm.getCommentaire());
        rating.setDate(ratingForm.getDate());


        return ratingRepository.save(rating);
    }

    @Override
    public double calculMoyenne(Long id_boutique) {
        Boutique  boutique = metierBoutique.getBoutiqueById(id_boutique);
        if (boutique ==null)throw new RuntimeException("THE SHOP " + id_boutique+ " DOESNT EXIST.");
        double note = 0;
        for (Rating rating : boutique.getRatings()){
            note += rating.getRating()/boutique.getRatings().size();
        }
        return note;
    }

    @Override
    public Rating updateRating( Rating rating) {
        Rating  r = ratingRepository.getOne(rating.getId());
        r.setDate(rating.getDate());
        r.setRating(rating.getRating());
        r.setCommentaire(rating.getCommentaire());
        return ratingRepository.save(r);
    }
}
