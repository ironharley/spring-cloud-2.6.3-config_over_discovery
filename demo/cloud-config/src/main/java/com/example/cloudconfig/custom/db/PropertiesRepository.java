package com.example.cloudconfig.custom.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Repository
@Validated
public interface PropertiesRepository extends JpaRepository<Properties, Long> {

    default Properties saveWithTime(@NotNull Properties p) {
       // p.setLastAccess(Instant.now());
        return this.save(p);
    }
}
