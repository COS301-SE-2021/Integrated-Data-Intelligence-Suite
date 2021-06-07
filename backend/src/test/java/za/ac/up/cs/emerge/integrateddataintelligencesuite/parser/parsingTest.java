package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
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
    public void testParsingImportedDataNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        Assert.assertThrows(InvalidRequestException.class, ()->parsingServiceTest.parseImportedData(null));
    }

}
