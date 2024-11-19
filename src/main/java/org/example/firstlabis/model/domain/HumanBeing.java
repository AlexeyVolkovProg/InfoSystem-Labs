package org.example.firstlabis.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.firstlabis.model.audit.TrackEntity;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "humans")
@NoArgsConstructor
@AllArgsConstructor
public class HumanBeing extends TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Embedded
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "real_hero", nullable = false)
    private boolean realHero;

    @NotNull
    @Column(name = "has_toothpick", nullable = false)
    private Boolean hasToothpick;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "car_id", nullable = true)
    private Car car;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Column(name = "impact_speed", nullable = false)
    private int impactSpeed;

    @Column(name = "soundtrack_name", nullable = false)
    private String soundtrackName;

    @Column(name = "minutes_of_waiting", nullable = false)
    private Long minutesOfWaiting;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "weapon_type", nullable = false)
    private WeaponType weaponType;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }

}