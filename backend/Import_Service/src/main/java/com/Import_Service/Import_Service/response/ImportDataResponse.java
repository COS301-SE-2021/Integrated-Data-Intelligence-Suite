package com.Import_Service.Import_Service.response;

import com.Import_Service.Import_Service.dataclass.ImportedData;

import java.util.ArrayList;

public class ImportDataResponse {
    private ArrayList<ImportedData> list;
    private boolean success;
    private String message;

    public ImportDataResponse() {

    }

    public ImportDataResponse(boolean success, String message, ArrayList<ImportedData> list) {
        this.success = success;
        this.message = message;
        this.list = list;
    }

    public ArrayList<ImportedData> getList() {
        return list;
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
