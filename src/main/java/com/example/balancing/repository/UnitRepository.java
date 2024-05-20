package com.example.balancing.repository;

import com.example.balancing.models.record.Record;
import com.example.balancing.models.target.Target;
import com.example.balancing.models.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query(value = "select * from units u where u.id = ?1 and u.mode = ?2",
            nativeQuery = true)
    List<Unit> findByIdAndMode(Long id, String mode);

    @Query(value = "select * from units u where u.user_id = ?1",
            nativeQuery = true)
    List<Unit> findByUserId(Long id);

    @Query(value = "select max(id_by_user) from units u where user_id=?1",
            nativeQuery = true)
    Optional<Unit> findMaximumInsideId(Long id);

}
