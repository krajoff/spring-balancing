package com.example.balancing.repository;

import com.example.balancing.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query(value = "select * from units u where u.user_id = ?1", nativeQuery = true)
    List<Unit> findByUserId(Long id);

}
