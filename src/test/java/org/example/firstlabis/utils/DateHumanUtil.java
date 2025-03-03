package org.example.firstlabis.utils;

import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.model.domain.Coordinates;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;

public class DateHumanUtil {

    public static HumanBeing generateDefaultHumanBeingWithName(String name) {
        return HumanBeing.builder()
                .name(name)
                .coordinates(createDefaultCoordinates())
                .realHero(true)
                .hasToothpick(true)
                .car(createDefaultCar())
                .mood(Mood.RAGE)
                .impactSpeed(120)
                .soundtrackName("Epic Soundtrack")
                .minutesOfWaiting(15L)
                .weaponType(WeaponType.SHOTGUN)
                .build();
    }

    private static Coordinates generateCoordinates(int x, double y) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);
        return coordinates;
    }

    private static Car generateCar(String name, boolean cool) {
        Car car = new Car();
        car.setName(name);
        car.setCool(cool);
        return car;
    }

    private static Coordinates createDefaultCoordinates() {
        return generateCoordinates(50, 100.0);
    }


    private static Car createDefaultCar() {
        return generateCar("Tesla", true);
    }

}
