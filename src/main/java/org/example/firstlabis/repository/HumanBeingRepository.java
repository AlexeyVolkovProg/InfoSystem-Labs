package org.example.firstlabis.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.domain.enums.WeaponType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HumanBeingRepository extends JpaRepository<HumanBeing, Long> {
    Page<HumanBeing> findAllByName(@NonNull String name, @NonNull Pageable pageable);

    Page<HumanBeing> findAllByNameAndSoundtrackName(@NotNull String name, @NotNull String soundtrack,
                                                    @NonNull Pageable pageable);

    Page<HumanBeing> findAllByNameContaining(@NonNull String substring, @NonNull Pageable pageable);

    Optional<HumanBeing> findByName(@NotNull String name);

    List<HumanBeing> findAllByWeaponType(WeaponType weaponType);

    List<HumanBeing> findAllByHasToothpickFalse();

    long countByMinutesOfWaitingLessThan(long maxMinutesOfWaiting);
}
