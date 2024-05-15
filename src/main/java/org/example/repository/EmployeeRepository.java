package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query("from EmployeeEntity")
    Page<EmployeeEntity> getAll(Pageable pageable);

    Optional<EmployeeEntity> findById(int id);

    EmployeeEntity getByPhone(String phone);

    @Transactional
    @Modifying
    @Query("update EmployeeEntity e set e.name=:name, e.phone=:phone, e.surname=:surname,e.age=:age," +
            " e.address=:address, e.salary=:salary, e.positionId=:position, e.updatedDate=:updatedDate")
    Integer update(String name, String phone, String surname, Integer age, String address, String salary, Integer position,
                   LocalDateTime updatedDate);
}
