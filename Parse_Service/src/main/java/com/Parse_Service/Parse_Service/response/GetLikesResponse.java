package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

public class GetLikesResponse {
    private Integer likes;

    public GetLikesResponse(Integer likes) {
        this.likes = likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getLikes() {
        return likes;
    }
}
