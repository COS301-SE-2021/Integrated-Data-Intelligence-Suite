package za.ac.up.cs.emerge.integrateddataintelligencesuite.Importer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportService;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportServiceImpl;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidImporterRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidTwitterRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;

public class getTwitterDataTest {

    ImportService importService = new ImportServiceImpl();

    @Test
    void CancelRequestIfRequestIsNull(){
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> importService.getTwitterDataJson(null));
    }

    @Test
    void CancelRequestWhenStringIsEmpty(){
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> importService.getTwitterDataJson(new ImportTwitterRequest("")));
    }


}
