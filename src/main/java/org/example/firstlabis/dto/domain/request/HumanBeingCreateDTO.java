package org.example.firstlabis.dto.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.firstlabis.model.domain.Coordinates;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

@Setter
@Getter
@NoArgsConstructor
public class HumanBeingCreateDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "coordinates", required = true)
    private Coordinates coordinates;

    @JsonProperty(value = "real_hero", required = true)
    private boolean realHero;

    @JsonProperty(value = "has_toothpick", required = true)
    private Boolean hasToothpick;

    @JsonProperty(value = "mood", required = true)
    private Mood mood;

    @JsonProperty(value = "impact_speed", required = true)
    private int impactSpeed;

    @JsonProperty(value = "soundtrack_name", required = true)
    private String soundtrackName;

    @JsonProperty(value = "minutes_of_waiting", required = true)
    private Long minutesOfWaiting;

    @JsonProperty(value = "weapon_type", required = true)
    private WeaponType weaponType;

    public HumanBeingCreateDTO(String name, Coordinates coordinates, boolean realHero, Boolean hasToothpick,
                               Mood mood, int impactSpeed, String soundtrackName, Long minutesOfWaiting,
                               WeaponType weaponType) {
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.mood = mood;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isRealHero() {
        return realHero;
    }

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public int getImpactSpeed() {
        return impactSpeed;
    }

    public void setImpactSpeed(int impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public void setSoundtrackName(String soundtrackName) {
        this.soundtrackName = soundtrackName;
    }

    public Long getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public void setMinutesOfWaiting(Long minutesOfWaiting) {
        this.minutesOfWaiting = minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
}

