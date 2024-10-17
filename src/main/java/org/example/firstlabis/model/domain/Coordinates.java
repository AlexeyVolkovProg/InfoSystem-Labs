package org.example.firstlabis.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinates {

    @Column(name = "x")
    private int x;

    @Column(name = "y", nullable = false)
    private Double y;

}
