package za.ac.up.cs.emerge.integrateddataintelligencesuite.Importer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportService;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportServiceImpl;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidImporterRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidTwitterRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportTwitterResponse;

public class getTwitterDataTest {

    ImportService importService = new ImportServiceImpl();

    @Test
    @DisplayName("When a null request is sent")
    void Cancel_Request_If_Request_Is_Null(){
        ImportTwitterRequest request = null;
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> importService.getTwitterDataJson(request));
    }

    @Test
    @DisplayName("When the keyword in the Request is less than expected length (2)")
    void Cancel_Request_When_String_Is_Empty(){
        ImportTwitterRequest request = new ImportTwitterRequest("");
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> importService.getTwitterDataJson(request));
    }

    @Test
    @DisplayName("When the keyword in the request is longer then expected length (250)")
    void Cancel_Request_When_String_is_Too_Large(){
        ImportTwitterRequest request = new ImportTwitterRequest("Another way I like driving my sister crazy is " +
                "by speaking my own made up language to her. She loves the languages I make! The only language that we " +
                "both speak besides English is Pig Latin. I think you already knew that. Whatever. I think I'm gonna go " +
                "for now. Bye! Hi, I'm back now. I'm gonna contribute more to this soon-to-be giant wall of text.");
        Assertions.assertThrows(InvalidTwitterRequestException.class, () -> importService.getTwitterDataJson(request));
    }

    @Test
    @DisplayName("When the keyword in the request object is valid")
    void Return_Import_Twitter_Response_When_String_Is_Valid(){
        ImportTwitterRequest request = new ImportTwitterRequest("positive");
        ImportTwitterResponse response = null;
        try {
            response = importService.getTwitterDataJson(request);
            Assertions.assertNotEquals(response.getJsonData(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("When the keyword has a space")
    void Return_Import_Twitter_Response_when_String_has_spaces(){
        ImportTwitterRequest request = new ImportTwitterRequest("two words");
        ImportTwitterResponse response = null;
        try {
            response = importService.getTwitterDataJson(request);
            Assertions.assertNotEquals(response.getJsonData(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("When the keyword is long, but not too long")
    void Return_Import_Twitter_Response_When_String_Is_Not_Too_Large(){
        ImportTwitterRequest request = new ImportTwitterRequest("String that is not too long string");
        ImportTwitterResponse response = null;
        try {
            response = importService.getTwitterDataJson(request);
            Assertions.assertNotEquals(response.getJsonData(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
