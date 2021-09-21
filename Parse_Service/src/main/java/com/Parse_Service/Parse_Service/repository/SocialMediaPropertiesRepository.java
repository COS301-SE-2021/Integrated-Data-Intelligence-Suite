package com.Parse_Service.Parse_Service.repository;

import com.Parse_Service.Parse_Service.dataclass.SocialMediaProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialMediaPropertiesRepository extends JpaRepository<SocialMediaProperties, Long> {

    @Query("SELECT s FROM social_media_properties s WHERE s.name = :name")
    Optional<SocialMediaProperties> findSocialMediaPropertiesByName(@Param("name") String name);
}
