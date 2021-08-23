package eu.qcloud.tip;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import eu.qcloud.twitter.TwitterPoster;
import twitter4j.TwitterException;

@Service
public class TipService {

    @Autowired
    TipRepository tipRepository;

    @Autowired
    TwitterPoster twitterPoster;

    public Tip saveTip(Tip tip) {
        return tipRepository.save(tip);
    }

    @Scheduled(fixedRateString = "${qcloud.tip-refresh}")
    public void checkPublish() throws TwitterException {
        Optional<List<Tip>> tipsOpt = tipRepository.findByPublishedTwitterAndShowAtBefore(false, new Date());
        if (tipsOpt.isPresent()) {
            unShowAll();
            for (Tip tip : tipsOpt.get()) {
                // publisho to twitter method here
                System.out.println("Publishing " + tip.getTitle());
                tip.setDisplay(true); // I need another cron to set display to false
                if (!tip.isPublishedTwitter()) { // if this boolean is false we publish the tweet and then we set it to true 
                    twitterPoster.postTweet(tip.getTwitterText());
                    tip.setPublishedTwitter(true);
                }
            }
            tipRepository.saveAll(tipsOpt.get());
        }
        System.out.println("Publish Cron " + new Date());
    }

    private void unShowAll() {
        Optional<List<Tip>> publishedRightNow = tipRepository.findByDisplay(true);
        if (publishedRightNow.isPresent()) {
            for (Tip published : publishedRightNow.get()) {
                published.setDisplay(false);
            }
            tipRepository.saveAll(publishedRightNow.get());
        }

    }

    public List<Tip> getAllTips() {
        return tipRepository.findAll();
    }

    public boolean deleteTip(Long id) {
        try {
            tipRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Tip> getActive() {
        return tipRepository.findAllByDisplay(true);
    }
}
