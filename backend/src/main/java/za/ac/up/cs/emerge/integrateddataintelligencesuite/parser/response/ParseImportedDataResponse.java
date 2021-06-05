package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.NodeData;

import java.util.ArrayList;

public class ParseImportedDataResponse {

    ArrayList<NodeData> dataList;

    public ParseImportedDataResponse(ArrayList<NodeData> dataList){
        this.dataList = dataList;
    }
}
