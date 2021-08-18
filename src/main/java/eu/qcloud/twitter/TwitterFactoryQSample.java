package eu.qcloud.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterFactoryQSample {

    @Value("${twitter.consumer-key}")
    private String consumerKey;

    @Value("${twitter.consumer-secret}")
    private String consumerSecret;

    @Value("${twitter.access-token}")
    private String accessToken;

    @Value("${twitter.access-token-secret}")
    private String accessTokenSecret;

    public Twitter createTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }
}
