package de.wesson.hololivesubscribercount.repository;

import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation if a SQLLite Repository
 */
@Repository
public interface HololiveChannelRespository extends MongoRepository<HololiveTalent, String> {


    List<HololiveTalent> findAllByIdolNameLike(String idolName);


}
