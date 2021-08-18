package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class ParseServiceTest {
    @Autowired
    private ParseServiceImpl service;

    @Test
    @DisplayName("If_ParseImportedDataRequest_Is_Null")
    public void parseImportedDataNullRequest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.parseImportedData(null));
    }

    @Test
    @DisplayName("If_Request_JSONString_Is_Null")
    public void parseImportedDataJsonStringNull() {
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.TWITTER, null);
        Assertions.assertThrows(InvalidRequestException.class, () -> service.parseImportedData(request));
    }

    @Test
    @DisplayName("If_Request_Type_Is_Null")
    public void parseImportedDataTypeNull() {
        ParseImportedDataRequest request = new ParseImportedDataRequest(null, "");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.parseImportedData(request));
    }
}
