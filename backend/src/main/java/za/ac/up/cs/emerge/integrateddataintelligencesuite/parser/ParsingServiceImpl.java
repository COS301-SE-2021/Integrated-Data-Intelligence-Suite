package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.NodeData;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
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

        JSONArray theArr = new JSONArray(request.getJsonString());
        ArrayList<NodeData> newList = new ArrayList<>();
        if (request.getType() == DataSource.TWITTER){
            for (int i=0; i < theArr.length()-1; i++){


            }

        }




        return new ParseImportedDataResponse(newList);

    }
}
