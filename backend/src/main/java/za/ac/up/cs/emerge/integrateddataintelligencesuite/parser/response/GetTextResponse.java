package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

public class GetTextResponse {
    private String text;

    public GetTextResponse(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
