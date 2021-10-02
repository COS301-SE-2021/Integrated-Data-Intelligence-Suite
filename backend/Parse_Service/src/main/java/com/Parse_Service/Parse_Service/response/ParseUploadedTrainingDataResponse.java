package com.Parse_Service.Parse_Service.response;

import com.Parse_Service.Parse_Service.dataclass.ParsedTrainingData;
import java.util.ArrayList;

public class ParseUploadedTrainingDataResponse {
    ArrayList<ParsedTrainingData> trainingDataList;
    private boolean success;
    private String message;

    public ParseUploadedTrainingDataResponse() {

    }

    public ParseUploadedTrainingDataResponse(boolean success, String message, ArrayList<ParsedTrainingData> dataList) {
        this.success = success;
        this.message = message;
        this.trainingDataList = dataList;
    }

    public ArrayList<ParsedTrainingData> getSocialDataList(){
        return trainingDataList;
    }

    public void setSocialDataList(ArrayList<ParsedTrainingData> socialDataList) {
        this.trainingDataList = socialDataList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
