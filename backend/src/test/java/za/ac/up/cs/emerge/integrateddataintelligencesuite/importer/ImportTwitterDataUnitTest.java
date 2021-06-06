package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;

@ExtendWith(MockitoExtension.class)
public class ImportTwitterDataUnitTest {

    @InjectMocks
    private ImportServiceImpl importService;

    @Test
    public void displayTweets (){

        importService = new ImportServiceImpl();

        try {
            System.out.println(importService.getTwitterDataJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
