package org.example.firstlabis.model.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.firstlabis.model.audit.TrackEntity;

@Entity
@Setter
@Getter
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
public class Car extends TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean cool;
}
