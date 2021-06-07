package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri.TwitterExtractor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class parsingTest {
    private TwitterExtractor twitterMock;
    private ParsingServiceImpl parsingServiceTest= new ParsingServiceImpl();

    //@Before
    /*public void setup(){
        parsingServiceTest = new ParsingServiceImpl();
        twitterMock = mock(TwitterExtractor.class);
        parsingServiceTest.t
    }*/

    @Test
    @DisplayName("When the request object is null")
    public void testParsingImportedDataNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        Assert.assertThrows(InvalidRequestException.class, ()->parsingServiceTest.parseImportedData(null));
    }

    @Test
    public void testDataSourceNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        ParseImportedDataRequest request = new ParseImportedDataRequest(null,"{}");
        Assert.assertThrows(InvalidRequestException.class, ()->parsingServiceTest.parseImportedData(request));
    }

    @Test
    public void testDataSourceNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.TWITTER,null);
        Assert.assertThrows(InvalidRequestException.class, ()->parsingServiceTest.parseImportedData(request));
    }

}
