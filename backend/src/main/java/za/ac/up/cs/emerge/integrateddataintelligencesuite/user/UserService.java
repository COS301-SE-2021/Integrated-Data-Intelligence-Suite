package za.ac.up.cs.emerge.integrateddataintelligencesuite.user;

import org.springframework.stereotype.Service;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.request.GetCurrentUserRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.request.RegisterUserRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.request.VerifyAccoundRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response.GetCurrentUserResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response.RegisterUserResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response.VerifyAccountResponse;

@Service
public interface UserService {
    RegisterUserResponse createUser(RegisterUserRequest request);

    GetCurrentUserResponse getCurrentUser(GetCurrentUserRequest request);

    VerifyAccountResponse verifyAccount(VerifyAccoundRequest request);

}
