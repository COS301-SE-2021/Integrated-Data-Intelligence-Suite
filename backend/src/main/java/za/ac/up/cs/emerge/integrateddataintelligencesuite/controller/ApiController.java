package za.ac.up.cs.emerge.integrateddataintelligencesuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.analyzer.SentimentAnalyzer;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.ParsingServiceImpl;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.ParsedData;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.twitterManager.TweetWithSentiment;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ApiController {

	// @Autowired
	// ApiRepository tutorialRepository; Will be Used Later for Database Interaction

	@GetMapping("tutorials/search/{searchKeywords}")
	public ResponseEntity< List<TweetWithSentiment>> fetch_tweets(@PathVariable String searchKeywords) throws InterruptedException, IOException, JSONException, InvalidRequestException {

	    SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

	    // Gson gson = new GsonBuilder().setPrettyPrinting().create();

		//Pre-Condition
        if (searchKeywords == null || searchKeywords.length() == 0) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		//Wandi function

		//parser Import Data

		//Steve And Rhuli
		
		ParseImportedDataRequest parse_request = new ParseImportedDataRequest(DataSource.TWITTER, "must get from wandi") ;
		
		ParsingServiceImpl parsing_service_impl = new ParsingServiceImpl();


		ParseImportedDataResponse  parse_response= parsing_service_impl.parseImportedData(parse_request);
		ArrayList<ParsedData> list_of_tweet_nodes = parse_response.getDataList();

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
