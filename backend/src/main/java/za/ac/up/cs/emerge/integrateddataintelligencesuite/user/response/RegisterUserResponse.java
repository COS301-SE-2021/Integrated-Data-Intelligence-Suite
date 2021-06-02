package za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response;

public class RegisterUserResponse {

    private final long userId;

    public RegisterUserResponse(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "RegisterUserResponse{" +
                "userId=" + userId +
                '}';
    }
}
