package org.example.firstlabis.utils;

import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.model.domain.Coordinates;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

import java.time.LocalDateTime;

public class DateHumanUtil {
    public static HumanBeing createHumanBeing(String name, Coordinates coordinates, boolean realHero,
                                              Boolean hasToothpick, Car car, Mood mood, int impactSpeed,
                                              String soundtrackName, Long minutesOfWaiting, WeaponType weaponType) {
        HumanBeing human = new HumanBeing();
        human.setName(name);
        human.setCoordinates(coordinates);
        human.setCreationDate(LocalDateTime.now());
        human.setRealHero(realHero);
        human.setHasToothpick(hasToothpick);
        human.setCar(car);
        human.setMood(mood);
        human.setImpactSpeed(impactSpeed);
        human.setSoundtrackName(soundtrackName);
        human.setMinutesOfWaiting(minutesOfWaiting);
        human.setWeaponType(weaponType);
        return human;
    }

    public static HumanBeing createDefaultHumanBeingWithName(String name) {
        return createHumanBeing(
                name,
                createDefaultCoordinates(),
                true,
                true,
                createDefaultCar(),
                Mood.APATHY,
                120,
                "Epic Soundtrack",
                15L,
                WeaponType.SHOTGUN
        );
    }

    private static Coordinates createCoordinates(int x, double y) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);
        return coordinates;
    }

    private static Coordinates createDefaultCoordinates() {
        return createCoordinates(50, 100.0);
    }

    private static Car createCar(String name, boolean cool) {
        Car car = new Car();
        car.setName(name);
        car.setCool(cool);
        return car;
    }

    private static Car createDefaultCar() {
        return createCar("Tesla", true);
    }

}
