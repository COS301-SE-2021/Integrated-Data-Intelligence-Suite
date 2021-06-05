package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.NodeData;
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

        JSONArray jsonArray = new JSONArray(request.getJsonString());
        ArrayList<NodeData> newList = new ArrayList<>();

        Mock mocks = new Mock();

        if (request.getType() == DataSource.TWITTER){
            for (int i=0; i < jsonArray.length()-1; i++){
                //create and set node
                NodeData nodeData = new NodeData();
                TwitterParser parser = new TwitterParser();

                //setText
                //GetTextMessageRequest textRequest = new GetTextMessageRequest(theArr[i]);
                //GetTextMessageResponse textResponse = parser.getText(textRequest);
                nodeData.setTextMessage(mocks.getText());

                //setDate
                //GetDateRequest dateRequest = new GetDateRequest(theArr[i]);
                //GetDateResponse dateResponse = parser.getDate(dateRequest);
                nodeData.setDate(mocks.getDate());

                //setDate
                //GetDateRequest dateRequest = new GetDateRequest(theArr[i]);
                //GetDateResponse dateResponse = parser.getDate(dateRequest);
                nodeData.setMention(mocks.getMention());

                newList.add(nodeData);
            }
        }

        return new ParseImportedDataResponse(newList);
    }
}
