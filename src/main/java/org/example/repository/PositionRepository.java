package org.example.repository;

import org.example.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {

//    PositionEntity getById(int id);
    Optional<PositionEntity> getPositionById(int id);
}
