package com.Import_Service.Import_Service.response;

import com.Import_Service.Import_Service.dataclass.ImportedData;

import java.util.ArrayList;

public class ImportDataResponse {
    private ArrayList<ImportedData> list;

    public ImportDataResponse() {

    }

    public ImportDataResponse(ArrayList<ImportedData> list) {
        this.list = list;
    }

    public ArrayList<ImportedData> getList() {
        return list;
    }
}
