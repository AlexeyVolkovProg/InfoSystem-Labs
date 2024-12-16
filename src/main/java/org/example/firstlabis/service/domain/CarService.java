package org.example.firstlabis.service.domain;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.firstlabis.dto.domain.request.CarCreateDTO;
import org.example.firstlabis.dto.domain.request.CarUpdateDTO;
import org.example.firstlabis.dto.domain.response.CarResponseDTO;
import org.example.firstlabis.dto.socket.HumanBeingOperationMessage;
import org.example.firstlabis.dto.socket.dto.HumanBeingSocketMessageDTO;
import org.example.firstlabis.dto.socket.enums.OperationType;
import org.example.firstlabis.mapper.domain.CarMapper;
import org.example.firstlabis.mapper.socket.HumanBeingMessageMapper;
import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.repository.CarRepository;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final CarRepository carRepository;

    private final HumanBeingRepository humanBeingRepository;

    private final HumanBeingMessageMapper humanBeingMessageMapper;

    private final CarMapper carMapper;

    private final SimpMessagingTemplate messagingTemplate;

    private final EntityManager entityManager;


    @Transactional
    public CarResponseDTO create(CarCreateDTO carCreateDTO) {
        Car entity = carMapper.toEntity(carCreateDTO);
        entity = carRepository.save(entity);
        entityManager.flush();
        return carMapper.toResponseDto(entity);
    }

    @Transactional
    public CarResponseDTO updateCar(Long id, CarUpdateDTO dto) {
        Car entity = carRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        carMapper.updateEntityFromDto(dto, entity);
        entity = carRepository.save(entity);
        entityManager.flush();
        Optional<HumanBeing> humanBeing = humanBeingRepository.findOwnerByCarId(entity.getId());
        humanBeing.ifPresent(being -> notifyFrontend(being, OperationType.UPDATE_CAR));
        return carMapper.toResponseDto(entity);
    }

    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    public CarResponseDTO findCarById(Long id) {
        Car entity = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return carMapper.toResponseDto(entity);
    }

    /**
     * Метод ищет все объекты car, применяя пагинацию с фильтрами по строковым полям
     */
    public Page<CarResponseDTO> getAllWithFilters(String name, Pageable pageable) {
        if (name != null) {
            return carRepository.findAllByName(name, pageable).map(carMapper::toResponseDto);
        }
        return carRepository.findAll(pageable).map(carMapper::toResponseDto);
    }

    /**
     * Метод ищет все объекты car, в имени которых есть указанная подстрока
     */
    public Page<CarResponseDTO> getAllByNameContaining(String substring, Pageable pageable) {
        return carRepository.findAllByNameContaining(substring, pageable).map(carMapper::toResponseDto);
    }

    /**
     * Включает разрешение на редактирование сущности со стороны администраторов
     *
     * @param id сущности
     */
    public void enableAdminEdit(Long id) {
        setEditAdminStatus(id, true);
    }

    /**
     * Выключает разрешение на редактирование сущности со стороны администраторов
     *
     * @param id сущности
     */
    public void turnOffAdminEdit(Long id) {
        setEditAdminStatus(id, false);
    }

    private void setEditAdminStatus(Long id, boolean status) {
        Car car = carRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        car.setEditAdminStatus(status);
        carRepository.save(car);
    }

    /**
     * Метод уведомляющий front через веб сокет о создании нового HumanBeing или его обновлении
     */
    private void notifyFrontend(HumanBeing humanBeing, OperationType operationType) {
        HumanBeingSocketMessageDTO socketMessage = humanBeingMessageMapper.toSocketMessage(humanBeing);
        HumanBeingOperationMessage operationMessage = new HumanBeingOperationMessage(operationType, socketMessage);
        String jsonMessage = new Gson().toJson(operationMessage);
        messagingTemplate.convertAndSend("/topic/human-being", jsonMessage);
        log.info("Уведомление для front: " + jsonMessage);
    }

}