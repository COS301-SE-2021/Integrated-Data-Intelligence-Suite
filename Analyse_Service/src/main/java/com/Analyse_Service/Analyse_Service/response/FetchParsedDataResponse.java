package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class FetchParsedDataResponse {

    ArrayList<ParsedData> parsedDataList;

    public FetchParsedDataResponse(ArrayList<ParsedData> parsedDataList){
        this.parsedDataList = parsedDataList;
    }

    public ArrayList<ParsedData>  getParsedDataList(){
        return this.parsedDataList;
    }
}
