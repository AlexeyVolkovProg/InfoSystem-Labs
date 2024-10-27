package org.example.firstlabis.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.example.firstlabis.model.domain.HumanBeing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanBeingRepository extends JpaRepository<HumanBeing, Long> {
    Page<HumanBeing> findAllByName(@NonNull String name, @NonNull Pageable pageable);

    Page<HumanBeing> findAllByNameAndSoundtrackName(@NotNull String name, @NotNull String soundtrack,
                                                    @NonNull Pageable pageable);
    Page<HumanBeing> findAllByNameContaining(@NonNull String substring, @NonNull Pageable pageable);
}
