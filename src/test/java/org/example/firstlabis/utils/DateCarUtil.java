package org.example.firstlabis.utils;

import org.example.firstlabis.model.domain.Car;

public class DateCarUtil {

    public static Car createCar(String name, boolean cool) {
        return Car.builder()
                .name(name)
                .cool(cool)
                .build();
    }


    public static Car createDefaultCar() {
        return createCar("Tesla Model S", true);
    }
}
