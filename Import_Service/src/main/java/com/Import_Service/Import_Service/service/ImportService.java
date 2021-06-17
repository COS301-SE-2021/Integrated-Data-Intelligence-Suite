package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "Import-Service")
public interface ImportService {

    ImportDataResponse importData(ImportDataRequest request) throws Exception;

    ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception ;
}
