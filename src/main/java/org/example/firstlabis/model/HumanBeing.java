package org.example.firstlabis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.firstlabis.model.enums.Mood;
import org.example.firstlabis.model.enums.WeaponType;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
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
