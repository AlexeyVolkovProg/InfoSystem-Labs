package org.example.firstlabis.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.firstlabis.model.domain.Coordinates;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

public record HumanBeingSocketMessage(
        @JsonProperty(value = "human_id")
        Long human_id ,

        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "coordinates")
        Coordinates coordinates,

        @JsonProperty("real_hero")
        Boolean realHero,

        @JsonProperty("has_toothpick")
        Boolean hasToothpick,

        @JsonProperty("mood")
        Mood mood,

        @JsonProperty("impact_speed")
        Integer impactSpeed,

        @JsonProperty("soundtrack_name")
        String soundtrackName,

        @JsonProperty("minutes_of_waiting")
        Long minutesOfWaiting,

        @JsonProperty("weapon_type")
        WeaponType weaponType,

        @JsonProperty("car")
        CarSocketMessageDTO car
) {

}
