package com.Gateway_Service.Gateway_Service.service;

import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import org.springframework.web.bind.annotation.GetMapping;

public class ImportServiceFallback implements ImportService {
    @Override
    public ImportDataResponse importData(ImportDataRequest request) throws Exception{
        return null;
    }

    @Override
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception {
        return null;
    }
}
