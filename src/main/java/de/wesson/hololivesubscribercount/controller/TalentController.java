package de.wesson.hololivesubscribercount.controller;

import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import de.wesson.hololivesubscribercount.repository.HololiveChannelRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/* {"https://hololivecounter.web.app/","https://hololivecounter.firebaseapp.com/","https://hololivecounter.web.app","https://hololivecounter.firebaseapp.com", "http://localhost:4200/","http://localhost:4200"}*/
/**
 * @author Philip Anderson
 **/
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TalentController {

    @Autowired
    private HololiveChannelRespository repository;

    @GetMapping("/talentDetail/{channelid}")
    public HololiveTalent getTalent(@PathVariable(name = "channelid") String channelid) {
        return repository.findById(channelid).orElse(new HololiveTalent());
    }

    public void addOrUpdateTalent(@RequestBody HololiveTalent channel) {
        repository.save(channel);
    }

    @GetMapping("/talents")
    public List<HololiveTalent> getAllTalents() {
        return (List<HololiveTalent>)repository.findAll(Sort.by(Sort.Direction.DESC,"subscriberCount"));
    }

    @GetMapping("/findByName/{idolName}")
    public List<HololiveTalent> findByName(@PathVariable(name="idolName") String idolName){
        return repository.findAllByIdolNameLike(idolName);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    @GetMapping("/getTalentCount")
    public long getTalentCount(){
        return repository.count();
    }


}
