package net.engineeringfaizan.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringfaizan.journalApp.entity.User;
import net.engineeringfaizan.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //we dont need to write it again and again in all files so we use slf4j annotation
//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    //Post call
    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.info("Hhahahah");
            log.debug("error ooccured for {}" , user.getUserName());
            return false;
        }

    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User" ,"Admin"));
        userRepository.save(user);
    }

    //Get all call
    public List<User> getAllEntries(){
        return userRepository.findAll();
    }

    //get by id
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    //Delete id
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}
