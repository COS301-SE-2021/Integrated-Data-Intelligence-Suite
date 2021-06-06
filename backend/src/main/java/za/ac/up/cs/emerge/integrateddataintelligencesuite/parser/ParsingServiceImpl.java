package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.ParsedData;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.mocks.Mock;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri.TwitterParser;

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

        Mock mocks = new Mock();

        if (request.getType() == DataSource.TWITTER){
            for (int i=0; i < jsonArray.length(); i++){
                //create and set node
                //System.out.println(jsonArray.get(i).toString());
                //System.out.println(" _________________________________________________________________________________________");

                ParsedData parsedData = new ParsedData();
                TwitterParser parser = new TwitterParser();

                //setText
                //GetTextMessageRequest textRequest = new GetTextMessageRequest(theArr[i]);
                //GetTextMessageResponse textResponse = parser.getText(textRequest);
                parsedData.setTextMessage(mocks.getText());

                //setDate
                //GetDateRequest dateRequest = new GetDateRequest(theArr[i]);
                //GetDateResponse dateResponse = parser.getDate(dateRequest);
                parsedData.setDate(mocks.getDate());

                newList.add(parsedData);
            }
        }

        return new ParseImportedDataResponse(newList);
    }
}
