package za.ac.up.cs.emerge.integrateddataintelligencesuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.analyzer.SentimentAnalyzer;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.twitterManager.TweetWithSentiment;


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

	    // TweetsManager tweetsManager = new TweetsManager(consumer_key, consumer_secret, access_token, access_token_secret); 
	    SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

	    // Gson gson = new GsonBuilder().setPrettyPrinting().create();

		//Pre-Condition
        // if (searchKeywords == null || searchKeywords.length() == 0) {
		// 	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        // }

    
		//Execute the Search Query on the Twitter API
		//Wandi function

		//parser Import Data

		//Steve And Rhuli

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
