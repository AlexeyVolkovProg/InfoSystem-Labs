package org.example.firstlabis.service;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.mapper.domain.HumanBeingMapper;
import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.CarRepository;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HumanBeingService {
    private final HumanBeingRepository humanBeingRepository;
    private final CarRepository carRepository;
    private final HumanBeingMapper humanBeingMapper;

    public HumanBeingResponseDTO createHumanBeing(HumanBeingCreateDTO dto) {
        HumanBeing entity = humanBeingMapper.toEntity(dto);
        entity = humanBeingRepository.save(entity);
        return humanBeingMapper.toResponseDto(entity);
    }

    public HumanBeingResponseDTO updateHumanBeing(Long id, HumanBeingUpdateDTO dto) {
        HumanBeing entity = humanBeingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HumanBeing not found with id: " + id));
        humanBeingMapper.updateEntityFromDto(dto, entity);
        entity = humanBeingRepository.save(entity);
        return humanBeingMapper.toResponseDto(entity);
    }

    public void deleteHumanBeing(Long id) {
        if (!humanBeingRepository.existsById(id)) {
            throw new IllegalArgumentException("HumanBeing not found with id: " + id);
        }
        humanBeingRepository.deleteById(id);
    }

    @Transactional
    public void attachTheCar(Long idHumanBeing, Long idCar){
        if (!carRepository.existsById(idCar)) {
            throw new IllegalArgumentException("Car not found with id: + id");
        }else{
            //todo оптимизировать запрос
            HumanBeing humanBeing = humanBeingRepository.findById(idHumanBeing)
                    .orElseThrow(() -> new IllegalArgumentException("HumanBeing not found with id: " + idHumanBeing));
            Car car = carRepository.findById(idCar)
                    .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + idCar));
            humanBeing.setCar(car);
        }
    }


    public HumanBeingResponseDTO findHumanBeingById(Long id) {
        HumanBeing entity = humanBeingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HumanBeing not found with id: " + id));
        return humanBeingMapper.toResponseDto(entity);
    }

    /**
     * Метод ищет все объекты HumanBeing, применяя пагинацию с фильтрами по строковым полям
     */
    public Page<HumanBeingResponseDTO> getAllWithFilters(String name, String soundtrackName, Pageable pageable) {
        if (name != null && soundtrackName != null) {
            return humanBeingRepository.findAllByNameAndSoundtrackName(name, soundtrackName, pageable).
                    map(humanBeingMapper::toResponseDto);
        } else if (name != null) {
            return humanBeingRepository.findAllByName(name, pageable).map(humanBeingMapper::toResponseDto);
        }
        return humanBeingRepository.findAll(pageable).map(humanBeingMapper::toResponseDto);
    }

    /**
     * Метод ищет все объекты HumanBeing, в имени которых есть указанная подстрока
     */
    public Page<HumanBeingResponseDTO> getAllByNameContaining(String substring, Pageable pageable) {
        return humanBeingRepository.findAllByNameContaining(substring, pageable).map(humanBeingMapper::toResponseDto);
    }
}
