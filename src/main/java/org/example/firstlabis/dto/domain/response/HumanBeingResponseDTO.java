package org.example.firstlabis.dto.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class HumanBeingResponseDTO{
        @JsonProperty("id")
        Long id;

        @JsonProperty("name")
        String name;

        @JsonProperty("coordinates")
        CoordinatesResponseDTO coordinates;

        @JsonProperty("creation_date")
        LocalDateTime creationDate;

        @JsonProperty("real_hero")
        boolean realHero;

        @JsonProperty("has_toothpick")
        Boolean hasToothpick;

        @JsonProperty("car")
        CarResponseDTO car;

        @JsonProperty("mood")
        Mood mood;

        @JsonProperty("impact_speed")
        int impactSpeed;

        @JsonProperty("soundtrack_name")
        String soundtrackName;

        @JsonProperty("minutes_of_waiting")
        Long minutesOfWaiting;

        @JsonProperty("weapon_type")
        WeaponType weaponType;

        @JsonProperty("edit_admin_status")
        Boolean editAdminStatus;
}
