package com.example.balancing.repository;

import com.example.balancing.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {

    @Query(value = "select * from units u where u.user_id = ?1", nativeQuery = true)
    List<Unit> findByUserId(UUID id);

}
