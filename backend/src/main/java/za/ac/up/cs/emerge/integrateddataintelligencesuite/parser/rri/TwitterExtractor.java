package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;
import org.json.JSONObject;

public class TwitterExtractor implements Extractor {

    public GetTextResponse getText(GetTextRequest request) {
        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject dataObj = obj.getJSONObject("statuses");
        String responseText = dataObj.getString("text");
        GetTextResponse response = new GetTextResponse(responseText);
        return response;
    }

    public GetDateResponse getDate(GetDateRequest request) {
        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject dataObj = obj.getJSONObject("statuses");
        String[] dateTimeInfo = dataObj.getString("created_at").split("T");
        String responseDate = dateTimeInfo[0];

        GetDateResponse response = new GetDateResponse(responseDate);
        return response;
    }
}