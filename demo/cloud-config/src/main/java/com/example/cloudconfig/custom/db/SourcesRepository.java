package com.example.cloudconfig.custom.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourcesRepository extends JpaRepository<Sources, String> {
}
