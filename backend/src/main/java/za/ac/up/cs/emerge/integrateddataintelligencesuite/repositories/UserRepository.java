package za.ac.up.cs.emerge.integrateddataintelligencesuite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.user.user;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<user,Long> {


}
