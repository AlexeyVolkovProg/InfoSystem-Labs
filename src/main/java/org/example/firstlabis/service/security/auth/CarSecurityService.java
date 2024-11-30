package org.example.firstlabis.service.security.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.model.domain.Car;
import org.example.firstlabis.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarSecurityService extends TrackEntitySecurityService<Car, Long> {

    private final CarRepository carRepository;

    @Override
    protected Car findById(Long id) {
        return carRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("HumanBeing not found with id: " + id));
    }
}
