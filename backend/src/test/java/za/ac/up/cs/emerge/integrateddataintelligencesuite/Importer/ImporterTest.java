package za.ac.up.cs.emerge.integrateddataintelligencesuite.Importer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportService;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportServiceImpl;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.ImporterException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidImporterRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;

public class ImporterTest {
    ImportService importService = new ImportServiceImpl();

    @Test
    @DisplayName("When Request object is null")
    void CancelImportWhenRequestIsNull(){
        Assertions.assertThrows(InvalidImporterRequestException.class, () -> importService.importData(null));
    }

    @Test
    void CancelImportWhenRequestObjectHasEmptyString(){
        Assertions.assertThrows(InvalidImporterRequestException.class, () -> importService.importData(new ImportDataRequest("",2)));
    }

    @Test
    void CancelImportwhenRequestlimitIsLessThen1(){
        Assertions.assertThrows(InvalidImporterRequestException.class, () -> importService.importData(new ImportDataRequest("keyword", 0)));
    }


}
