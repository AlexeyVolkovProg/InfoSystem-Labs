package org.example.firstlabis.model.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.firstlabis.model.audit.TrackEntity;

@Entity
@Setter
@Builder
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
