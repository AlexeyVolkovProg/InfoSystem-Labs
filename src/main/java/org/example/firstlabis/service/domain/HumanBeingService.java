package org.example.firstlabis.service.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.mapper.domain.HumanBeingMapper;
import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.domain.enums.Mood;
import org.example.firstlabis.model.domain.enums.WeaponType;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.CarRepository;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.example.firstlabis.service.security.HumanBeingSecurityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HumanBeingService {
    private final HumanBeingRepository humanBeingRepository;
    private final CarRepository carRepository;
    private final HumanBeingMapper humanBeingMapper;
    private final HumanBeingSecurityService humanBeingSecurityService;

    public HumanBeingResponseDTO createHumanBeing(HumanBeingCreateDTO dto) {
        if (humanBeingRepository.findByName(dto.name()).isPresent()){
            throw new EntityNotFoundException("HumanBeing already exists with name " + dto.name());
        }
        HumanBeing entity = humanBeingMapper.toEntity(dto);
        entity = humanBeingRepository.save(entity);
        return humanBeingMapper.toResponseDto(entity);
    }

    public HumanBeingResponseDTO updateHumanBeing(Long id, HumanBeingUpdateDTO dto) {
        HumanBeing entity = humanBeingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HumanBeing not found with id: " + id));
        humanBeingMapper.updateEntityFromDto(dto, entity);
        entity = humanBeingRepository.save(entity);
        return humanBeingMapper.toResponseDto(entity);
    }

    public void deleteHumanBeing(Long id) {
        if (!humanBeingRepository.existsById(id)) {
            throw new EntityNotFoundException("HumanBeing not found with id: " + id);
        }
        humanBeingRepository.deleteById(id);
    }

    @Transactional
    public void attachTheCar(Long idHumanBeing, Long idCar){
        if (!carRepository.existsById(idCar)) {
            throw new EntityNotFoundException("Car not found with id: + id");
        }else{
            //todo оптимизировать запрос
            HumanBeing humanBeing = humanBeingRepository.findById(idHumanBeing)
                    .orElseThrow(() -> new EntityNotFoundException("HumanBeing not found with id: " + idHumanBeing));
            Car car = carRepository.findById(idCar)
                    .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + idCar));
            humanBeing.setCar(car);
        }
    }


    public HumanBeingResponseDTO findHumanBeingById(Long id) {
        HumanBeing entity = humanBeingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HumanBeing not found with id: " + id));
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

    /**
     * Включает разрешение на редактирование сущности со стороны администраторов
     * @param id сущности
     */
    public void enableAdminEdit(Long id){
        setEditAdminStatus(id, true);
    }

    /**
     * Выключает разрешение на редактирование сущности со стороны администраторов
     * @param id сущности
     */
    public void turnOffAdminEdit(Long id){
        setEditAdminStatus(id, false);
    }

    private void setEditAdminStatus(Long id, boolean status){
        HumanBeing humanBeing = humanBeingRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("HumanBeing not found with id: " + id));
        humanBeing.setEditAdminStatus(status);
        humanBeingRepository.save(humanBeing);
    }

    /**
     * Удаляем всех human с определенным типом оружия, с проверкой прав на редактирование
     * @param weaponType тип оружия
     */
    @Transactional
    public void deleteAllByWeaponType(WeaponType weaponType){
        humanBeingRepository.findAllByWeaponType(weaponType).forEach(humanBeing -> {
            if (humanBeingSecurityService.hasEditRights(humanBeing.getId())){
                humanBeingRepository.delete(humanBeing);
            }
        });
    }

    /**
     * Удаляем всех human без зубочисток, с проверкой прав на редактирование
     */
    @Transactional
    public void deleteAllWithOutToothpicks(){
        humanBeingRepository.findAllByHasToothpickFalse().forEach(humanBeing -> {
            if (humanBeingSecurityService.hasEditRights(humanBeing.getId())){
                humanBeingRepository.delete(humanBeing);
            }
        });
    }


    /**
     * Выставляем всем human грустное настроение, с проверкой на редактирование
     */
    @Transactional
    public void setAllMoodToSorrow(){
        humanBeingRepository.findAll().forEach(humanBeing -> {
            if (humanBeingSecurityService.hasEditRights(humanBeing.getId())){
                humanBeing.setMood(Mood.SORROW);
                humanBeingRepository.save(humanBeing);
            }
        });
    }

    /**
     * Возвращает кол-во Human, у которых поле MinutesOfWaiting меньше заданного
     */
    public long countByMinutesOfWaitingLessThan(Long maxMinutesOfWaiting){
        return humanBeingRepository.countByMinutesOfWaitingLessThan(maxMinutesOfWaiting);
    }

    /**
     * Возвращает множество уникальных значений поля impactSpeed
     */
    public Set<Integer> getUniqueImpactSpeeds(){
        return humanBeingRepository.findAll()
                .stream()
                .map(HumanBeing::getImpactSpeed)
                .collect(Collectors.toSet());
    }



}
