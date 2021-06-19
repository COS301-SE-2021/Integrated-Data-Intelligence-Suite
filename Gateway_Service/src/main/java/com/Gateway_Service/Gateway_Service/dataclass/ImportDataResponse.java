package com.Gateway_Service.Gateway_Service.dataclass;

import com.Import_Service.Import_Service.dataclass.ImportedData;

import java.util.ArrayList;

public class ImportDataResponse {
    ArrayList<ImportedData> list;

    public ImportDataResponse() {

    }

    public ImportDataResponse(ArrayList<ImportedData> list) {
        this.list = list;
    }

    public ArrayList<ImportedData> getList() {
        return list;
    }
}
