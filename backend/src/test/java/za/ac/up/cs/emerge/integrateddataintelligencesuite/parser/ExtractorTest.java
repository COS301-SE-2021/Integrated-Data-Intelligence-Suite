package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri.TwitterExtractor;

public class ExtractorTest {
    private TwitterExtractor twitterExtractorTest = new TwitterExtractor();

    @Test
    @DisplayName("When the request object of GetText is null")
    public void testExtractorGetTextNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        Assert.assertThrows(InvalidRequestException.class, ()->twitterExtractorTest.getText(null));
    }
}
