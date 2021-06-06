package za.ac.up.cs.emerge.integrateddataintelligencesuite.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.model.User;

public interface ApiRepository extends JpaRepository<User, Long> {

}
