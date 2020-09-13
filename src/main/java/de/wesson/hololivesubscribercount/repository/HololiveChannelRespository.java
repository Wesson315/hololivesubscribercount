package de.wesson.hololivesubscribercount.repository;

import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation if a SQLLite Repository
 */
@Repository
public interface HololiveChannelRespository extends PagingAndSortingRepository<HololiveTalent, String> {


    List<HololiveTalent> findAllByIdolNameLike(String idolName);


}
