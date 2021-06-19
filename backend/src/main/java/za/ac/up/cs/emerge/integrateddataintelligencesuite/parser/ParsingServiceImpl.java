package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.ParsedData;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.mocks.Mock;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetLocationRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetLocationResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri.TwitterExtractor;

import java.util.*;

public class ParsingServiceImpl implements ParsingService{

    @Override
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException, JSONException {
        if (request == null) {
            throw new InvalidRequestException("ParseImportedDataRequest Object is null");
        }
        else{
            if (request.getJsonString() == null){
                throw new InvalidRequestException("Imported string is null");
            }

            if (request.getType() == null){
                throw new InvalidRequestException("Imported type is null");
            }
        }

        JSONObject obj = new JSONObject(request.getJsonString());
        JSONArray jsonArray = obj.getJSONArray("statuses");;
        ArrayList<ParsedData> newList = new ArrayList<>();

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

                newList.add(parsedData);
            }
        }

        return new ParseImportedDataResponse(newList);
    }
}
