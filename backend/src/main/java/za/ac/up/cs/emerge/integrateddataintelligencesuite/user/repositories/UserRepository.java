package za.ac.up.cs.emerge.integrateddataintelligencesuite.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
