package org.example.firstlabis.dto.socket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.firstlabis.model.domain.Coordinates;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

public record HumanBeingSocketMessageDTO(
        @JsonProperty(value = "id")
        Long id,

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
        CarSocketMessageDTO car,

        @JsonProperty("edit_admin_status")
        Boolean editAdminStatus
) {

}
