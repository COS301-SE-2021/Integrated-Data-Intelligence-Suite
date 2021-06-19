package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

public interface ImportService {
    ImportTwitterResponse getTwitterDataJsonx(ImportTwitterRequest req) throws Exception;

    ImportDataResponse importDatax(ImportDataRequest request) throws ImporterException;

}
