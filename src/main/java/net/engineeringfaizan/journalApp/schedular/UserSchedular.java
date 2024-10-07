package net.engineeringfaizan.journalApp.schedular;

import net.engineeringfaizan.journalApp.cache.AppCache;
import net.engineeringfaizan.journalApp.entity.JournalEntry;
import net.engineeringfaizan.journalApp.entity.User;
import net.engineeringfaizan.journalApp.enums.Sentiment;
import net.engineeringfaizan.journalApp.repository.UserRepositoryImpl;
import net.engineeringfaizan.journalApp.service.EmailService;
import net.engineeringfaizan.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


    @Autowired
    private AppCache appCache;

//    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for (User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x ->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment , Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments){
                    if (sentiment != null)
                        sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment , 0)+1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment , Integer> entry : sentimentCounts.entrySet()){
                if (entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null){
                emailService.mailSender(user.getEmail(),"Sentiment for last 7 days", mostFrequentSentiment.toString());

            }
//            String entry = String.join(" " ,fliterEntries);

//            String sentiment = sentimentAnalysisService.getSentiment(entry);


        }
    }
    @Scheduled(cron = "0 0/5 * * * *")
    public void clearAppCache(){
        appCache.init();
    }
}
