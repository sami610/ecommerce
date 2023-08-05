package org.example.api.repository;

import org.example.api.entities.ScoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoresEntityRepository extends JpaRepository<ScoresEntity, Long> {
    List<ScoresEntity> findByShopperId(@Param("shopperId") long shopperId);
}
