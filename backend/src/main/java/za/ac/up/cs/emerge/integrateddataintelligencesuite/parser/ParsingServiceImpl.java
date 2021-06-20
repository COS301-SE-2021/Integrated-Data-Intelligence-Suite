package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.ParsedData;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.mocks.Mock;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.repository.ParsedDataRepository;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.*;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.*;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri.TwitterExtractor;

import java.util.*;

public class ParsingServiceImpl implements ParsingService{

    @Autowired
    private ParsedDataRepository parsedDataRepository;

    @Override
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException, JSONException {
        if (request == null) {
            throw new InvalidRequestException("Request object is null");
        }
        else{
            if (request.getJsonString() == null || request.getJsonString().isEmpty()){
                throw new InvalidRequestException("Imported string is null");
            }

            if (request.getType() == null){
                throw new InvalidRequestException("Imported type is null");
            }
        }

        System.out.println(request.getJsonString());
        JSONObject obj = new JSONObject(request.getJsonString());
        JSONArray jsonArray = obj.getJSONArray("statuses");;
        ArrayList<ParsedData> parsedList = new ArrayList<>();

        if (request.getType() == DataSource.TWITTER){
            for (int i=0; i < jsonArray.length(); i++){
                //create and set node
                ParsedData parsedData = new ParsedData();
                TwitterExtractor extractor = new TwitterExtractor();

                //parse text data from post
                GetTextRequest textRequest = new GetTextRequest(jsonArray.get(i).toString());
                GetTextResponse textResponse = extractor.getText(textRequest);
                parsedData.setTextMessage(textResponse.getText());

                //parse date data from post
                GetDateRequest dateRequest = new GetDateRequest(jsonArray.get(i).toString());
                GetDateResponse dateResponse = extractor.getDate(dateRequest);
                parsedData.setDate(dateResponse.getDate());

                //parse location data from post
                GetLocationRequest locationRequest = new GetLocationRequest(jsonArray.get(i).toString());
                GetLocationResponse locationResponse = extractor.getLocation(locationRequest);
                parsedData.setLocation(locationResponse.getLocation());

                //parse likes from post
                GetLikesRequest likesRequest = new GetLikesRequest(jsonArray.get(i).toString());
                GetLikesResponse likesResponse = extractor.getLikes(likesRequest);
                parsedData.setLikes(likesResponse.getLikes());

                parsedList.add(parsedData);

            }
        }

        parsedDataRepository.saveAll(parsedList);
        return new ParseImportedDataResponse(parsedList);
    }
}
