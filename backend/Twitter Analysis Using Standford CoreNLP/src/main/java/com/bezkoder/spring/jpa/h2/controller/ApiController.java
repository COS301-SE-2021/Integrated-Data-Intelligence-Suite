package com.bezkoder.spring.jpa.h2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bezkoder.spring.jpa.h2.analyzer.SentimentAnalyzer;
import com.bezkoder.spring.jpa.h2.twitterManager.TweetWithSentiment;
import com.bezkoder.spring.jpa.h2.twitterManager.TweetsManager;

// //////////////////////////////////////////////////////////////////////////////////////////
// /////////////////////////////// API mapping + REST points ///////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Status;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ApiController {

	// @Autowired
	// ApiRepository tutorialRepository; Will be Used Later for Database Interaction


	static String consumer_key = ""; 
	static String consumer_secret ="" ;
	static String access_token = ""; 
	static String access_token_secret = "";

	@GetMapping("tutorials/search/{searchKeywords}")
	public ResponseEntity< List<TweetWithSentiment>> fetch_tweets(@PathVariable String searchKeywords) throws InterruptedException, IOException{

	    TweetsManager tweetsManager = new TweetsManager(consumer_key, consumer_secret, access_token, access_token_secret); 
	    SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

	    // Gson gson = new GsonBuilder().setPrettyPrinting().create();

		//Pre-Condition
        if (searchKeywords == null || searchKeywords.length() == 0) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
        //In Case of Multiple Search terms are entered
        Set<String> keywords = new HashSet<>();
        for (String keyword : searchKeywords.split(",")) {
            keywords.add(keyword.trim().toLowerCase());
        }
        if (keywords.size() > 3) {
            keywords = new HashSet<>(new ArrayList<>(keywords).subList(0, 3));
        }
        

		//Execute the Search Query on the Twitter API
		List<Status> statuses = tweetsManager.performQuery(searchKeywords);
		System.out.println("Found statuses ... " + statuses.size());

		//Get A Tweet with its relevant Sentiment
		//(Tweet,Sentiment)
		List<TweetWithSentiment> sentiments = new ArrayList<>();
		for (Status status : statuses) {
			TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(status.getText());
			if (tweetWithSentiment != null) {
				sentiments.add(tweetWithSentiment);
			}
		}


		return new ResponseEntity< List<TweetWithSentiment>>(sentiments, HttpStatus.OK);





	}









}
