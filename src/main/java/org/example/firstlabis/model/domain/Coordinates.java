package org.example.firstlabis.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Coordinates {

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private Double y;

}
