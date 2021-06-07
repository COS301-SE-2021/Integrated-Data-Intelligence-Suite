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
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportService;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportServiceImpl;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportedData;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportDataResponse;
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

		//Initialise Objects
	    SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
	    // Gson gson = new GsonBuilder().setPrettyPrinting().create();

		//Pre-Condition
        if (searchKeywords == null || searchKeywords.length() == 0) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}


		ImportService importService = new ImportServiceImpl();
		ImportDataResponse importDataResponse = null;
		try {
			importDataResponse = importService.importData(new ImportDataRequest(searchKeywords, 10));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(importDataResponse == null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ArrayList<ImportedData> importedDataArrayList = importDataResponse.getList();
		if(importedDataArrayList==null || importedDataArrayList.size() < 1){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ImportedData data = importedDataArrayList.get(0);


		//Fetch Parsed Tweet nodes
		ParseImportedDataRequest parse_request = new ParseImportedDataRequest(DataSource.TWITTER, data.getData()) ;
		ParsingServiceImpl parsing_service_impl = new ParsingServiceImpl();
		ParseImportedDataResponse  parse_response= parsing_service_impl.parseImportedData(parse_request);
		ArrayList<ParsedData> list_of_tweet_nodes = parse_response.getDataList(



		//Extract Tweet Sentiment
		List<TweetWithSentiment> sentiments = new ArrayList<>();
		for (ParsedData parsed_data_node : list_of_tweet_nodes) {
			TweetWithSentiment tweetWithSentiment = sentimentAnalyzer.findSentiment(parsed_data_node.getTextMessage());
			if (tweetWithSentiment != null) {
				sentiments.add(tweetWithSentiment);
			}
		}

		return new ResponseEntity< List<TweetWithSentiment>>(sentiments, HttpStatus.OK);
	}









}
