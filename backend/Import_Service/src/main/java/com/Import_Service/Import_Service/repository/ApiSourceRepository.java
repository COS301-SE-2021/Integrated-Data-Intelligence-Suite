package com.Import_Service.Import_Service.repository;

import com.Import_Service.Import_Service.dataclass.APISource;
import com.Import_Service.Import_Service.rri.AuthorizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface ApiSourceRepository extends JpaRepository<APISource, Long> {

    @Query("SELECT s FROM apisource s WHERE s.name = :name")
    Optional<APISource> findAPISourceByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE apisource u SET u.url = :newUrl WHERE u.id = :id")
    int updateUrl(@Param("id") Long id, @Param("newUrl") String newUrl);

    @Modifying
    @Query("UPDATE apisource u SET u.url = :newName WHERE u.id = :id")
    int updateName(@Param("id") Long id, @Param("newName") String newName);

    @Modifying
    @Query("UPDATE apisource u SET u.method = :newMethod WHERE u.id = :id")
    int updateMethod(@Param("id") Long id, @Param("newMethod") String newMethod);

    @Modifying
    @Query("UPDATE apisource u SET u.authorization = :newAuth WHERE u.id = :id")
    int updateAuth(@Param("id") Long id, @Param("newAuth") String newAuth);

    @Modifying
    @Query("UPDATE apisource u SET u.authType = :newType WHERE u.id = :id")
    int updateType(@Param("id") Long id, @Param("newType") AuthorizationType newType);

    @Modifying
    @Query("UPDATE apisource u SET u.searchKey = :newSearch WHERE u.id = :id")
    int updateSearchKey(@Param("id") Long id, @Param("newSearch") String newSearch);

    @Modifying
    @Query("UPDATE apisource u SET u.parameters = :newParams WHERE u.id = :id")
    int updateParams(@Param("id") Long id, @Param("newParams") Map<String, String> newParams);
}
