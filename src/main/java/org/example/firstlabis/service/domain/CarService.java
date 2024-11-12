package org.example.firstlabis.service.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.CarCreateDTO;
import org.example.firstlabis.dto.domain.request.CarUpdateDTO;
import org.example.firstlabis.dto.domain.response.CarResponseDTO;
import org.example.firstlabis.mapper.domain.CarMapper;
import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public CarResponseDTO create(CarCreateDTO carCreateDTO) {
        Car entity = carMapper.toEntity(carCreateDTO);
        entity = carRepository.save(entity);
        return carMapper.toResponseDto(entity);
    }

    public CarResponseDTO updateCar(Long id, CarUpdateDTO dto) {
        Car entity = carRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        carMapper.updateEntityFromDto(dto, entity);
        entity = carRepository.save(entity);
        return carMapper.toResponseDto(entity);
    }

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    public CarResponseDTO findCarById(Long id) {
        Car entity = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
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
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id));
        car.setEditAdminStatus(status);
        carRepository.save(car);
    }

}