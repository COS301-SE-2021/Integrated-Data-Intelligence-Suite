package za.ac.up.cs.emerge.integrateddataintelligencesuite.Importer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportService;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportServiceImpl;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.ImporterException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidImporterRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;

public class ImporterTest {
    ImportService importService = new ImportServiceImpl();

    @Test
    @DisplayName("When Request object is null")
    void Cancel_Import_When_Request_Is_Null(){
        Assertions.assertThrows(InvalidImporterRequestException.class, () -> importService.importData(null));
    }

    @Test
    @DisplayName("When a keyword is less then expected length")
    void Cancel_Import_When_Request_Object_Has_Empty_String(){
        Assertions.assertThrows(InvalidImporterRequestException.class, () -> importService.importData(new ImportDataRequest("",2)));
    }

    @Test
    @DisplayName("When the number of searches is less than expected")
    void Cancel_Import_when_Request_limit_Is_Less_Then_one(){
        Assertions.assertThrows(InvalidImporterRequestException.class, () -> importService.importData(new ImportDataRequest("keyword", 0)));
    }

    @Test
    @DisplayName("When keyword and limit are valid")
    void Return_Import_Response_when_Request_is_valid(){
        ImportDataRequest request = new ImportDataRequest("keyword", 2);
        ImportDataResponse response = null;

        try {
            response = importService.importData(request);
            Assertions.assertEquals(response.getList().size(), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
