package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions;

public class ParserException extends Exception {
    public ParserException(String message){
        super(message);
    }

    public ParserException(Throwable cause){
        super(cause);
    }

    public ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
