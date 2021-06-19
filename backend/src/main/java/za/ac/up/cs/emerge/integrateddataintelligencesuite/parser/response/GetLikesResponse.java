package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

public class GetLikesResponse {
    private Integer likes;

    public GetLikesResponse(Integer date) {
        this.likes = date;
    }

    public void setLikes(Integer date) {
        this.likes = date;
    }

    public Integer getLikes() {
        return likes;
    }
}
