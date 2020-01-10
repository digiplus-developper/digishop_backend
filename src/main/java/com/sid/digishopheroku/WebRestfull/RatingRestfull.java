package com.sid.digishopheroku.WebRestfull;

import com.sid.digishopheroku.Metier.MetierRating;
import com.sid.digishopheroku.Model.Rating;
import com.sid.digishopheroku.WebRestfull.Forms.RatingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingRestfull {
    @Autowired
    MetierRating metierRating;

    // ajouter un Rating à une Boutique
    @PostMapping("/addRating")
    Rating addnewRating(@RequestBody RatingForm ratingForm){
        return metierRating.addRating(ratingForm);
    }
    // obtenir la note moyenne d'une boutique
    @GetMapping("/getrating/{id_boutique}")
    Double getMoyenneRatings(@PathVariable Long id_boutique){
        return metierRating.calculMoyenne(id_boutique);
    }
//mise a jour d un rating
@PutMapping("/updateRating}")
Rating addnewRating(@RequestBody Rating rating) {
    return metierRating.updateRating(rating);
}

}
