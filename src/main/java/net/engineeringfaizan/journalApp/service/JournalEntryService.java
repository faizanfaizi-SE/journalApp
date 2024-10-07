package net.engineeringfaizan.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringfaizan.journalApp.entity.JournalEntry;
import net.engineeringfaizan.journalApp.entity.User;
import net.engineeringfaizan.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {



    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    //Get all call
    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    //Post call
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e){
            log.error("errpr" ,e);
            throw new RuntimeException("An error occured while saving the entry");
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);

    }

    //get by id
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    //Delete id
    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
             removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);

            }


        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting and entry");
        }

        return removed;

    }
}
