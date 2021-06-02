package za.ac.up.cs.emerge.integrateddataintelligencesuite.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.repositories.UserRepository;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.request.GetCurrentUserRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.request.RegisterUserRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.request.VerifyAccoundRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response.GetCurrentUserResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response.RegisterUserResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.response.VerifyAccountResponse;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public RegisterUserResponse createUser(RegisterUserRequest request){

        user u = new user("wandile", "makhubele", Permissions.USER);
        userRepository.save(u);
        return new RegisterUserResponse(u.getId());
    }

    public GetCurrentUserResponse getCurrentUser(GetCurrentUserRequest request){
        return null;
    }

    public VerifyAccountResponse verifyAccount(VerifyAccoundRequest request){
        return null;
    }


}
