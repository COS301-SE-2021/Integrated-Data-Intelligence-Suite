package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass.ParsedData;

@Repository
public interface ParsedDataRepository extends JpaRepository<ParsedData, Long> {
}
