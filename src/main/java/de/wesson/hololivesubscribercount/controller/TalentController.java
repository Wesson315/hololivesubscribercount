package de.wesson.hololivesubscribercount.controller;

import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import de.wesson.hololivesubscribercount.repository.HololiveChannelRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.util.List;
/* {"https://hololivecounter.web.app/","https://hololivecounter.firebaseapp.com/","https://hololivecounter.web.app","https://hololivecounter.firebaseapp.com", "http://localhost:4200/","http://localhost:4200"}*/
/**
 * @author Philip Anderson
 **/
@RestController
@CrossOrigin(origins = "*")
public class TalentController {

    @Autowired
    private HololiveChannelRespository repository;

    @GetMapping("/api/talents/talentDetail/{channelid}")
    public HololiveTalent getTalent(@PathVariable(name = "channelid") String channelid) {
        return repository.findById(channelid).orElse(new HololiveTalent());
    }

    public void addOrUpdateTalent(@RequestBody HololiveTalent channel) {
        repository.save(channel);
    }

    @GetMapping("/api/talents/talents")
    public List<HololiveTalent> getAllTalents() {
        return (List<HololiveTalent>)repository.findAll(Sort.by(Sort.Direction.DESC,"subscriberCount"));
    }

    @GetMapping("/api/talents/findByName/{idolName}")
    public List<HololiveTalent> findByName(@PathVariable(name="idolName") String idolName){
        return repository.findAllByIdolNameLike(idolName);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    @GetMapping("/api/talents/getTalentCount")
    public long getTalentCount(){
        return repository.count();
    }

    @GetMapping(value = "/data/avatars/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    @Cacheable("image")
    public byte[] getAvatar(@PathVariable(name="fileName") String fileName){
        try {
            FileInputStream stream = new FileInputStream("data/avatars/"+fileName);
            return stream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


}
