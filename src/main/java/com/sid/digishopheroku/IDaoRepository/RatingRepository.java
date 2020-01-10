package com.sid.digishopheroku.IDaoRepository;

import com.sid.digishopheroku.Model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating , Long> {

}
