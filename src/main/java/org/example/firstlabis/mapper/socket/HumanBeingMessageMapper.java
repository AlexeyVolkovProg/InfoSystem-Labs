package org.example.firstlabis.mapper.socket;

import org.example.firstlabis.dto.socket.dto.CarSocketMessageDTO;
import org.example.firstlabis.dto.socket.dto.HumanBeingSocketMessageDTO;
import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.model.domain.HumanBeing;
import org.springframework.stereotype.Component;

@Component
public class HumanBeingMessageMapper {
    public HumanBeingSocketMessageDTO toSocketMessage(HumanBeing humanBeing) {
        Car car = humanBeing.getCar();
        CarSocketMessageDTO carDto = car != null
                ? new CarSocketMessageDTO(car.getId(), car.getName(), car.isCool(), car.isEditAdminStatus())
                : new CarSocketMessageDTO(null, null, null, null);

        return new HumanBeingSocketMessageDTO(
                humanBeing.getId(),
                humanBeing.getName(),
                humanBeing.getCoordinates(),
                humanBeing.isRealHero(),
                humanBeing.getHasToothpick(),
                humanBeing.getMood(),
                humanBeing.getImpactSpeed(),
                humanBeing.getSoundtrackName(),
                humanBeing.getMinutesOfWaiting(),
                humanBeing.getWeaponType(),
                carDto,
                humanBeing.isEditAdminStatus()
        );
    }
}
