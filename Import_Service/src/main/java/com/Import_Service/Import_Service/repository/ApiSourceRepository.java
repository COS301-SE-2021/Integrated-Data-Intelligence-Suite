package com.Import_Service.Import_Service.repository;

import com.Import_Service.Import_Service.dataclass.APISource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface ApiSourceRepository extends JpaRepository<APISource, Long> {

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
    @Query("UPDATE apisource u SET u.parameters = :newParams WHERE u.id = :id")
    int updateParams(@Param("id") Long id, @Param("newParams") Map<String, String> newParams);
}
