package org.example.firstlabis.dto.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

import java.time.LocalDateTime;

public record HumanBeingResponseDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("name")
        String name,

        @JsonProperty("coordinates")
        CoordinatesResponseDTO coordinates,

        @JsonProperty("creation_date")
        LocalDateTime creationDate,

        @JsonProperty("real_hero")
        boolean realHero,

        @JsonProperty("has_toothpick")
        Boolean hasToothpick,

        @JsonProperty("car")
        CarResponseDTO car,

        @JsonProperty("mood")
        Mood mood,

        @JsonProperty("impact_speed")
        int impactSpeed,

        @JsonProperty("soundtrack_name")
        String soundtrackName,

        @JsonProperty("minutes_of_waiting")
        Long minutesOfWaiting,

        @JsonProperty("weapon_type")
        WeaponType weaponType
) {

}
