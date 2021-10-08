package com.Parse_Service.Parse_Service.repository;

import com.Parse_Service.Parse_Service.dataclass.NewsProperties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsPropertiesRepository extends JpaRepository<NewsProperties, Long> {

    @Query("SELECT s FROM news_properties s WHERE s.name = :name")
    Optional<NewsProperties> findNewsPropertiesByName(@Param("name") String name);
}
