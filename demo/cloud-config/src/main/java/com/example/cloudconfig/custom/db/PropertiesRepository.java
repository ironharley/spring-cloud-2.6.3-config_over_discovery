package com.example.cloudconfig.custom.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Repository
@Validated
public interface PropertiesRepository extends JpaRepository<Properties, Long> {

    @Modifying
    @Query("delete from Properties p where p.id = :id")
    void delete(@Param("id") long  id);
}
