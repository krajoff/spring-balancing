package com.example.balancing.repositories.weight;

import com.example.balancing.models.weight.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {

}


