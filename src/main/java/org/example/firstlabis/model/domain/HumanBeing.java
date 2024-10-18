package org.example.firstlabis.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "humans")
public class HumanBeing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Coordinates coordinates;

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(nullable = false)
    private boolean realHero;

    @Column(nullable = false)
    private Boolean hasToothpick;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    @Column(nullable = false)
    private int impactSpeed;

    @Column(nullable = false)
    private String soundtrackName;

    private Long minutesOfWaiting;

    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;

}
