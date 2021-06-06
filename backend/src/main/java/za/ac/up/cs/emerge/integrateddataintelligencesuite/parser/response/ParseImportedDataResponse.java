package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseImportedDataResponse {

    ArrayList<ParsedData> dataList;

    public ParseImportedDataResponse(ArrayList<ParsedData> dataList){
        this.dataList = dataList;
    }
}
