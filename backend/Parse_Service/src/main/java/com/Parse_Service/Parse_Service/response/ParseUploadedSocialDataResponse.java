package com.Parse_Service.Parse_Service.response;

import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseUploadedSocialDataResponse {
    ArrayList<ParsedData> socialDataList;
    private boolean success;
    private String message;

    public ParseUploadedSocialDataResponse() {

    }

    public ParseUploadedSocialDataResponse(boolean success, String message, ArrayList<ParsedData> dataList) {
        this.success = success;
        this.message = message;
        this.socialDataList = dataList;
    }

    public ArrayList<ParsedData> getDataList(){
        return socialDataList;
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
