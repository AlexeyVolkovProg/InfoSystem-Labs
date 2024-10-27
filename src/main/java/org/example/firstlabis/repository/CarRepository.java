package org.example.firstlabis.repository;

import lombok.NonNull;
import org.example.firstlabis.model.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAllByName(@NonNull String name, @NonNull Pageable pageable);

    Page<Car> findAllByNameContaining(@NonNull String substring, @NonNull Pageable pageable);
}
