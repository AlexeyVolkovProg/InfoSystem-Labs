package org.example.firstlabis.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    @NotEmpty
    @Column(nullable = false)
    private int x;

    @NotEmpty
    @Column(nullable = false)
    private Double y;

}
