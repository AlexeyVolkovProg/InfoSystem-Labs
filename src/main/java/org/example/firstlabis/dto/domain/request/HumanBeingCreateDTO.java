package org.example.firstlabis.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.firstlabis.model.domain.Coordinates;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;


public record HumanBeingCreateDTO(
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "coordinates", required = true)
        Coordinates coordinates,

        @JsonProperty(value = "real_hero", required = true)
        boolean realHero,

        @JsonProperty(value = "has_toothpick", required = true)
        Boolean hasToothpick,

        @JsonProperty(value = "mood", required = true)
        Mood mood,

        @JsonProperty(value = "impact_speed", required = true)
        int impactSpeed,

        @JsonProperty(value = "soundtrack_name", required = true)
        String soundtrackName,

        @JsonProperty(value = "minutes_of_waiting", required = true)
        Long minutesOfWaiting,

        @JsonProperty(value = "weapon_type", required = true)
        WeaponType weaponType
) {


}
