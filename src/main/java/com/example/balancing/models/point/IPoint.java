package com.example.balancing.models.point;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "points")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public interface IPoint {
}
