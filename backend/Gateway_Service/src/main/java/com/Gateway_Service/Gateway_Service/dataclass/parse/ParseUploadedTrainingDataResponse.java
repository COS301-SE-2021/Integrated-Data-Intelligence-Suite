package com.Gateway_Service.Gateway_Service.dataclass.parse;

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

    public ArrayList<ParsedTrainingData> getTrainingDataList(){
        return trainingDataList;
    }

    public void setTrainingDataList(ArrayList<ParsedTrainingData> trainingDataList) {
        this.trainingDataList = trainingDataList;
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
