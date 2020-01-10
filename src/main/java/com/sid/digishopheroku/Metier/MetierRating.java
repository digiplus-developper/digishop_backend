package com.sid.digishopheroku.Metier;

import com.sid.digishopheroku.Model.Rating;
import com.sid.digishopheroku.WebRestfull.Forms.RatingForm;

public interface MetierRating {

   Rating addRating(RatingForm ratingForm);
   double calculMoyenne(Long id_boutique);
   Rating updateRating(Rating rating);
}
